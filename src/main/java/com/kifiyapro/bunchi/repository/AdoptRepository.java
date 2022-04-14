package com.kifiyapro.bunchi.repository;

import com.kifiyapro.bunchi.modle.Adopt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface AdoptRepository extends JpaRepository<Adopt, Long> {

    List<Adopt> findAllByAdoptTimeBetween(
            Instant from_date,
            Instant to_date);
    long count();
}
