package space.vidsnip.model;

import javax.persistence.Embeddable;

@Embeddable
public class Video {
    private String videoId;
    private Integer startSeconds;
    private Integer endSeconds;

    protected Video() {}

    /**
     * Create a Video snippet.
     *
     * @param videoId YouTube video ID.
     * @param startSeconds Start time in this video Snip.
     * @param endSeconds End time in this video Snip.
     */
    public Video(String videoId, int startSeconds, int endSeconds) {
        this.videoId = videoId;
        this.startSeconds = startSeconds;
        this.endSeconds = endSeconds;
    }

    public String getVideoId() {
        return this.videoId;
    }

    public int getStartSeconds() {
        return this.startSeconds;
    }

    public int getEndSeconds() {
        return this.endSeconds;
    }
}
