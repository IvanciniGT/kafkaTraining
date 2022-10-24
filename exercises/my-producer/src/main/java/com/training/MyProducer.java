package com.training;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;

public class MyProducer implements Callback  {
    
    public static void main( String[] args ) {
        MyProducer myProducer                           = new MyProducer();
        // 1 - Create configuration
        Properties configuration                        = myProducer.createProducerConfiguration();
        // 2 - Create prooducer with taht condifguration
        Producer<String, String> myActualKafkaProducer  = myProducer.createProducer(configuration);
        // 3 - Create a message
                                                                                      //SEVERITY / RELEVANCE
        ProducerRecord<String, String> myMessage        = myProducer.createMessage("TOPIC","1","Value1");
        // 4 - Send Message
        myActualKafkaProducer.send(myMessage , myProducer);
        myMessage        = myProducer.createMessage("TOPIC","9","Value1");
        // 4 - Send Message
        myActualKafkaProducer.send(myMessage , myProducer);
        myMessage        = myProducer.createMessage("TOPIC","8","Value1");
        // 4 - Send Message
        myActualKafkaProducer.send(myMessage , myProducer);
        myMessage        = myProducer.createMessage("TOPIC","10","Value1");
        // 4 - Send Message
        myActualKafkaProducer.send(myMessage , myProducer);
        // 5 - Close the producer
        // We are not going to be creating a kafka producer... sending a message and closing it rigth after that
        // What we are going to do is:
        // 1 - Create a Producer
        // 2 - That producer is going to be there for ever sending msgs
        // 3 - If the app stops... Ima close the producer.
        
        myActualKafkaProducer.close();
        
        // Imagine we have 10 different values for SEVERITY
        /*
        KEY 1, 2, 3 -> PARTITION 0
        KEY 4, 5, 6, 7, 8 -> PARTITION 1
        KEY 9 -> PARTITION 2
        KEY 10 -> PARTITION 3
        */
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
        // Class containing the Partitioning algorithm
        myProducerConfiguration.put( ProducerConfig.PARTITIONER_CLASS_CONFIG,
                                     "com.training.MyPartitioner");
                                     
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
    public void onCompletion​(RecordMetadata metadata, Exception exception){
        if ( exception != null ){
            // In case we have an error... maybe we will retry to send the msg in a little bit... or whatever other thing we may need to do with it
            System.out.println("The message could not be send");
            System.out.println("     Message: " + metadata.toString() );
            System.out.println("   Exception: " + exception.getMessage());
        }else{
            System.out.println("The message was received and stored by Kafka");
            System.out.println("     Message: " + metadata.toString() );
        }
    }

    
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
