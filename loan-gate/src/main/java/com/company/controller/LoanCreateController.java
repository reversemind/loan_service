package com.company.controller;

import com.company.model.adapter.LoanAdapter;
import com.company.model.adapter.PersonAdapter;
import com.company.api.ILoanService;
import com.company.model.dto.ApplyLoanDTO;
import com.company.model.Loan;
import com.company.service.CountryNameLookupService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.company.Constants.ROOT_PATH;
import static com.company.Constants.VERSION;

/**
 *
 */
@Slf4j
@Controller
public class LoanCreateController {

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    @Qualifier("loanService")
    ILoanService loanService;

    @Autowired
    CountryNameLookupService countryNameLookupService;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(
            value = ROOT_PATH + "/" + VERSION + "/loan",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public
    @ResponseBody
    Loan applyForLoan(@RequestBody ApplyLoanDTO loanDTO) {
        log.debug("loanDto:{}", loanDTO);

        String countryCode = countryNameLookupService.getByIp(httpServletRequest);
        log.debug("detected country code:{}", countryCode);

        Loan loan = loanService.createNewLoan(
                new PersonAdapter(loanDTO).adaptee(),
                new LoanAdapter(loanDTO).adaptee().setCountryCode(countryCode)
        );

        log.debug("saved loan:{}", loan);
        return loan;
    }


}
