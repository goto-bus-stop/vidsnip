package space.vidsnip;

import space.vidsnip.model.graphuser.GraphUser;
import space.vidsnip.model.graphuser.GraphUserRepository;
import space.vidsnip.model.snip.Snip;
import space.vidsnip.model.snip.SnipRepository;
import space.vidsnip.model.user.User;
import space.vidsnip.model.user.UserRepository;
import space.vidsnip.model.Video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Controller
public class TimelineController {
    @Autowired
    private SnipRepository snips;
    @Autowired
    private UserRepository users;
    @Autowired
    private GraphUserRepository graphUsers;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String timeline(
        Authentication auth,
        Model model,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = new PageRequest(page, size);

        User user = this.users.findByUsername(auth.getName()).get();

        List<String> usernames = new LinkedList<>();
        usernames.add(user.getUsername());


        GraphUser graphUser = graphUsers.findByUsername(user.getUsername());
        if(graphUser.getWatches()!=null&&graphUser.getWatches().size()>0){
            for(GraphUser graphUserUsername : graphUser.getWatches()){
                usernames.add(graphUserUsername.getName());
            }
        }


        List<User> userList = users.findByUsernameIn(usernames);

        model.addAttribute("user", user);
        model.addAttribute("snips", this.snips.findByAuthorInOrderByCreatedAtDesc(userList,pageable));
        return "timeline";
    }

    private boolean isWatching(User self, User other) {
        GraphUser gself = this.graphUsers.findByUsername(self.getUsername());
        GraphUser gother = this.graphUsers.findByUsername(other.getUsername());
        return gself != null && gother != null && gself.isWatching(gother);
    }

    @RequestMapping(value = "/@/{username}", method = RequestMethod.GET)
    public String userTimeline(
        Authentication auth,
        Model model,
        @PathVariable String username,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Optional<User> currentUser = auth != null
            ? this.users.findByUsername(auth.getName())
            : Optional.empty();
        Pageable pageable = new PageRequest(page, size);
        return this.users.findByUsername(username)
            .map(user -> {
                Optional<Video> video = user.getBio().map(Snip::getVideo);
                model.addAttribute("bio", video.isPresent() ? video.get() : null);
                model.addAttribute("user", user);
                model.addAttribute("isOwnProfile", false);
                model.addAttribute("isWatching", false);
                currentUser.ifPresent(self -> {
                    model.addAttribute("currentUser", self);
                    model.addAttribute("isOwnProfile", self.getId().equals(user.getId()));
                    model.addAttribute("isWatching", this.isWatching(self, user));
                });
                model.addAttribute("snips", this.snips.findByAuthor(user, pageable));
                return "profile";
            })
            .orElseGet(() -> {
                model.addAttribute("username", username);
                return "profile_not_found";
            });
    }
}
