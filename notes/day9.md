In Kafka MSGS (Events) are not updatable.
    In a topic, msgs are RECORDED in a LOG file
    
    
                Worker                                      KAFKA cluster
                 Configured: Several Connectors               Brokers
    BD | File       Source -> producer ---------------------> TOPIC: connect-test
                    Sink -> Consumer    < __________________________|
                                |
                                V
                            File... DBs