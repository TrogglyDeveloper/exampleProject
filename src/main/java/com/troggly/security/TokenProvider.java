package com.troggly.security;

import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by Vlad on 27.07.2017.
 */
@Service
public class TokenProvider {

    @Autowired
    private HazelcastInstance hazelcastInstance;

    public void put(String token, AuthorizedUser user) {
        hazelcastInstance.getMap("token-cache").put(token, user);
    }

    public AuthorizedUser get(String token) {
        AuthorizedUser user = null;
        if (token != null) {
            user = (AuthorizedUser) hazelcastInstance.getMap("token-cache").get(token);
          //  logger.info("User найден");
        }

        if (user == null) {
          //  logger.info("User не найден"+token);
        }
        return user;
    }

    public String newToken() {
        return UUID.randomUUID().toString();
    }

}
