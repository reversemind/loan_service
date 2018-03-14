package com.company.model.adapter;

import com.company.loan.Adapter;
import com.company.model.dto.ApplyLoanDTO;
import com.company.model.Loan;

/**
 *
 */
public class LoanAdapter implements Adapter<Loan> {

    private Loan loan;

    public LoanAdapter(ApplyLoanDTO applyLoanDTO) {
        if (applyLoanDTO == null) throw new IllegalArgumentException("apply loan dto is empty");
        this.loan = Loan
                .builder()
                .amount(applyLoanDTO.getAmount())
                .term(applyLoanDTO.getTerm())
                .status(Loan.Status.PROCESSING)
                .build();
    }

    @Override
    public Loan adaptee() {
        return this.loan;
    }
}
