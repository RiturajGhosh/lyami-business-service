/*
 * This code is proprietary and confidential to Lyamii.com.
 * All rights are reserved. Unauthorized use, reproduction,
 * or distribution of this code is strictly prohibited.
 */
package com.lyami.v1.authentication.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class JwtResponse {

  private String token;
  private final String type = "Bearer";
  private Long id;
  private String username;
  private String email;
  private List<String> roles;
}
