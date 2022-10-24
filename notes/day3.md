First Installation YESTERDAY

    ZOOKEEPER -> default zookeeper.properties -> /tmp/zookeeper
                cluster.id=1yGsNnHZTi-tv1_vLUPkdw

    BROKER 0  -> default server.properties    -> /tmp/kaffa-logs
                                                      ^ ??? Logs
                                                            Msgs
                                                            
                                                Any configuration?

    We have a Topic.... Where is that info?     -> Zookeeper    
    We have a ClusterID.... Where is that info? -> Zookeeper
                                                   Brokers
                                                       cluster.id=1yGsNnHZTi-tv1_vLUPkdw

SECOND Installation. NO... the same cluster

    ZOOKEEPER -> default zookeeper.properties -> /tmp/zookeeper
                cluster.id=1yGsNnHZTi-tv1_vLUPkdw

    BROKER 0  -> server0.properties    -> ~/.../dafka/broker0
                                                        cluster.id=1yGsNnHZTi-tv1_vLUPkdw
                                                                   
    BROKER 1  -> server1.properties    -> ~/.../dafka/broker1
    BROKER 2  -> server2.properties    -> ~/.../dafka/broker2

    What was happening with Broker0, at this point?
    Did we have the previous logs inside this broker? NO
        Just the information about a Topic being created

THIRD START 
    ZOOKEEPER -> default zookeeper.properties -> /tmp/zookeeper
        cluster.id = HyZRY8xPT1CRWpVz9BBvkg
    BROKER 0  -> server0.properties    -> ~/.../dafka/broker0
                                                        cluster.id=1yGsNnHZTi-tv1_vLUPkdw
    FATAL ERROR !!!!!

FOURTH INSALLATION. The good ONE

    ZOOKEEPER -> zookeeper.properties  -> ~/.../kafka/zookeeper
    BROKER 0  -> server0.properties    -> ~/.../kafka/broker0
    BROKER 1  -> server1.properties    -> ~/.../kafka/broker1
    BROKER 2  -> server2.properties    -> ~/.../kafka/broker2


# The number of threads that the server uses for receiving requests from the network 
and sending responses to the network
num.network.threads=3

# The number of threads that the server uses for processing requests, 
which may include disk I/O -> Depends on our HDD speed
                                             SDD
                                             NVME
                                             Cabinet (cache)
num.io.threads=8.  BOTTLENECK

Kafka requires a bunch of CPUs???
    It depends? Depending on the tranformations that we could be doing with the msgs

Kafka stores everything into our DISK
                        
                        Receive Inputs       Transform.      Receive Inputs
Producer (send MSG) --> ----> MSG ---> QUEUE ------->. QUEUE ---->
                        ---->                ------->        ---->
                        ---->                ------->        ---->
                    < --                     ------->
                                             ------->
                                             ------->
                                             ------->
                                             ------->
isr: In Sync Replicas

    PRODUCER---------->>            KAFKA CLUSTER
                                        TOPIC.A
                                                P0
                                                P1
                                                P2
     msg ->
        key ----> numeric hash % # Brokers
        body
        topic
        partition

# Create a topic
# Start a console producer
# Send a bunch of msgs
# Start a console consumer
# Start another console consumer
# Send a bunch of msgs



# Consumer group

A consumer group is a group of comsumers... working as if they were just 1.... but....
In addition, each consumer within a consumer group is going to be tied to a single partition.
Only 1 consumer from a consumer-group will be tied to a single partition.
But 1 consumer can be tied to multiple partitions

    Consumer 1 ------ Partition 1
               \_____ Partition 2
    Consumer 2 -------Partition 3
    
    
Producing -----> Kafka Cluster
                    TopicA                                       CG 1 - Consumer 1' -> Notification -> EMAIL
                                                                            P1 & P3
                                P0 P2,(BROKER 0)  ------------          Consumer 1  -> Notification -> EMAIL
                                P1 (BROKER 1)   _____________/              P0
  MSG: Notifications                                             CG 2.  Consumer 2  -> Notification -> SMS
                                                                            P0 
                                                                        Consumer 2'
                                                                            P1
                                                                        Consumer 2'' 
                                                                            P2
                                                    
How many producers can send MSGS to TopicA P1? We ha no limit here. We can have a bunch
- If we have too many... what could happen?
- If I have only 1... but dawm.... taht one is sending too way many msgs... what could happen?
    With BROKER 0?
        That broker may receive a huge workload... And can be not enough resources in that broker to process all those incomming msg

What can we do here, to solve this situation? To create a new Partition
In that way, the workload is going to be splitted between them.
                          
First thing .... I need to store a msg ASAP.
Second thing ... I need to process/consume that msg

Partitions allow to improve brokers performance
           but also to have more consumers

bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --list
bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group test-consumer-group


--------------------------------------------------------------------
Tomorrow we are going to create a Producer in JAVA
Tomorrow we are going to create a Consumer in JAVA

KAFKA API 
    package called: producer
    
        Producer
            (Initialize) < - ProducerConfig
                                Partitioner
            
            (Send)       < - ProducerRecord = MSG
                         < - CallBack function
            (CallBack)   - > RecordMetadata 
                                This contains information about the msg (Partition, offset, state)
                        
                        In case we have a problem, we will receive a call to our Callback function too
    
    package called: consumer
    
        Consumer
            (Initialize) < - ConsumerConfig
    
            (Subscribe)  < - Topic (In addition I will be able to supply the partition)
    
            (Read)       < - ConsumerRecords = MSG
                             send a msg to kafka (Confirmation msg) Ey Kafka I did get that msg. Thank you
                                manually / automatically

------
    Streams -> Installed inside the Kafka Cluster