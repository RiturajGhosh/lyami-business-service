package com.lyami.v1.validator;

import com.lyami.v1.dto.request.StayRegistrationRequest;
import com.lyami.v1.dto.response.ErrorResponse;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

public class StayRegistrationRequestValidator implements ConstraintValidator<ValidStayRegReq, StayRegistrationRequest> {

    @Override
    public boolean isValid(StayRegistrationRequest stayRegistrationRequest, ConstraintValidatorContext constraintValidatorContext) {
        Set<ErrorResponse.Error> errors = new HashSet<>();
        //if the stay is hostel we need host name
        if(StringUtils.equalsIgnoreCase(stayRegistrationRequest.getStayType().getLabel(), "hostel")){
            if(StringUtils.isBlank(stayRegistrationRequest.getHostName())){
                //error out
            }
        }else{
            //if the stay is hotel we need marketing team contact, ops team contact
            if(StringUtils.isBlank(stayRegistrationRequest.getMarketingContact())){
                //error out
            }
            if(StringUtils.isBlank(stayRegistrationRequest.getOpsContact())){
                //erro out
            }
        }
        if(CollectionUtils.isNotEmpty(errors)){
            return false;
        }
        return true;
    }
}
