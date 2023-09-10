/*
 * This code is proprietary and confidential to Lyamii.com.
 * All rights are reserved. Unauthorized use, reproduction,
 * or distribution of this code is strictly prohibited.
 */
package com.lyami.v1.common.exception;

import com.lyami.v1.common.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    public static final int FALLBACK_ERROR_CODE = 900;
    public static final String FALLBACK_ERROR_MSG = "Error occurred please try again or contact with admin";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleArgumentNotValidException(MethodArgumentNotValidException ex) {
        var response = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .flatMap(objectError -> Arrays.stream(objectError.getDefaultMessage().split(",")))
                .map(errorMsg -> {
                    String[] msg = errorMsg.split("##");
                    return new ErrorResponse.Error(Integer.parseInt(msg[1]), msg[0]);
                })
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
        return new ResponseEntity(Optional.of(Collections.singletonList(new ErrorResponse.Error(FALLBACK_ERROR_CODE,
                FALLBACK_ERROR_MSG))), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static ErrorResponse.Error parseError(String errorMessage) {
        try {
            if (StringUtils.isNotBlank(errorMessage)) {
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
}
