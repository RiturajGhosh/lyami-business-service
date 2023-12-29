package com.lyami.v1.dto.entity.stayregistration;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
public class StayRegImage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] imageData;
    private String fileName;
    private String fileType;
    private Integer stayRegistrationId;
    private int imageType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stayRegistrationId", referencedColumnName = "id", updatable = false, insertable = false)
    private StayRegistration stayRegistration;
}
