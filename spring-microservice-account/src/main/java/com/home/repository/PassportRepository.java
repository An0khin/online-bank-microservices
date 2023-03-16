package com.home.repository;

import com.home.model.Passport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface PassportRepository extends JpaRepository<Passport, Integer> {
    Optional<Passport> findBySeriesAndNumber(Integer series, Integer number);

}
