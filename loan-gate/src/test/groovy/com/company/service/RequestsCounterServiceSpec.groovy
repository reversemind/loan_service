package com.company.service

import spock.lang.Specification

import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

/**
 *
 */
class RequestsCounterServiceSpec extends Specification {

    def 'count concurrently and accurately all hits'() {
        setup: 'initialize'

        def period = 10L
        def numberOfThreads = 500

        def requestsCounterService = Spy(RequestsCounterService)
        requestsCounterService.requestPeriodCounter >> period * 10

        CountDownLatch countDownLatch = new CountDownLatch(numberOfThreads as Integer);
        AtomicInteger counter = new AtomicInteger(0)

        when: 'create and make a bunch of hits'

        long startTime = System.currentTimeMillis();

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            executor.submit(new Runnable() {
                @Override
                void run() {
                    counter.incrementAndGet()
                    countDownLatch.countDown()
                    requestsCounterService.hit("countryCode")
                }
            })
        }

        countDownLatch.await((System.currentTimeMillis() - startTime) * 10, TimeUnit.MILLISECONDS)

        then: 'should call initialize only ones'
        1 * requestsCounterService.initializeNewCountry(_)

        numberOfThreads == counter.get()

        executor.shutdown();
        while (!executor.isTerminated()) {
            Thread.sleep(10)
        }

    }
}
