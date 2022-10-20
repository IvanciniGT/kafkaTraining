
                                    Kafka Cluster
        
    Producer    >>>>>>>>>>>>.       Broker 1                                        SUBSCRIBE
                                        T1_P1_R1 [ M1, M3, M5 ]                 T1 <------ CG1_C1.  M1 M3 M4
                                            offset  0.  1.  2                   T2 <------   
                                    Broker 2
                                        T1_P1_R2 [ M1, M3, M5 ]                 T1 <------ CG1_C2.  M2 M5
                                            offset  0.  1.  2                   T2 <------
                                    Broker 3
                                        T1_P2_R1 [ M2, M4 ]                     T2 <------ CG2_C1.  M2
                                            offset  0.  1
                                    Broker 4
                                        T1_P2_R2 [ M2, M4 ]                     T2 <------ CG2_C2.  M4
                                            offset  0.  1                       T2 <------ CG2_C3.  Nothing
                            
                                    Topic T1
                                        Partitions : 2
                                        Replicas: 2
                                    
    Producer:
        M1 -> T1_P1
        M2 -> T1_P2
        M3 -> T1_P1
        M4 -> T1_P2
        M5 -> T1_P1

# How consumers consume MSGs from KAFKA

Does Kafka remove msgs from a Topic (Partition) once a consumer read them?  NO
Kafka is going to keep msgs "forever".

But... them.... How does a consumer know the msg that it has to read?
Kafka keeps track of the last offset a consumer group got


# CONSUMER_GROUP

Is a group of consumers (may be 1 or a bunch of them)
that they all consume messages as if they were just 1




JAVA

.java ----------> .class. -----------> RUN 
        javac       bytecode.  java


JAVA 
    Kotlin ---> Android
    Scala ----> BigData 


# Start a Kafka Cluster

bin/zookeeper-server-start.sconfig/zookeeper.properties 
bin/kafka-server-start.sh config/server.properties 

# Create a new Topic
bin/kafka-topics.sh --create --topic firsttopic --bootstrap-server localhost:9092



bin/kafka-topics.sh --create --topic firsttopic --bootstrap-server localhost:9092 \
                    --replication-factor 3 --partitions 4

secondtopic 
    4 partitions and 3 replicas





# List current topics
bin/kafka-topics.sh --list --b

# Get a topic summary
bin/kafka-topics.sh --describe --topic firsttopic  --bootstrap-server localhost:9092


Topic: firsttopic       
TopicId: jjyXg4sSQ2iFhQu3W55GRw 
PartitionCount: 1       
ReplicationFactor: 1    
                         Partition: 0    Leader: 0       Replicas: 0,1.         Isr: 0,1 <<< Those are the Replicas that are synchronized
                         Partition: 1    Leader: 1       Replicas: 0,1          Isr: 0,1
                         
bin/kafka-console-producer.sh --topic firsttopic  --bootstrap-server localhost:9092
bin/kafka-console-consumer.sh --topic firsttopic  --bootstrap-server localhost:9092 --from-beginning



Producers       LoadBalancer                Broker 0.   T3.P0.R0 (100Gb)
                                                Local HDD -----> Storage Cabinet (3 HDD)
                HAProxy                     Broker 1.   T3.P0.R1
                                                Local HDD -----> Storage Cabinet (3 HDD)
                                            Broker 2 NOT WORKING ANYMORE.   T3.P0.R2 ---- NOT ALIVE ANYMORE
                                                Local HDD -----> Storage Cabinet (3 HDD)
                                            
                                            
Nowadays we are not going to have a human being in fornt of my production Environment.
Nowadays, my production environment is run by Kubernetes