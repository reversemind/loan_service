package com.company.api;

import com.company.model.Loan;
import com.company.model.Person;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 *
 */
public interface ILoanService {

    Set<Loan> getLoansByStatus(Loan.Status loanStatus);

    Set<Loan> getLoansByUserIdAndByStatus(Long id, Loan.Status loanStatus);

    Loan createNewLoan(Person person, Loan loan);

}
