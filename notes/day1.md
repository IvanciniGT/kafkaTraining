# What is Kafka?

A messaging broker (middleware)

# What is a messaging broker for?

- Allow asynchronous message send
    - To communicate different parts of my app ... or actually differents systems/services
- When we need to store information (logs: events + timestamp) ... for a long time? Actually we can
    - That is not the main purpose of Kafka.. But we have people doing that.
    - Kafka is not a DATABASE
- Kafka allow to transform & route msgs in the middle of that process.
    - Trasform: Filter, update, rich, throw away info or msgs

Do I need something like Kafka to comunicate systems/servcies/processes? 
- Performance problems / scalability
        Producer 1
        Producer 2    >>> KAFKA >>>   consumer 1
        Producer 3             (queue)
- Asynchronous communication

    You ---------- > Whatsapp < ------- You friend
    
        Consumer & Producer don't need to be present at the same time.
 
    You ---------- > phone < ------- You friend
    
    Maybe, I just need to add a task somewhere. To make sure that I'm not going to lose that info.
    I will process that info at certain point.
    
                    KAFKA 
        Website -> we have to Send an email
                            ^^
                            Email sender

## Who is going to receive information from KAFKA? NOBODY

Kafka is not a pushing messaging broker (such as RabitMQ)
Kafka is a pulling messaging broker

Why Kafka is so popular?
- Opensource? 
    Apache (Software development foundation) . They just manage projects. 
        We need developers: Confluent
                            People helping with the project
- Free? 
    Apache versión it is.
    Confluent version .. It depends on the license
- Is able to manage a huge a mount of information
- Is able to run in a bigdata infraestructure
- We love Kafka capabilities for data transformation


# BIGDATA

When we need to deal with:
- A huge amount of information
- Information which is being generated at a huge rate
- Complex information

BigData infraestructure: A net/grid/group of commody hardware(*1) working 
together as if they were just ONE.
(*1) Cheap and not that powerful computers 

In order to run a bigdata infraestruture, we need a software that allows to split 
the work into several pieces and sent each piece to a different computer for its management:
 Apache Hadoop: Allows us to create a BD infraestructure.
 We have software can that use hadoop to split its workloads: Such as KAFKA

Nowadays we have moved towards Microservices architectures -> A bunch of communications
                                                              Some of them will require a MB: Kafka

What are the main characteristics that are required in a Production Environment?
- High availability
    We TRY to make sure to accomplish with a certaing Time Service Agreement: 99%, 99,9% 99,99%
    We TRY to make sure that we are not going to lose information
        REPLICATION (process & data)
- Scalability
    Ability to adjust the infraestructure depending on the current needs (workload)
        REPLICATION. HORIZONTAL SCALABILITY. More copies (replicas) of the app running

    REPLICATION: Clusters (Set / Group) of computers
                 Clusters of process

KAFKA architecture & Terms
----------------------------

Whenever we run Kafka, it doesn't matter if we only have 1 copy of Kafka running,
we have a CLUSTER!
That cluster may consist of just 1 single Kafka process
    (We are not going to do this in a production enviroment)

CLUSTER is just a logical group(set) of Kafka processes
Each Kafka Process running inside a cluster is called BROKER, and it is identified by an ID:
BROKER_ID

Whenever we send a message to Kafka, where is the message stored? TOPIC

A TOPIC is a sequence of ordered messages (QUEUE- FIFO)

Kafka is going to split a TOPIC into PARTITIONS. 
    Each message is going to be stored only 1 one single partition
What can we achive with those PARTITIONS? Scalability

For each PARTITION we can make REPLICAS.
What can we achive with those REPLICAS? HIGH AVAILABILITY

                                        KAFKA CLUSTER
                                        
                                        Broker 1
                                            Part1R1* Part2R2
    Send a msg 1.                       Broker 2
      -> routed TopicA P1                   Part1R3  Part2R1* 
                                        Broker n
                                            Part1R2  Part2R3
                                        
                                        Part1R4, Part2R4 <<<< Unassigned
                                        
                                        TOPIC A:
                                            2 partitions
                                            4 replicas each partition
                                        
One broker (kafka server) only can hold 1 replica of each partition.

So... If I have 3 servers...
How many partitions can I create for a TOPIC? All the partitions that I want to create... It doesn't matter
How many replicas am I able to configure for a TOPIC? No more than 3

MSGS are stored inside a PARTITION in a file called LOG
A Partition is a sequence of LOGs
                                        
How many Brokers should I have in a production Environment? Minimum

1º In aproduction environment we usually store information in 3 different places


Apache ZOOKEPPER? 


-----

    Producer -> (A)-------------|
                                |--Broker 1 (A KAFKA PROCESS)
                                |    TOPIC_A_P1_R1 [*]
                                |--Broker 3
                                |   
                                |--Broker 2
                                |   TOPIC_A_P1_R2
    Producer -> (B)-------------|
                                    TOPIC_A: 1 partition x 2 replicias

Brokers... they will talk to each other... but they cannot promote theirselves to PRIMARY REPLICAS.
They need a vote from another broker: MAYORITY

Time ago, Zookeeper was in charge of those votes... an the election of a LEADER
And, I could have just 2 BROKERS... but 3 zookeepers

Nowadays, I can run Kafka without Zookeeper... But I need at least 3 nodes.

3 zookepers and 100 kafka nodes.

Time ago, Zookeper also was acting as a LoadBalancer. This is not how we work nowadays.
Nowadays I can send msgs directly to Kafka Brokers... and they communicate each other incomming messages



PRODUCER: Is any process sending msgs to Kafka
What extra information should be send?
    - TOPIC
    - PARTITION

I want to control the PARTITION where my msg is going to be stored... WHY?
I want the msgs splited into the partitions by using which algorith?
    ROUND_ROBIN? 3 PARTITIONS: 1, 2, 3, 1, 2, 3, 1, 2, 3
        Performance Storing information? GOOD
        Performance Reading? NOT THAT GOOD

TOPIC: EMAILS
Per country
    -> MSG in Spanish are stored in the same partition : Consumer -> Emails in Spanish
    -> MSG in English are stored in the same partition : Consumer -> Emails in English
    -> MSG in Italian are stored in the same partition : Consumer -> Emails in Italian

PRODUCER
    ROUND_ROBIN_PATTERN
    A different routing algorithm
    msg (ES) P1
    msg (EN) P2
    msg (IT) P3
    msg (ES) P1

                        Broker 1 HDD
                            TOPIC EMAILS P.0
                        Broker 2 HDD
                            TOPIC EMAILS P.1
                        Broker 3 HDD
                            TOPIC EMAILS P.2
I did lie to you !!!!!

Kafka TOPICs are not FIFO.
Can you guess what is FIFO in KAFKA? PARTITIONS

Each message is going to have a KEY and a BODY
By default, PRODUCERs use a round robin algorithm... not exactly... kind off

KEY -> NUMERIC HASH % Number or partitions -> 0.... (N-1)


INFORMATION -> HASH (summary)
The same information is always going to generate exactly the same HASH

8 | 3
  ----
-6   2
 2 < Module / remainder.  0....(divisor-1)

