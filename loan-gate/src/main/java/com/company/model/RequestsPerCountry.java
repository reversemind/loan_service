package com.company.model;

import lombok.*;

import java.util.concurrent.atomic.AtomicLong;

/**
 *
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"hitsPerPeriod", "period", "startedTimeStamp"})
public class RequestsPerCountry {

    private Long period;
    private String countryCode;
    private AtomicLong hitsPerPeriod = new AtomicLong(0L);
    private AtomicLong startedTimeStamp = new AtomicLong(System.currentTimeMillis());

    public RequestsPerCountry(String countryCode, Long period) {
        this(countryCode, period, System.currentTimeMillis());
    }

    public RequestsPerCountry(String countryCode, Long period, Long currentTimeStamp) {
        this.startedTimeStamp.set(currentTimeStamp);
        this.countryCode = countryCode;
        this.period = period;
        this.hitsPerPeriod.incrementAndGet();
    }

    /**
     * Reset counter
     * hitsPerPeriod = 0
     * startedTimeStamp - currentTimeStamp
     *
     * @return - this
     */
    public RequestsPerCountry reset() {
        this.hitsPerPeriod.set(0L);
        this.startedTimeStamp.set(System.currentTimeMillis());
        return this;
    }

    /**
     * Reset counter
     * hitsPerPeriod = 0
     *
     * @param startTimestamp - specify startTimestamp
     * @return - this
     */
    public RequestsPerCountry reset(Long startTimestamp) {
        this.hitsPerPeriod.set(0L);
        this.startedTimeStamp.set(startTimestamp);
        return this;
    }

    /**
     * Hit request and count it
     *
     * @return - this
     */
    public RequestsPerCountry hit() {
        Long currentTimeStamp = System.currentTimeMillis();
        if (this.startedTimeStamp.get() + this.period < currentTimeStamp) {
            this.reset(currentTimeStamp);
        }
        this.hitsPerPeriod.incrementAndGet();
        return this;
    }

    /**
     * Hit request and count it
     *
     * @return - this
     */
    public RequestsPerCountry hit(Long currentTimeStamp) {
        if (this.startedTimeStamp.get() + this.period < currentTimeStamp) {
            this.reset(currentTimeStamp);
        }
        this.hitsPerPeriod.incrementAndGet();
        return this;
    }

}
