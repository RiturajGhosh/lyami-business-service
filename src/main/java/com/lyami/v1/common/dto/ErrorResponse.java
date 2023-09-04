/*
 * This code is proprietary and confidential to Lyamii.com.
 * All rights are reserved. Unauthorized use, reproduction,
 * or distribution of this code is strictly prohibited.
 */
package com.lyami.v1.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@JsonInclude(Include.NON_NULL)
@Data
public class ErrorResponse {
    private List<Error> errors;

    @Data
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class Error {
        private Integer errorCode;
        private String errorMessage;
    }
}
