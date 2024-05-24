package com.lyami.v1.service.user;

import com.lyami.v1.dto.entity.user.UserBusinessDetails;
import com.lyami.v1.dto.request.UserBusinessDetailsRegistrationRequest;
import com.lyami.v1.dto.request.UserBusinessDetailsUpdateRequest;
import com.lyami.v1.dto.response.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserBusinessDetailsService {

    void addBusinessDetails(UserBusinessDetailsRegistrationRequest userBusinessDetailsRegistrationRequest);

    void updateBusinessDetails(UserBusinessDetailsUpdateRequest userBusinessDetailsUpdateRequest);

    UserProfileResponse getUserBusinessDetails(String email);

    UserDashboardResponse getUserDashboard(String email);

    List<SoldPackageResponse> getUpcomingTours(String email);

    OngoingTourSingleDayItinerary getDetailedItinerary(String email);

    List<OngoingTourSingleDayBrief> getBriefDetailsForOngoingTour(String email);
}
