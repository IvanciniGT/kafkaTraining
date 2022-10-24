package com.training;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.*;
import java.util.Map;


public class MyPartitioner implements Partitioner  {
    
    public void close(){}
    public void configure(Map<String,?> configs){}

    public int partition(String topic, Object key, byte[] keyBytes, 
                         Object value, byte[] valueBytes, Cluster cluster){
        // Imagine we have 10 different values for SEVERITY
        /*
        KEY 4, 5, 6, 7, 8 -> PARTITION 1
        KEY 9 -> PARTITION 2
        KEY 10 -> PARTITION 3
        KEY any different value -> PARTITION 0
        */
        if (key.equals("9")){
            return 2;
        }
        else if (key.equals("10")){
            return 3;
        }
        else if (key.equals("4")||key.equals("5")||key.equals("6")||key.equals("7")||key.equals("8")){
            return 1;
        }
        else{
            return 0;
        }
    }
}
