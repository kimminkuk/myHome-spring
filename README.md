# myHome-spring


# 현재 막힌 부분
1. Html에서 화면 생성하고, 모션 동작 등등 이벤트 발생(JavaScript, Front) 신호를 -> 
   Java Class(Back)로 Data(모션 종료 이벤트 신호 등등)처리해서 전송 후 DB에 저장하고 싶은데...
   JavaScript -> Java 데이터 넘기는건 ajax로 get, post로 넘기면 될 거 같긴한데...
   어느 타이밍? 어느 시점? 어떤 Flow로 진행해야할지 모르겠네.
   html btn 클릭 -> javaScript에서 모션동작 처리, 종료 이벤트 발생 -> java Class로 데이터 넘기기 (???)
   그냥 이렇게 하면 될려나

# TODO
1. game html에서 이름 입력 후, enter 입력하면 post로 전송되는 문제
2. game.js에서 gameEnd() xhr.readyState === xhr.DONE 처리 방법 어떻게 할지? (200, 201 이 아니라서 에러발생 중)

# DB
1. REDIS
   - Lettuce 설치
   - Spring Boot에서 Reids 설정 방법 Study
   
# REDIS
1. redis-server : redis server 실행
2. redis-cli ping :PONG 출력되면 Redis Server 실행 중
3. redis-cli : redis command 사용할 수 있게 된다.
4. redis-cli shutdown
