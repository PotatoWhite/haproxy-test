
[참고자료]

사내 Ncloud 시스템의 DNS RR을 HAProxy로 교체
사내 Ncloud 시스템의 DNS RR을 HAProxy로 교체하여 <그림 6>과 같이 앞에서 설명한 HA 구성과 동일한 형태로 구성했다. 그 결과 기존 DNS로 구성할 때보다 서버 증설 및 삭제에 있어 유연성이 증가했으며 HA 구성으로 서비스 안정성도 높아졌다.

그런데 서버 반영 후 애플리케이션 서버에서 클라이언트 IP 주소를 찾아서 처리하는 로직에 문제가 발생했다. 확인 결과 사용자의 요청이 HAProxy를 거치면서 클라이언트 IP 주소 정보가 HAProxy의 정보로 변조되어 보이는 상황이었다.

이 문제가 발생하지 않도록 하기 위해서 HAProxy에서 제공하는 X-Forwarded-For 옵션을 적용하고 Apache 서에는 mod_rpaf 모듈을 설치해서 애플리케이션 서버가 HTTP 헤더에서 클라이언트 IP 주소를 조회하면 실제 클라이언트 IP 주소가 반환되도록 보완했다.

https://d2.naver.com/helloworld/284659
