. ./env.sh

$KAFKA_HOME/bin/kafka-server-stop.sh config/server0.properties    
sleep 5
$KAFKA_HOME/bin/zookeeper-server-stop.sh 

