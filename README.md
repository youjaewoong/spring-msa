# spring-msa
###[discoveryservice]()
클라우드 환경의 다수의 서비스(예: API 서버)들의 로드 밸런싱 및 장애 조치 목적을 가진 미들웨어 서비스 입니다.

###[apigateway-service]()
클라이언트와 백엔드 서비스 사이에 위치하는 리버스 프록시 역활을 하는 서비스 입니다.
인증, 속도제어, 서킷브레이커, 모니터링등 여러 공동 모듈 및 관리포인트들을 추가하여 모든 API들의 관문(GATEWAY)의 역할을 하는 서비스 입니다.
####config.FilterConfig
요청이 오는 router bean을 정의 합니다. 현재는 application.yml 에 정의되어있습니다.

####filter.AuthorizationHeaderFilter
```
요청이 오는 uri 의 대한 검증을 하는 filter 입니다.
- header 유효성 검증
- token 유효성 검증
```

###user service
Fault Tolerance(=장애 허용 시스템) 에서 사용되는 대표적인 패턴으로써 서비스에서 타 서비스 호출 시 에러, 응답지연, 무응답, 
일시적인 네트워크 문제 등을 요청이 무작위로 실패하는 경우에 Circuit를 오픈하여 메세지가 다른 서비스로 전파되지 못하도록 막고 미리 정의해놓은 
Fallback Response를 보내어 서비스 장애가 전파되지 않도록 하는 패턴 (대표적으로 MSA 환경에서 사용)
Resilience4JConfig

FeignErrorDecoder
- feigenClient 
