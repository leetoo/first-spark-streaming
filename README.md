# first-spark-streaming

1. Run ZooKeeper. From Kafka dir:

        bin/zookeeper-server-start.sh config/zookeeper.properties

2. Run Kafka broker. From Kafka dir:

        bin/kafka-server-start.sh config/server.properties

3. Create Kafka Topic. From Kafka dir:

        bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic tweetsTopic

    You can create Simple console consumer just to be sure that tweets are being pushed. From Kafka dir:

        bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic tweetsTopic --from-beginning

4. Run Cassandra. From cassandra dir:

        bin/cassandra

5. Create cassandra keyspace, tables. From Kafka dir:

        CREATE KEYSPACE twitter WITH REPLICATION =  { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

        USE twitter;

        CREATE TABLE tweets (text varchar,ts varchar,hashtag varchar,PRIMARY KEY (hashtag, ts));

        CREATE TABLE tweets_hashtags (text varchar,ts varchar,hashtag varchar,PRIMARY KEY (hashtag, ts));

        CREATE TABLE tweets_payload (text varchar,id varchar,truncated boolean,timestamp_ms varchar,coordinates varchar,retweet_count int,created_at varchar,user_location varchar,username varchar,user_screen_name varchar,user_followers_count int,PRIMARY KEY (created_at, timestamp_ms));

6. Build project (mvn clean install), run it from spark directory

        bin/spark-submit --class JavaKafkaToCassandraHelloWorld --master local[2] /home/cloudera/IdeaProjects/TryingSparkStreaming/target/spark.streaming.first-1.0-SNAPSHOT-jar-with-dependencies.jar


6. Run flume agent with configuration from "flume.config"

