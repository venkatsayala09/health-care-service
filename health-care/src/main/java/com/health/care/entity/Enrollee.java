package com.health.care.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Enrollee")
@Table(name = "ENROLLEE")
public class Enrollee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ActivationStatus activationStatus;

    private String birthDate;

    private String phoneNumber;

    @JsonManagedReference
    @OneToMany(
            mappedBy = "enrollee",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<Dependent> dependents;

    public void setDependents(Set<Dependent> dependents) {
        if(dependents != null || !dependents.isEmpty()) {
            this.dependents = dependents;
            this.dependents.forEach(entity -> entity.setEnrollee(this));
        }
    }
}
