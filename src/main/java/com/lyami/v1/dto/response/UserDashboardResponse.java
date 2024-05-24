package com.lyami.v1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
public class UserDashboardResponse {
    private String userFirstName;
    private String userLastName;
    private String greeting;
    private OngoingTourSingleDayBrief ongoingTourSingleDayBrief;

}



