package com.troggly.repository;

import com.troggly.model.ConfirmEmailHashes;
import com.troggly.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfirmEmailHashesRepository  extends JpaRepository<ConfirmEmailHashes,Long> {
    public ConfirmEmailHashes findByHash(String hash);

    public List<ConfirmEmailHashes> findAllByUser(User user);
}
