package id.hci.api.helper.auth;

import id.hci.api.dao.CustomerRepository;
import id.hci.api.dao.model.Customer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private CustomerRepository users;

    public CustomUserDetailsService(CustomerRepository users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = this.users.findByUser_name(username).orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found"));


        RdUserDetails userDetails = new RdUserDetails(customer.getUser_name(), customer.getPassword(), customer.getStatus());

        System.out.println("testing----> " + userDetails.getUsername() + " - " + userDetails.getPassword() + " - roles:" + userDetails.getRoles());
        return userDetails;
    }
}