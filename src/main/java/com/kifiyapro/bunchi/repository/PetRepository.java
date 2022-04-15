package com.kifiyapro.bunchi.repository;

import com.kifiyapro.bunchi.modle.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
//
    @Query(value = "select * from Pet where type=:type and age=:age and size =:size and good_with_children=:good and gender=:gender limit :limit",nativeQuery = true)
    List<Pet> listPetBy(
            @Param("type") String type,
            @Param("age") Long age,
            @Param("size") String size,
            @Param("good") Boolean good_with_children,
            @Param("gender")String gender,
            @Param("limit") Long limit );

            long count();

    Pet findByPet_id(Long id);


//    Page<UserSubscription>
}
