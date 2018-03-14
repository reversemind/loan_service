package com.company.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Person Entity
 */
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "loans")
@JsonIgnoreProperties(value = {"loans"})
@Table(name = "ln_person", uniqueConstraints = @UniqueConstraint(name = "ln_pk_person", columnNames = {"id"}))
public class Person implements Serializable {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 128)
    private String name;

    @Column(name = "last_name", nullable = false, length = 128)
    private String surname;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Loan> loans;

    /**
     * Add loan to Person
     *
     * @param loan -
     * @return - this
     */
    public synchronized Person add(Loan loan) {
        if (loan == null) return this;

        if (this.loans == null) {
            this.loans = new HashSet<>();
        }
        this.loans.add(loan);
        return this;
    }
}
