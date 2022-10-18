
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

