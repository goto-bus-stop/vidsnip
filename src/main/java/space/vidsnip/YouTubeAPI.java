package space.vidsnip;

import com.google.api.services.youtube.model.SearchResult;

import java.io.IOException;
import java.util.List;

public interface YouTubeAPI {
    public List<SearchResult> search(String query) throws IOException;
}
