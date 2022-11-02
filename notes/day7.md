
                    Kafka (pull)

Producer    -->      Topic   < ----  Consumer

Kafka Native protocol is baed on TCP/IP
                                        / HTTP

----

                    RabbitMQ (push) // activeMQ
                        
Producer    -->  Exchange       ->  Queue      ----> Consumer
                                    (~Topic)

            Player 1 : Dragon       EXCHANGE ----> Q1   < ----      Player1
              key_0_1_1_1             key_1_?_?_?
            Player 2                               Q2               Player2
            Player 3                               Q3               Player3
            Player 4                  key_?_?_?_1  Q4               Player4
                                    
            
Common patters 

                                    MessagingBroker (KAFKA)
    ClashRoyale Battles 2vs2                             Consumer
        Player 1 : Dragon       ----> TOPIC_ID_MATCH < - Player1
        Player 2                                         Player2
        Player 3                                         Player3
        Player 4                                         Player4
                                   flush   
                                                        In its code:
                                                            if (KEY == ME) forget
                                    
                                    
                                        TOPIC
                                    
        In 1 second... we may send a couple of msgs... 2x3 = 6x4 = 24 msgs /s
        
        
        
What metadata is stored inside Kafka for each MSG?
    Offset (ORDINALITY)
    TimeStamp
    Key
    Value



------

I want to pay with may card in the toll. Async
    Credit card
    Debit card
                                TOLL COMPANY                      
Me In my CAR    ---TOLL---->    KAFKA                             
                Pay card        TOPIC: PENDING PAYMENTS < ---- CONSUMER ----- SYNC ------>  BANK
                                    OFFSET  701 702 .........
                                                                        < ------ YES
                                                                            - That's all COMMIT
                                    FAILED_PAYMENTS
                                            0(701)

                                                                        < ------ NO
                                                                                            BANK IS OFFLINE
                                                                                            BANK GETS BROKEN IN THE MIDDLE 
                                                                            COMMIT     This is the decition that I am going to take. FUCK ME !
                                                                            Republish the msg to the same topic: 1098
                                                                                Increment RETRIES VARIABLE inside MSG
                                                                                ------> THE CONSUMER GETS BROKEN
                                                                                                                        COMMIT 701                                      NOK I could charge twice the same person
                                                                CONSUMER GETS BROKEN IN THE MIDDLE OF A TRANSACTION
                                                                    THE CONSUMER IS NOT GOING TO COMMIT THE OFFSET!
                                                                    An all those things below
                                                                CONSUMER IS OFFLINE
                                                                    So... I can start another Consumer (or maybe I have it working already in a pool)
                                                                        with the same CONSUMER GROUP...
                                                                            That new consumer is gonna start with msg 701
                                                                    Nothing happens

                                KAFKA IS OFFLINE
                                KAFKA GETS BROKEN IN THE MIDDLE OF A TRANSACTION
                                    Kafka cluster with several servers and in different physical locations
                                
                reject the payment
                
How many offsets KAFKA is going to store for a CONSUMER GROUP : JUST 1
-------

Commercial place:

Firedetector: SENSOR -> ACTIVE  : PRODUCER. ****** MSG 1s 5s 10s.  (SENSOR IS DOWN)
                        PASSIVE : SERVICE


MSG
    Key         ID                  integer
    Value       Signal (0|1)        boolean


FD1
FD2                                        PROTECTION SYSTEM
FD3         --->        KAFKA                    COMPUTER
                                                        EMBEDDED SYSTEM (harware & software) 24/7/40 years
                        SIGNAL(1)FDs < ----   Sg1   Program 1 : OPEN EV Water()    ------->   EV1. EV2. EV3.      Language C
                            ^                                                            1 per floor of the building
                            |                                                   
                            |Stream                        * Those EV will require to be closed at certain point < HUMAN
                            |  filterValues(1).                     Interruptor. ON / OFF
                            |                                                    
                        SIGNALsFDs   < ----   Sg0|1 Program 2 : Needs to detect if a fire detector is broken
                                                                    EVENT : An email needs to be send
                                                                    EVENT : An SMS needs to be send
                                                                    Turn a light on in a control a panel
                        NOTIFICATION        
                            |Stream (replicate msg)
                            v
                        EMAILS       < -----   Send an email
                        SMS.         < -----   Send an SMS ----> Use the phone signal
                        0 1 2 3 4 5 6                                                 
                                                In case we have a problem while sending msg 1... Does this program need to commit Offset 1?
                                                                                                    YES
                                                                                                    But ... In addition Republish Same topic
                                                                                                        COMMIT at the end... FUCK HIM !
We could have a bunch of FD per floor

We don't want that communication (FD-> PS) to be synchronous?

Audit

----
API

PROVIDERs
CONSUMERs
KAFKA STREAMs               ETL
KAFKA CONNECT To READ or WRITE msgs automatically from known sources or targets
    DATABASE
    HTTP Server
    MQTT
    FILE                    ETL
    
Extract Tranform Load