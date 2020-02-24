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
* **상권 분석**
<img src="https://github.com/Jeongwonseok/Portfolio_JWS/blob/master/image/분석.png" width="450px" height="300px" title="px(픽셀) 크기 설정" alt="RubberDuck"></img>
```
설명 : 사용자는 설정한 범위 내의 상권 관련 데이터를 출력한다.
출력 데이터 : 상권 개요, 매출 분석, 인구 분석, 점포 현황
```

* **상권 추천**
![ERD2](https://github.com/Jeongwonseok/Portfolio_JWS/blob/master/image/추천.png)
```
설명 : 사용자는 실시한 설문조사 결과와 분석한 데이터를 비교하고, 상권을 추천받는다.
출력 데이터 : 상권 개요, 매출 분석, 인구 분석, 점포 현황, 상세평가지수
```

* **프랜차이즈**
![ERD2](https://github.com/Jeongwonseok/Portfolio_JWS/blob/master/image/프랜차이즈.png)
```
설명 : 사용자는 원하는 프랜차이즈의 상세정보를 출력한다.
출력 데이터 : 연 평균 매출액, 초기 부담금, 초기 인테리어 비용, 가맹점 수, 광고비, 판촉비, 가맹 계약기간
```

## 3.2. 부가 기능
부가 기능 이미지
```
부가 기능 설명
```

****
# 4. 사용 기술
## 4.1. 추천 알고리즘
추천 알고리즘 이미지
```
추천 알고리즘 설명
```

## 4.2. 데이터 크롤링
데이터 크롤링 이미지
```
데이터 크롤링 설명
```

## 4.3. 데이터 분석법
데이터 분석법 이미지
```
데이터 분석법 설명
```

****
# 5. 부록
## 5.1. 수상 이력
* 창의적 공학설계 경진대회 은상 (성결대학교)
* 컴퓨터공학과 제 21회 졸업작품 전시회 우수상 (성결대학교)
* 2019 컴퓨터공학과 경진대회 설계프로젝트 최우수상 (성결대학교)
