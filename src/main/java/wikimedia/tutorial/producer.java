package wikimedia.tutorial;

import java.net.URI;
// import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

// import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
// import org.apache.kafka.clients.producer.ProducerRecord;
// import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

import com.launchdarkly.eventsource.EventSource;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import com.launchdarkly.eventsource.background.BackgroundEventSource;


public class producer 
{
    public static void main(String[] args) throws InterruptedException
    {
        // topic name
        String topic = "topic_one";   
        // // key
        // String key = "key_one";
        // // value
        // String value = "Here is some random value string";
        // properties object
        Properties kafkaProps = new Properties();
        kafkaProps.put("bootstrap.servers","localhost:9092");
        // serializer
        kafkaProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        kafkaProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // producer
        KafkaProducer<String, String> producer = new KafkaProducer<String,String>(kafkaProps);
        
        // producerRecord
        // ProducerRecord<String,String> record = new ProducerRecord<>(topic, key, value);

        // // send producer with callback
        // producer.send(record, new Callback() {
        // public void onCompletion(RecordMetadata recordMetadata, Exception e) {
        //     Logger logger = LoggerFactory.getLogger(producer.class);
        //     if (e != null) {
        //         e.printStackTrace();
        //     }
        //     else {
        //         logger.info("Successfully received details: " + "\n" +
        //         "Topic metadata: " + recordMetadata.topic() + "\n" +
        //         "Partition metadata: " + recordMetadata.partition() + "\n" +
        //         "Offset metadata: " + recordMetadata.offset());
        //     }
        // }
        // });

        BackgroundEventHandler eventHandler = new WikimediaHandler(producer, topic);

        String url = "https://stream.wikimedia.org/v2/stream/recentchange";
        BackgroundEventSource eventSource = new BackgroundEventSource.Builder(eventHandler, new EventSource.Builder(URI.create(url))).build();
        eventSource.start();
        TimeUnit.MINUTES.sleep(10);
        // EventHandler eventHandler;
        // EventSource.Builder builder = new Builder(eventHandler, URI.create(url));
        // EventSource eventSource = builder.build();
        // eventSource.start();

        // close producer 
        // producer.close(Duration.ofMillis(1000));
    }
    

}
