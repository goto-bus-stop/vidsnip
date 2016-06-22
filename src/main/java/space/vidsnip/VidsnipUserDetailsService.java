package space.vidsnip;

import space.vidsnip.model.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public class VidsnipUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository users;

    public UserDetails loadUserByUsername(String username) {
        return this.users.findByUsername(username).map(user ->
            new User(
                user.getUsername(),
                user.getEncryptedPassword(),
                new LinkedList<GrantedAuthority>()
            )
        ).orElse(null);
    }
}
