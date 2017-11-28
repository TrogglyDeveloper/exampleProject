package com.troggly.controller;

import com.troggly.apiObject.AddUserApi;
import com.troggly.apiObject.MainReply;
import com.troggly.apiObject.UserApi;
import com.troggly.apiObject.UserList;
import com.troggly.enums.Role;
import com.troggly.enums.Type;
import com.troggly.mapper.UserMapper;
import com.troggly.model.User;
import com.troggly.model.UserDetails;
import com.troggly.repository.UserRepository;
import com.troggly.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Vlad on 26.07.2017.
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(path = "/user/findByLogin/{login}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public UserApi getAllUsers(@PathVariable String login) {
        // logger.info("Login"+login);
      //  logger.info("DICK" + userService.findOne(login).getUserDetails().getAddress());
        // logger.info("ASs"+userService.findOne(login).toString());
        UserApi userApi = userMapper.fromInternal(userService.findOne(login));

        return userApi;
    }

    @RequestMapping(path = "/user/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public UserList addNewUser(@RequestBody AddUserApi addUserApi) {
        UserList userList = new UserList();
        try { //TODO bissnes logic
            //validation если уже занят логин

            if(addUserApi.userDetails.id==null){
                addUserApi.userDetails.id = addUserApi.login+"_details";
            }
            UserApi userApi = new UserApi();
            userApi.type = addUserApi.type;
            userApi.roles = addUserApi.roles;
            userApi.date = addUserApi.date;
            userApi.login = addUserApi.login;
            userApi.userDetails = addUserApi.userDetails;
            User user = userMapper.toInternal(userApi);
            int rounds = 12;
            String passHash = BCrypt.hashpw(addUserApi.password, BCrypt.gensalt(rounds));
            user.setPasswordHash(passHash);
            User newUser = userService.save(user);
            userList.users.add(userMapper.fromInternal(newUser));
            logger.info("Create new User " + newUser.toString());
        } catch (Exception e) {
            userList.returnedCode = -1;
            userList.errorMessage = e.getMessage();
        }

        return userList;
    }

    @RequestMapping(path = "/user/registration", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public UserList registrationNewUser(@RequestBody AddUserApi addUserApi) {
        UserList userList = new UserList();
        try {//TODO bissnes logic
            UserApi userApi = new UserApi();
            if(addUserApi.userDetails.id==null){
                addUserApi.userDetails.id = addUserApi.login+"_details";
            }
            userApi.type = addUserApi.type;
            List<String> roles = new ArrayList<>();
            roles.add(Role.ROLE_USER.name());
            userApi.roles = roles;
            userApi.date = addUserApi.date;
            userApi.login = addUserApi.login;
            userApi.userDetails = addUserApi.userDetails;
            User user = userMapper.toInternal(userApi);
            logger.info("pass"+user.getLogin());
            int rounds = 12;
            String passHash = BCrypt.hashpw(addUserApi.password, BCrypt.gensalt(rounds));
            user.setPasswordHash(passHash);
            User newUser = userService.save(user);
            userList.users.add(userMapper.fromInternal(newUser));
            logger.info("Registration new User " + newUser.toString());

//            User user = new User();
//            user.setPasswordHash("$2a$06$ptSH.gR7OB6tmo2yzX8.Cu1khpWmkGHSu/pGfCcRrV0NxO13H.WqG");//12345
//            user.setDate(new Date());
//            user.setLogin("adminss");
//            user.setType(Type.PRIVATE_PERSON);
//            List<Role> roles = new ArrayList<>();
//            roles.add(Role.ROLE_ADMIN);
//            roles.add(Role.ROLE_MANAGER);
//            roles.add(Role.ROLE_USER);
//
//            user.setRoles(roles);
//
//            UserDetails userDetails = new UserDetails();
//            userDetails.setSecondName("Matrosov");
//            userDetails.setFirstName("Vladyslav");
//            userDetails.setAddress("Chernihiv");
//            userDetails.setId(user.getLogin()+"_details");
//
//            user.setUserDetails(userDetails);
//            // userDetailsRepository.save(userDetails);
//            userRepository.save(user);
        } catch (Exception e) {
            userList.returnedCode = -1;
            userList.errorMessage = e.getMessage();
            logger.error(e.toString());
            e.printStackTrace();
        }

        return userList;
    }
    @RequestMapping(path = "/user/getAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public UserList getAllUsers() {
        UserList userList = new UserList();
        // logger.info("Login"+login);
        //  logger.info("DICK" + userService.findOne(login).getUserDetails().getAddress());
        // logger.info("ASs"+userService.findOne(login).toString());
        try {
            for (User user : userService.findAll()) {
            userList.users.add(userMapper.fromInternal(user));
            }
        }catch (Exception e){
            userList.errorMessage = e.getMessage();
            userList.returnedCode = -1;
            e.printStackTrace();
        }

        return userList;
    }

    @RequestMapping(path="/user/del/{login}",  method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MainReply delUser(@PathVariable String login ){
        MainReply mainReply = null;
        try{
            userService.delete(login);
            mainReply = new MainReply();
        }catch (Exception e){
            mainReply = new MainReply();
            mainReply.returnedCode = -1;
            mainReply.errorMessage = e.getMessage();
        }
        return mainReply;
    }

}
