. ./env.sh

$KAFKA_HOME/bin/zookeeper-server-start.sh config/zookeeper.properties &
sleep 5
$KAFKA_HOME/bin/kafka-server-start.sh     config/server0.properties &
$KAFKA_HOME/bin/kafka-server-start.sh     config/server1.properties &
$KAFKA_HOME/bin/kafka-server-start.sh     config/server2.properties &

