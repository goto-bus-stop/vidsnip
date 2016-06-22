package space.vidsnip;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.YouTube;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class YouTubeAPIImpl implements YouTubeAPI {
    private static final long MAX_RESULTS = 50;

    private YouTube youtube;

    @Value("${vidsnip.youtube.key}")
    private String key;

    public YouTubeAPIImpl() {
        this.youtube = new YouTube.Builder(
            new NetHttpTransport(),
            new JacksonFactory(),
            (HttpRequest request) -> {}
        ).setApplicationName("vidsnip").build();
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public List<SearchResult> search(String query) throws IOException {
        YouTube.Search.List search = this.youtube.search().list("id,snippet");
        search.setKey(this.key);
        search.setQ(query);
        search.setType("video");
        search.setMaxResults(MAX_RESULTS);
        SearchListResponse searchResponse = search.execute();
        return searchResponse.getItems();
    }
}
