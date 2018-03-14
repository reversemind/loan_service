package com.company.config;

import com.company.model.Loan;
import com.company.model.Person;
import com.company.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Date;

/**
 *
 */
@Slf4j
@Configuration
public class InitializeDataBaseConfig {

    /**
     * Initialize DB before test and dev run
     *
     * @param personRepository -
     * @return -
     */
    @Bean
    @Profile(value = {"dev", "test"})
    public CommandLineRunner initializeDataBase(PersonRepository personRepository) {
        return (args) -> {

            Person person = Person
                    .builder()
                    .id(1L)
                    .name("firstName")
                    .surname("lastName")
                    .build();

            person.add(Loan
                    .builder()
                    .person(person)
                    .amount(123.0)
                    .countryCode("lv")
                    .status(Loan.Status.PROCESSING)
                    .term(new Date())
                    .build()
            );

            person.add(Loan
                    .builder()
                    .person(person)
                    .amount(321.0)
                    .countryCode("eu")
                    .status(Loan.Status.PROCESSING)
                    .term(new Date(System.currentTimeMillis() + 500000L))
                    .build()
            );

            person.add(Loan
                    .builder()
                    .person(person)
                    .amount(123321.0)
                    .countryCode("lv")
                    .status(Loan.Status.APPROVED)
                    .term(new Date(System.currentTimeMillis() + 1500000L))
                    .build()
            );

            person.add(Loan
                    .builder()
                    .person(person)
                    .amount(100000.0)
                    .countryCode("lv")
                    .status(Loan.Status.APPROVED)
                    .term(new Date(System.currentTimeMillis() + 2500000L))
                    .build()
            );

            log.debug("person to save:{}", person);
            personRepository.save(person);
        };
    }

}
