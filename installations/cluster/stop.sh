. ./env.sh

$KAFKA_HOME/bin/kafka-server-stop.sh config/server0.properties    
$KAFKA_HOME/bin/kafka-server-stop.sh config/server1.properties    
$KAFKA_HOME/bin/kafka-server-stop.sh config/server2.properties    
sleep 5
$KAFKA_HOME/bin/zookeeper-server-stop.sh 

