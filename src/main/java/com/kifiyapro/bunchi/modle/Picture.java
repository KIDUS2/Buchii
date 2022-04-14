package com.kifiyapro.bunchi.modle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "picture")
@Entity
public class Picture {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "pet", nullable = false)
    private Pet pet;

    @Column(name = "pricture_path", nullable = false, length = 200)
    private String pricturePath;

    @Column(name = "uploaded_on", nullable = false)
    private Instant uploadedOn;

}
