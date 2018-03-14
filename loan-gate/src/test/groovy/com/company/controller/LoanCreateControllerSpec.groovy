package com.company.controller

import com.company.Constants
import com.company.config.WebMvcConfig
import com.company.model.dto.ApplyLoanDTO
import com.company.model.Loan
import com.company.model.Person
import com.company.service.CountryNameLookupService
import com.company.service.LoanService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification
import spock.lang.Subject

import static com.company.Constants.*
import static org.hamcrest.Matchers.is
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
/**
 * Loan Create Controller
 */
class LoanCreateControllerSpec extends Specification {

    def loanService = Mock(LoanService)
    def countryNameLookupService = Mock(CountryNameLookupService)

    def
    @Subject
            controller = new LoanCreateController(loanService: loanService, countryNameLookupService: countryNameLookupService)

    def mockMvc = MockMvcBuilders
            .standaloneSetup(controller)
            .setHandlerExceptionResolvers(LoanInfoControllerSpec.defineExceptionResolver())
            .build()

    ObjectMapper MAPPER = new WebMvcConfig().mapperBuilder().build()

    def 'create new loan'() {
        setup: 'initialize'
        countryNameLookupService.getByIp(_) >> Constants.DEFAULT_COUNTRY

        def PERSON = Person
                .builder()
                .id(1L)
                .name("firstName")
                .surname("lastName")
                .build()
        def date = new Date()

        when: 'create new loan'

        def result = mockMvc.perform(
                post(ROOT_PATH + "/" + VERSION + "/loan")
                .content(MAPPER.writeValueAsString(
                        ApplyLoanDTO
                                .builder()
                                .personId(1L)
                                .amount(10.0)
                                .term(date)
                                .name("firstName")
                                .surname("lastName")
                                .build())


        ).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        )

        then:
        1 * loanService.createNewLoan(_, _) >> Loan
                .builder()
                .id(1L)
                .amount(10.0)
                .person(PERSON)
                .countryCode(DEFAULT_COUNTRY)
                .term(date)
                .status(Loan.Status.PROCESSING)
                .build()

        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("\$.countryCode", is("lv")))
                .andExpect(jsonPath("\$.person.name", is("firstName")))
                .andExpect(jsonPath("\$.person.surname", is("lastName")))

                .andReturn()
    }

}
