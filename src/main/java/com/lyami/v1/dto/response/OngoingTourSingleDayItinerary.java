package com.lyami.v1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class OngoingTourSingleDayItinerary {
    private Integer dayNo;

    private String heading;

    private String shortDesc;

    private String location;

    private String morning;

    private String afternoon;

    private String evening;

    private String night;

}
