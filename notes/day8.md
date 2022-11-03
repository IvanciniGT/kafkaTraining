What KAFKA is!
    cluster : BROKERS.... topics (partitions + replcias)
Producers
Consumers
Streams
To understand how to use KAFKA to solve common communiication Scenerios
Connect
----

Kafka Connect is a framework for connecting KAFKA with external systems

Until now, whenever we wanted to connect Kafka with an external app / system we had to deliver
out custom PRODUCER or CONSUMER.

BUT, there are many standarizaed apps/external systems that we usually integrate with kafka.
And in those scenerios we don't need to develop our custom PRODUCER or CONSUMER.

We have a bunch of predefined PRODUCERS&CONSUMERS

Apache Kafka doesn't come with a number of connectors. Just a couple of them... as an example.

We said that Kafka is "produced" by a company called Confluent

Apache Kafka: Opensource software <<< Confluent
Confluent Distributions of Kafka:
- Opensource version... which is actually free
                        + a number of connectors 
- Paid versions.

Kafka connect works in 2 different modes:
- Standalone: Which is great for learning purposes
- Distribute mode (Cluster) -> Production environment

Kafka connect:
- SOURCE CONNECTOR: This defines the source of information...How to extract the information:
    - A file... where each line of text could be a msg
    - A SQL database
    - MQTT Server / Client /Producer
    >> PRODUCER
- SINK CONNECTOR: This defines where the information is going to be stored at the end.
    >> CONSUMER   
- CONNECT/WORKER: ~ KAFKA STREAM. This defines how to connect the sources with the sink...
                            And the transformations....


----
Kafka stream allows us to define a program to be executed inside our cluster

Kafka connect allows us to define a program to be executed:
- Standalone
- Inside a cluster

Whenever we work with Kafka connect we have to define:
- At least 1 connector (source or sink)
- A worker: The program which is going to execute the connectors.


    
                    Standalone 
                      Kafka Connect (worker) ---->   Kafka Cluster
                        connectors:                     Broker 1
test.txt < --------------- FileSource                   Broker 2
test.sink.txt < ---------- FileSink                     Broker 3