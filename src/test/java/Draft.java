import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

public class Draft {

    @Test
    public void draft() throws IOException {
        String str = "{\n" +
                "\n" +
                "    \"filter_level\":\"low\",\n" +
                "    \"retweeted\":false,\n" +
                "    \"in_reply_to_screen_name\":null,\n" +
                "    \"possibly_sensitive\":false,\n" +
                "    \"truncated\":false,\n" +
                "    \"lang\":\"en\",\n" +
                "    \"in_reply_to_status_id_str\":null,\n" +
                "    \"id\":778904425243049984,\n" +
                "    \"extended_entities\":{},\n" +
                "    \"in_reply_to_user_id_str\":null,\n" +
                "    \"timestamp_ms\":\"1474540260979\",\n" +
                "    \"in_reply_to_status_id\":null,\n" +
                "    \"created_at\":\"Thu Sep 22 10:31:00 +0000 2016\",\n" +
                "    \"favorite_count\":0,\n" +
                "    \"place\":null,\n" +
                "    \"coordinates\":null,\n" +
                "    \"text\":\"RT @Arianna8927: Trump should listen to the guy who tweeted this:\\n\\nhttps://t.co/XzYSrIrPpM\",\n" +
                "    \"contributors\":null,\n" +
                "    \"retweeted_status\":{},\n" +
                "    \"geo\":null,\n" +
                "    \"entities\":{},\n" +
                "    \"is_quote_status\":false,\n" +
                "    \"source\":\"<a href=\\\"http://twitter.com/download/android\\\" rel=\\\"nofollow\\\">Twitter for Android<\\/a>\",\n" +
                "    \"favorited\":false,\n" +
                "    \"in_reply_to_user_id\":null,\n" +
                "    \"retweet_count\":0,\n" +
                "    \"id_str\":\"778904425243049984\",\n" +
                "    \"user\":{\n" +
                "        \"location\":\"United States\",\n" +
                "        \"default_profile\":true,\n" +
                "        \"profile_background_tile\":false,\n" +
                "        \"statuses_count\":4617,\n" +
                "        \"lang\":\"en\",\n" +
                "        \"profile_link_color\":\"0084B4\",\n" +
                "        \"profile_banner_url\":\"https://pbs.twimg.com/profile_banners/2907521684/1463929258\",\n" +
                "        \"id\":2907521684,\n" +
                "        \"following\":null,\n" +
                "        \"protected\":false,\n" +
                "        \"favourites_count\":2880,\n" +
                "        \"profile_text_color\":\"333333\",\n" +
                "        \"verified\":false,\n" +
                "        \"description\":\"mom, nurse casemanager w/master degree &six sigma green belt. ne sport fan, poker player, avid reader&polite debater #equalityforall, #womensrights #uniteblue\",\n" +
                "        \"contributors_enabled\":false,\n" +
                "        \"profile_sidebar_border_color\":\"C0DEED\",\n" +
                "        \"name\":\"Pamela\",\n" +
                "        \"profile_background_color\":\"C0DEED\",\n" +
                "        \"created_at\":\"Sat Nov 22 21:35:06 +0000 2014\",\n" +
                "        \"default_profile_image\":false,\n" +
                "        \"followers_count\":1305,\n" +
                "        \"profile_image_url_https\":\"https://pbs.twimg.com/profile_images/767404088866680832/Ik4NMRk7_normal.jpg\",\n" +
                "        \"geo_enabled\":true,\n" +
                "        \"profile_background_image_url\":\"http://abs.twimg.com/images/themes/theme1/bg.png\",\n" +
                "        \"profile_background_image_url_https\":\"https://abs.twimg.com/images/themes/theme1/bg.png\",\n" +
                "        \"follow_request_sent\":null,\n" +
                "        \"url\":null,\n" +
                "        \"utc_offset\":null,\n" +
                "        \"time_zone\":null,\n" +
                "        \"notifications\":null,\n" +
                "        \"profile_use_background_image\":true,\n" +
                "        \"friends_count\":3359,\n" +
                "        \"profile_sidebar_fill_color\":\"DDEEF6\",\n" +
                "        \"screen_name\":\"561Pamela\",\n" +
                "        \"id_str\":\"2907521684\",\n" +
                "        \"profile_image_url\":\"http://pbs.twimg.com/profile_images/767404088866680832/Ik4NMRk7_normal.jpg\",\n" +
                "        \"listed_count\":7,\n" +
                "        \"is_translator\":false\n" +
                "    }\n" +
                "\n" +
                "}";
        TweetPayload a = getPayloadFromMessage(str);
        String s = "CREATE TABLE tweets_payload (" +
                "text varchar," +
                "id varchar," +
                "truncated boolean," +
                "timestamp_ms varchar," +
                "coordinates varchar," +
                "retweet_count int," +
                "created_at varchar," +
                "user_location varchar," +
                "username varchar," +
                "user_screen_name varchar," +
                "user_followers_count int," +
                "PRIMARY KEY (created_at, timestamp_ms)" +
                ");";

        String z = "CREATE TABLE tweets_hashtags (" +
                "text varchar," +
                "ts varchar," +
                "hashtag varchar," +
                "PRIMARY KEY (hashtag, ts)" +
                ");";
        System.out.println(z);
        System.out.println();
    }

    private static TweetPayload getPayloadFromMessage(String tweetJsonFull) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode fullJson = mapper.readTree(tweetJsonFull);

        TweetPayload tweetPayload = new TweetPayload();

        tweetPayload.setId(fullJson.get("id").asText());

        tweetPayload.setText(fullJson.get("text").asText());

        tweetPayload.setTruncated(fullJson.get("truncated").asBoolean());

        tweetPayload.setTimestamp_ms(fullJson.get("timestamp_ms").asLong());

        tweetPayload.setCoordinates(fullJson.get("coordinates").asText());

        tweetPayload.setRetweet_count(fullJson.get("retweet_count").asInt());

        tweetPayload.setCreated_at(fullJson.get("created_at").asText());

        JsonNode user = fullJson.get("user");

        tweetPayload.setUser_location(user.get("location").asText());

        tweetPayload.setUsername(user.get("name").asText());

        tweetPayload.setUser_screen_name(user.get("screen_name").asText());

        tweetPayload.setUser_followers_count(user.get("followers_count").asInt());

        return tweetPayload;
    }


}
