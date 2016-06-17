package space.vidsnip;

import space.vidsnip.model.SnipRepository;
import space.vidsnip.model.User;
import space.vidsnip.model.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TimelineController {
    @Autowired
    private SnipRepository snips;
    @Autowired
    private UserRepository users;

    @RequestMapping(value = "/timeline", method = RequestMethod.GET)
    public String timeline(Model model) {
        final long currentUserId = 1;
        model.addAttribute("user", this.users.findOne(currentUserId));
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
