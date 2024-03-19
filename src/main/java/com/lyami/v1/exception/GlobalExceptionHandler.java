/*
 * This code is proprietary and confidential to Lyamii.com.
 * All rights are reserved. Unauthorized use, reproduction,
 * or distribution of this code is strictly prohibited.
 */
package com.lyami.v1.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.lyami.v1.dto.response.ErrorResponse;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.lyami.v1.constants.ApplicationConstants.ACCESS_DENIED_EXCEPTION_MSG;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    public static final int FALLBACK_ERROR_CODE = 900;
    public static final String FALLBACK_ERROR_MSG = "Error occurred please try again or contact with admin";
    private static final Pattern ENUM_MSG = Pattern.compile("values accepted for Enum class");


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleArgumentNotValidException(MethodArgumentNotValidException ex) {
        /* String[] msg = errorMsg.split("##");
                    return new ErrorResponse.Error(Integer.parseInt(msg[1]), msg[0]);*/
        var response = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .flatMap(objectError -> Arrays.stream(objectError.getDefaultMessage().split(",")))
                .map(GlobalExceptionHandler::parseError)
                .collect(Collectors.toList());
        return new ResponseEntity<>(Optional.of(response), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LyamiBusinessException.class)
    public ResponseEntity handleBusinessException(LyamiBusinessException ex) {
        var errorResponse = new ErrorResponse(List.of(parseError(ex.getMessage())));
        return new ResponseEntity<>(Optional.of(errorResponse), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception ex) {
        log.error("Exception", ex);
        return new ResponseEntity(Optional.of(Collections.singletonList(new ErrorResponse.Error(FALLBACK_ERROR_CODE,
                FALLBACK_ERROR_MSG))), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static ErrorResponse.Error parseError(String errorMessage) {
        try {
            if (StringUtils.isNotBlank(errorMessage)) {
                log.info(errorMessage);
                var message = errorMessage.split("##");
                var errorCode = message.length > 1 ? Integer.parseInt(message[1]) : FALLBACK_ERROR_CODE;
                return new ErrorResponse.Error(errorCode, message[0]);
            }
        } catch (Exception ex) {
            log.error("Internal error", ex);
            return new ErrorResponse.Error(FALLBACK_ERROR_CODE, FALLBACK_ERROR_MSG);
        }
        return new ErrorResponse.Error(FALLBACK_ERROR_CODE, FALLBACK_ERROR_MSG);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity handleAuthorizationException(AccessDeniedException ex) {
        log.info(ex.getMessage());
        var errorResponse = new ErrorResponse(List.of(parseError(ACCESS_DENIED_EXCEPTION_MSG)));
        return new ResponseEntity<>(Optional.of(errorResponse), HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResponse.Error handleJsonErrors(HttpMessageNotReadableException exception){
        if (exception.getCause() != null && exception.getCause() instanceof InvalidFormatException) {
            Matcher match = ENUM_MSG.matcher(exception.getCause().getMessage());
            if (match.find()) {
               // return new ErrorResponse.Error("value should be: " + match.group(1),  HttpStatus.BAD_REQUEST);
            }
        }

        return null;
    }
}
