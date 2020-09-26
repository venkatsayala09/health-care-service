package com.health.care.dto;

import com.health.care.entity.ActivationStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class EnrolleeDTO {

    private Long id;
    private String name;
    private ActivationStatus activationStatus;
    private String birthDate;
    private String phoneNumber;
    private List<DependentDTO> dependents;

}
