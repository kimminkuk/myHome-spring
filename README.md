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
        0% 6. 2023년도 지원하기.
   #### 예약 확인 페이지 ( 100% )
      100% 1. 예약시, 확인 해주는 창 나옴
      100% 2. 예약 버튼 누를 시, Get 방식으로 DB에 저장
      100% 3. 취소 시, 선택한 예약 데이터들 초기화
      
   #### 시연 영상
   ![예약페이지Ver3 0](https://user-images.githubusercontent.com/27074717/208299666-1e7ed3c1-0a3d-4930-8268-1625316b0a07.gif)

---------

### 1-4. 콴트 페이지 ( 중단, 실패 )
   #### 치명적인 이슈가 발생 ( 중단합니다. )
      1. 파싱을 2437개의 회사를 200일치의 데이터를 가져오니, 중간 중간 값이 이상한 데이터가 나오는 경우가 있습니다.
         그래서, 파이썬의 BeautifulSoup등을 이용해보았지만 해결하지 못했습니다.
         물론, 올바른 데이터가 나오지 않은 회사만 JSOUP을 다시 반복하면 어느 정도는 해결되었지만 의도한 해결 방법은 아니였습니다.
      2. 대안으로 Google Sheet의 APPS Script로 2437개의 600일치 데이터를 가져오는것이였습니다만..
         이렇게 관리하는것은 매번 데이터를 많이 수정해야한다는 사실을 알게 되었습니다.
         너무 많은 CSV데이터는 저장이 힘들고, 매일매일 업데이트 관리를 하는것이 어렵다는 사실입니다.
      3. 아직은 어떻게 해야할지 모르겠습니다.
         가격 데이터를 가져오기만 하면 그래프를 그릴 수 있는 상황이긴합니다만, 매번 매번 업데이트는 정말 하기 힘든 상황이라는것을 알게 되었습니다.
         더 나은 자료 및 방법이 있으면, 다시 시작합니다.

   #### 기본 틀 생성
      100% 1. 버튼 기능들 활성화
       70% 2. 그래프 기능
        0% 3. 달력 기능
        0% 4. 파일 저장 기능 ( 파일 시스템 오픈을 어떻게 하는지 모르겠음 )
   #### 기업 데이터 생성
      100% 1. Text파일로 Company Code, Name 생성 ( 파이썬으로 코넥스 시장은 제외 )
      100% 2. Naver Finance Parsing 기능
      100% 3. 1번의 목록들에 여러 정보 입력해서 Text파일로 저장
      100% 4. 200일전 가격까지 취득 필요 (약 6개월)
        0% 5. 600일까지 추가하자.
   #### DB
      100% 1. H2 DataBase
            1.1. 전략들 저장
       50% 2. Redis
            2.1. 기업 데이터 3번 항목을 백업용으로 ZSet을 사용해서 저장 
                 ex) 2023-01-14
            2.2  기업 일별시세 저장(200일까지 기록)
   #### 기능
      100% 1. Front-end <-> Back-end 연결 체크 ( 이거 왜 써둔거지?? 그냥 서버 뚫었다는 내용이였나? 기억이 잘 안남 )
      100% 2. 전략 정보들의 문자열 체크 (1. 10~ 2. 10~20 3. ~20 4. 10 , 이외에 예외처리 추가)
      100% 3. 전략들의 결과 데이터 HTML에 표시
    (x)50% 4. Text파일로 저장 가능하도록 추가 ( Web 환경에서, 파일 시스템을 Open하는 좋은 방법을 못 찾겠음.. 일방적으로 저장하는 방법밖에 아직 모름 )
       70% 5. 3개의 그래프 그리기 ( 최대, 최소 값이 +, - 경우에 따라서 데이터를 추가해야합니다. 현재는 최소는 -, 최대는 + 만 적용 중)
        0% 6. 마지막 파싱 날짜에서, 현재 파싱한 날짜와 비교해서 부족한 만큼 텍스트파일을 자동으로 관리하도록 구현 ( 이거 감이 안잡히네 흠.. )
        0% 7. ZSset으로 백업용 데이터 만들기
        0% 8. 분기 데이터(재무제표)를 선택할 수 있도록 버튼 생성 및 데이터 추가
        0% 9. 분기 데이터(재무제표)의 증감도 추가합니다. ( 그래프 그리는 데이터 추가했던거랑 똑같이 하면 될듯? )
      
   #### 초안 이미지
   <img width="458" alt="콴트 페이지 초안" src="https://user-images.githubusercontent.com/27074717/209965774-6f67342a-2dd0-4170-ab48-640ba1273cb2.png"> <img width="400" alt="초안 그래프 이미지" src="https://user-images.githubusercontent.com/27074717/213703456-03001f7d-b75a-435a-9526-74d39913cb43.png">
   #### 진행 이미지 70% ( 텍스트 정리 필요 )
   <img width="1440" alt="콴트페이지-중간" src="https://user-images.githubusercontent.com/27074717/216818850-2b933346-d697-488f-b598-8fc031d27c51.png">


---------   
# DB 
1. REDIS
    - localhost로 사용 중 ( 랭킹페이지 )

2. H2 Database
    - localhost로 사용 중 ( 예약페이지 )
    - 실행위치: /Users/mkkim/Downloads/h2/bin
    
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
