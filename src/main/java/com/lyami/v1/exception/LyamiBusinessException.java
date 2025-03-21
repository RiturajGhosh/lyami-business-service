/*
 * This code is proprietary and confidential to Lyamii.com.
 * All rights are reserved. Unauthorized use, reproduction,
 * or distribution of this code is strictly prohibited.
 */
package com.lyami.v1.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LyamiBusinessException extends RuntimeException{

    public LyamiBusinessException(String message) {
        super(message);
    }
}
