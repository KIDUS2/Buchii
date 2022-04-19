package com.kifiyapro.bunchi.modle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pet")
@Entity
public class Pet {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id", nullable = false)
    private Long petId;

    @Column(name = "type")
    private String type;
    @Column(name = "age")
    private String age;
    @Column(name = "good_with_children")
    private Boolean goodWithChildren;

    @Column(name = "gender")
    private String gender;
    @Column(name = "size")
    private String size;

    @Column(name = "photo")
    private String photo;


    @Column(name = "created_on")
    private Instant createdOn;
    @Column(name = "updated_on")
    private Instant updatedOn;

}
