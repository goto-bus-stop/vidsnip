package space.vidsnip;

import space.vidsnip.model.graphuser.GraphUser;
import space.vidsnip.model.graphuser.GraphUserRepository;
import space.vidsnip.model.user.User;
import space.vidsnip.model.user.UserRepository;
import space.vidsnip.model.snip.Snip;
import space.vidsnip.model.snip.SnipRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
    @Autowired
    private GraphUserRepository graphUsers;
    @Autowired
    private SnipRepository snips;

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

        GraphUser graphUser = new GraphUser(username);

        this.users.save(user);
        this.graphUsers.save(graphUser);

        model.addAttribute("user", user);
        return "timeline";
    }

    @RequestMapping(value = "/me/edit", method = RequestMethod.GET)
    public String showEditProfileForm(Authentication auth, Model model) {
        User user = this.users.findByUsername(auth.getName()).get();
        model.addAttribute("user", user);
        return "profile_edit";
    }

    @RequestMapping(value = "/me/bio", method = RequestMethod.GET)
    public String showEditBioForm(Authentication auth, Model model) {
        User user = this.users.findByUsername(auth.getName()).get();
        model.addAttribute("user", user);
        return "create_bio";
    }

    @RequestMapping(value = "/me/edit", method = RequestMethod.POST)
    public String saveProfile(
        Authentication auth,
        Model model,
        @RequestParam("real_name") String realName,
        @RequestParam("email") String email,
        @RequestParam("new_password") String newPassword,
        @RequestParam("password") String oldPassword
    ) {
        User user = this.users.findByUsername(auth.getName()).get();
        model.addAttribute("user", user);

        boolean isChangingRealName = realName != "" && !realName.equals(user.getRealName());
        boolean isChangingEmail = email != "" && !email.equals(user.getEmail());
        boolean isChangingPassword = newPassword != "";

        if (isChangingRealName) {
            user.setRealName(realName);
        }

        if (isChangingEmail || isChangingPassword) {
            if (!this.passwordEncoder.matches(oldPassword, user.getEncryptedPassword())) {
                model.addAttribute("error", "Incorrect password.");
                return "profile_edit";
            }

            if (isChangingEmail) {
                // TODO use global email validator of some sort
                user.setEmail(email);
            }
            if (isChangingPassword) {
                // TODO use global password validator of some sort
                String encryptedPassword = this.passwordEncoder.encode(newPassword);
                newPassword = null;
                user.setEncryptedPassword(encryptedPassword);
            }
        }

        this.users.save(user);

        return "redirect:/@/" + user.getUsername();
    }

    @RequestMapping(value = "/me/bio", method = RequestMethod.POST)
    public String saveBio(
        Authentication auth,
        Model model,
        @RequestParam("video_id") String videoId,
        @RequestParam("start_seconds") String startSeconds,
        @RequestParam("end_seconds") String endSeconds
    ) {
        User user = this.users.findByUsername(auth.getName()).get();

        Snip snip = SnipController.makeSnip(user, videoId, startSeconds, endSeconds);
        this.snips.save(snip);

        user.setBio(snip);
        this.users.save(user);

        return "redirect:/@/" + user.getUsername();
    }
}
