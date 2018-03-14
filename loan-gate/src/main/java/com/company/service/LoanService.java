package com.company.service;

import com.company.api.ILoanService;
import com.company.model.Loan;
import com.company.model.Person;
import com.company.repository.LoanRepository;
import com.company.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 *
 */
@Slf4j
@Service("loanService")
public class LoanService implements ILoanService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    LoanRepository loanRepository;

    @Override
    @Transactional
    public Set<Loan> getLoansByStatus(Loan.Status loanStatus) {
        log.debug("loanStatus:{}", loanStatus);

        Set<Loan> loans = loanRepository.findByStatus(loanStatus);
        log.debug("loans by status:{}", loans);

        return loans;
    }

    @Override
    @Transactional
    public Set<Loan> getLoansByUserIdAndByStatus(Long id, Loan.Status loanStatus) {
        log.debug("person id:{} loanStatus:{}", id, loanStatus);

        Set<Loan> loans = loanRepository.findByPersonIdAndByLoanStatus(id, loanStatus);
        log.debug("loans:{}", loans);

        return loans;
    }

    @Override
    @Transactional
    public Loan createNewLoan(Person person, Loan loan) {
        log.debug("going to create new loan:{} for person:{}", loan, person);

        if (person == null) throw new IllegalArgumentException("Person is empty");
        if (loan == null) throw new IllegalArgumentException("Loan is empty");
        if (person.getId() == null) throw new IllegalArgumentException("Person id is not specified");

        person.add(loan);
        loan.setPerson(person);

        personRepository.save(person);
        return loan;
    }

}
