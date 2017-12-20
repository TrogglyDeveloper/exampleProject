package com.troggly.controller;

import com.troggly.apiObject.AddUserApi;
import com.troggly.apiObject.MainReply;
import com.troggly.apiObject.UserApi;
import com.troggly.apiObject.UserList;
import com.troggly.enums.Role;
import com.troggly.enums.Type;
import com.troggly.mapper.UserMapper;
import com.troggly.model.ConfirmEmailHashes;
import com.troggly.model.User;
import com.troggly.model.UserDetails;
import com.troggly.model.UserEmail;
import com.troggly.repository.UserDetailsRepository;
import com.troggly.repository.UserRepository;
import com.troggly.service.ConfirmEmailHashesService;
import com.troggly.service.UserService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.*;
import java.util.*;

/**
 * Created by Vlad on 26.07.2017.
 */
@RestController
@PropertySource("classpath:file-server.properties")
public class UserController {

    @Autowired
    private Environment env;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private ConfirmEmailHashesService confirmEmailHashesService;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(path = "/user/findByLogin/{login}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public UserApi getAllUsers(@PathVariable String login) {
        UserApi userApi = userMapper.fromInternal(userService.findOne(login));

        return userApi;
    }

    @RequestMapping(path = "/user/checkLogin/{login}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MainReply checkLoginUser(@PathVariable String login) {
        MainReply mainReply = null;
        try{
        if(userService.findOne(login)!=null){
            mainReply = new MainReply();
            mainReply.errorMessage = "User with such login already exists";
            mainReply.returnedCode = -2;
            return mainReply;
        }else {
            mainReply = new MainReply();
            return mainReply;
        }
        }catch (Exception e){
            mainReply = new MainReply();
            mainReply.errorMessage = "It's problem login";
            mainReply.returnedCode = -1;
            return mainReply;
        }
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

                    ConfirmEmailHashes confirmEmailHashes = null;
        for(UserEmail email:newUser.getUserDetails().getEmails()){
            if(email.isConfirm()==false){
                confirmEmailHashes = new ConfirmEmailHashes();
        confirmEmailHashes.setEmail(email);
        confirmEmailHashes.setUser(newUser);
        String hash =  BCrypt.hashpw(""+addUserApi.password+addUserApi.login, BCrypt.gensalt(rounds));
                hash =  hash.replace('/','|');
                hash =  hash.replace('\\','|');
        confirmEmailHashes.setHash(hash);
        logger.info("hash for test:"+confirmEmailHashes.getHash());
        confirmEmailHashesService.save(confirmEmailHashes);
            }
        }

        confirmEmailHashesService.sendConfirmEmail(newUser);
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

    @RequestMapping(path = "/user/confirmEmail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MainReply confirmEmail( @RequestParam("hash") String hash) {
        MainReply mainReply = null;
        try{

                confirmEmailHashesService.confirmEmail(hash);
                mainReply = new MainReply();
                return mainReply;


        }catch (Exception e){
            e.printStackTrace();
            mainReply = new MainReply();
            mainReply.errorMessage = e.getMessage();
            mainReply.returnedCode = -1;
            return mainReply;
        }
    }

    @RequestMapping(value = "/file/public/image/{file:.+}", method = RequestMethod.GET,
            produces = MediaType.ALL_VALUE)
    public ResponseEntity<InputStreamResource> getImage(@PathVariable String file) throws IOException {
logger.info("FILE-NAME:"+file);

        String fileName = env.getProperty("directory")+file;
        String[] arr = file.split("\\.");


        File initialFile = new File(fileName);

        InputStream targetStream = new FileInputStream(initialFile);
        if(arr[arr.length-1].equals("jpeg")||arr[arr.length-1].equals("jpg")){
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(new InputStreamResource(targetStream));
        }
        if(arr[arr.length-1].equals("png")){
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(new InputStreamResource(targetStream));
        }
        if(arr[arr.length-1].equals("gif")){
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_GIF)
                    .body(new InputStreamResource(targetStream));
        }
        return ResponseEntity
                .notFound().build();
    }


}
