package space.vidsnip.model;

import javax.persistence.Embeddable;

@Embeddable
public class Video {
    private String videoId;
    private Integer startSeconds;
    private Integer endSeconds;

    protected Video() {}

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
