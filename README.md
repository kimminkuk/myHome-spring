# myHome-spring

# 계획 일정 ~12/E
1. github 내용 겹치는거 삭제 -> 현재 myHome-Spring에 전부 페이지화 할 예정 (아래 목록들만 해도 10개는 지울듯)<br>
   - 1.1 예약페이지 만들던거 ( 부스트캠프 하다가 실패, https://github.com/kimminkuk/boostcourse ) (todolist 이쁘게 만들기)
   - 1.2 상품 올리는 페이지 구현하던거 (https://github.com/kimminkuk/item-service 여기거 옮기기)
   - 1.3 게시판 글쓰기 만들던거 ( 부스트캠프에서 하던거 리팩토링, https://github.com/kimminkuk/boostcourse )
   - 1.4 콴트 조사한 페이지 ( https://github.com/kimminkuk/220501_ver1)   
   - 1.5 동영상   
   - 1.6 설비예약 페이지 만들기
   - 1.7 광고 페이지 (블록?) 만들기
   - 1.8 계속 이동하는 케릭터 ( 앱으로 구상 중 )
   - 1.9 맛 평가 리뷰 ( 맛, 평가 리뷰 등등, 성분 표시 )

# TODO
1-1. 예약페이지
   - 기본 틀 생성 ( 80%, 화면 이동 만들어야함, 화면에 블록은 24개 나오게 할거임 )
   - 예약 기능 ( 100% -> 60%, DB 설계를 다시 해야 할 거 같음. )
     > 지금은 같은 설비의 이름에 계속해서 시간을 Update 중, 하지만 DB 시간 멤버의 String 제한도 있다. 
     > 몇개 예약 안할때야 문제 없지만, 몇 백~십만이면 String 터짐 + for 으로 시간 멤버를 split하고 여러가지 연산을 많이 한다.
     > 아무래도, update 말고, 값을 순서대로 넣어줘야하나? 근데 그러면.. 예약을 할 때마다 전체 DB를 조회해야하나??? 흠흠 고민 중
     > 그리고, 예약 방법도 front에서 많이 변경 했기 때문에, 현재 예약이 불가능하다. 캘린더 완성 후, 다시 시작해야함
   - 캘린더 생성 ( 90%, 위치 조정, 날짜 클릭 시 효과 넣기 )
   - 예약 확인 페이지 ( 100% )
   
# DB 
1. REDIS
    - localhost로 사용 중 ( 랭킹페이지 )

2. H2 Database
    - localhost로 사용 중 ( 예약페이지 )
 
# REDIS Command
1. redis-server : redis server 실행
2. redis-cli ping :PONG 출력되면 Redis Server 실행 중
3. redis-cli : redis command 사용할 수 있게 된다.
4. redis-cli shutdown


# 완료 목록
1. gamePage
