
# Chamma-Android-Template

<br/> <br/>

# 파일 조직도

<br/><br/>

```xml

├── manifests/
|   └── AndroidManifest.xml
|
└──java
    ├── config/
    |   ├── BaseActivityDB  #  DataBinding Activity format
    |   ├── BaseActivityVB  #  ViewBinding Activity format
    |   ├── BaseFragmentDB  #  DataBinding Fragment format
    |   └── BaseFragmentVB  #  ViewBinding Fragment format
    ├── models/
    |   └── ex) loginmodel
    |        ├── ex) LoginResponse
    |        ├── ex) LoginPostData
    |        └── ex) LoginData
    ├── ui/
    |   └── ex) login
    |        ├── ex) LoginActivity
    |        ├── ex) LoginViewModel
    |        └── ex) LoginViewModelFactory
    | 
    | 
    ├── util/
    |   ├── LoadingDialog # Activity / Fragment 모두에서 띄울수 있는 LoadingDialog
    |   └── ToastMessageUtil # 토스트메세지 간편  실행 가능
    | 
    └── App  # 앱 실행시, sharedPreferences / retrofit 객체 생성, 
						 # 앱 전역에서 Context 반환 가능
						 # Okhttp 이용 Retrofit2 통신시 Log로 모니터링 가능
```

<br/><br/>

# 네이밍 규칙

<br/>

### XML file

- activity_액티비티명
- fragment_프래그먼트명
- item_어디속한item인지_무엇을보여주는지
    - ex) activity_home 에 속한 recyclerview의 아이템인데, 화장실 리스트 보여주는것이면
    - item_home_toilet

<br/>

### XML id

→ 뒤 네이밍은 자유롭게 하지만, 명시적으로 하기!! (어떤 기능인지 꼭 의미가 있어야함)

- text view : tv_어떤텍스트인지 자유롭게
- edittext : et_어떤et인지 자유롭게
- imageView : iv_어떤 이미지인지 자유롭게
- button / imagebutton : btn_어떤 버튼인지 자유롭게
- layout : layout_어떤 레이아웃인지 자유롭게

<br/>

### XML color

→ 셋중 하나 선택해서 네이밍. 구별 용이하도록, 겹치는 색깔 없도록 유의

- chamma_색깔명
- chamma_쓰인view이름
- chamma_쓰인page이름

<br/>

### Package / File

→ 페이지 패키지명

- 소문자 영어
- 페이지 나타내는 단어 선택
- ex) 로그인페이지 패키지명 → login

→ model 패키지명

- 소문자 영어
- 끝에 model 붙이기
- ex) 로그인페이지에 사용하는 model 패키지명 → loginmodel

→ model 파일명

- Retrofit 응답 모델 : ~Response.kt
- Retrofit post 모델 : ~PostData.kt
- 일반 데이터 : ~Data.kt

<br/><br/>

# Github 규칙

<br/><br/>

→ 작업범위 Issue 생성 

- 템플릿 참고
- label링 필수

→ dev 브랜치 기준  기능개발시 feature 브랜치 파서 진행

- feature/issue-이슈번호
- feature/#이슈번호-페이지명

→ 기능개발 완료시, feature 브랜치에서 dev 브랜치로 pull request

→ 기능개발 도중, 팀원이 pull request 하게되면, Android Studio 에서 merge 후 commit 하기 (충돌방지)
