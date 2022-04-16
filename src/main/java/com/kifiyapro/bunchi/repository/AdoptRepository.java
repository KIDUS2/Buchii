package com.kifiyapro.bunchi.repository;

import com.kifiyapro.bunchi.modle.Adopt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AdoptRepository extends JpaRepository<Adopt, Long> {

    @Query(value = "SELECT  * from adopt where created_on between :from_date and :to_date",nativeQuery = true)
    List<Adopt> findBewteenDate(
            @Param("from_date") Date from_date,
            @Param("to_date") Date to_date);


//    @Query(value = "SELECT DISTINCT a from adopt a LEFT JOIN FETCH a.pet_id e where a. ",nativeQuery = true)
//    List<Adopt> findBewteenDate(
//            @Param("from_date") Instant from_date,
//            @Param("to_date") Instant to_date);


    long count();


    @Query(value = "SELECT date_trunc('week', created_on::timestamp) AS weekly, COUNT(*)  FROM pet GROUP BY weekly ORDER BY weekly", nativeQuery = true)
    List<ListweeklyandTotalCount> listweeklyandTotalCounts(Date From_date,Date To_date);

    public static interface ListweeklyandTotalCount {
        long getweekly();
        long getcount();

    }
}
