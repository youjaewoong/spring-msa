### [apigateway-service](https://github.com/youjaewoong/spring-msa/tree/master/apigateway-service)
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
