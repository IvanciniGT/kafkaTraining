WebUI                           KAFKA                   BACKEND
Native Smartphone App
    tweets          ---->    TWEETS         < ----------  Persist Tweet in DB
                                |    |           
                                |    V
                                | HASHTAGS       < -----  Calculate trending topics
                                v
                                @                          Send a notification to a user which is cited
    
    
I won't lose information in case the BACK END is not runnng

Tweet

Hi! My friends... Here I am in the bathroom... Do you want a pic ;)
#AtHome #BathroomLove
Hi! My friends... Here I am in the bed... Do you want a pic ;)
#AtHome #BedLove
Hi! My friends... Here I am in Twitter... Do you want a pic ;)
#AtHome #TwiterLove

ATHOME          3
BATHROOMLOVE    1
TwiterLove      1
BEDLOVE         1


Hi! My friends... Here I am in the bathroom... Do you want a pic ;)
#AtHome #BathroomLove
Hi! My friends... Here I am in Twitter... Do you want a pic ;)
#AtHome #TwiterLove

ATHOME          2
BATHROOMLOVE    1
BEDLOVE         1

PROCESS:
1- Get Words Starting with "#"
  >  Hi! My friends... Here I am in the bathroom... Do you want a pic ;)
     #AtHome #BathroomLove
     
  >  #AtHome 
  >  #BathroomLove
  
2- ToUpperCase
  >  #ATHOME
  >  #BATHROOMLOVE

3- Remove the # sign
  >  ATHOME
  >  BATHROOMLOVE

4- Count By groupng msg by value

----
MAP REDUCE?

Map functions are applied in lazy mode... They are only applies prior to a REDUCE funcion

COLLECTION1 ----> MAP FUNCTIONS -----> COLLECTION2
1                   +2                      3
2                                           4
3                                           5

Map function generates a new collection containing the same amount of values as we have in the first collection
FlapMap functions 

COLLECTION1 ----> FLATMAP FUNCTIONS -----> COLLECTION2
1                   factors    1            1
6                          3 2 1            3
9                          3 3 1            2
                                            1
                                            3
                                            3
                                            1

JOINS: Allow to Join (Same concept as in a DB) COLLECTIONS


---

bin/kafka-topics.sh --create --topic TWEETS --bootstrap-server localhost:9092 \
                    --replication-factor 1 --partitions 1

bin/kafka-topics.sh --create --topic HASHTAGS --bootstrap-server localhost:9092 \
                    --replication-factor 1 --partitions 1

bin/kafka-console-consumer.sh --topic TWEETS  --bootstrap-server localhost:9092 --from-beginning
bin/kafka-console-consumer.sh --topic HASHTAGS  --bootstrap-server localhost:9092 --from-beginning

bin/kafka-console-producer.sh --topic TWEETS  --bootstrap-server localhost:9092 


SQL


SELECT count(*) FROM TABLE GROUP BY word;