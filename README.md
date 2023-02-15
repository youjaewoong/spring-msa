# spring-msa

### 시나리오
```
회원가입
로그인
사용자 heath-check (토큰포함)
사용자 welcome
사용자상세
카탈로그 목록 확인
상품주문
카탈로그 목록 확인 (수량감소 확인 - 카프카) 
사용자상세 (주문확인)
```

---

### [discoveryservice]()
클라우드 환경의 다수의 서비스(예: API 서버)들의 로드 밸런싱 및 장애 조치 목적을 가진 미들웨어 서비스 입니다.

---

### [apigateway-service]()
```
클라이언트와 백엔드 서비스 사이에 위치하는 리버스 프록시 역활을 하는 서비스 입니다.
인증, 속도제어, 서킷브레이커, 모니터링등 여러 공동 모듈 및 관리포인트들을 추가하여 모든 API들의 관문(GATEWAY)의 역할을 하는 서비스 입니다.
```
#### config.FilterConfig
요청이 오는 router bean을 정의 합니다. 현재는 application.yml 에 정의되어있습니다.

#### filter.AuthorizationHeaderFilter
```
요청이 오는 uri 의 대한 검증을 하는 filter 입니다.
- header 유효성 검증
- token 유효성 검증
```
#### filter.CustomFilter
```
요청이 오는 uri 의 대한 검증을 하는 테스트용 Filter 입니다.
```
#### filter.GlobalFilter
```
요청이 오는 uri 의 대한 검증을 하는 Filter 입니다.
- application.yml 에 args 의 값에 따른 제어 기능이 포함되어 있습니다.
- 로그처리에 대한 필터입니다.
```

---

### user service
사용자 로그인, 조회, 토큰 생성 및 인증처리 처리하는 서비스 입니다.
#### client.OrderServiceClient
```
feignClient 로 orderService method 를 호출합니다.
```
#### config.Resilience4JConfig
```
서킷브레이크 bean 초기설정 입니다.
서킷브레이크란
 - server A가 server B을 호출할 때 circuit breaker가 모든 트래픽을 bypass 합니다.
 - 이때 server B에 응답이 없다면 circuit breaker가 server B로의 호출을 강제로 끊고 server A에게 에러 메시지를 날립니다.
 - 이를 더 발전시켜 server A에게 에러 메시지를 보내는 것이 아니라 다른 의미 있는 메시지를 보내줄 수 있습니다. (Fall-back 메시징)
```
#### controller.UserController
```
rest api 호출 controller
서킷브레이크란
 - 유저등록
 - 유저조회
 - 유저전체 조회
```
#### error.FeignErrorDecoder
```
feigenClient 에서 발생하는 에러코드 별 커스터마이징 합니다.
```
#### jpa.UserEntity
```
jpa table을 생성하는 entity 객체입니다.
```
#### jpa.UserRepository
```
db 호출하는 UserRepository 객체입니다.
```
#### security.AuthenticationFilter
```
시큐리티 인증 및 jwt 토큰생성을 처리하는 Filter 클래스입니다.
```
#### security.WebSecurity
```
시큐리티 설정 구성입니다.
 - ip 제한
 - password encoder
 - spring security manager 작업위임
   - jwt 토큰생성
   - 인증 성공시 context에 email password 등록
```

---

### order-service
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

