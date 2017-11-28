package com.troggly.repository;

import com.troggly.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Vlad on 03.09.2017.
 */
@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio,Long> {
//    @Query("SELECT c FROM Content c WHERE c.author=:author")

    List<Portfolio> findAllByProjectType(String type);
    @Query("SELECT DISTINCT p.projectType FROM Portfolio p")
    List<String> findDistinctProjectType();
}
