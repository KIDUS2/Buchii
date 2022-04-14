package com.kifiyapro.bunchi.modle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "customer")
@Entity
public class Customer {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id", nullable = false)
    private Long customer_id;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "phonenumber", nullable = false, unique = true)
    private String phonenumber;

    @Column(name = "created_on")
    private Instant created_on;

    @Column(name = "updated_on")
    private Instant updated_on;
//
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "pet", nullable = false)
//    private Pet pet;


}
