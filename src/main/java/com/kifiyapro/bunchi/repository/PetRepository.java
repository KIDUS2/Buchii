package com.kifiyapro.bunchi.repository;

import com.kifiyapro.bunchi.modle.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {


}
