# 목적 : HA PROXY 테스트를 위한 간단한 예제 
- 본 테스트는 Docker 내부에 HA PROXY가 있으며, 실제 Application은 Dockerhost를 기반으로 수행하였다.
1. 개발PC브라우저 -> HAPROXY -> Application 으로 접근
    - HA PROXY에서 삽입하는 X-Forwarded-for header를 통해 Application이 ClientIP를 Echo한다.
2. 만일 직접 개발PC브라우저 -> Application 으로 접근
    - X-Forwarded-for 가 없으므로 ClientIP를 Echo하지 못한다.

## 1. 디렉토리 설명
- . : springboot application 
- ./haproxy : 테스트를 위한 haproxy docker


## 2. 설정 변경(주의사항)
- ./haproxy/haproxy.cfg 파일의 수정이 필요할 수 있다.
1. terminal에 다음 명령을 수행한 후 
    ~~~
    > ip addr show docker0
    3: docker0: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc noqueue state UP group default 
    link/ether 02:42:1a:bb:e3:a4 brd ff:ff:ff:ff:ff:ff
    inet 172.17.0.1/16 brd 172.17.255.255 scope global docker0
       valid_lft forever preferred_lft forever
    inet6 fe80::42:1aff:febb:e3a4/64 scope link 
       valid_lft forever preferred_lft foreve
    ~~~
    - inet 172.17.0.1 이 아닌 경우 ./haproxy/haproxy.cfg 의 최하단 부 IP주소를 변경해야 함



## 3. 실행방법
0. Gradle 설치 되어있어야 함 6.0 이상
0. Docker 설치 되어있어야 함

1. application build
    ~~~
    > gradle build
    ~~~
2. application 실행
    ~~~
    > java -jar ./build/libs/simple-rest-echo-server-0.0.1-SNAPSHOT.jar 
    ~~~
3. Docker build(HA Proxy)
    ~~~
    > cd haproxy
    > docker build . -t potatoproxy
    ~~~
4. Docker Container로 HAPROXY 실행하기
    ~~~
    > docker run -p 80:80 -d potatoproxy
    ~~~

## 4. 테스트 방법
1. haproxy 경유
    - 브라우저에사 http://localhost:80/echo?message=hello 로 이동
    ~~~
    {
    "message": "hello",
    "ip": "172.17.0.1"
    }
    ~~~
2. haproxy 미 경유
    - 브라우저에사 http://localhost:8080/echo?message=hello 로 이동
    ~~~
    {
    "message": "hello",
    "ip": null
    }
    ~~~

