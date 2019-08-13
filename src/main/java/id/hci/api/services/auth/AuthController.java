package id.hci.api.services.auth;

import id.hci.api.dao.BaseDaoTemplate;
import id.hci.api.dao.CustomerRepository;
import id.hci.api.dao.model.Customer;
import id.hci.api.helper.auth.JwtTokenProvider;
import id.hci.api.helper.auth.RdUserDetails;
import id.hci.api.services.model.AuthenticationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BaseDaoTemplate baseDaoTemplate;


    /*
    @GetMapping("test")
    public ResponseEntity testing() {
        System.out.println("---> testing");


        Customer customer  = this.restoUserRepository.findByUser_name("yohan_sp@gmail.com").orElseThrow(() -> new UsernameNotFoundException("testin"));
        String data = null;
        data.trim();
        return ResponseEntity.ok(restoUser);
    }
    */
    @PostMapping("signin")
    public ResponseEntity signin(@RequestBody AuthenticationRequest data) {

        try {
            UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(data.getUsername(), data.getPassword());;

            authenticationManager.authenticate(upat);

             Customer customer = this.customerRepository.findByUser_name(data.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Username " + data.getUsername() + "not found"));


            RdUserDetails rdUserDetails = new RdUserDetails(customer.getUser_name(), customer.getPassword(),customer.getStatus());

            String token = jwtTokenProvider.createToken(data.getUsername(), rdUserDetails.getRoles());
            Map<Object, Object> result = new HashMap<>();
            result.put("token", token);
            result.put("user", customer);
            return ResponseEntity.ok(result);

        } catch (AuthenticationException authe) {
            authe.printStackTrace();
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

}
