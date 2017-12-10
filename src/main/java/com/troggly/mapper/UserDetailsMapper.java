package com.troggly.mapper;

import com.troggly.apiObject.UserDetailsApi;
import com.troggly.model.UserDetails;
import com.troggly.model.UserEmail;
import com.troggly.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Vlad on 26.07.2017.
 */
@Component
public class UserDetailsMapper {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    public UserDetailsApi fromInternal(UserDetails userDetails) {
        UserDetailsApi userDetailsApi = null;
        if (userDetails != null) {
            userDetailsApi = new UserDetailsApi();
            userDetailsApi.id = userDetails.getId();
            userDetailsApi.firstName = userDetails.getFirstName();
            userDetailsApi.middleName = userDetails.getMiddleName();
            userDetailsApi.secondName = userDetails.getSecondName();
            userDetailsApi.address = userDetails.getAddress();
//            Set<UserEmailApi> userEmailApiSet = new HashSet<>();
            List<String> emailList = new ArrayList<>();
            for(UserEmail mail : userDetails.getEmails()){
                emailList.add(mail.getEmail());
            }
            userDetailsApi.emails = emailList;
          //  userDetailsApi.emails = userDetails.getEmails();//TODO EMAILS GET
            userDetailsApi.skype_array = userDetails.getSkype_array();
            userDetailsApi.telephones = userDetails.getTelephones();
            userDetailsApi.country = userDetails.getCountry();
            userDetailsApi.company = userDetails.getCompany();
            userDetailsApi.additionalInformation = userDetails.getAdditionalInformation();
            userDetailsApi.zipCode = userDetails.getZipCode();

        }

        return userDetailsApi;
    }

    public UserDetails toInternal(UserDetailsApi userDetailsApi) {
        UserDetails userDetails = null;
        if(userDetailsApi==null) return null; //TODO
        if (userDetailsApi.id != null) {
            userDetails = userDetailsRepository.findOne(userDetailsApi.id);
        }
        if (userDetails == null) {
            userDetails = new UserDetails();
            userDetails.setId(userDetailsApi.id);
            userDetails.setAddress(userDetailsApi.address);
            userDetails.setFirstName(userDetailsApi.firstName);
            userDetails.setMiddleName(userDetailsApi.middleName);
            userDetails.setSecondName(userDetailsApi.secondName);
            Set<UserEmail> userEmailsSet = new HashSet<>();
            UserEmail mailBuffer = null;
            for(String userEmail : userDetailsApi.emails){
                mailBuffer = new UserEmail();
                mailBuffer.setEmail(userEmail);
                mailBuffer.setConfirm(false);
                userEmailsSet.add(mailBuffer);
            }
            userDetails.setEmails(userEmailsSet);
          //  UserEmailApi userEmailApi = new UserEmailApi();

           // userDetails.setEmails(userDetailsApi.emails); // TODO EMAILS SET
            userDetails.setSkype_array(userDetailsApi.skype_array);
            userDetails.setTelephones(userDetailsApi.telephones);
            userDetails.setCompany(userDetailsApi.company);
            userDetails.setCountry(userDetailsApi.country);
            userDetails.setAdditionalInformation(userDetailsApi.additionalInformation);
            userDetails.setZipCode(userDetailsApi.zipCode);
        }
        return userDetails;
    }
}
