package space.vidsnip;

import space.vidsnip.model.snip.Snip;
import space.vidsnip.model.snip.SnipRepository;
import space.vidsnip.model.user.User;
import space.vidsnip.model.user.UserRepository;
import space.vidsnip.model.Video;

import com.google.api.services.youtube.model.SearchResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    @Autowired
    private SnipRepository snips;
    @Autowired
    private UserRepository users;

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String createSnip(Model model) {
        return "create_snip";
    }

    final private static int[] durationMultipliers = { 1, 60, 60, 24 };
    /**
     * Parse a duration like 7:30 into a number of seconds.
     */
    private static int parseDuration(String str) {
        String[] parts = str.split(":");
        int duration = 0;
        // Walk through the duration parts from right to left, and through the
        // duration multipliers from left to right.
        int di = 0;
        for (int pi = parts.length - 1; pi >= 0; pi--, di++) {
            duration += Integer.parseInt(parts[pi]) * durationMultipliers[di];
        }
        return duration;
    }

    public static Snip makeSnip(User author, String videoId, String startSeconds, String endSeconds) {
        Video video = new Video(
            videoId,
            parseDuration(startSeconds),
            parseDuration(endSeconds)
        );
        return new Snip(author, video);
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String publishSnip(
        Authentication auth,
        @RequestParam("video_id") String videoId,
        @RequestParam("start_seconds") String startSeconds,
        @RequestParam("end_seconds") String endSeconds
    ) {
        User author = this.users.findByUsername(auth.getName()).get();
        Snip snip = makeSnip(author, videoId, startSeconds, endSeconds);

        this.snips.save(snip);

        return "redirect:/";
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
