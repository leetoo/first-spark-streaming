import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import scala.Tuple3;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.datastax.spark.connector.japi.CassandraJavaUtil.mapToRow;
import static com.datastax.spark.connector.japi.CassandraJavaUtil.mapTupleToRow;
import static com.datastax.spark.connector.japi.CassandraJavaUtil.someColumns;
import static com.datastax.spark.connector.japi.CassandraStreamingJavaUtil.javaFunctions;


/**
 * Consumes messages from one or more topics in Kafka and does wordcount.
 *
 * Usage: JavaKafkaToCassandraHelloWorld <zkQuorum> <group> <topics> <numThreads>
 *   <zkQuorum> is a list of one or more zookeeper servers that make quorum
 *   <group> is the name of kafka consumer group
 *   <topics> is a list of one or more kafka topics to consume from
 *   <numThreads> is the number of threads the kafka consumer should use
 *
 * To run this example:
 *   `$ bin/run-example org.apache.spark.examples.streaming.JavaKafkaToCassandraHelloWorld zoo01,zoo02, \
 *    zoo03 my-consumer-group topic1,topic2 1`
 */

public final class JavaKafkaToCassandraHelloWorld {

    private JavaKafkaToCassandraHelloWorld() {
    }

    public static void main(String[] args) throws Exception {


        SparkConf sparkConf = new SparkConf()
                .setAppName("JavaSparkKafka")
                .set("spark.cassandra.connection.host", "localhost")
                .set("spark.cassandra.connection.port", "9042");
        // Create the context with 2 seconds batch size
        JavaStreamingContext jssc = new JavaStreamingContext(sparkConf, new Duration(2000));

        int numThreads = 1;
        Map<String, Integer> topicMap = new HashMap<>();
        String[] topics = {"tweetsTopic"};
        for (String topic : topics) {
            topicMap.put(topic, numThreads);
        }

        JavaPairReceiverInputDStream<String, String> messages =
                KafkaUtils.createStream(jssc, "localhost:2181", "group", topicMap);


        JavaDStream<Tweet> tweets = messages.map(m -> new Tweet(m._2()));
        JavaDStream<TweetPayload> tweetPayload = messages.map(m -> getPayloadFromMessage(m._2()));
        JavaDStream<Tuple3<String, String, String>> hashtagMessageTs = messages.map(m -> getHashtagMessageTsTuple(m._2()));

        javaFunctions(tweets).writerBuilder("twitter", "tweets", mapToRow(Tweet.class)).saveToCassandra();
        javaFunctions(tweetPayload).writerBuilder("twitter", "tweets_payload", mapToRow(TweetPayload.class)).saveToCassandra();

        javaFunctions(hashtagMessageTs).writerBuilder("twitter", "tweets_hashtags", mapTupleToRow(
                String.class,
                String.class,
                String.class
        )).withColumnSelector(someColumns("hashtag", "text", "ts"))
                .saveToCassandra();

        jssc.start();
        jssc.awaitTermination();
    }

    private static Tuple3<String, String, String> getHashtagMessageTsTuple(String tweetJsonFull) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode fullJson = mapper.readTree(tweetJsonFull);

        String hashtag;

        String text = fullJson.get("text").asText();
        if (text.toLowerCase().contains("hillary"))
            hashtag = "hillary";
        else if (text.toLowerCase().contains("trump"))
            hashtag = "trump";
        else
            hashtag = "none";

        String ts = fullJson.get("timestamp_ms").asText();


        return new Tuple3<>(hashtag, text, ts);

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