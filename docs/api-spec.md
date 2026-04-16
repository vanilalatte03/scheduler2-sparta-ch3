## 일정 생성

### 기능

새로운 일정을 생성한다.

### Method / URL

`POST /schedules`

### Request Body

```
{
  "userId": 1,
  "title": "제목",
  "content": "내용"
}
```

### 요청 조건

- `userId` : 필수, 최대 30자
- `title` : 필수, 최대 30자
- `content` : 필수, 최대 200자
- 일정은 작성 유저명 대신 `유저 고유 식별자(userId)`를 가진다.

### Response Body

```
{
  "id":1,
  "title":"제목",
  "content":"내용",
  "userId":1,
  "createdAt":"2026-04-16T19:00:00",
  "modifiedAt":"2026-04-16T19:00:00"
}
```

### 상태 코드

- `201 Created`

### 예외

- 필수값 누락 → `400 Bad Request`
- 존재하지 않는 유저 ID → `404 Not Found`

---

## 전체 일정 조회

### 기능

전체 일정을 조회한다.

유저 ID가 포함된다.

### Method / URL

`GET /schedules`

`GET /schedules?userId=1`

### Query Parameter

- `userId` : 선택값, 있으면 해당 유저의 일정만 조회하고 없으면 전체 조회한다.

### Response Body 예시

```
[
  {
    "id":2,
    "title":"제목2",
    "content":"내용2",
    "userId":1,
    "createdAt":"2026-04-16T18:00:00",
    "modifiedAt":"2026-04-16T18:30:00"
  },
  {
    "id":1,
    "title":"제목1",
    "content":"내용1",
    "userId":1,
    "createdAt":"2026-04-16T17:00:00",
    "modifiedAt":"2026-04-16T17:00:00"
  }
]
```

### 상태 코드

- `200 OK`

### 예외

- 존재하지 않는 유저 ID로 조회 → `404 Not Found`

---

## 선택 일정 조회

### 기능

ID로 일정을 단건 조회한다.

### Method / URL

`GET /schedules/{id}`

### Path Variable

- `id` : 일정 ID

### Response Body

```
{
  "id":1,
  "title":"제목",
  "content":"내용",
  "userId":1,
  "createdAt":"2026-04-16T17:00:00",
  "modifiedAt":"2026-04-16T17:00:00"
}
```

### 상태 코드

- `200 OK`

### 예외

- 해당 ID의 일정이 없으면 → `404 Not Found`

---

## 일정 수정

### 기능

선택한 일정을 수정한다.

### Method / URL

`PUT /schedules/{id}`

### 수정 가능 필드

- `title`
- `content`

### 수정 불가 필드

- `userId`
- `createdAt`
- `modifiedAt`

### Request Body

```
{
  "title":"제목 수정",
  "content":"내용 수정"
}
```

### 요청 조건

- `title` : 필수
- `content` : 필수

### Response Body

```
{
  "id":1,
  "title":"제목 수정",
  "content":"내용 수정",
  "userId":1,
  "createdAt":"2026-04-16T17:00:00",
  "modifiedAt":"2026-04-16T19:30:00"
}
```

### 상태 코드

- `200 OK`

### 예외

- 필수값 누락 → `400 Bad Request`
- 일정 없음 → `404 Not Found`

---

## 일정 삭제

### 기능

선택한 일정을 삭제한다.

### Method / URL

`DELETE /schedules/{id}`

### Response

없음

### 상태 코드

- `204 No Content`

### 예외

- 일정 없음 → `404 Not Found`

---

## 유저 생성

### 기능

새로운 유저를 생성한다.

### Method / URL

`POST /users`

### Request Body

```
{
  "userName":"지호",
  "email":"jiho@example.com",
  "password":"12345678"
}
```

### 요청 조건

- `userName` : 필수
- `email` : 필수
- `password` : 필수, 8자 이상
- 이메일은 중복될 수 없다.

### Response Body

```
{
  "id":1,
  "userName":"지호",
  "email":"jiho@example.com",
  "createdAt":"2026-04-16T16:00:00",
  "modifiedAt":"2026-04-16T16:00:00"
}
```

### 상태 코드

- `201 Created`

### 예외

- 필수값 누락 → `400 Bad Request`
- 비밀번호가 8자 미만인 경우 → `400 Bad Request`
- 이메일 중복 → `409 Conflict`

---

## 전체 유저 조회

### 기능

전체 유저를 조회한다.

### Method / URL

`GET /users`

### Response Body 예시

```
[
  {
    "id":1,
    "userName":"지호",
    "email":"jiho@example.com",
    "createdAt":"2026-04-16T16:00:00",
    "modifiedAt":"2026-04-16T16:00:00"
  },
  {
    "id":2,
    "userName":"호지",
    "email":"hoji@example.com",
    "createdAt":"2026-04-16T16:10:00",
    "modifiedAt":"2026-04-16T16:10:00"
  }
]
```

### 상태 코드

- `200 OK`

---

## 선택 유저 조회

### 기능

ID로 유저를 단건 조회한다.

### Method / URL

`GET /users/{id}`

### Path Variable

- `id` : 유저 ID

### Response Body

```
{
  "id":1,
  "userName":"지호",
  "email":"jiho@example.com",
  "createdAt":"2026-04-16T16:00:00",
  "modifiedAt":"2026-04-16T16:00:00"
}
```

### 상태 코드

- `200 OK`

### 예외

- 해당 ID의 유저가 없으면 → `404 Not Found`

---

## 유저 수정

### 기능

유저 정보를 수정한다.

### Method / URL

`PUT /users/{id}`

### Request Body

```
{
  "userName":"임지호",
  "email":"limjiho@example.com",
  "password":"12345678"
}
```

### 요청 조건

- `userName` : 필수
- `email` : 필수
- `password` : 필수, 8자 이상
- 이메일은 중복될 수 없다.

### Response Body

```
{
  "id":1,
  "userName":"임지호",
  "email":"limjiho@example.com",
  "createdAt":"2026-04-16T16:00:00",
  "modifiedAt":"2026-04-16T18:00:00"
}
```

### 상태 코드

- `200 OK`

### 예외

- 필수값 누락 → `400 Bad Request`
- 비밀번호가 8자 미만인 경우 → `400 Bad Request`
- 유저 없음 → `404 Not Found`
- 이메일 중복 → `409 Conflict`

---

## 유저 삭제

### 기능

선택한 유저를 삭제한다.

### Method / URL

`DELETE /users/{id}`

### Response

없음

### 상태 코드

- `204 No Content`

### 예외

- 유저 없음 → `404 Not Found`