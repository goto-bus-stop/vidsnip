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

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

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

    @RequestMapping(value = "/watch/{username}")
    public String watchUser(
        Authentication auth,
        Model model,
        @PathVariable String username
    ) {
        User user = this.users.findByUsername(auth.getName()).get();
        User watch = this.users.findByUsername(username).get();

        GraphUser graphWatch = graphUsers.findByUsername(watch.getUsername());
        GraphUser graphUser = graphUsers.findByUsername(user.getUsername());

        graphUser.addWatch(graphWatch);
        graphUsers.save(graphUser);

        return "redirect:/@/" + watch.getUsername();
    }

    @RequestMapping(value = "/unwatch/{username}")
    public String unwatchUser(
        Authentication auth,
        Model model,
        @PathVariable String username
    ) {
        User user = this.users.findByUsername(auth.getName()).get();
        User watch = this.users.findByUsername(username).get();

        GraphUser graphWatch = graphUsers.findByUsername(watch.getUsername());
        GraphUser graphUser = graphUsers.findByUsername(user.getUsername());

        graphUser.removeWatch(graphWatch);
        graphUsers.save(graphUser);

        return "redirect:/@/" + watch.getUsername();
    }

    @RequestMapping(value = "/watcherslist", method = RequestMethod.GET)
    public String watchList(
            Authentication auth,
            Model model
    ) {
        User user = this.users.findByUsername(auth.getName()).get();
        GraphUser graphUser = graphUsers.findByUsername(user.getUsername());

        List<String> usernames = new LinkedList<>();
        for(GraphUser graphUserUsername : graphUser.getWatches()){
            usernames.add(graphUserUsername.getName());
        }

        Collection<User> userList = users.findByUsernameIn(usernames);




        model.addAttribute("user", user);
        model.addAttribute("watches", userList);
        return "userlist";
    }
}
