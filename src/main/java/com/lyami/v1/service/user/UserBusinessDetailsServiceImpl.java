package com.lyami.v1.service.user;

import com.lyami.v1.dto.entity.PackageDetails;
import com.lyami.v1.dto.entity.PackageItinerary;
import com.lyami.v1.dto.entity.SoldPackage;
import com.lyami.v1.dto.entity.user.UserBusinessDetails;
import com.lyami.v1.dto.request.UserBusinessDetailsRegistrationRequest;
import com.lyami.v1.dto.request.UserBusinessDetailsUpdateRequest;
import com.lyami.v1.dto.response.*;
import com.lyami.v1.exception.LyamiBusinessException;
import com.lyami.v1.mapper.UserBusinessDetailsMapper;
import com.lyami.v1.repository.PackageItineraryRepository;
import com.lyami.v1.repository.UserBusinessDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lyami.v1.mapper.PackageDetailsMapper;

import java.util.*;

@Slf4j
@Service
public class UserBusinessDetailsServiceImpl implements UserBusinessDetailsService {

    private UserBusinessDetailsRepository userBusinessDetailsRepository;
    private UserBusinessDetailsMapper userBusinessDetailsMapper;
    private PackageDetailsMapper packageDetailsMapper;
    private PackageItineraryRepository packageItineraryRepository;


    @Autowired
    public UserBusinessDetailsServiceImpl(UserBusinessDetailsRepository userBusinessDetailsRepository,
                                          UserBusinessDetailsMapper userBusinessDetailsMapper,
                                          PackageDetailsMapper packageDetailsMapper,
                                          PackageItineraryRepository packageItineraryRepository) {
        super();
        this.userBusinessDetailsRepository = userBusinessDetailsRepository;
        this.userBusinessDetailsMapper = userBusinessDetailsMapper;
        this.packageDetailsMapper = packageDetailsMapper;
        this.packageItineraryRepository = packageItineraryRepository;
    }

    @Override
    public UserProfileResponse getUserBusinessDetails(String email){
        UserBusinessDetails userBusinessDetails = userBusinessDetailsRepository.findByEmail(email);
        if(userBusinessDetails == null) throw new LyamiBusinessException("User does not exist");

        return userBusinessDetailsMapper.mapUserBusinessDeatailstoUserProfileResponse(userBusinessDetails);
    }
    @Override
    public void addBusinessDetails(UserBusinessDetailsRegistrationRequest userBusinessDetailsRegistrationRequest){
        UserBusinessDetails ubd = userBusinessDetailsRepository.findByEmail(userBusinessDetailsRegistrationRequest.getEmail());

        if(ubd != null) throw new LyamiBusinessException("User already exists");

        var userBusinessDetails = userBusinessDetailsMapper.mapUserProfileRegistrationReqtoEntity(userBusinessDetailsRegistrationRequest);
        userBusinessDetailsRepository.save(userBusinessDetails);
    }

    @Override
    public void updateBusinessDetails(UserBusinessDetailsUpdateRequest userBusinessDetailsUpdateRequest){
        UserBusinessDetails ubd = userBusinessDetailsRepository.findByEmail(userBusinessDetailsUpdateRequest.getEmail());

        if(ubd == null) throw new LyamiBusinessException("User does not exist");

        var userBusinessDetails = userBusinessDetailsMapper.mapUserProfileUpdateReqtoEntity(userBusinessDetailsUpdateRequest);

        userBusinessDetailsRepository.updateUserBusinessDetailsByEmail(userBusinessDetails);
    }

    @Override
    public UserDashboardResponse getUserDashboard(String email){
        UserBusinessDetails userBusinessDetails = userBusinessDetailsRepository.findByEmail(email);
        if(userBusinessDetails == null) throw new LyamiBusinessException("User does not exist");

        SoldPackage ongoingTour = findOngoingTour(userBusinessDetails.getSoldPackageSet());

        OngoingTourSingleDayBrief ongoingTourSingleDayBrief = null;
        if(ongoingTour != null) {
            Date departureDate = new Date(ongoingTour.getPrfDepatureDate().getTime());

            Integer dayToFind = dayToFind(departureDate, ongoingTour.getPackageDetails().getNoOfDays());
            if (dayToFind != null) {
                List<PackageItinerary> packageItineraries = packageItineraryRepository.findPackageItinerariesByPackageIdOrderByDayNo(ongoingTour.getPackageId());
                PackageItinerary packageItinerary = packageItineraries.get(dayToFind);
                ongoingTourSingleDayBrief = new OngoingTourSingleDayBrief(packageItinerary.getDayNo(),
                        packageItinerary.getPackageId(),
                        packageItinerary.getHeading(),
                        packageItinerary.getShortDesc()
                );
            }
        }

        return new UserDashboardResponse(userBusinessDetails.getUserFirstName(),
                userBusinessDetails.getUserLastName(),
                getGreetingsByTimeOfDay(), ongoingTourSingleDayBrief);
    }


    @Override
    public List<SoldPackageResponse> getUpcomingTours(String email){
        UserBusinessDetails userBusinessDetails = userBusinessDetailsRepository.findByEmail(email);
        if(userBusinessDetails == null) throw new LyamiBusinessException("User does not exist");

        List<SoldPackageResponse> upcomingTours = new ArrayList<>();
        for(SoldPackage soldPackage : userBusinessDetails.getSoldPackageSet()){

            Date currentDate = new Date();

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            currentDate = calendar.getTime();
            Date departureDate = new Date(soldPackage.getPrfDepatureDate().getTime());

            if(departureDate.after(currentDate)){
                upcomingTours.add(new SoldPackageResponse(soldPackage.getPackageId(), soldPackage.getPrfDepatureDate(),
                    packageDetailsMapper.generateResponse(soldPackage.getPackageDetails())));
            }
        }
        return upcomingTours;
    }

    @Override
    public OngoingTourSingleDayItinerary getDetailedItinerary(String email){
        UserBusinessDetails userBusinessDetails = userBusinessDetailsRepository.findByEmail(email);
        if(userBusinessDetails == null) throw new LyamiBusinessException("User does not exist");

        SoldPackage ongoingTour = findOngoingTour(userBusinessDetails.getSoldPackageSet());

        OngoingTourSingleDayItinerary ongoingTourSingleDayItinerary = null;
        if(ongoingTour != null) {
            Date departureDate = new Date(ongoingTour.getPrfDepatureDate().getTime());
            Integer dayToFind = dayToFind(departureDate, ongoingTour.getPackageDetails().getNoOfDays());
            if (dayToFind != null) {
                List<PackageItinerary> packageItineraries = packageItineraryRepository.findPackageItinerariesByPackageIdOrderByDayNo(ongoingTour.getPackageId());
                PackageItinerary packageItinerary = packageItineraries.get(dayToFind);
                ongoingTourSingleDayItinerary = new OngoingTourSingleDayItinerary(
                        packageItinerary.getDayNo(),
                        packageItinerary.getHeading(),
                        packageItinerary.getShortDesc(),
                        packageItinerary.getLocation(),
                        packageItinerary.getMorning(),
                        packageItinerary.getAfternoon(),
                        packageItinerary.getEvening(),
                        packageItinerary.getNight()
                );
            }
        }

        return ongoingTourSingleDayItinerary;
    }

    @Override
    public List<OngoingTourSingleDayBrief> getBriefDetailsForOngoingTour(String email){
        UserBusinessDetails userBusinessDetails = userBusinessDetailsRepository.findByEmail(email);
        if(userBusinessDetails == null) throw new LyamiBusinessException("User does not exist");

        SoldPackage ongoingTour = findOngoingTour(userBusinessDetails.getSoldPackageSet());
        List<OngoingTourSingleDayBrief> ongoingTourAllDayBrief = new ArrayList<>();

        if(ongoingTour != null) {
            List<PackageItinerary> packageItineraries = packageItineraryRepository.findPackageItinerariesByPackageIdOrderByDayNo(ongoingTour.getPackageId());
            for(PackageItinerary packageItinerary : packageItineraries){
                ongoingTourAllDayBrief.add(new OngoingTourSingleDayBrief(
                        packageItinerary.getDayNo(),
                        packageItinerary.getPackageId(),
                        packageItinerary.getHeading(),
                        packageItinerary.getShortDesc()
                ));
            }
        }

        return ongoingTourAllDayBrief;
    }

    private SoldPackage findOngoingTour(Set<SoldPackage> tours){
        if(tours == null || tours.isEmpty()) return null;

        for(SoldPackage soldPackage : tours){
            Date departureDate = new Date(soldPackage.getPrfDepatureDate().getTime());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(departureDate);
            calendar.add(Calendar.DAY_OF_MONTH, soldPackage.getPackageDetails().getNoOfDays());
            Date endDate = calendar.getTime();

            Date currentDate = new Date();
            calendar.setTime(currentDate);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            currentDate = calendar.getTime();

            if(currentDate.after(departureDate) || currentDate.equals(departureDate)){
                if(currentDate.before(endDate)){
                    return soldPackage;
                }
            }
        }

        return null;
    }

    private String getGreetingsByTimeOfDay(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        if (hour >= 6 && hour < 12) {
            return "Good Morning";
        } else if (hour >= 12 && hour < 17) {
            return "Good Afternoon";
        } else if (hour >= 17 && hour < 20) {
            return "Good Evening";
        } else {
            return "Good Night";
        }
    }

    private Integer dayToFind(Date departureDate, Integer totalTripDays){
        Calendar calendar = Calendar.getInstance();
        Date currentDate = new Date();
        calendar.setTime(currentDate);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        currentDate = calendar.getTime();

        long differenceInMillis = currentDate.getTime() - departureDate.getTime();
        long dayDifference = differenceInMillis/(1000 * 60 * 60 * 24);
        int dayToFind;
        if(dayDifference < -1 || dayDifference >= totalTripDays){
            return null;
        } else {
            if(dayDifference == -1){
                dayToFind = 1;
            } else if(dayDifference == totalTripDays - 1){
                dayToFind = (int) dayDifference;
            } else {
                //if the time is greater than 9:00 p.m., function should return next day's itinerary
                if(hour >= 21){
                    dayToFind = (int) dayDifference + 1;
                } else {
                    dayToFind = (int) dayDifference;
                }
            }
        }

        return dayToFind;
    }

}
