# 메시징과 이벤트

모든 시스템이 동기식 통신에 의존하는 것은 아니다. 상황에 따라 애플리케이션의 런타임 흐름을 방해하지 않고 특정 이벤트를 트리거하고 싶을 수도 있다.  

시스템 컴포넌트 사이의 통신이 비동기적으로 발생하는 기법의 영향을 크게 받는 아키텍처 유형이 있다.  
시스템의 컴포넌트들이 더 이상 다른 애플리케이션이 제공하는 인터페이스에 연결되어 있지 않기 때문에 이 같은 시스템은 그러한 기법을 사용하여 더욱 느슨하게 결합된다.  
여기서는 연결을 블로킹하는 API 에만 의존하지 않고, 메시지와 이벤트가 논블로킹(non-blocking) 방식으로 애플리케이션의 행위를 유도하게 한다.  

'블로킹(blocking)' 은 애플리케이션 흐름이 진행되기 위해 응답을 기다려야 하는 연결을 의미한다. 논블로킹은 반대다.  
메시지 기반 시스템은 헥사고날 애플리케이션에 의해 유도되는 보조 액터다.

```shell
$ bin/zookeeper-server-start.sh config/zookeeper.properties
$ bin/kafka-server-start.sh config/server.properties
```

```shell
$ bin/kafka-topics.sh --create --topic topology-inventory-events --bootstrap-server localhost:9092
$ bin/kafka-console-producer.sh --topic topology-inventory-events --bootstrap-server localhost:9092
$ bin/kafka-console-consumer.sh --topic topology-inventory-events --bootstrap-server localhost:9092
```