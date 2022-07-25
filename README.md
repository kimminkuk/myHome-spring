# myHome-spring


# 현재 막힌 부분 및 진행 상황
1. game.html -> form 입력(이름) -> 게임 끝 -> 점수 Post 전송 -> PostMapping으로 Redis DB에 저장 확인.<br>
   그러나, Controller에서 PostMapping을 public String으로 메소드를 생성해서 return을 "basic", "basic/game" 등등 으로 해야지 network에서 500에러가 발생하지 않는다.<br>
   원인은 잘 모르겠다. 흠; 처음에는 public void 로 선언하고 했었는데 계속 500 error가 발생해서 (DB로 저장은 가능하나, ajax 통신에서 200, 201 신호를 발생하지 못함).<br>
   수정함 왜지지ㅣㅣㅣㅣㅣ.<br>

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
