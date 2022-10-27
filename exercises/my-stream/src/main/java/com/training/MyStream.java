package com.training;

//import org.apache.kafka.clients.consumer.*;
//import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.*;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.*;

import java.util.*;
import java.util.regex.*;

public class MyStream {
    
    public static void main( String[] args ) throws Exception {

        // Configure the KafkaStream
        Properties kafkaStreamConfiguration = createKafkaStreamConfiguration();
        // Configure the Process that our KafkaStream needs to do
        Topology myCustomTopology           = createOurProcessLogic();
        // Create a KafkaStream (process, configuration)
        KafkaStreams streams                = new KafkaStreams( myCustomTopology , kafkaStreamConfiguration );
        // Start the process in the server
        streams.start();
        // We will need to keep this program running... The one that is creating an controlling the KafkaStreams
        keepThisProgramRunning();
        // Stop the process in the server
        streams.close();
        
    }

    private static void keepThisProgramRunning(){
        try{
            while(true){
                Thread.sleep(300000);
            }
        }catch(Exception e){
        }
    }


    private static Properties createKafkaStreamConfiguration(){
        Properties configuration=new Properties();
        configuration.put( StreamsConfig.BOOTSTRAP_SERVERS_CONFIG              , "localhost:9092" );
        configuration.put( StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG        , Serdes.String().getClass().getName() );
        configuration.put( StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG      , "org.apache.kafka.common.serialization.Serdes$StringSerde" );
        
        configuration.put( StreamsConfig.APPLICATION_ID_CONFIG                 , "wordcounter" );
        configuration.put( StreamsConfig.STATE_DIR_CONFIG                      , "/tmp/mystream" );
        configuration.put( StreamsConfig.COMMIT_INTERVAL_MS_CONFIG             , 1000 );
        return configuration;
    }
    
    private static Topology createOurProcessLogic(){
        
        Pattern separateWords = Pattern.compile("[-\\s_.;,:()]+");
        Pattern removeHashtags = Pattern.compile("#");
        final Serde<String> wordSerde = Serdes.String();
        final Serde<Long> countSerder = Serdes.Long();
        
        
        StreamsBuilder builder= new StreamsBuilder();
        // Logic
        // The topic where my msgs are stored
        KStream<String,String> words = builder.stream("TWEETS");                                                // KStream<String,​String>
        // The actual process that I want to be applied to those msgs
                // Twtter trendingTopics
        words                
                .flatMapValues(value -> Arrays.asList(separateWords.split(value)) )
                .filter((key, word) -> word.startsWith("#"))
                .mapValues( word -> word.toUpperCase())
                .flatMapValues(value -> Arrays.asList(removeHashtags.split(value)) )
                .filterNot((key, word) -> word.equals(""))
                .groupBy((key, word) -> word)   // SELECT count(*) FROM TABLE GROUP BY word;
                .count()  // Reduce
        // The topic where my processed msgs should be stored
                .toStream()
                .to("HASHTAGS", Produced.with(wordSerde,countSerder));
        /////////
        return builder.build();
    }
    
}
