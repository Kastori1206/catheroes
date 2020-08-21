<!-- PROJECT LOGO -->
<br />
<p align="center">
  <a href="README.md">
    <img src="document/img/main.jpg" alt="mock-up">
  </a>

  <h2 align="center">캣히어로즈</h2>

  <p align="center">
    길고양이와 돌봄이들의 공존을 위한 서비스
    <br />
</p>


<br>

<!-- ABOUT THE PROJECT -->

## :paperclip: 프로젝트 소개

> 주변 길고양이를 사진으로 검색해서 찾아 정보 공유를 하자!!!

캣히어로즈는 길고양이와 돌보는 사람들의 공존을 지향하는 커뮤니티 웹사이트입니다.
<br>
우리동네 주변의 길고양이들은 많은 사람들의 귀여움을 받고 있지만, 일부 시민들에게 피해를 주고있는 것 또한 사실입니다.
캣히어로즈는 길고양이들이 적절한 관리를 받음으로써 이러한 문제를 완화시킬 수 있다고 믿습니다. 
<br>
캣히어로즈는 이미지 패턴분석 기능을 제공합니다.
사용자가 길고양이 사진을 업로드하면 품종을 분석하여 기등록된 길고양이 중 같은 품종의 길고양이 리스트를 보여줍니다. 
해당 길고양이가 아직 등록되지 않았다면, 새로운 길고양이를 지도에 추가할 수 있습니다.
게시글 작성, 팔로우기능 및 알람기능을 사용하여 애묘인들과 실시간으로 커뮤니케이션이 가능합니다.
아임포트로 도움이 필요한 길고양이에 대해 모금기능을 활성화하여 실제 계좌이체가 가능하도록 구현하였습니다.
이메일인증, 비밀번호 암호화, SSL 방식의 보안서버 구축을 통해 회원계정의 보안을 강화하였습니다.

<br>

<br>

### :clipboard: 프로젝트 산출물

- [프로젝트 계획서](https://lab.ssafy.com/s03-webmobile1-sub3/s03p13d206/document/공통PJT_구미2반_D206_계획서.docx)
- [캣히어로즈 발표자료](https://lab.ssafy.com/s03-webmobile1-sub3/s03p13d206/document/공통PJT_구미2반_D206_PPT.pptx)


### :clipboard : 데모영상
[![Watch the video](document/img/main.jpg)](https://youtu.be/z1T9MDCg0s0)

### :clipboard: 기능 소개

#### 알람기능                                                              
<img src ="./document/gif/알람.gif" width="250" height="350">

#### 뉴스 크롤링
<img src ="https://lab.ssafy.com/s03-webmobile1-sub3/s03p13d206/blob/master/document/gif/뉴스.gif" width="250" height="350">

#### 이메일 인증
<img src ="https://lab.ssafy.com/s03-webmobile1-sub3/s03p13d206/blob/master/document/gif/이메일인증.gif" width="250" height="350">

#### 인피니티스크롤
<img src ="https://lab.ssafy.com/s03-webmobile1-sub3/s03p13d206/blob/master/document/gif/인피니트 스크롤.gif" width="250" height="350">

#### 후원
<img src ="https://lab.ssafy.com/s03-webmobile1-sub3/s03p13d206/blob/master/document/gif/후원.gif" width="250" height="350">

#### SSL
<img src ="https://lab.ssafy.com/s03-webmobile1-sub3/s03p13d206/blob/master/document/gif/ssl.gif" width="250" height="350">

### Built With

사용한 프레임워크

- [Vue.js](https://vuejs.org/)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Django](https://www.djangoproject.com/)

<br>

<!-- GETTING STARTED -->

## :gear: Getting Started

캣히어로즈 시작 방법입니다.

### Installation

1. Clone the repo

```sh
git clone https://lab.ssafy.com/s03-webmobile1-sub3/s03p13d206
```

2. 프론트 실행

```sh
cd frontend
npm i
yarn serve --port 3000
```

3. 케라스(파이썬 버전 최신버전 사용권장)

```sh
cd keras
python -m venv venv
source venv/Scripts/activate
pip install -r requirements.txt
python manage.py migrate
python manage.py runserver
```

4. 백엔드 실행

```sh
cd backend
mvn spring-boot:run
```

<br>

## :hammer_and_pick: 개발스택

협업Tool

- [GitLab](https://about.gitlab.com/)
- [Jira](https://www.atlassian.com/software/jira/)
- [Mattermost](https://mattermost.com/)
- [Postman](https://www.postman.com/)
- [Notion](https://www.notion.so/)

Languege

- [JavaScript]
- [Java](https://java.com/ko/download/)
- [python](https://www.python.org/)

DB

- [MariaDB](https://mariadb.org/)

Library

- [JWT](https://jwt.io/)
- [javaMail](https://javaee.github.io/javamail/)
- [Spring boot](https://spring.io/projects/spring-boot/)
- [Spring security](https://spring.io/projects/spring-security/)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [keras](https://keras.io/)
- [Yolo](https://pjreddie.com/yolo/)w

API

- [Kakao](https://developers.kakao.com/)
- [I'mport](https://www.iamport.kr/)
- [AWS SDK](https://aws.amazon.com/ko/sdk-for-java/)

ETC

- RestfulApi
- [Swagger](https://swagger.io/)
- SSL

<br>

<!-- CONTACT -->

## :busts_in_silhouette: Contact

### [Samsung Software Academy for Youth](https://www.ssafy.com/) 1th Gumi, 공통프로젝트 D206


  - `김영민` - kastori1990@gmail.com<br>
  - `김현국` - khg6152450@naver.com<br>
  - `이원희` - haruu0419@gmail.com<br>
  - `최수병` - chb0328@gmail.com <br>


Project Link: [https://lab.ssafy.com/s03-webmobile1-sub3/s03p13d206](https://lab.ssafy.com/s03-webmobile1-sub3/s03p13d206)

<br>

<!-- ACKNOWLEDGEMENTS -->


<br>
