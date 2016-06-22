package space.vidsnip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import space.vidsnip.model.graphuser.GraphUser;
import space.vidsnip.model.graphuser.GraphUserRepository;
import space.vidsnip.model.snip.SnipRepository;
import space.vidsnip.model.user.User;
import space.vidsnip.model.user.UserRepository;

/**
 * Created by Marijn on 22/06/2016.
 */
@Controller
public class WatchController {

    @Autowired
    private SnipRepository snips;
    @Autowired
    private UserRepository users;
    @Autowired
    private GraphUserRepository graphUsers;

    @RequestMapping(value = "/watch/{username}", method = RequestMethod.GET)
    public String watchUser(
            Authentication auth,
            Model model,
            @PathVariable String username

    ) {
        User user = this.users.findByUsername(auth.getName()).get();
        User watch = this.users.findByUsername(username).get();

        System.out.println("Authname: "+auth.getName());
        System.out.println("Watchname: "+username);

        GraphUser graphWatch = graphUsers.findByUsername(watch.getUsername());
        GraphUser graphUser = graphUsers.findByUsername(user.getUsername());

        graphUser.addWatch(graphWatch);
        graphUsers.save(graphUser);

        model.addAttribute("user", user);
        model.addAttribute("snips", this.snips.findAll());
        return "timeline";
    }
}
