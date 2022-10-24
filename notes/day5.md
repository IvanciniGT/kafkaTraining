         
-----
If we work with a tool such as RabbitMQ, we will need to do a bunch of configuration inside RabbitMQ
Kafka works in a different way.... Completely differnret way.

Kafka is extremly simple... It makes nothing.
In Kafka all the logic of how its TOPICs works is under the client's (CONSUMER/PRODUCER) logic.

----
Each producer defines it own Partitioner... which is going to decide the target Partition of each message.
By default, this is happening:

    A msg wants to be sent
    Thant msg is going to have a KEY
    Key is transformed into a number (by using a hash algorithm)
    That hash algorithm is going to create random numbers
    We are going to divide that number into the NUMBER(amount) of PARTITIONS... 
    and we will get the remainder of that division
    Ths remainder is going to be between 0 and the amount of partitions less 1.
    That remainder is going to be the target partition

---
This is not enough

In Kafka we have a concept call KafkaStream
                                ___________
 A kafkaStream is a program that is going to Extract msgs from a TOPIC
                                          to process thos msgs 
                                          to store the result of that transformation back to Kafka, in a diferent Topic




KAFKA Cluster
    Broker 0
        TOPIC A.0
    Broker 1
        TOPIC A.1
        TOPIC B.0
    Broker 2
        TOPIC A.2
        TOPIC B.1
    Broker 3
        TOPIC A.3

    
    KafkaStreamController -> Machine
        start KafkStream

We are going to have program (KafkaStream) which is going to be started from any remote machine


By using KafkaStream we may:
- Filter msgs
- Route msgs to 1 or even a bunch of topics (Replication)
- Tranform the information


