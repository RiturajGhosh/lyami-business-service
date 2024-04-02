package com.lyami.v1.dto.entity.commons;

import com.lyami.v1.dto.entity.PackageDetails;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
public class Image implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer packageId;

    private String imageUri;

    private ImageType imageType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "packageId", referencedColumnName = "id", updatable = false, insertable = false)
    private PackageDetails packageDetails;

    public enum ImageType{
        HOSTEL(0, "hostel"), PACKAGE(2, "package"), GENERIC(2, "generic");

        private Integer value;
        private String label;

        ImageType(Integer value, String label) {
            this.value = value;
            this.label = label;
        }
    }
}
