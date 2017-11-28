package com.troggly.repository;

import com.troggly.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Vlad on 03.09.2017.
 */
@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {
}
