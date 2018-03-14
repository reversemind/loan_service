package com.company.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

import static com.company.Constants.DATE_TIME_FORMAT_ISO;
import static com.company.Constants.PERSON_ID_KEY_NAME_JSON;

/**
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplyLoanDTO implements Serializable {

    @JsonProperty(value = PERSON_ID_KEY_NAME_JSON)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private Long personId;

    private Double amount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT_ISO)
    private Date term;

    private String name;
    private String surname;
}
