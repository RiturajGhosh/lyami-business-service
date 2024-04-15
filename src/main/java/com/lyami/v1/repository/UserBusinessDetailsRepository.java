package com.lyami.v1.repository;

import com.lyami.v1.dto.entity.commons.Address;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lyami.v1.dto.entity.user.UserBusinessDetails;

@Repository
public interface UserBusinessDetailsRepository extends JpaRepository<UserBusinessDetails, Long>{
	UserBusinessDetails findByEmail(String email);

	@Transactional
	default void updateUserBusinessDetailsByEmail(UserBusinessDetails userBusinessDetails){
		UserBusinessDetails ubd = findByEmail(userBusinessDetails.getEmail());

		if(userBusinessDetails.getPhoneNumber() != null) ubd.setPhoneNumber(userBusinessDetails.getPhoneNumber());
		if(userBusinessDetails.getBirthDate() != null) ubd.setBirthDate(userBusinessDetails.getBirthDate());
		if(userBusinessDetails.getBloodGroup() != null) ubd.setBloodGroup(userBusinessDetails.getBloodGroup());
		if(userBusinessDetails.getGender() != null) ubd.setGender(userBusinessDetails.getGender());
		if(userBusinessDetails.getUserFirstName() != null) ubd.setUserFirstName(userBusinessDetails.getUserFirstName());
		if(userBusinessDetails.getUserLastName() != null) ubd.setUserLastName(userBusinessDetails.getUserLastName());
		if(userBusinessDetails.getAddress() != null) {
			Address address = new Address();
			if(userBusinessDetails.getAddress().getHouseNumber() == null) address.setHouseNumber(ubd.getAddress().getHouseNumber());
			else address.setHouseNumber(userBusinessDetails.getAddress().getHouseNumber());

			if(userBusinessDetails.getAddress().getStreet() == null) address.setStreet(ubd.getAddress().getStreet());
			else address.setStreet(userBusinessDetails.getAddress().getStreet());

			if(userBusinessDetails.getAddress().getCity() == null) address.setCity(ubd.getAddress().getCity());
			else address.setCity(userBusinessDetails.getAddress().getCity());

			if(userBusinessDetails.getAddress().getState() == null) address.setState(ubd.getAddress().getState());
			else address.setState(userBusinessDetails.getAddress().getState());

			if(userBusinessDetails.getAddress().getPincode() == null) address.setPincode(ubd.getAddress().getPincode());
			else address.setPincode(userBusinessDetails.getAddress().getPincode());

			if(userBusinessDetails.getAddress().getPostOffice() == null) address.setPostOffice(ubd.getAddress().getPostOffice());
			else address.setPostOffice(userBusinessDetails.getAddress().getPostOffice());

			if(userBusinessDetails.getAddress().getPoliceStation() == null) address.setPoliceStation(ubd.getAddress().getPoliceStation());
			else address.setPoliceStation(userBusinessDetails.getAddress().getPoliceStation());

			ubd.setAddress(address);
		}

		save(ubd);
	}

}
