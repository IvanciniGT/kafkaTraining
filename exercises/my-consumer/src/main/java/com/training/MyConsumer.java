package com.training;

import org.apache.kafka.clients.consumer.*;

import java.util.Properties;
import java.util.Arrays;
import java.time.Duration;

public class MyConsumer {
    
    public static void main( String[] args ) throws Exception {
        MyConsumer myConsumer                           = new MyConsumer();
        // 1 - Create configuration
        Properties configuration                        = myConsumer.createConsumerConfiguration();
        // 2 - Create prooducer with taht condifguration
        Consumer<String, String> myActualKafkaConsumer  = myConsumer.createConsumer(configuration);
        
        
        // 3 - Subscribe to a List of Topics
        myActualKafkaConsumer.subscribe( Arrays.asList("TOPICA") ); 
        // Move to topic 0
        myActualKafkaConsumer.assignment().forEach(topic -> {
            myActualKafkaConsumer.seek(topic,0);
        });
        myActualKafkaConsumer.poll(0); // To force KAfka to set a commit offset for the first time. Actually 0... or the earliest
        // 4 - Read Messages
        
        //while(true){
        ConsumerRecords<String, String> messages = myActualKafkaConsumer.poll(Duration.ofMillis(100));
        for (ConsumerRecord<String, String> message : messages){
            System.out.println("Message Received:");
            System.out.println("   Offset: "+ message.offset());
            System.out.println("   Key:    "+ message.key());
            System.out.println("   Value:  "+ message.value());
            System.out.println("   Partition:  "+ message.partition());
        }
        //Thread.sleep(100);
        // myActualKafkaConsumer.commitSync(); // if ENABLE_AUTO_COMMIT_CONFIG = "false"
        //}
        
        
        // 5 - Close the consumer
        // We are not going to be creating a kafka consumer... sending a message and closing it rigth after that
        // What we are going to do is:
        // 1 - Create a Consumer
        // 2 - That consumer is going to be there for ever sending msgs
        // 3 - If the app stops... Ima close the consumer.
        
        //myActualKafkaConsumer.close();
        
    }

    public Properties createConsumerConfiguration(){
        // Create a Properties to hold the Consumer configuration
        Properties myConsumerConfiguration = new Properties();
        // The Kafka Server (BROKER or BROKERs)
        myConsumerConfiguration.put( ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,   "localhost:9092");
        // Let KAFKA know Automatically that we received the message
        myConsumerConfiguration.put( ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,  "true"); //false
        // Setup the consumer group
        myConsumerConfiguration.put( ConsumerConfig.GROUP_ID_CONFIG,            "MY_JAVA_CONSUMERS_8");
            // "all"   =   "-1"    Kafka is going to wait for all isr to have stored the msgs before ack
            // "0"                 The consumer is not going to wait for a confirmation
            // "1"                 Kafka is going to wait for the leader to have stored the msgbefore ack
        // Every single msg contains a KEY and a BODY
        // But.. what do we send to Kafka? bytes
        // Key  12345         -> Deserialize
        // Body "Whatever"    -> Deserialize
        myConsumerConfiguration.put( ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                                     "org.apache.kafka.common.serialization.StringDeserializer");
        myConsumerConfiguration.put( ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                                     "org.apache.kafka.common.serialization.StringDeserializer");
        // Please read msgs from the beginning
        myConsumerConfiguration.put( ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
                                     "earliest");
        return myConsumerConfiguration;
    }
            
    public Consumer<String, String> createConsumer(Properties configuration){
        // Define a Consumer with the provided configuration
        return new KafkaConsumer<>(configuration);
        // This is equivalent as when we were executing:
        // $ bin/kafka-console-consumer.sh --consumer.config config/consumer.conf
    }            
    
}

/*
                                KAFKA CLUSTER
MyConsumer                      Broker0
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
