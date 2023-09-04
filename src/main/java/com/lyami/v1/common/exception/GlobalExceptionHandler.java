/*
 * This code is proprietary and confidential to Lyamii.com.
 * All rights are reserved. Unauthorized use, reproduction,
 * or distribution of this code is strictly prohibited.
 */
package com.lyami.v1.common.exception;

import com.lyami.v1.common.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleArgumentNotValidException(MethodArgumentNotValidException ex){
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
}
