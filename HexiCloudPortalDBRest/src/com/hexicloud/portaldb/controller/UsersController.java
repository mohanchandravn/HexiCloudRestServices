package com.hexicloud.portaldb.controller;

import com.hexicloud.portaldb.bean.CustomerRegistry;
import com.hexicloud.portaldb.bean.User;
import com.hexicloud.portaldb.service.EmailsService;
import com.hexicloud.portaldb.service.UsersService;

import java.sql.SQLException;

import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class UsersController {
    private static final Logger logger = Logger.getLogger(UsersController.class);

    @Autowired
    private UsersService usersService;
    @Autowired
    private EmailsService emailsService;

    @RequestMapping(value = "/services/rest/createPortalUser/", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> createPortalUser(@RequestBody User user) throws Exception {
        logger.info("******* Start of createPortalUser() in controller ***********");
        usersService.createUser(user);

        logger.info("******** End of createPortalUser() in controller ***********");
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/services/rest/updateUserPassword/", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateUserPassword(@RequestBody User user) throws Exception {
        logger.info("******* Start of updateUserPassword() in controller ***********");
        usersService.updatePassword(user);

        logger.info("******** End of updateUserPassword() in controller ***********");
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/services/rest/checkUserIdAvailable/{userId}/", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boolean> checkUserIdExist(@PathVariable("userId") String userId) throws Exception {

        logger.info("******* Start of checkUserIdExist() in controller ***********");
        boolean userIdExists = usersService.checkExistingUser(userId);
        logger.info("******* End of checkUserIdExist() in controller ***********");
        return new ResponseEntity<Boolean>(userIdExists, HttpStatus.OK);
    }

    @RequestMapping(value = "/services/rest/forgotPasswordService/{userId}/", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER') and #userId == authentication.name")
    public ResponseEntity<String> forgotPasswordService(@PathVariable("userId") String userId) {
        try {
            if (userId != null) {
                User user = usersService.queryUserInfoByUserId(userId);
                if (user == null)
                    return new ResponseEntity<String>("not a valid user id", HttpStatus.NO_CONTENT);
                else {
//                    String decodedPassword = EncryptionUtil.decryptString(user.getPassword());
//                    ;
//                    String subject = "Password Details";
//                    String emailContent = "your password is : " + decodedPassword;
//                    //String emailId = "shivakumar.gunjur.manjukumar@oracle.com";//user.getEmail()
                    String result = emailsService.sendEmail(user.getEmail(),user);
                    if (result != null && result.equalsIgnoreCase("N")) {
                        return new ResponseEntity<String>(HttpStatus.EXPECTATION_FAILED);
                    }

                }
            }
        } catch (SQLException sqlExp) {
            sqlExp.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NamingException nmExp) {
            nmExp.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception exp) {
            exp.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @RequestMapping(value = "/services/rest/getCustRegistries/", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CustomerRegistry>> getCustRegistries() {
        logger.info("******* Start of getCustRegistries() in controller ***********");

        List<CustomerRegistry> customerRegistries = usersService.getCustRegistries();
        if (customerRegistries.isEmpty()) {

            logger.info("customerRegistries  not found");
            return new ResponseEntity<List<CustomerRegistry>>(HttpStatus.NO_CONTENT);
        }

        logger.info("******** End of getClmDataByUserId() in controller ***********");
        return new ResponseEntity<List<CustomerRegistry>>(customerRegistries, HttpStatus.OK);

    }

    @RequestMapping(value = "/services/rest/searchUsers/", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> searchUserDetails(@RequestParam(value = "userId", required = false)String userId,
                                                        @RequestParam(value = "emailId", required = false)String emailId,
                                                        @RequestParam(value = "customerId", required = false)String customerId)
    {
       List<User> usersList = usersService.searchUserDetails(userId,emailId,customerId);

        return  new ResponseEntity<List<User>>(usersList, HttpStatus.OK);
    }

    @RequestMapping(value = "services/rest/updateUser/", method = RequestMethod.PUT)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity updateUserDetails(@RequestBody User user)
    {
        try{
            usersService.updateUser(user);
        }catch(Exception exp)
        {
            logger.error("error in services/rest/updateUser/ rest service:",exp);
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
    
}
