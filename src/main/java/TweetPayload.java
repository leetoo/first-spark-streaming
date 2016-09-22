public class TweetPayload {
    private String text;
    private String id;
    private Boolean truncated;
    private Long timestamp_ms;
    private String coordinates;
    private Integer retweet_count;
    private String created_at;
    private String user_location;

    private String username;
    private String user_screen_name;
    private Integer user_followers_count;


    public TweetPayload() {
    }

    public TweetPayload(String text, String id, Boolean truncated, Long timestamp_ms, String coordinates,
                        Integer retweet_count, String created_at,
                        String user_location, String username, String user_screen_name,
                        Integer user_followers_count) {
        this.text = text;
        this.id = id;
        this.truncated = truncated;
        this.timestamp_ms = timestamp_ms;
        this.coordinates = coordinates;
        this.retweet_count = retweet_count;
        this.created_at = created_at;
        this.user_location = user_location;
        this.username = username;
        this.user_screen_name = user_screen_name;
        this.user_followers_count = user_followers_count;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getTruncated() {
        return truncated;
    }

    public void setTruncated(Boolean truncated) {
        this.truncated = truncated;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Long getTimestamp_ms() {
        return timestamp_ms;
    }

    public void setTimestamp_ms(Long timestamp_ms) {
        this.timestamp_ms = timestamp_ms;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public Integer getRetweet_count() {
        return retweet_count;
    }

    public void setRetweet_count(Integer retweet_count) {
        this.retweet_count = retweet_count;
    }

    public String getUser_location() {
        return user_location;
    }

    public void setUser_location(String user_location) {
        this.user_location = user_location;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUser_screen_name() {
        return user_screen_name;
    }

    public void setUser_screen_name(String user_screen_name) {
        this.user_screen_name = user_screen_name;
    }

    public Integer getUser_followers_count() {
        return user_followers_count;
    }

    public void setUser_followers_count(Integer user_followers_count) {
        this.user_followers_count = user_followers_count;
    }
}
