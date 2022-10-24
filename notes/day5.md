         
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
        start KafkaStream

We are going to have program (KafkaStream) which is going to be started from any remote machine


By using KafkaStream we may:
- Filter msgs
- Route msgs to 1 or even a bunch of topics (Replication)
- Tranform the information


----
Have you ever work with Functional Programming before?

Programming paradigms: Styles of writing out code
- Imperative
    -   if else for while
- Procedural. That is when we have a the opportunity (ability) to use and define custom (functions / procedures) methods
    - privacy modifier + returned type + name of the function + arguments
- Functional programming. When we can reference a function from a variable
    - C, C#, PYTHON
    - =>
- Object Oriented Programming: We work with those concepts called CLASS / INSTANCE / INHERITANCE
    - class interface extends implements override
In java since version 10, 11 we can create scripts... with no classes at all



    

Programming languages
            _________ same as Italian, Spanish or English

Place the chair below the window!           Imperative.   ACTION needed to achieve an state
A chair needs to be placed under the window Declarative.  STATE
-------------------------------------------------------------------
There is a chair below the window           Affirmative
There is no chair below the window          Negative
Is there a chair below the window.          Interrogative. Question


-----

MSG ---> Count the amount of times each word appears in the text
 I can summarize (sum) all those value....
    TWITTER Trending topics
    #Hashtag
    
Create a publication for my WALL -> msg 
what do I need to do with that msg?
    - Publish that in my wall / persist DB
    - Count the Hashtags
    
    
Publish tweet -> Synchronous or an Asyncronous operation?

|----Screen----------------------------------|
|                      NOTIFICATIONS+1       |
|                                            |
|                                            |
|                                            |
|       FORM to publish -> SEND              |
|               Contains a picture           | scanned to perform a face recognition?
|                                            |


EVENT           LOGGED
SEND ----> Add that msg to KAFKA
            TOPIC ---> JUST-CREATED-MSGS    > ---- Which is going to persist that msg in the DB
                                            > ---- Which is going to detect and sum hashtags
                                                    list of Hashtags. ^^^ KafkaStream
                                                        |
                       HASHTAGS         < ---------------
                         ^
                         |_  Consumer (Each hour ) -> SUM Those... and order them 
                                                        Filter: TOP 10 
                                                        
KafkaStream

Program.... 
    Connect to a kafka cluster
    Define a program that needs to be executed inside the kafka cluster
        FUNCTIONAL PROGRAMMING
    start that program
    It can finish or be doing that FOREVER... dependening on the requirements.