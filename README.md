# URL Shortener Service

간결하고 효율적인 URL 단축 서비스입니다. Spring Boot와 Kotlin으로 개발되었으며, 체계적인 레이어 구조와 현대적인 UI를 제공합니다.

## 프로젝트 요구사항 및 구현 내용

### 1. 기본 URL 단축 서비스 구현
- [x] Controller, Service, Repository 레이어로 구조화
- [x] POST /api/shorten 엔드포인트 구현
  - 문자와 숫자로만 된, 문자로 시작하는 랜덤 6자리 문자열 키 생성
  - In-memory HashMap에 저장
  - 200 OK와 함께 단축 URL 반환
- [x] GET /api/shorten/{shortKey} 엔드포인트 구현
  - 단축 키를 받아 해당하는 원본 URL 반환

### 2. 관리자 기능 구현 및 UI 개선
- [x] 관리자 페이지 구현 (/admin)
- [x] URL 관리 기능 (조회, 삭제)
- [x] 사용자 친화적인 홈페이지 UI 구현
- [x] JIRA 스타일 무채색 디자인 적용

### 3. 시스템 편의 기능
- [x] 요청 테스트를 위한 HTTP 클라이언트 파일 제공
- [x] 주요 작업에 로깅 구현 (Kotlin Logging 사용)
- [x] 확장성을 위한 추가 저장소 설정 (PostgreSQL) 준비

## 프로젝트 구조

```
src/
├── main/
│   ├── kotlin/org/yechan/mcp/
│   │   ├── controller/       # API 컨트롤러
│   │   ├── service/          # 비즈니스 로직
│   │   ├── repository/       # 데이터 접근 계층
│   │   └── dto/              # 데이터 전송 객체
│   └── resources/
│       └── templates/        # Thymeleaf 템플릿 (홈페이지, 관리자 페이지)
```

## API 엔드포인트

### URL 단축하기
```
POST /api/shorten
```
**요청 본문:**
```json
{
  "url": "https://www.example.com/some/very/long/url"
}
```
**응답:**
```json
{
  "shortenedUrl": "http://localhost:8080/api/shorten/abC12d",
  "shortKey": "abC12d"
}
```

### 원본 URL 가져오기
```
GET /api/original/{shortKey}
```
**응답:**
```json
{
  "originalUrl": "https://www.example.com/some/very/long/url"
}
```

### 원본 URL로 리디렉션
```
GET /api/shorten/{shortKey}
```
**응답:**
- HTTP 302 응답으로 원본 URL로 리디렉션

### 모든 URL 목록 가져오기
```
GET /api/shorten/urls
```

### URL 삭제하기
```
DELETE /api/shorten/{shortKey}
```

## 웹 페이지

- **홈페이지**: `http://localhost:8080/`
  - URL 단축 입력 폼
  - 결과 표시 및 복사 기능
  
- **관리자 페이지**: `http://localhost:8080/admin`
  - 모든 단축 URL 목록 표시
  - URL 삭제 및 관리 기능
  - 통계 표시

## 실행 방법

### 기본 실행
```bash
./gradlew bootRun
```

### 빌드 및 실행
```bash
./gradlew build
java -jar build/libs/mcp-0.0.1-SNAPSHOT.jar
```

## 테스트

프로젝트에는 다음과 같은 테스트 방법이 포함되어 있습니다:

1. **HTTP 클라이언트 파일**: `http-client.http`를 IntelliJ IDEA에서 사용하여 API 수동 테스트
2. **단위 테스트**: `./gradlew test` 명령으로 실행

## 기술 스택

- **언어**: Kotlin 1.9.25
- **프레임워크**: Spring Boot 3.4.4
- **빌드 도구**: Gradle
- **템플릿 엔진**: Thymeleaf
- **로깅**: Kotlin Logging
- **프론트엔드**: HTML, CSS, JavaScript
