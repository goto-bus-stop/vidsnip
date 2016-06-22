package space.vidsnip;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class VidsnipConfig {
    @Bean
    public UserDetailsService userDetailsService() {
        return new VidsnipUserDetailsService();
    }
}
