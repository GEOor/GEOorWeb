# GEOorWeb

2021학년도 2학기 한국항공대 소프트웨어학과에서 [GEO&](http://geonspace.com/)과 진행한 산학 협력 프로젝트입니다.

## 📢 주제
HillShade 알고리즘을 통한 그림자가 지는 도로 예측

## 🔥 요구사항
- HillShade 알고리즘 Java 단에서 구현
- 관련 Web 구현 (그림자가 지는 도로 시각화 Web)

## 🚀 진행과정
- 태양 방위각, 고도 관련 Web 페이지 크롤링 (JAVA Selenium)
- DEM 데이터 좌표 변환 코드 개발 (TM -> GRS80)
- SHP 파일 사용 (Geotools)
- HillShade 알고리즘 구현
- Web 구현 (Spring, PostgreSQL, PostGIS, Geoserver, Openlayers 사용)

## 👀 HillShade란?

- HillShade란 그림자가 지는 정도를 수치적으로 표현한 것이다.

![image](https://user-images.githubusercontent.com/65909160/147637971-ded4770a-31ee-4cec-bf54-5df03fae5302.png)

## ✔ HillShade 알고리즘

![image](https://user-images.githubusercontent.com/65909160/147638154-37e7339e-8db6-4842-b890-af6932b17319.png)

- HillShade 알고리즘을 사용하는 이유는 다음과 같다.
- 아래 사진처럼 만약 왼쪽에 태양이 떠 있고, 큰 산이 가로막고 있다면 오른쪽 도로에는 그늘이 생길 것이다.

![image](https://user-images.githubusercontent.com/65909160/147639237-b796b85c-ca2f-4376-ae5b-4fada20ff14e.png)


## ⚙ Web 서비스 아키텍처

![image](https://user-images.githubusercontent.com/65909160/147638540-e9cf2d5d-c0f7-4fbc-95e1-602469209d4c.png)

- 배경지도(VWorld 사용) 깔기
- 그 위에 HillShade 값에 따라 WFS Layer에 색 입히기
- Layer 추가 및 View 출력

![image](https://user-images.githubusercontent.com/65909160/147638928-0520311c-78d6-4b6e-9854-5234f5e8ea5f.png)

## 🎞 실행 화면

- 아래 사진과 같이 그림자가 질 가능성이 높은 곳은 빨간색으로 표시

![image](https://user-images.githubusercontent.com/65909160/147639141-678bbf17-d786-4fe1-9d6e-487a0844c089.png)

- 실제 지형과 비교 사진 (왼쪽은 네이버 지도로 지형 확인, 오른쪽은 구현한 Web)

![image](https://user-images.githubusercontent.com/65909160/147639304-01c87360-f13a-4348-b1b9-d025b167ef17.png)


## 🛠 기술 스택

- ### **프론트엔드**

  <img alt="JavaScript" src="https://img.shields.io/badge/JavaScript-F7DF1E.svg?style=for-the-badge&logo=JavaScript&logoColor=white"/> <img alt="Openlayers" src="https://img.shields.io/badge/Openlayers-1F6B75.svg?style=for-the-badge&logo=Openlayers&logoColor=%2361DAFB"/>


- ### **백엔드**

    <img alt="Spring" src="https://img.shields.io/badge/Spring-6DB33F.svg?style=for-the-badge&logo=Spring&logoColor=white"/>
    <img alt="PostgreSQL" src="https://img.shields.io/badge/PostgreSQL-4169E1.svg?style=for-the-badge&logo=PostgreSQL&logoColor=white"/>
    <img alt="Spring Boot" src="https://img.shields.io/badge/Spring Boot-6DB33F.svg?style=for-the-badge&logo=SpringBoot&logoColor=white"/>
    <img alt="JDBC" src="https://img.shields.io/badge/JDBC-007396.svg?style=for-the-badge&logo=Java&logoColor=%2361DAFB"/> <img alt="Geoserver" src="https://img.shields.io/badge/GeoServer-10A0CC.svg?style=for-the-badge"/>

- ### **형상 관리**

    <img alt="Git" src="https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white"/>

    <img alt="GitHub" src="https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white"/> <img alt="Notion" src="https://img.shields.io/badge/Notion-000000.svg?style=for-the-badge&logo=Notion&logoColor=white"/>
