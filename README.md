# myHome-spring

# 계획 일정 ~10/E
1. github 내용 겹치는거 삭제 -> 현재 myHome-Spring에 전부 페이지화 할 예정 (아래 목록들만 해도 10개는 지울듯)<br>
   - 1.0 버튼 이쁘게 만들기 및 css 로 위에 설정 조정 ( 아주 힘든 과정일거 같음 )
   - 1.1 콴트 조사한 페이지 ( https://github.com/kimminkuk/220501_ver1)
   - 1.2 상품 올리는 페이지 구현하던거 (https://github.com/kimminkuk/item-service 여기거 옮기기)
   - 1.3 예약페이지 만들던거 ( 부스트캠프 하다가 실패, https://github.com/kimminkuk/boostcourse )
   - 1.4 게시판 글쓰기 만들던거 ( 부스트캠프에서 하던거 리팩토링, https://github.com/kimminkuk/boostcourse )
   - 1.5 동영상 

# 현재 막힌 부분 및 진행 상황
1. game.html -> form 입력(이름) -> 게임 끝 -> 점수 Post 전송 -> PostMapping으로 Redis DB에 저장 확인.<br>
   그러나, Controller에서 PostMapping을 public String으로 메소드를 생성해서 return을 "basic", "basic/game" 등등 으로 해야지 network에서 500에러가 발생하지 않는다.<br>
   원인은 잘 모르겠다. 흠; 처음에는 public void 로 선언하고 했었는데 계속 500 error가 발생해서 (DB로 저장은 가능하나, ajax 통신에서 200, 201 신호를 발생하지 못함).<br>
   수정함 왜지지ㅣㅣㅣㅣㅣ.<br>

2.  Redis로 LocalDateTime 을 String으로 Value 처리하니, 직렬화/역직렬화 처럼 보이는 문제가 발생해서 아래처럼 일단 해결함
    근데 이거는 내가 매번 생성해줘야하잖아 여러 패턴을.. 자동화로 만드는 방법없나? 클래스 생성 등등 
    - @DateTimeFormat(pattern = "yyyy-MM-dd kk:mm:ss")<br>
      private LocalDateTime myTime;

3. Test Code 작성하는거 지금 많이 이상함
    - 메모리에 못쓰고 Redis 에다가 바로 Write해서 확인 중.. 인프런 다시 빠르게 보자 이번주 동안
    - Redis get All, get One 이거 스터디.. 지금은 뭔가 굉장히 별로임 모든걸 가져오는걸 못하고 특정 key의 패턴으로만 가져옴<br>
      redisTemplate.keys("패턴*") => 패턴1, 패턴2, 패턴, 패턴1231 이런식의 Key값만 가져와짐
      

# TODO
1. 게임페이지의 상호작용 처리 중.. 벽 튕기는거 코딩 중 (물리는 가속도만 적용시키자)
    - game html에서 이름 입력 후, enter 입력하면 post로 전송되는 문제
    - game.js에서 gameEnd() xhr.readyState === xhr.DONE 처리 방법 어떻게 할지? (200, 201 이 아니라서 에러발생 중)

2. 쓰레기를 처리하는 페이지 생성 -> 앱
    - 어떤 쓰레기인지 입력 ( 아이스팩 등등 입력 )
    - 처리 하는 방법 설명 및 그림?
3. 맛 평가 리뷰 
    - ex) 메가커피 민트초코프라페 검색 -> 맛 평가 등등 리뷰 올리고 후기남기고?? 블로그랑 너무 겹치긴하네 접근성을 올리면 될려나?

# DB
1. REDIS
   - Lettuce 설치
   - Spring Boot에서 Reids 설정 방법 Study
   
# REDIS
1. redis-server : redis server 실행
2. redis-cli ping :PONG 출력되면 Redis Server 실행 중
3. redis-cli : redis command 사용할 수 있게 된다.
4. redis-cli shutdown
