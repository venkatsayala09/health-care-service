package com.health.care.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class DependentDTO {

    private Long id;
    private String name;
    private String birthDate;

    public DependentDTO(String name, String birthDate) {
        this.name = name;
        this.birthDate = birthDate;
    }


}
