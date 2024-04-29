package com.lyami.v1.dto.request;

import com.lyami.v1.exception.LyamiBusinessException;
import com.lyami.v1.validator.ValidEmailId;
import com.lyami.v1.validator.ValidStayRegReq;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Set;

@Data
@ValidStayRegReq(groups = StayRegistrationRequest.StayRegistrationRequestClassValidatorGroup.class)
public class StayRegistrationRequest {

    @NotBlank(groups = StayRegistrationRequestFieldValidatorGroup.class)
    private String stayName;
    @NotNull(groups = StayRegistrationRequestFieldValidatorGroup.class)
    private StayType stayType;
    private String countryDisplayCode;
    @NotNull(groups = StayRegistrationRequestFieldValidatorGroup.class)
    private Integer pincode;
    @NotBlank(groups = StayRegistrationRequestFieldValidatorGroup.class)
    private String address;
    @NotBlank(groups = StayRegistrationRequestFieldValidatorGroup.class)
    private String contactNumber;
    @ValidEmailId(groups = StayRegistrationRequestFieldValidatorGroup.class)
    private String emailAddress;
    private String hostName;
    private String marketingContact;
    private String opsContact;
    @NotNull(groups = StayRegistrationRequestFieldValidatorGroup.class)
    private Set<Amenities> amenities;


    @Getter
    public enum Amenities {
        LOCAL_TOUR("local tours", 1),
        BREAKFAST_BUFFET("breakfast buffet", 2),
        FEMALE_DORM("female dormitory", 3),
        NO_RACISM("no racism", 4),
        LOCKER("locker", 5),
        WIFI("wifi", 6),
        AIRPORT_TRANSFER("airport transfer", 7),
        LUNCH_BUFFET("lunch buffet", 8),
        POOL("pool", 9),
        GYM("gym", 10);

        private String label;
        private int displayCode;

        Amenities(String label) {
            this.label = label;
        }

        Amenities(String label, int displayCode) {
            this.label = label;
            this.displayCode = displayCode;
        }

        public static Amenities fromLabel(String label) {
            return Arrays.stream(Amenities.values())
                    .filter(amenities -> StringUtils.equalsIgnoreCase(amenities.getLabel(), label))
                    .findFirst()
                    .orElseThrow(() -> new LyamiBusinessException("invalid amenity"));
        }
    }

    @Getter
    public enum StayType {
        HOSTEL("hostel", 1), HOTEL("hotel", 2);
        private String label;
        private int code;

        StayType(String label) {
            this.label = label;
        }

        StayType(String label, int code) {
            this.label = label;
            this.code = code;
        }

        public static StayType fromLabel(String label) {
            return Arrays.stream(StayType.values())
                    .filter(stayType -> StringUtils.equalsIgnoreCase(stayType.getLabel(), label))
                    .findFirst()
                    .orElseThrow(() -> new LyamiBusinessException("Invalid Stay type"));
        }
    }

    public interface StayRegistrationRequestClassValidatorGroup {
    }

    public interface StayRegistrationRequestFieldValidatorGroup {
    }
}
