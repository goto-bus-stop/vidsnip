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

import java.util.ArrayList;
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
    public List<YouTubeVideoMeta> getSearchQuery(@RequestParam(value = "q") String query)
            throws IOException {
        List<YouTubeVideoMeta> videos = new ArrayList<YouTubeVideoMeta>(50);
        for (SearchResult result : this.youtube.search(query)) {
            videos.add(new YouTubeVideoMeta(result));
        }
        return videos;
    }

    public class YouTubeVideoMeta {
        public String id;
        public String title;
        public String description;
        public String thumbnail;

        public YouTubeVideoMeta(SearchResult from) {
            this.id = from.getId().getVideoId();
            this.title = from.getSnippet().getTitle();
            this.description = from.getSnippet().getDescription();
            this.thumbnail = from.getSnippet().getThumbnails().getDefault().getUrl();
        }
    }
}
