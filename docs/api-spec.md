## 공통 사항

### 인증 방식

- 로그인 성공 시 세션에 `userId`가 저장됩니다.
- 인증이 필요한 API는 세션 쿠키(`JSESSIONID`)가 필요합니다.
- 로그인하지 않은 상태에서 인증이 필요한 API를 호출하면 `401 Unauthorized`가 발생합니다.

### 공통 에러 응답

```json
{
  "message": "에러 메시지"
}
```

---

## 일정 생성

### 기능

새 일정을 생성합니다.

로그인한 사용자의 일정으로 생성됩니다.

### Method / URL

`POST /schedules`

### 인증

필요

### Request Body

```json
{
  "title": "회의 준비",
  "content": "발표 자료 정리"
}
```

### 요청 조건

- `title` : 필수, 공백 불가, 최대 30자
- `content` : 필수, 공백 불가, 최대 200자

### Response Body

```json
{
  "id": 1,
  "title": "회의 준비",
  "content": "발표 자료 정리",
  "userId": 1,
  "createAt": "2026-01-01T09:00:00",
  "modifyAt": "2026-01-01T09:00:00"
}
```

### 상태 코드

- `201 Created`

### 예외

- 로그인 필요 시 `401 Unauthorized`
- 요청값 검증 실패 시 `400 Bad Request`

---

## 일정 전체 조회

### 기능

일정 목록을 페이지 단위로 조회합니다.

수정일 기준 내림차순으로 조회됩니다.

### Method / URL

`GET /schedules`

`GET /schedules?page=0&size=10`

### Query Parameter

- `page` : 선택, 기본값 `0`, 0 이상
- `size` : 선택, 기본값 `10`, 1 이상

### Response Body 예시

```json
{
  "content": [
    {
      "id": 2,
      "title": "회의 준비",
      "content": "발표 자료 정리",
      "commentCount": 3,
      "createdAt": "2026-01-01T09:00:00",
      "modifiedAt": "2026-01-01T10:30:00",
      "userName": "홍길동"
    },
    {
      "id": 1,
      "title": "점심 약속",
      "content": "강남역 12시",
      "commentCount": 0,
      "createdAt": "2026-01-01T08:00:00",
      "modifiedAt": "2026-01-01T08:00:00",
      "userName": "임지호"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10
  },
  "totalElements": 2,
  "totalPages": 1,
  "last": true,
  "size": 10,
  "number": 0,
  "sort": {
    "sorted": true,
    "unsorted": false,
    "empty": false
  },
  "first": true,
  "numberOfElements": 2,
  "empty": false
}
```

### 상태 코드

- `200 OK`

### 예외

- `page` 또는 `size` 검증 실패 시 `400 Bad Request`

---

## 선택 일정 조회

### 기능

일정 ID로 단건 조회합니다.

### Method / URL

`GET /schedules/{scheduleId}`

### Path Variable

- `scheduleId` : 일정 ID

### Response Body

```json
{
  "id": 1,
  "title": "회의 준비",
  "content": "발표 자료 정리",
  "userId": 1,
  "createAt": "2026-01-01T09:00:00",
  "modifyAt": "2026-01-01T10:30:00"
}
```

### 상태 코드

- `200 OK`

### 예외

- 일정이 없으면 `404 Not Found`

---

## 일정 수정

### 기능

선택한 일정을 수정합니다.

작성자 본인만 수정할 수 있습니다.

### Method / URL

`PATCH /schedules/{scheduleId}`

### 인증

필요

### Path Variable

- `scheduleId` : 일정 ID

### 수정 가능 필드

- `title`
- `content`

### Request Body

```json
{
  "title": "회의 준비 수정",
  "content": "발표 자료 및 리허설 진행"
}
```

### 요청 조건

- `title` : 필수, 공백 불가, 최대 30자
- `content` : 필수, 공백 불가, 최대 200자

### Response Body

```json
{
  "id": 1,
  "title": "회의 준비 수정",
  "content": "발표 자료 및 리허설 진행",
  "userId": 1,
  "createAt": "2026-01-01T09:00:00",
  "modifyAt": "2026-01-01T11:00:00"
}
```

### 상태 코드

- `200 OK`

### 예외

- 로그인 필요 시 `401 Unauthorized`
- 본인 일정이 아니면 `403 Forbidden`
- 요청값 검증 실패 시 `400 Bad Request`
- 일정이 없으면 `404 Not Found`

---

## 일정 삭제

### 기능

선택한 일정을 삭제합니다.

작성자 본인만 삭제할 수 있습니다.

### Method / URL

`DELETE /schedules/{scheduleId}`

### 인증

필요

### Path Variable

- `scheduleId` : 일정 ID

### Response

없음

### 상태 코드

- `204 No Content`

### 예외

- 로그인 필요 시 `401 Unauthorized`
- 본인 일정이 아니면 `403 Forbidden`
- 일정이 없으면 `404 Not Found`

---

## 댓글 생성

### 기능

특정 일정에 댓글을 생성합니다.

로그인한 사용자의 댓글로 생성됩니다.

### Method / URL

`POST /schedules/{scheduleId}/comments`

### 인증

필요

### Path Variable

- `scheduleId` : 일정 ID

### Request Body

```json
{
  "content": "이 일정 확인했습니다."
}
```

### 요청 조건

- `content` : 필수, 공백 불가, 최대 200자

### Response Body

```json
{
  "id": 1,
  "content": "이 일정 확인했습니다.",
  "userId": 1,
  "scheduleId": 3,
  "createAt": "2026-01-01T12:00:00",
  "modifyAt": "2026-01-01T12:00:00"
}
```

### 상태 코드

- `201 Created`

### 예외

- 로그인 필요 시 `401 Unauthorized`
- 요청값 검증 실패 시 `400 Bad Request`
- 일정이 없으면 `404 Not Found`

---

## 댓글 목록 조회

### 기능

특정 일정의 댓글 목록을 조회합니다.

### Method / URL

`GET /schedules/{scheduleId}/comments`

### Path Variable

- `scheduleId` : 일정 ID

### Response Body 예시

```json
[
  {
    "id": 1,
    "content": "이 일정 확인했습니다.",
    "userId": 1,
    "scheduleId": 3,
    "createAt": "2026-01-01T12:00:00",
    "modifyAt": "2026-01-01T12:00:00"
  },
  {
    "id": 2,
    "content": "참석 가능합니다.",
    "userId": 2,
    "scheduleId": 3,
    "createAt": "2026-01-01T12:10:00",
    "modifyAt": "2026-01-01T12:10:00"
  }
]
```

### 상태 코드

- `200 OK`

### 예외

- 일정이 없으면 `404 Not Found`

---

## 댓글 수정

### 기능

댓글을 수정합니다.

작성자 본인만 수정할 수 있습니다.

### Method / URL

`PATCH /comments/{commentId}`

### 인증

필요

### Path Variable

- `commentId` : 댓글 ID

### Request Body

```json
{
  "content": "내용을 수정했습니다."
}
```

### 요청 조건

- `content` : 필수, 공백 불가, 최대 200자

### Response Body

```json
{
  "id": 1,
  "content": "내용을 수정했습니다.",
  "userId": 1,
  "scheduleId": 3,
  "createAt": "2026-01-01T12:00:00",
  "modifyAt": "2026-01-01T12:30:00"
}
```

### 상태 코드

- `200 OK`

### 예외

- 로그인 필요 시 `401 Unauthorized`
- 본인 댓글이 아니면 `403 Forbidden`
- 요청값 검증 실패 시 `400 Bad Request`
- 댓글이 없으면 `404 Not Found`

---

## 댓글 삭제

### 기능

댓글을 삭제합니다.

작성자 본인만 삭제할 수 있습니다.

### Method / URL

`DELETE /comments/{commentId}`

### 인증

필요

### Path Variable

- `commentId` : 댓글 ID

### Response

없음

### 상태 코드

- `204 No Content`

### 예외

- 로그인 필요 시 `401 Unauthorized`
- 본인 댓글이 아니면 `403 Forbidden`
- 댓글이 없으면 `404 Not Found`

---

## 유저 생성

### 기능

새 유저를 생성합니다.

### Method / URL

`POST /users`

### Request Body

```json
{
  "userName": "임지호",
  "email": "jiho@example.com",
  "password": "12345678"
}
```

### 요청 조건

- `userName` : 필수, 공백 불가, 최대 30자
- `email` : 필수, 이메일 형식, 최대 60자
- `password` : 필수, 공백 불가, 8자 이상 60자 이하
- 이메일은 중복될 수 없습니다.

### Response Body

```json
{
  "id": 1,
  "userName": "임지호",
  "email": "jiho@example.com",
  "createAt": "2026-01-01T08:00:00",
  "modifyAt": "2026-01-01T08:00:00"
}
```

### 상태 코드

- `201 Created`

### 예외

- 요청값 검증 실패 시 `400 Bad Request`
- 이메일 중복 시 `409 Conflict`

---

## 전체 유저 조회

### 기능

전체 유저를 조회합니다.

### Method / URL

`GET /users`

### Response Body 예시

```json
[
  {
    "id": 1,
    "userName": "임지호",
    "email": "jiho@example.com",
    "createAt": "2026-01-01T08:00:00",
    "modifyAt": "2026-01-01T08:00:00"
  },
  {
    "id": 2,
    "userName": "홍길동",
    "email": "gildong@example.com",
    "createAt": "2026-01-01T08:10:00",
    "modifyAt": "2026-01-01T08:10:00"
  }
]
```

### 상태 코드

- `200 OK`

---

## 선택 유저 조회

### 기능

유저 ID로 단건 조회합니다.

### Method / URL

`GET /users/{userId}`

### Path Variable

- `userId` : 유저 ID

### Response Body

```json
{
  "id": 1,
  "userName": "임지호",
  "email": "jiho@example.com",
  "createAt": "2026-01-01T08:00:00",
  "modifyAt": "2026-01-01T08:00:00"
}
```

### 상태 코드

- `200 OK`

### 예외

- 유저가 없으면 `404 Not Found`

---

## 유저 수정

### 기능

유저 정보를 수정합니다.

본인만 수정할 수 있습니다.

### Method / URL

`PATCH /users/{userId}`

### 인증

필요

### Path Variable

- `userId` : 유저 ID

### Request Body

```json
{
  "userName": "임지호 수정",
  "email": "newjiho@example.com"
}
```

### 요청 조건

- `userName` : 필수, 공백 불가, 최대 30자
- `email` : 필수, 이메일 형식, 최대 60자
- 이메일은 다른 유저와 중복될 수 없습니다.

### Response Body

```json
{
  "id": 1,
  "userName": "임지호 수정",
  "email": "newjiho@example.com",
  "createAt": "2026-01-01T08:00:00",
  "modifyAt": "2026-01-01T09:30:00"
}
```

### 상태 코드

- `200 OK`

### 예외

- 로그인 필요 시 `401 Unauthorized`
- 본인 유저가 아니면 `403 Forbidden`
- 요청값 검증 실패 시 `400 Bad Request`
- 유저가 없으면 `404 Not Found`
- 이메일 중복 시 `409 Conflict`

---

## 유저 삭제

### 기능

유저를 삭제합니다.

본인만 삭제할 수 있습니다.

### Method / URL

`DELETE /users/{userId}`

### 인증

필요

### Path Variable

- `userId` : 유저 ID

### Response

없음

### 상태 코드

- `204 No Content`

### 예외

- 로그인 필요 시 `401 Unauthorized`
- 본인 유저가 아니면 `403 Forbidden`
- 유저가 없으면 `404 Not Found`

---

## 로그인

### 기능

이메일과 비밀번호로 로그인합니다.

로그인 성공 시 세션이 생성됩니다.

### Method / URL

`POST /users/login`

### Request Body

```json
{
  "email": "jiho@example.com",
  "password": "12345678"
}
```

### 요청 조건

- `email` : 필수, 이메일 형식
- `password` : 필수, 공백 불가

### Response Body

```json
"로그인 성공"
```

### 상태 코드

- `200 OK`

### 예외

- 요청값 검증 실패 시 `400 Bad Request`
- 이메일 또는 비밀번호 불일치 시 `401 Unauthorized`
