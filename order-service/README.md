## [order-service](https://github.com/youjaewoong/spring-msa/tree/master/order-service)
사용자 주문생성, 주문 조회 등을 확인하는 서비스 입니다. 주문 생성 시 동시성 처리에 대한 kafkaclient 로직이 포함되어있습니다.
#### controller.OrderController
```
사용자 주문생성, 주문 조회 등의 rest api 입니다.
```
#### messagequeue.KafkaProducer
```
설정된 kafkaTemplate 으로 공통 send 로직이 구햔되어 있습니다.
```
#### messagequeue.KafkaProducerConfig
```
kafkaTemplate 전송 시 필요한 기능을 초기설정 합니다.
```
#### security.WebSecurity
```
user-service 의 요청이 들어올 경우 permitAll 처리 합니다.
```
