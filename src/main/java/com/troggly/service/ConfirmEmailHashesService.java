package com.troggly.service;

import com.troggly.apiObject.MainReply;
import com.troggly.exeptions.ConfirmExeprion;
import com.troggly.model.ConfirmEmailHashes;
import com.troggly.model.User;
import com.troggly.model.UserDetails;
import com.troggly.model.UserEmail;
import com.troggly.repository.ConfirmEmailHashesRepository;
import com.troggly.repository.UserDetailsRepository;
import com.troggly.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("confirmEmailHashesService")
public class ConfirmEmailHashesService extends GeneralServiceImp<ConfirmEmailHashes, Long> {

    @Autowired
    private ConfirmEmailHashesRepository confirmEmailHashesRepository;

    @Autowired
    private EmailTemplateService emailTemplateService;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public JpaRepository<ConfirmEmailHashes, Long> getRepository() {
        return confirmEmailHashesRepository;
    }

    public ConfirmEmailHashes findConfirmEmailHashesByHash(String hash){
        ConfirmEmailHashes confirmEmailHashes = null;
        confirmEmailHashes = confirmEmailHashesRepository.findByHash(hash);
        return confirmEmailHashes;
    }
    public void sendConfirmEmail (User user) throws Exception{
        String rezultMessage = null;
     //   String userName, String subject, String hash, String emailAddress
        List<ConfirmEmailHashes> listHashes = confirmEmailHashesRepository.findAllByUser(user);

        if(listHashes.isEmpty()){
            throw new ConfirmExeprion("This user does not have hashes or unconfirmed email addresses");//TODO Properties
        }

        for(ConfirmEmailHashes confirmEmailHashes : listHashes){
            emailTemplateService.sendRequestConfirmEmail(user.getUserDetails().getFirstName(),
                    confirmEmailHashes.getHash(),confirmEmailHashes.getEmail().getEmail());
        }

    }

    public void confirmEmail(String hash) throws Exception{
        ConfirmEmailHashes confirmEmailHashes = confirmEmailHashesRepository.findByHash(hash);
        if(confirmEmailHashes!=null) {
            User user = confirmEmailHashes.getUser();
            UserEmail userEmail = confirmEmailHashes.getEmail();
            Set<UserEmail> userEmailSet = new HashSet<>();
            UserDetails userDetails = userDetailsRepository.findOne(user.getUserDetails().getId());
            for (UserEmail email : userDetails.getEmails()) {
                if (userEmail.getId().equals(email.getId())) {
                    //  user.getUserDetails().getEmails().remove(email);
                    email.setConfirm(true);
                    userEmailSet.add(email);
                    //   user.getUserDetails().getEmails().add(email);
                } else {
                    userEmailSet.add(email);
                }
            }
            userDetails.setEmails(userEmailSet);
            //   user.getUserDetails().setEmails(userEmailSet);
            //  userService.save(user);
            userDetailsRepository.save(userDetails);
            confirmEmailHashesRepository.delete(confirmEmailHashes.getId());
            emailTemplateService.sendCompliteRegistration(userDetails.getFirstName(),userEmail.getEmail());

            //  User user = userService.f
            // userEmail.setConfirm(true);
        }else{
            throw new ConfirmExeprion();
        }



        //TODO SEND MAIL WELCOME PAGE
//            if(userService.findOne(login)!=null){
//                mainReply = new MainReply();
//                mainReply.errorMessage = "User with such login already exists";
//                mainReply.returnedCode = -2;
//                return mainReply;
//            }else {
//                mainReply = new MainReply();
//                return mainReply;
//            }
    }
}
