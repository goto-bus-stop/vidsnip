package space.vidsnip;

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

import java.util.Optional;

@Controller
public class TimelineController {
    @Autowired
    private SnipRepository snips;
    @Autowired
    private UserRepository users;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String timeline(
        Authentication auth,
        Model model
    ) {
        User user = this.users.findByUsername(auth.getName()).get();
        model.addAttribute("user", user);
        model.addAttribute("snips", this.snips.findAll());
        return "timeline";
    }

    @RequestMapping(value = "/@/{username}", method = RequestMethod.GET)
    public String userTimeline(
        Model model,
        @PathVariable String username,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = new PageRequest(page, size);
        return this.users.findByUsername(username)
            .map(user -> {
                Optional<Video> video = user.getBio().map(Snip::getVideo);
                model.addAttribute("bio", video.isPresent() ? video.get() : null);
                model.addAttribute("user", user);
                model.addAttribute("snips", this.snips.findByAuthor(user, pageable));
                return "profile";
            })
            .orElseGet(() -> {
                model.addAttribute("username", username);
                return "profile_not_found";
            });
    }
}
