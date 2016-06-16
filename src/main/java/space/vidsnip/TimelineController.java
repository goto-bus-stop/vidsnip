package space.vidsnip;

import space.vidsnip.model.SnipRepository;
import space.vidsnip.model.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
}
