/*
 * This code is proprietary and confidential to Lyamii.com.
 * All rights are reserved. Unauthorized use, reproduction,
 * or distribution of this code is strictly prohibited.
 */
package com.lyami.v1.controller;

import com.lyami.v1.constants.ApplicationConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/dummy")
public class DummyController {

  @Value("${test.key}")
  private String testMessage;

  @GetMapping("/all")
  public String allAccess() {
    return "This content will be available publicly for all the roles"+testMessage;
  }

  @GetMapping("/user")
  @PreAuthorize(ApplicationConstants.USER_ROLE_AUTHORIZER)
  public String userAccess() {
    return "This content will be only available for user roles";
  }

  @GetMapping("/mod")
  @PreAuthorize(ApplicationConstants.MODERATOR_ROLE_AUTHORIZER)
  public String moderatorAccess() {
    return "This content will be only available for moderator roles";
  }

  @GetMapping("/admin")
  @PreAuthorize(ApplicationConstants.ADMIN_ROLE_AUTHORIZER)
  public String adminAccess() {
    return "Admin Board.";
  }
}
