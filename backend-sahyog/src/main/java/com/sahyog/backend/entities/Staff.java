package com.sahyog.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Staff {
    @Id
    @SequenceGenerator(
            name = "staff_sequence",
            sequenceName = "staff_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "staff_sequence"
    )
    private int staffId;
    public String healthId;
    public String healthIdNumber;
    public String name;
    public String gender;
    public int yearOfBirth;
    public int dayOfBirth;
    public int monthOfBirth;
    public String mobile;
    @Embedded
    public Address address;

    @OneToOne(
            cascade = CascadeType.ALL,
            optional = false
    )
    @JoinColumn(
            name = "user_id_fk",
            referencedColumnName = "userId"
    )
    @JsonManagedReference(value = "staffBacKRef")
    public User user;

}
