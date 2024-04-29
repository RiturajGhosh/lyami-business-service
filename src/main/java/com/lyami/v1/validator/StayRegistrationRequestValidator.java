package com.lyami.v1.validator;

import com.lyami.v1.dto.request.StayRegistrationRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashSet;
import java.util.Set;

public class StayRegistrationRequestValidator implements ConstraintValidator<ValidStayRegReq, StayRegistrationRequest> {

    @Value("${stayreg.opscontact.required}")
    private String invalidOpsContact;

    @Value("${stayreg.marketingcontact.required}")
    private String invalidMarketingContact;

    @Value("${stayreg.hostname.required}")
    private String invalidHostName;

    @Override
    public boolean isValid(StayRegistrationRequest stayRegistrationRequest, ConstraintValidatorContext constraintValidatorContext) {
        Set<String> errors = new HashSet<>();
        //if the stay is hostel we need host name
        if(StringUtils.equalsIgnoreCase(stayRegistrationRequest.getStayType().getLabel(), "hostel")){
            if(StringUtils.isBlank(stayRegistrationRequest.getHostName())){
                errors.add(invalidHostName);
            }
        }else{
            //if the stay is hotel we need marketing team contact, ops team contact
            if(StringUtils.isBlank(stayRegistrationRequest.getMarketingContact())){
                errors.add(invalidMarketingContact);
            }
            if(StringUtils.isBlank(stayRegistrationRequest.getOpsContact())){
                errors.add(invalidOpsContact);
            }
        }
        if(CollectionUtils.isNotEmpty(errors)){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(String.join(", ", errors))
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
