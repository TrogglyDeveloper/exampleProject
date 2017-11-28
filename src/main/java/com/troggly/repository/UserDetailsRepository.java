package com.troggly.repository;

import com.troggly.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Vlad on 10.05.2017.
 */
@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails,String> {
}
