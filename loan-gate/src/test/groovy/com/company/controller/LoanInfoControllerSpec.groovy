package com.company.controller

import com.company.config.WebMvcConfig
import com.company.error.ExceptionHandlingController
import com.company.model.Loan
import com.company.model.Person
import com.company.service.LoanService
import org.springframework.http.MediaType
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.method.HandlerMethod
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod
import spock.lang.Specification
import spock.lang.Subject

import java.lang.reflect.Method

import static com.company.Constants.*
import static org.hamcrest.Matchers.is
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

/**
 * Loan Info Controller Spec
 */
class LoanInfoControllerSpec extends Specification {

    def loanService = Mock(LoanService)

    def
    @Subject
    controller = new LoanInfoController(loanService: loanService)

    def mockMvc = MockMvcBuilders
            .standaloneSetup(controller)
            .setHandlerExceptionResolvers(defineExceptionResolver())
            .build()

    def 'get all approved loans / empty'() {
        when: 'approved loans'

        def result = mockMvc.perform(get(ROOT_PATH + "/" + VERSION + "/loan" + "/status/approved"))

        then:
        1 * loanService.getLoansByStatus(_) >> Collections.EMPTY_SET
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().string("[]"))
                .andReturn()
    }

    def 'get all approved loans / non empty'() {

        setup: 'initialize'
        def PERSON = Person
                .builder()
                .name("firstName")
                .surname("lastName")
                .build()

        when: 'approved loans'
        def result = mockMvc.perform(get(ROOT_PATH + "/" + VERSION + "/loan" + "/status/approved"))

        then:
        1 * loanService.getLoansByStatus(_) >> [
                Loan
                        .builder()
                        .person(PERSON)
                        .amount(321.0)
                        .countryCode("lv")
                        .status(Loan.Status.APPROVED)
                        .term(new Date(0L))
                        .build(),
                Loan
                        .builder()
                        .person(PERSON)
                        .amount(123.0)
                        .countryCode("eu")
                        .status(Loan.Status.APPROVED)
                        .term(new Date(1L))
                        .build()
        ]

        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("\$[0].amount", is(321.0d)))
                .andExpect(jsonPath("\$[0].status", is("APPROVED")))
                .andExpect(jsonPath("\$[1].amount", is(123.0d)))
                .andExpect(jsonPath("\$[1].status", is("APPROVED")))
                .andExpect(jsonPath("\$[0].person.name", is("firstName")))
                .andExpect(jsonPath("\$[0].person.surname", is("lastName")))

                .andReturn()
    }

    def 'get all approved loans by person id / empty'() {
        when: 'approved loans by person id'

        def result = mockMvc.perform(get(ROOT_PATH + "/" + VERSION + "/loan" + "/status/approved/" + PERSON_ID_KEY_NAME + "/1"))

        then:
        1 * loanService.getLoansByUserIdAndByStatus(_, _) >> Collections.EMPTY_SET
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().string("[]"))
                .andReturn()
    }

    def 'get all approved loans by person id / wrong person id'() {
        when: 'approved loans by person id'

        def result = mockMvc.perform(get(ROOT_PATH + "/" + VERSION + "/loan" + "/status/approved/" + PERSON_ID_KEY_NAME + "/WRONG_NUMBER"))

        then:
        result.andDo(print())
                .andExpect(status().isNotAcceptable())
                .andReturn()
    }

    def 'get all approved loans by person id / wrong person id is empty'() {
        when: 'approved loans by person id'

        def result = mockMvc.perform(get(ROOT_PATH + "/" + VERSION + "/loan" + "/status/approved/" + PERSON_ID_KEY_NAME + "/ "))

        then:
        result.andDo(print())
                .andExpect(status().isNotAcceptable())
                .andReturn()
    }

    def 'get all approved loans by person id / OK'() {
        setup: 'initialize'
        def PERSON = Person
                .builder()
                .name("firstName")
                .surname("lastName")
                .build()

        when: 'approved loans by person id'

        def result = mockMvc.perform(get(ROOT_PATH + "/" + VERSION + "/loan" + "/status/approved/" + PERSON_ID_KEY_NAME + "/1"))

        then:
        1 * loanService.getLoansByUserIdAndByStatus(_, _) >> [
                Loan
                        .builder()
                        .person(PERSON)
                        .amount(321.0)
                        .countryCode("lv")
                        .status(Loan.Status.APPROVED)
                        .term(new Date(0L))
                        .build(),
                Loan
                        .builder()
                        .person(PERSON)
                        .amount(123.0)
                        .countryCode("eu")
                        .status(Loan.Status.APPROVED)
                        .term(new Date(1L))
                        .build()
        ]

        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("\$[0].amount", is(321.0d)))
                .andExpect(jsonPath("\$[0].status", is("APPROVED")))
                .andExpect(jsonPath("\$[1].amount", is(123.0d)))
                .andExpect(jsonPath("\$[1].status", is("APPROVED")))
                .andReturn()
    }

    public static ExceptionHandlerExceptionResolver defineExceptionResolver() {

        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setObjectMapper(new WebMvcConfig().mapperBuilder().build());

        ExceptionHandlerExceptionResolver exceptionResolver = new ExceptionHandlerExceptionResolver() {
            protected ServletInvocableHandlerMethod getExceptionHandlerMethod(HandlerMethod handlerMethod, Exception exception) {
                Method method = new ExceptionHandlerMethodResolver(ExceptionHandlingController.class)
                        .resolveMethod(exception);
                return new ServletInvocableHandlerMethod(new ExceptionHandlingController(), method);
            }
        };

        exceptionResolver.getMessageConverters().add(messageConverter);
        exceptionResolver.afterPropertiesSet();
        return exceptionResolver;
    }

}
