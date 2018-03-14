package com.company.service.validator;

import com.company.loan.validator.IValidator;
import com.company.model.dto.ApplyLoanDTO;
import com.company.service.BlackListPersonService;
import com.company.utils.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import static com.company.Constants.PERSON_ID_KEY_NAME;

/**
 * Validate PersonID from URI
 */
@Slf4j
@Service("blackListValidatorService")
public class BlackListValidatorService implements IValidator<HttpServletRequest> {

    @Autowired
    @Qualifier(value = "objectMapper")
    ObjectMapper objectMapper;

    @Autowired
    BlackListPersonService blackListPersonService;

    @Override
    public String getName() {
        return getClass().getCanonicalName();
    }

    @Override
    public boolean isValid(HttpServletRequest request) {
        if (request == null) return true;
        log.debug("request URI:{}", request.getRequestURI());

        // #1 - from URL
        // ROOT_PATH + "/" + VERSION + "/loan/status/approved/" + PERSON_ID_KEY_NAME + "/personIdValue"
        if (request.getRequestURI().contains(PERSON_ID_KEY_NAME)) {
            Long personId = StringUtils.parsePersonId(request.getRequestURI());
            log.debug("extracted personId:{}", personId);
            return blackListPersonService.isInBlackList(personId);
        }

        // #2 - from content
        String requestContent = httpServletRequestToString(request);
        log.debug("request content:{}", requestContent);
        // if it applyLoanDTO
        if (requestContent.trim().length() > 0) {
            try {
                ApplyLoanDTO applyLoanDTO = objectMapper.readValue(requestContent, ApplyLoanDTO.class);
                if (applyLoanDTO != null) {
                    Long personId = applyLoanDTO.getPersonId();
                    return blackListPersonService.isInBlackList(personId);
                }
            } catch (Exception ignore) {
            }
        }

        return true;
    }

    private String httpServletRequestToString(HttpServletRequest request) {

        StringBuilder stringBuilder = new StringBuilder();

        try {
            ServletInputStream servletInputStream = request.getInputStream();
            byte[] httpData = new byte[request.getContentLength()];
            int index = -1;
            while ((index = servletInputStream.read(httpData)) != -1) {
                for (int i = 0; i < index; i++) {
                    stringBuilder.append(Character.toString((char) httpData[i]));
                }
            }

        } catch (Exception ex) {
            log.error("Unable to parse request into string", ex);
        }

        return stringBuilder.toString();
    }

}
