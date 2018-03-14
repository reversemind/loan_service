package com.company.service.validator

import com.company.service.BlackListPersonService
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest

import static com.company.Constants.*

/**
 */
class BlackListValidatorServiceSpec extends Specification {

    def 'black list validator'() {
        setup: 'init'

        BlackListValidatorService blackListValidatorService = new BlackListValidatorService()

        def blackListPersonService = Mock(BlackListPersonService)
        blackListPersonService.isInBlackList(1L) >> true
        blackListPersonService.isInBlackList(10L) >> false
        blackListPersonService.isInBlackList(11L) >> false

        def httpRequest = Mock(HttpServletRequest)
        blackListValidatorService.blackListPersonService = blackListPersonService

        when: 'URI with correct person and valid id = 1'
        httpRequest.getRequestURI() >> ROOT_PATH + "/" + VERSION + "/loan/status/approved/" + PERSON_ID_KEY_NAME + "/1"
        def result = blackListValidatorService.isValid(httpRequest)

        then: 'should be not in black list'
        result


        when: 'URI with correct person and black listed id = 11'
        def httpRequestBlackList = Mock(HttpServletRequest)
        httpRequestBlackList.getRequestURI() >> ROOT_PATH + "/" + VERSION + "/loan/status/approved/" + PERSON_ID_KEY_NAME + "/11"
        result = blackListValidatorService.isValid(httpRequestBlackList)

        then: 'should be in black list'
        result == false

    }
}
