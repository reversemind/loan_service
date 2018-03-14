package com.company.model.adapter;

import com.company.loan.Adapter;
import com.company.model.dto.ApplyLoanDTO;
import com.company.model.Person;

/**
 *
 */
public class PersonAdapter implements Adapter<Person> {

    private Person person;

    public PersonAdapter(ApplyLoanDTO applyLoanDTO) {
        if (applyLoanDTO == null) throw new IllegalArgumentException("apply loan dto is empty");
        this.person = Person
                .builder()
                .id(applyLoanDTO.getPersonId())
                .name(applyLoanDTO.getName())
                .surname(applyLoanDTO.getSurname())
                .id(applyLoanDTO.getPersonId())
                .build();
    }

    @Override
    public Person adaptee() {
        return this.person;
    }
}
