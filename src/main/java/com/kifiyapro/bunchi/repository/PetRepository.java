package com.kifiyapro.bunchi.repository;

import com.kifiyapro.bunchi.modle.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
//
    @Query(value = "select * from Pet where type=:type and age=:age and size =:size and good_with_children=:good and gender=:gender limit :limit",nativeQuery = true)
    List<Pet> findAllByTypeAndAgeAndSizeAndGoodWithChildrenAndGender (
            @Param("type") String type,
            @Param("age") Long age,
            @Param("size") String size,
            @Param("good") Boolean goodWithChildren,
            @Param("gender")String gender,
            @Param("limit") Long limit );

            long count();


//    List<Pet> findAllByTypeAndAgeAndSizeAndGoodWithChildrenAndGender( String type,
//                                                                    Long age,
//                                                                    String size,
//                                                                 Boolean goodWithChildren,
//                                                                  String gender
//                                                         );
//
//







//    List<Pet> findAllByAge( Long age);
}
