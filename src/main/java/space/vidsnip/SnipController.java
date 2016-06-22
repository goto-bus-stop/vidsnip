package space.vidsnip;

import com.google.api.services.youtube.model.SearchResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.io.IOException;

@Controller
public class SnipController {
    @Autowired
    private YouTubeAPI youtube;

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String createSnip(Model model) {
        return "create_snip";
    }

    @RequestMapping(value = "/new/search", method = RequestMethod.GET)
    @ResponseBody
    public List<SearchResult> getSearchQuery(@RequestParam(value = "q") String query)
            throws IOException {
        return this.youtube.search(query);
    }
}
