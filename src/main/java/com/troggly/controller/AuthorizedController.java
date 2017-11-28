package com.troggly.controller;

import com.troggly.apiObject.LoginReply;
import com.troggly.apiObject.LoginRequest;
import com.troggly.mapper.UserMapper;
import com.troggly.model.User;
import com.troggly.security.AuthorizedUser;
import com.troggly.security.TokenProvider;
import com.troggly.service.UserService;
import com.troggly.exeptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vlad on 27.07.2017.
 */
@RestController
public class AuthorizedController {
    private static final Logger logger = LoggerFactory.getLogger(AuthorizedController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserMapper userMapper;

    @RequestMapping(path = "/auth", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<LoginReply> authenticateUser(@RequestBody LoginRequest request) {
        LoginReply reply = new LoginReply();
        User user = null;
        HttpStatus httpStatus = HttpStatus.OK;

        try {
            user = userService.autorizedUser(request.login, request.password);
            String token = tokenProvider.newToken();
            AuthorizedUser autorizatedUser = new AuthorizedUser(user);
            tokenProvider.put(token, autorizatedUser);

            List<String> roles = new ArrayList<>();
            for (GrantedAuthority grantedAuthority : autorizatedUser.getAuthorities()) {
                roles.add(grantedAuthority.getAuthority());
            }

            reply.roles = roles;
            reply.token = token;
            reply.user = userMapper.fromInternal(user);
        } catch (AuthorizedException e) {
            e.printStackTrace();
            logger.error("Error with auth :" + e.getMessage(), e);
        }

        return new ResponseEntity<>(reply, httpStatus);
    }
}
