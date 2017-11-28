package com.troggly.mapper;

import com.troggly.apiObject.UserApi;
import com.troggly.enums.Role;
import com.troggly.enums.Type;
import com.troggly.model.User;
import com.troggly.repository.UserDetailsRepository;
import com.troggly.repository.UserRepository;
import com.troggly.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Created by Vlad on 26.07.2017.
 */
@Component
public class UserMapper {
    private static final Logger logger = LoggerFactory.getLogger(UserMapper.class);
    @Autowired
    UserDetailsMapper userDetailsMapper;
    @Autowired
    UserRepository userRepository;


    public UserApi fromInternal(User user) {
        UserApi userApi = null;
        if (user != null) {
            userApi = new UserApi();
//            userApi.login = user.getLogin();
            userApi.login = user.getLogin();
            userApi.date = user.getDate();
            userApi.roles = new ArrayList<>();//java 8 stream filter
            for (Role role : user.getRoles()) {
                userApi.roles.add(role.name());
            }
            userApi.type = user.getType().name();
            userApi.userDetails = userDetailsMapper.fromInternal(user.getUserDetails());
        }
        return userApi;
    }

    public User toInternal(UserApi userApi) {
        User user = userRepository.findOne(userApi.login);

        if (user == null) {
            logger.info("Юзер не найден");
            user = new User();//создать билдер на метаинформации?
            user.setDate(userApi.date);
            ArrayList<Role> listRole = new ArrayList();
            for (String strRole : userApi.roles) {
                if (strRole.equals(Role.ROLE_ADMIN.name())) {
                    listRole.add(Role.ROLE_ADMIN);
                }
                if (strRole.equals(Role.ROLE_MANAGER.name())) {
                    listRole.add(Role.ROLE_MANAGER);
                }
                if (strRole.equals(Role.ROLE_USER.name())) {
                    listRole.add(Role.ROLE_MANAGER);
                }
            }
            user.setRoles(listRole);
            Type userType = null;
            if (userApi.type.equals(Type.PRIVATE_PERSON.name())) {
                userType = Type.PRIVATE_PERSON;
            }
            if (userApi.type.equals(Type.COMPANY_PERSON.name())) {
                userType = Type.COMPANY_PERSON;
            }
            user.setType((userType));
            user.setUserDetails(userDetailsMapper.toInternal(userApi.userDetails));
            user.setLogin(userApi.login);
            user.setPasswordHash("adasd"); //TODO test data?
        }else {
            logger.info("Юзер pizd"+user.toString());
        }
        return user;
    }
}
