Bz Application (Android)
======================

# 1. 개요
## 1.1. 주제
상권 정보 안내 & 추천 App (Android)

## 1.2. 목적 / 목표
여러 가지 상권 관련 데이터를 종합하고 분석하여 사용자에게 최적의 상권 위치를 추천해 주고, 정확한 상권 정보를 제공한다.
인구, 매출, 업종, 점포, 상권 관련 공공데이터를 종합하여 빅 데이터 구축

## 1.3. 개발 범위
* 상권 분석 - 지도상에 반경 설정 후 설정한 범위 내의 상권 관련 데이터 출력
* 상권 추천 - 사용자의 설문조사 결과와 분석한 데이터를 비교하여 출력
* 프랜차이즈 - 사용자가 원하는 프랜차이즈의 상세정보 출력

## 1.4. 개발 환경
* php 7.0
* MySQL 4.8.4
* Android Studio 3.4
* Python 3.1

****
# 2. 구성 및 설계
## 2.1. 시스템 구성도
![시스템 구성도](https://github.com/Jeongwonseok/Portfolio_JWS/blob/master/image/시스템구성도.png)

## 2.2. Use Case Diagram
![Use Case Diagram](https://github.com/Jeongwonseok/Portfolio_JWS/blob/master/image/UseCaseDiagram.png)

## 2.3. ERD
![ERD1](https://github.com/Jeongwonseok/Portfolio_JWS/blob/master/image/ERD1.png)
```
* 프랜차이즈 서비스에 대한 물리적 ERD이다.
```

![ERD2](https://github.com/Jeongwonseok/Portfolio_JWS/blob/master/image/ERD2.png)
```
* member(회원 정보) 테이블의 ID 애트리뷰트를 Bookmark(즐겨찾기) 테이블의 외래키로 설정한다.
  각 문의사항에는 하나의 답변만이 가능하므로 1:1 관계이다.
* member(회원 정보) 테이블의 ID 애트리뷰트를 survey(설문조사 결과) 테이블의 외래키로 설정한다.
  각 회원의 설문조사 결과는 하나이므로 1:1관계이다.
```

****
# 3. 제공 기능
## 3.1. 핵심 기능
* **메인 화면**
![메인](https://github.com/Jeongwonseok/Portfolio_JWS/blob/master/image/메인.png)
```
설명 : 사용자는 좌측 상단의 메뉴 버튼을 통해 부가 기능을 이용할 수 있다.
```

* **상권 분석**
![상권분석](https://github.com/Jeongwonseok/Portfolio_JWS/blob/master/image/분석.png)
```
설명 : 사용자는 설정한 범위 내의 상권 관련 데이터를 출력한다.
출력 데이터 : 상권 개요, 매출 분석, 인구 분석, 점포 현황
```

* **상권 추천**
![상권추천](https://github.com/Jeongwonseok/Portfolio_JWS/blob/master/image/추천.png)
```
설명 : 사용자는 실시한 설문조사 결과와 분석한 데이터를 비교하고, 상권을 추천받는다.
출력 데이터 : 상권 개요, 매출 분석, 인구 분석, 점포 현황, 상세평가지수
```

* **프랜차이즈**
![프랜차이즈](https://github.com/Jeongwonseok/Portfolio_JWS/blob/master/image/프랜차이즈.png)
```
설명 : 사용자는 원하는 프랜차이즈의 상세정보를 출력한다.
출력 데이터 : 연 평균 매출액, 초기 부담금, 초기 인테리어 비용, 가맹점 수, 광고비, 판촉비, 가맹 계약기간
```

## 3.2. 부가 기능
* **로그인 / ID,PW 찾기 / 회원 가입**
![로그인](https://github.com/Jeongwonseok/Portfolio_JWS/blob/master/image/로그인.png)
```
설명
- 사용자는 아이디, 비밀번호, 이름, 이메일 주소, 핸드폰 번호, 비밀번호 찾기 질문 입력을 통해 회원 가입을 진행한다.
- 회원가입 한 아이디, 비밀번호 입력을 통해 로그인이 가능하다.
- 회원가입 시 입력한 이름, 핸드폰 번호, 이메일 주소를 이용하여 ID 찾기가 가능하다.
- ID, PW 질문 & 답변을 통해 PW 찾기가 가능하다.
```

* **마이페이지**
![마이페이지](https://github.com/Jeongwonseok/Portfolio_JWS/blob/master/image/마이페이지.png)
```
설명 : 사용자는 회원 가입 시 작성한 개인 정보를 조회 및 수정이 가능하다.
```

* **공지 사항**
![공지사항](https://github.com/Jeongwonseok/Portfolio_JWS/blob/master/image/공지.png)
```
설명 : 사용자는 관리자가 게시한 창업 관련 프로그램, 업데이트 예정 등의 공지사항 조회가 가능하다.
```

* **문의 사항**
![문의사항](https://github.com/Jeongwonseok/Portfolio_JWS/blob/master/image/문의.png)
```
설명 : 사용자는 문의사항을 작성할 수 있다.
```

* **설문 조사**
![설문 조사](https://github.com/Jeongwonseok/Portfolio_JWS/blob/master/image/기록.png)
```
설명 : 사용자는 이전에 시행했던 설문 조사 기록 및 내용을 조회할 수 있다.
```

* **관심 목록**
![관심목록](https://github.com/Jeongwonseok/Portfolio_JWS/blob/master/image/관심목록.png)
```
설명 : 로그인 한 사용자는 상권 분석 기능 이용 중에 관심있는 지역을 저장할 수 있다.
```

* **광고 배너 / 핫이슈**
![광고](https://github.com/Jeongwonseok/Portfolio_JWS/blob/master/image/광고.png)
```
설명 : 상권 관련 광고를 제공해주는 광고 배너 기능과, 최근 이슈인 뉴스들을 제공해주는 핫이슈 기능이 있다.
```

****
# 4. 사용 기술
## 4.1. 추천 알고리즘 - ex) 성장성
![성장성1](https://github.com/Jeongwonseok/Portfolio_JWS/blob/master/image/성장성1.png)
```
설명 : 각 double형 변수에 해당 값을 저장하고, 이를 이용해 수식을 계산한다.
```
![성장성2](https://github.com/Jeongwonseok/Portfolio_JWS/blob/master/image/성장성2.png)
```
설명 : 성장성(매출 규모 증감률)에 관한 증감률을 계산, 성장성(매출 규모 증감률)에 관한 최대값 반환
```
![성장성3](https://github.com/Jeongwonseok/Portfolio_JWS/blob/master/image/성장성3.png)
```
설명 : 성장성(매출 규모 증감률)에 관한 최소값 반환
```

## 4.2. 데이터 크롤링
![크롤링1](https://github.com/Jeongwonseok/Portfolio_JWS/blob/master/image/크롤링1.png)
```
설명 : BASE_URL 설정 및 파일 저장 형식을 설정한다.
```
![크롤링2](https://github.com/Jeongwonseok/Portfolio_JWS/blob/master/image/크롤링2.png)
```
설명 : 상호, 영업표지, 대표자, 업종, 사업자등록일, 대표번호 등 총 13가지 종류의 프랜차이즈 정보를 출력한다.
```

****
# 5. 부록
## 5.1. 수상 이력
* 창의적 공학설계 경진대회 은상 (성결대학교)
* 컴퓨터공학과 제 21회 졸업작품 전시회 우수상 (성결대학교)
* 2019 컴퓨터공학과 경진대회 설계프로젝트 최우수상 (성결대학교)
