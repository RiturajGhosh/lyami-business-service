package com.lyami.v1.dto.entity.user;

import com.lyami.v1.dto.entity.SoldPackage;
import com.lyami.v1.dto.entity.commons.Address;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.Date;
import java.util.Set;

public class UserDetails {
    //primary key
    private int userDetailsId;
    //1:1 relation with User table- user table is primarily used for authentication and authorization purpose
    private User user;
    @Embedded
    private Address address;
    //unique id of the user - can be used as a surrogate key/ or in business purpose
    private Integer userUId;
    private String email;
    private String phoneNumber;
    private String userFirstName;
    private String userLastName;
    private Date birthDate;
    private Gender gender;
    private String bloodGroup;
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "UserTour",
            joinColumns = {@JoinColumn(name = "userDetailsId")},
            inverseJoinColumns = {@JoinColumn(name = "soldPackageId")}
    )
    private Set<SoldPackage> soldPackageSet;

    @Getter
    public enum Gender{
        MALE,FEMALE,OTHER;
    }


}
