/*
 * This code is proprietary and confidential to Lyamii.com.
 * All rights are reserved. Unauthorized use, reproduction,
 * or distribution of this code is strictly prohibited.
 */
package com.lyami.v1.authentication.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/dummy")
public class DummyController {

  public static final String ADMIN_ROLE_AUTHORIZER = "hasRole('ADMIN')";
  public static final String MODERATOR_ROLE_AUTHORIZER = "hasRole('MODERATOR')";
  public static final String USER_ROLE_AUTHORIZER = "hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')";

  @Value("${test.key}")
  private String testMessage;

  @GetMapping("/all")
  public String allAccess() {
    return "This content will be available publicly for all the roles"+testMessage;
  }

  @GetMapping("/user")
  @PreAuthorize(USER_ROLE_AUTHORIZER)
  public String userAccess() {
    return "This content will be only available for user roles";
  }

  @GetMapping("/mod")
  @PreAuthorize(MODERATOR_ROLE_AUTHORIZER)
  public String moderatorAccess() {
    return "This content will be only available for moderator roles";
  }

  @GetMapping("/admin")
  @PreAuthorize(ADMIN_ROLE_AUTHORIZER)
  public String adminAccess() {
    return "Admin Board.";
  }
}
