# myHome-spring

# 계획 일정 ~12/E
1. github 내용 겹치는거 삭제 -> 현재 myHome-Spring에 전부 페이지화 할 예정 (아래 목록들만 해도 10개는 지울듯)<br>
   - 1.1 예약페이지 만들던거 ( 부스트캠프 하다가 실패, https://github.com/kimminkuk/boostcourse ) (todolist 이쁘게 만들기)
   - 1.2 상품 올리는 페이지 구현하던거 (https://github.com/kimminkuk/item-service 여기거 옮기기)
   - 1.3 게시판 글쓰기 만들던거 ( 부스트캠프에서 하던거 리팩토링, https://github.com/kimminkuk/boostcourse )
   - 1.4 콴트 페이지 ( https://github.com/kimminkuk/220501_ver1)   
   - 1.5 동영상   
   - 1.6 설비예약 페이지 만들기
   - 1.7 광고 페이지 (블록?) 만들기
   - 1.8 계속 이동하는 케릭터 ( 앱으로 구상 중 )
   - 1.9 맛 평가 리뷰 ( 맛, 평가 리뷰 등등, 성분 표시 )

# TODO ( 계획 일정과 번호 매칭 )
---------
### 1-1. 예약페이지
   #### 기본 틀 생성 ( 100%, 화면 이동 만들어야함, 화면에 블록은 24개 나오게 할거임 )
      100% 1. 기본 라인, 틀 
      100% 2. 현재는 화면에 총 48개으로 24시간을 나눴다. -> 24개로 12시간씩 3등분할거임
                  ex) 0 ~ 12      6 ~ 18     12 ~ 24
      100% 3. 현재 시간에 | 이런식의 일자 라인 생성 예정
      100% 4. 예약 버튼 누르고 드래그 or 마우스로 늘리는 칸 추가로 생성해서 간단하게 늘릴 수 있게 추가 옵션 설정
      100% 5. 맨위 달력 선택은 input=type="date" 쓰기, 좌,우 date 이동 ( input type은 취소하고 캘린더 생성에서 사용한 object 이용 )
      100% 6. 실제 날짜 변경에 맞게 예약 화면 grid 칸이 변경되어야 한다.
   #### 예약 기능 ( 100% -> 90%, DB 설계를 다시 해야 할 거 같음. )
      100% 1. 지금은 같은 설비의 이름에 계속해서 시간을 Update 중, 하지만 DB 시간 멤버의 String 제한도 있다.
      100% 2. 몇개 예약 안할때야 문제 없지만, 몇 백~십만이면 String 터짐 + for 으로 시간 멤버를 split하고 여러가지 연산을 많이 한다.
        0% 3. 아무래도, update 말고, 값을 순서대로 넣어줘야하나? 근데 그러면.. 예약을 할 때마다 전체 DB를 조회해야하나??? 흠흠 고민 중
              ( DB 스터디 좀 하고, 기능 업데이트하자 )
      100% 4. 그리고, 예약 방법도 front에서 많이 변경 했기 때문에, 현재 예약이 불가능하다. 캘린더 완성 후, 다시 시작해야함
      100% 5. 날짜 포함해서 예약하는 기능 (현재는 테스트용으로, 00:00 ~ 24:00 까지만 예약하게 설정해두었다.)
   #### 캘린더 생성 ( 100%, 위치 조정, 날짜 클릭 시 효과 넣기 )
      100% 1. 예약 창에서 날짜 클릭 시, 켈린더 이미지 나옵니다.
      100% 2. 좌, 우 달 이동, 해당 년도의 달 이동 가능 ( 년도 이동 까지는 기능 제공 안합니다. )
      100% 3. 전, 다음 달의 날짜 데이터 생성
      100% 4. 날짜 선택 시, 예약 시간 변경
      100% 5. 2022년도 지원 (현재는 테스트 날짜에만 가능하게 되어 있다.-> 어떤 기능인지 까먹었다.)
   #### 예약 확인 페이지 ( 100% )
      100% 1. 예약시, 확인 해주는 창 나옴
      100% 2. 예약 버튼 누를 시, Get 방식으로 DB에 저장
      100% 3. 취소 시, 선택한 예약 데이터들 초기화
      
   #### 시연 영상
   ![예약페이지Ver3 0](https://user-images.githubusercontent.com/27074717/208299666-1e7ed3c1-0a3d-4930-8268-1625316b0a07.gif)

---------

### 1-4. 콴트 페이지
   #### 기본 틀 생성
      1. 버튼 기능들 활성화
      2. 
      3. 
   #### 기업 데이터 생성
      1. Text파일로 Company Code, Name 생성 ( 파이썬으로 코넥스 시장은 제외 )
      2. Naver Finance Parsing 기능
      3. 1번의 목록들에 여러 정보 입력해서 Text파일로 저장
   #### DB
      1. H2 DataBase
         1.1. 전략들 저장
      2. Redis
         2.1. 기업 데이터 3번 항목을 백업용으로 ZSet을 사용해서 저장 
              ex) 2023-01-14
   #### 기능
      1. Front-end <-> Back-end 연결 체크
      2. 전략 정보들의 문자열 체크 (1. 10~ 2. 10~20 3. ~20 4. 10 , 이외에 예외처리 추가)
      3. 전략들의 결과 데이터 HTML에 표시
      4. Text파일로 저장 가능하도록 추가
      5. 그래프 (고민 중..)
   #### 초안 이미지
   <img width="458" alt="콴트 페이지 초안" src="https://user-images.githubusercontent.com/27074717/209965774-6f67342a-2dd0-4170-ab48-640ba1273cb2.png">
   ![IMG_1905](https://user-images.githubusercontent.com/27074717/213703175-34ae9b5b-7887-40e8-8dad-9d8a44d9380e.png)
   <img width="1440" alt="myHome-spring 콴트페이지 초안1" src="https://user-images.githubusercontent.com/27074717/210174159-1ed7995a-1260-4df1-9594-14b3ae2ca2dd.png">


---------   
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
1. Page
2. 예약Page

# 자주 사용하는 사이트
1. 영상 gif 변환: https://ezgif.com/maker
