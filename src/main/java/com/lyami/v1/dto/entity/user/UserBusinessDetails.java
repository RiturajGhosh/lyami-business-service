package com.lyami.v1.dto.entity.user;

import com.lyami.v1.dto.entity.SoldPackage;
import com.lyami.v1.dto.entity.commons.Address;
import com.lyami.v1.dto.entity.commons.Country;

import jakarta.annotation.Generated;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
public class UserBusinessDetails {
    //primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userDetailsId;
    //1:1 relation with User table- user table is primarily used for authentication and authorization purpose
    @OneToOne
    @JoinColumn(name = "email", referencedColumnName = "email", insertable = false, updatable = false )
    private User user;

    @Embedded
    private Address address;
    //unique id of the user - can be used as a surrogate key/ or in business purpose
    @GeneratedValue(generator = "UUID_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(
        name = "UUID_seq", 
        sequenceName = "UUID_seq", 
        allocationSize = 50
    )
    private String UUID;
    //TO DO: add profile photo and passport blob here
    @Column(unique = true)
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

    @ManyToOne
    @JoinColumn(name = "countryId", referencedColumnName = "id", insertable = false, updatable = false)
    private Country country;

    @Getter
    public enum Gender{
        MALE,FEMALE,OTHER;
    }


}
