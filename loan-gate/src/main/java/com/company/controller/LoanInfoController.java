package com.company.controller;

import com.company.api.ILoanService;
import com.company.model.Loan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.company.Constants.*;

/**
 *
 */
@Slf4j
@Controller
@RequestMapping(ROOT_PATH + "/" + VERSION + "/loan")
public class LoanInfoController {

    @Autowired
    @Qualifier("loanService")
    ILoanService loanService;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/status/approved", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public
    @ResponseBody
    Set<Loan> getApprovedLoans() {

        Set<Loan> loans = loanService.getLoansByStatus(Loan.Status.APPROVED);
        log.debug("all approved loans:{}", loans);

        return loans;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/status/approved/" + PERSON_ID_KEY_NAME + "/{personId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public
    @ResponseBody
    Set<Loan> getApprovedLoansByPersonId(@PathVariable("personId") Long personId) {
        log.debug("personId:{}", personId);

        if (personId == null) throw new IllegalArgumentException();

        Set<Loan> loans = loanService.getLoansByUserIdAndByStatus(personId, Loan.Status.APPROVED);
        log.debug("approved loans:{}", loans);

        return loans;
    }

}
