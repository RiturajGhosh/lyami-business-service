package com.lyami.v1.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OngoingTourSingleDayBrief {
    private Integer dayNo;
    private String packageId;
    private String heading;
    private String shortDesc;
}
