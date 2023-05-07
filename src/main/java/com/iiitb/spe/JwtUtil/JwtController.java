package com.iiitb.spe.JwtUtil;

import com.iiitb.spe.AuthenticationService;
import com.iiitb.spe.service.MovieDetailsService;
import com.iiitb.spe.AuthenticationService;
import com.iiitb.spe.JwtUtil.models.JwtResponseModel;
import com.iiitb.spe.service.MovieDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
@CrossOrigin(origins = "*")
@RestController

public class JwtController {
    private  final MovieDetailsService movieDetailsService;
    private final AuthenticationService authenticationService;
    public JwtController(AuthenticationService authenticationService,MovieDetailsService movieDetailsService)
    {
        this.authenticationService = authenticationService;
        this.movieDetailsService=movieDetailsService;
    }
    @Autowired
    private JwtUserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenManager tokenManager;


    @CrossOrigin
    @GetMapping("user/verifyOTP")
    public ResponseEntity createToken(@RequestParam String phone_number,@RequestParam String otp) {

        String auth = this.movieDetailsService.verifyOTP(phone_number,otp);
        System.out.println("reached: "+ auth);

        if (Objects.equals(auth, "approved")) {
            System.out.println("entered");
            final UserDetails userDetails = userDetailsService.loadUserByUsername(phone_number);
            final String jwtToken = tokenManager.generateJwtToken(userDetails);
        System.out.println("hellso");
        //System.out.println(jwtToken);
        //System.out.println());
        return ResponseEntity.ok(new JwtResponseModel(jwtToken));

        }
        else
        {
            return ResponseEntity.ok(new JwtResponseModel("not"));
        }
        //return auth;
    }
}
