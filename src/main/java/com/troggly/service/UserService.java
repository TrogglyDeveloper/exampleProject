package com.troggly.service;

import com.troggly.model.User;
import com.troggly.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.troggly.exeptions.AuthorizedException;

/**
 * Created by Vlad on 26.07.2017.
 */
@Service("userService")
public class UserService extends GeneralServiceImp<User, String> {

    private static final Logger logger =  LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public JpaRepository<User, String> getRepository() {
        return this.userRepository;
    }

    @Transactional
    public User autorizedUser(String login, String password) throws AuthorizedException{
        User user = null;
        if(login!=null&&password!=null){
            String passwordHash  = BCrypt.hashpw(password,BCrypt.gensalt(12));
            user = userRepository.findOne(login);
            boolean checkpw = BCrypt.checkpw(password,passwordHash);
            if(checkpw){
                //ok
                return user;
            }else {
                //     logger.error("hash "+b);
                throw new AuthorizedException();
            }
        }
        return user;
    }
}
