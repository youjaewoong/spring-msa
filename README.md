# spring-msa

###user service
Fault Tolerance(=장애 허용 시스템) 에서 사용되는 대표적인 패턴으로써 서비스에서 타 서비스 호출 시 에러, 응답지연, 무응답, 
일시적인 네트워크 문제 등을 요청이 무작위로 실패하는 경우에 Circuit를 오픈하여 메세지가 다른 서비스로 전파되지 못하도록 막고 미리 정의해놓은 
Fallback Response를 보내어 서비스 장애가 전파되지 않도록 하는 패턴 (대표적으로 MSA 환경에서 사용)
Resilience4JConfig

FeignErrorDecoder
- feigenClient 
