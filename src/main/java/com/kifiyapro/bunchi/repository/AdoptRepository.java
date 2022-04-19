package com.kifiyapro.bunchi.repository;

import com.kifiyapro.bunchi.modle.Adopt;
import com.kifiyapro.bunchi.modle.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Repository
public interface AdoptRepository extends JpaRepository<Adopt, Long> {




    List<Adopt> findAllByCreatedOnBetween(Date from_date, Date to_date);

    long count();

   Integer  countAdoptByPet(Pet pet);

   Integer countAdoptByCreatedOnBetween(Date date1, Date date2);



    @Query(value = "SELECT date_trunc('week', created_on::timestamp) AS weekly, COUNT(*)  FROM pet GROUP BY weekly ORDER BY weekly", nativeQuery = true)
    List<ListweeklyandTotalCount> listweeklyandTotalCounts(Instant From_date, Instant To_date);
    public static interface ListweeklyandTotalCount {
        long getweekly();

        long getcount();

    }


}