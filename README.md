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

### [discoveryservice](https://github.com/youjaewoong/spring-msa/tree/master/discoveryservice)
클라우드 환경의 다수의 서비스(예: API 서버)들의 로드 밸런싱 및 장애 조치 목적을 가진 미들웨어 서비스 입니다.

---

### [apigateway-service](https://github.com/youjaewoong/spring-msa/tree/master/apigateway-service)
클라이언트와 백엔드 서비스 사이에 위치하는 리버스 프록시 역활을 하는 서비스 입니다.
인증, 속도제어, 서킷브레이커, 모니터링등 여러 공동 모듈 및 관리포인트들을 추가하여 모든 API들의 관문(GATEWAY)의 역할을 하는 서비스 입니다.

---

### [user-service](https://github.com/youjaewoong/spring-msa/tree/master/user-service)
사용자 로그인, 조회, 토큰 생성 및 인증처리 처리하는 서비스 입니다.

---

### [order-service](https://github.com/youjaewoong/spring-msa/tree/master/order-service)
사용자 주문생성, 주문 조회 등을 확인하는 서비스 입니다. 주문 생성 시 동시성 처리에 대한 kafkaclient 로직이 포함되어있습니다.


