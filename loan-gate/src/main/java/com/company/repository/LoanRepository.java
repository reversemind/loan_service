package com.company.repository;

import com.company.model.Loan;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 *
 */
@Repository
public interface LoanRepository extends CrudRepository<Loan, Long> {

    Set<Loan> findByStatus(Loan.Status status);

    @Query("select L from Loan L where L.person.id = :personId and L.status = :status")
    Set<Loan> findByPersonIdAndByLoanStatus(@Param("personId") Long personId, @Param("status") Loan.Status status);

}
