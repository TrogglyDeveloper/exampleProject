package com.troggly.repository;
import com.troggly.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Vlad on 10.05.2017.
 */
@Repository
public interface UserRepository extends JpaRepository<User,String> {
    //hello Hleb, run zapl plz
}
