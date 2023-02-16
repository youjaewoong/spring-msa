## [user-service](https://github.com/youjaewoong/spring-msa/tree/master/user-service)
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
