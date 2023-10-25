package wikimedia.tutorial;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.launchdarkly.eventsource.MessageEvent;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;

public class WikimediaHandler implements BackgroundEventHandler{
    WikimediaHandler(KafkaProducer<String, String> producer, String topic) {
        this.mProducer = producer;
        this.mTopic = topic;
    }
    @Override
    public void onOpen() throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onOpen'");
    }

    @Override
    public void onClosed() throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onClosed'");
    }

    @Override
    public void onMessage(String event, MessageEvent messageEvent) {
        logger.info(messageEvent.getData());
        mProducer.send(new ProducerRecord<>(mTopic, messageEvent.getData()));
    }

    @Override
    public void onComment(String comment) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onComment'");
    }

    @Override
    public void onError(Throwable t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onError'");
    }
    
    private Logger logger = LoggerFactory.getLogger(WikimediaHandler.class);
    private KafkaProducer<String, String> mProducer;
    private String mTopic;
}
