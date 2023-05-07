package com.iiitb.spe.service;


import javax.transaction.Transactional;

import com.twilio.Twilio;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iiitb.spe.models.Movie_Details;
import com.iiitb.spe.repositories.MovieDetailsRepository;

import java.util.Random;

@Service
@Transactional
public class MovieDetailsService {
    @Autowired
    private MovieDetailsRepository repo;


    public Movie_Details findByMovieName(String movie_name)
    {
        return repo.findByMovieName(movie_name);
    }
    private TwilioRestClient twilioRestClient;

    public String generateOTP(String phoneNumber) {
        // Generate a random 6-digit OTP
        System.out.println(phoneNumber+"Service");
        String otp = String.format("%06d", new Random().nextInt(999999));

        System.out.println("Start");
        // Send the OTP to the user's phone via SMS
        final String ACCOUNT_SID = "ACbcec39040fe22ddea4512e79e24b6c90";
        final String AUTH_TOKEN = "39eaeefa239ec6911da5e33644686f06";
        final String FROM_NUMBER = "+16317693117";

        System.out.println("before");
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Verification verification = Verification.creator("VA7b7cae4a6cafb25ff2d150b9412ea804","+91"+phoneNumber,"sms").create();
        System.out.println(verification.getStatus());
        System.out.println("after");

        return verification.getStatus();
    }

    public String verifyOTP(String phoneNumber, String enteredOTP) {
        // Verify the OTP entered by the user
        final String ACCOUNT_SID = "ACbcec39040fe22ddea4512e79e24b6c90";
        final String AUTH_TOKEN = "39eaeefa239ec6911da5e33644686f06";
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        VerificationCheck verification;
        try {
            verification = VerificationCheck.creator("VA7b7cae4a6cafb25ff2d150b9412ea804").setTo("+91" + phoneNumber).setCode(enteredOTP).create();
        }catch (Exception e){
            return "decline";
        }
        return verification.getStatus();
    }
}
