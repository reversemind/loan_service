package com.company.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static com.company.Constants.DATE_TIME_FORMAT_ISO;

/**
 * Loan Entity
 */
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"person"})
@EqualsAndHashCode
@Table(name = "ln_loan", uniqueConstraints = {
        @UniqueConstraint(name = "ln_pk_loan", columnNames = {"id"}),
        @UniqueConstraint(columnNames = {"amount", "created_at", "country_code", "person_id"})
}
)
public class Loan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @Column(name = "created_at", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT_ISO)
    private Date term;

    @Column(name = "country_code", nullable = false)
    private String countryCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "loan_status", nullable = false)
    private Status status;

    public Loan setCountryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public enum Status {
        APPROVED,
        DECLINED,
        PROCESSING
    }
}
