package com.training;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

public class MyProducer {
    
    public static void main( String[] args ) {
        MyProducer myProducer                           = new MyProducer();
        // 1 - Create configuration
        Properties configuration                        = myProducer.createProducerConfiguration();
        // 2 - Create prooducer with taht condifguration
        Producer<String, String> myActualKafkaProducer  = myProducer.createProducer(configuration);
        // 3 - Create a message
        ProducerRecord<String, String> myMessage        = myProducer.createMessage("TOPIC","Key1","Value1");
        // 4 - Send Message
        myActualKafkaProducer.send(myMessage /*, CallBack*/);
        // 5 - Close the producer
        // We are not going to be creating a kafka producer... sending a message and closing it rigth after that
        // What we are going to do is:
        // 1 - Create a Producer
        // 2 - That producer is going to be there for ever sending msgs
        // 3 - If the app stops... Ima close the producer.
        
        myActualKafkaProducer.close();
        
    }

    public Properties createProducerConfiguration(){
        // Create a Properties to hold the Producer configuration
        Properties myProducerConfiguration = new Properties();
        // The Kafka Server (BROKER or BROKERs)
        myProducerConfiguration.put( ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,   "localhost:9092");
        // The number of replicas where out msg has to be stored before having a confirmation from KAFKA
        myProducerConfiguration.put( ProducerConfig.ACKS_CONFIG,                "all");
            // "all"   =   "-1"    Kafka is going to wait for all isr to have stored the msgs before ack
            // "0"                 The producer is not going to wait for a confirmation
            // "1"                 Kafka is going to wait for the leader to have stored the msgbefore ack
        // Every single msg contains a KEY and a BODY
        // But.. what do we send to Kafka? bytes
        // Key  12345         -> Serialize
        // Body "Whatever"    -> Serialize
        myProducerConfiguration.put( ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                                     "org.apache.kafka.common.serialization.StringSerializer");
        myProducerConfiguration.put( ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                                     "org.apache.kafka.common.serialization.StringSerializer");
        return myProducerConfiguration;
    }
            
    public Producer<String, String> createProducer(Properties configuration){
        // Define a Producer with the provided configuration
        return new KafkaProducer<>(configuration);
        // This is equivalent as when we were executing:
        // $ bin/kafka-console-producer.sh --producer.config config/producer.conf
    }            
    
    public ProducerRecord<String, String> createMessage( String topic_name,String key, String value){
        return new ProducerRecord(topic_name, key,  value);
    }
    

        // Writing ProducerRecords messages to kafka by using our Producer

    
}

/*
                                KAFKA CLUSTER
MyProducer                      Broker0
                                Broker1
                                Broker2

                                ZOOKEEPER
                                TopicA . Partitions:    Replicas.    ISR
                                            0            0 1         0 1
                                            1            1 2         1 
                                

                                    BACKEND
                                        NodeJS
BROWSER                                     Backend JS Code -> KAFKA
  Frontend JS (Angular, React) -------->
    x (contact to KAFKA directly)
    
                                                               RabbitMQ
*/
