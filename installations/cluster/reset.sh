. ./env.sh

rm -rf $KAFKA_DATA
mkdir -p $KAFKA_DATA

mkdir -p $KAFKA_DATA/kafka/zookeeper
mkdir -p $KAFKA_DATA/kafka/broker0
mkdir -p $KAFKA_DATA/kafka/broker1
mkdir -p $KAFKA_DATA/kafka/broker2
