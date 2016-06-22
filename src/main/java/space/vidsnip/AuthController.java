package space.vidsnip;

import space.vidsnip.model.user.User;
import space.vidsnip.model.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository users;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegisterForm() {
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String createUserAccount(
        Model model,
        @RequestParam("username") String username,
        @RequestParam("email") String email,
        @RequestParam("password") String rawPassword
    ) {
        String encryptedPassword = this.passwordEncoder.encode(rawPassword);
        rawPassword = null;

        User user = new User(username);
        user.setEmail(email);
        user.setEncryptedPassword(encryptedPassword);

        this.users.save(user);

        model.addAttribute("user", user);
        return "timeline";
    }
}
