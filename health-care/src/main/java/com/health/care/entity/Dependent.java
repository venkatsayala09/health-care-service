package com.health.care.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Dependent")
@Table(name = "DEPENDENT")
public class Dependent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String birthDate;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "enrolleeId", referencedColumnName = "id")
    private Enrollee enrollee;

    public Dependent(String name, String birthDate, Enrollee enrollee) {
        this.name = name;
        this.birthDate = birthDate;
        this.enrollee = enrollee;
    }
}
