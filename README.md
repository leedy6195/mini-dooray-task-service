# ISSUE
* TASK TABLE에 NAME 컬럼 없음
* 조회는 아예 안씀
* 설명 추가 필요

# 프로젝트
주의사항
* 회원 ID를 가져오는 곳은 전부 해당 ID 가 없으면 진행 불가하도록 체크 필요

프로젝트 생성
* PROJECT_ACCOUNT에 해당 권한 (관리자, 멤버 등) 넣어주기

프로젝트 멤버 추가

프로젝트 상태 변경

TASK CRUD
* MILE STONE 만 변경 가능하도록 하는 기능 필요

COMMENT CRUD

TAG CRUD
* TASK TAG 포함

MILE STONE CRUD

# 메시지
* json 포맷을 사용합니다.

## 요청 메시지
* json body를 포함하여 보내야 하는 메시지의 경우, Content-Type 헤더를 명시해야 합니다.
* 모든 요청에 Host를 명시합니다.
* ~~모든 요청에 Authorization 헤더를 포함해 요청합니다.~~

> Content-Type : application/json

> Host : localhost:3306

## 응답 메시지

# API Spec

## PROJECT-API > Project
### 프로젝트 생성
* 설명 추가
#### POST /project-api/projects

##### Request
* Parameters

* Body

|항목명|항목명(영문)|항목크기(최소값)|항목구분|샘플데이터|비고|
|---|----|---|--|---|--|
|사용자 아이디|accountId|String|필수|sampleId|작성자|
|프로젝트 이름|projectName|45(2)|필수|샘플프로젝트||

```json
{
    "accountId": "accountId",
    "projectName": "projectName"
}
```

##### 요청 에제

```http
POST /project-api/projects
```

##### Response

* Body

```json
{
	"header": {
		"resultCode": 201,
		"resultMessage": "created successfully",
		"successful": true
	},
	"result": [
		{
			"id": 1
		}
	],
	"totalCount": 1
}

```
##### HTTP 응답 코드
* 201 CREATED
* 404 NOT FOUND
	* NotFoundException

### 프로젝트 수정
* 이름
* 상태
	* 활성(01)
	* 휴면(02)
	* 종료(03)
#### PUT /project-api/projects/{projectId}
##### Request
* Parameters

|항목명|항목명(영문)|항목크기(최소값)|항목구분|샘플데이터|비고|
|---|----|---|--|---|--|
|프로젝트 아이디|projecId|Long|필수|1|식별자|

* Body

|항목명|항목명(영문)| 항목크기(최소값) |항목구분|샘플데이터|비고|
|---|----|-----------|--|---|--|
|프로젝트 이름|projectName| 45(2)     |옵션|샘플프로젝트||
|프로젝트 상태 코드|projectStateCode| 2(2)      |옵션|01||

```json
{
	"projectName": "projectName",
	"projectStateCode": "01"
}
```

##### 요청 예제

```http
PUT /project-api/projects/{projectId}
```

##### Response

* Body

```json
{
	"header": {
		"resultCode": 200,
		"resultMessage": "updated successfully",
		"successful": true
	},
	"result": [
		{
			"id": 1
		}
	],
	"totalCount": 1
}

```
##### HTTP 응답 코드
* 200 OK
* 404 NOT FOUND
	* NotFoundException

### 프로젝트 삭제
* 설명 추가
#### DELETE /project-api/projects/{projectId}
##### Request
* Parameters

|항목명|항목명(영문)|항목크기(최소값)|항목구분|샘플데이터|비고|
|---|----|---|--|---|--|
|프로젝트 아이디|projecId|Long|필수|1|식별자|

##### 요청 에제

```http
DELETE /project-api/projects/{projectId}
```

##### Response

* Body

```json
{
	"header": {
		"resultCode": 204,
		"resultMessage": "deleted successfully",
		"successful": true
	},
	"result": [],
	"totalCount": 0
}
```
##### HTTP 응답 코드
* 204 NO CONTENT
* 404 NOT FOUND
	* NotFoundException

### 프로젝트 조회
* 설명 추가

#### GET /project-api/projects/{projectId}
##### Request
* Parameters

|항목명|항목명(영문)|항목크기(최소값)|항목구분|샘플데이터|비고|
|---|----|---|--|---|--|

##### 요청 에제

```http
```

##### Response

* Body

```json
{
}
```
##### HTTP 응답 코드
* 

#### GET /project-api/projects
##### Request
* Parameters

|항목명|항목명(영문)|항목크기(최소값)|항목구분|샘플데이터|비고|
|---|----|---|--|---|--|
|페이지|page||옵션|0|페이지 번호, 목록 조회시 필수|
|한 페이지 결과수|numOfRows||옵션|10|한 페이지 결과 수, 목록조회시 필수|

##### 요청 에제

```http
```

##### Response

* Body

```json
{
}

```
##### HTTP 응답 코드
* 

## PROJECT-API > ProjectAccount
* 관리자
* 멤버
### 프로젝트 사용자 추가
#### POST /project-api/projects/{projectId}/accounts
##### Request
* Parameters

|항목명|항목명(영문)|항목크기(최소값)|항목구분|샘플데이터|비고|
|---|----|---|--|---|--|
|프로젝트 아이디|projecId|Long|필수|1|식별자|

* Body

|항목명|항목명(영문)|항목크기(최소값)|항목구분|샘플데이터|비고|
|---|----|---|--|---|--|
|사용자 아이디|accountId|40(5)|필수|sampleId|권한을 부여받는 사용자|
|권한 코드|authorityCode|4(4)|필수|authorityCode|권한|

```json
{
    "accountId":"accountId",
    "authorityCode":"authorityCode"    
}
```

##### 요청 에제

```http
POST /project-api/projects/{projectId}/accounts
```

##### Response

* Body

```json
{
	"header": {
		"resultCode": 201,
		"resultMessage": "created successfully",
		"successful": true
	},
	"result": [],
	"totalCount": 0
}

```
##### HTTP 응답 코드
* 201 CREATED
* 404 NOT FOUND
	* NotFoundException

### 프로젝트 사용자 수정
#### PUT /project-api/projects/{projectId}/accounts/{accountId}
##### Request
* Parameters

|항목명|항목명(영문)|항목크기(최소값)|항목구분|샘플데이터|비고|
|---|----|---|--|---|--|
|프로젝트 아이디|projecId|Long|필수|1|식별자|
|사용자 아이디|accountId|40(5)|필수|sampleId||

* Body

|항목명|항목명(영문)| 항목크기(최소값) |항목구분| 샘플데이터 |비고|
|---|----|-----------|--|-------|--|
|권한 코드|authorityCode| 2(2)      |필수| 01    |수정될 권한|

```json
{
    "authorityCode": "01"    
}
```

##### 요청 에제

```http
PUT /project-api/projects/{projectId}/accounts/{accountId}
```

##### Response

* Body

```json
{
	"header": {
		"resultCode": 200,
		"resultMessage": "updated successfully",
		"successful": true
	},
	"result": [],
	"totalCount": 0
}

```
##### HTTP 응답 코드
* 200 OK
* 404 NOT FOUND
	* NotFoundException

### 프로젝트 사용자 삭제
#### DELETE /project-api/projects/{projectId}/accounts/{accountId}
##### Request
* Parameters

|항목명|항목명(영문)|항목크기(최소값)|항목구분|샘플데이터|비고|
|---|----|---|--|---|--|
|프로젝트 아이디|projecId|Long|필수|1|식별자|
|사용자 아이디|accountId|40(5)|필수|sampleId||

##### 요청 에제

```http
DELETE /project-api/projects/{projectId}/accounts/{accountId}
```

##### Response

* Body

```json
{
	"header": {
		"resultCode": 204,
		"resultMessage": "deleted successfully",
		"successful": true
	},
	"result": [],
	"totalCount": 0
}
```
##### HTTP 응답 코드
* 208 NO CONTENT
* 404 NOT FOUND
	* NotFoundException

### 프로젝트 사용자 권한 조회
#### GET /project-api/projects/{projectId}/accounts/{accountId}
##### Request
* Parameters

|항목명|항목명(영문)|항목크기(최소값)|항목구분|샘플데이터|비고|
|---|----|---|--|---|--|

##### 요청 에제

```http
```

##### Response

* Body

```json
{
}

```
##### HTTP 응답 코드
* 

#### GET /project-api/projects/{projectId}/accounts
##### Request
* Parameters

|항목명|항목명(영문)|항목크기(최소값)|항목구분|샘플데이터|비고|
|---|----|---|--|---|--|
|페이지|page||옵션|0|페이지 번호, 목록 조회시 필수|
|한 페이지 결과수|numOfRows||옵션|10|한 페이지 결과 수, 목록조회시 필수|

##### 요청 에제

```http
```

##### Response

* Body

```json
{
}

```
##### HTTP 응답 코드
* 

## PROJECT-API > MileStone
### 마일스톤 추가
#### CREATE /project-api/projects/{projectId}/milestones
##### Request
* Parameters

|항목명|항목명(영문)|항목크기(최소값)|항목구분|샘플데이터|비고|
|---|----|---|--|---|--|
|프로젝트 아이디|projecId|Long|필수|1|식별자|

* Body

|항목명|항목명(영문)|항목크기(최소값)|항목구분|샘플데이터|비고|
|---|----|---|--|---|--|
|마일스톤 이름|name|45(2)|필수|sampleMileStone|추가할 마일스톤 이름|
|시작일|startDate|LocalDate|필수|2023-06-10||
|종료일|endDate|LocalDate|필수|2023-06-13||

```json
{
    "name": "name",
    "startDate": "2023-06-10",
    "endDate": "2023-06-13"
}
```

##### 요청 에제

```http
CREATE /project-api/projects/{projectId}/milestones
```

##### Response

* Body

```json
{
	"header": {
		"resultCode": 201,
		"resultMessage": "created successfully",
		"successful": true
	},
	"result": [
		{
			"id": 1
		}
	],
	"totalCount": 1
}

```
##### HTTP 응답 코드
* 201 CREATED
* 404 NOT FOUND
	* NotFoundException

### 마일스톤 수정
#### PUT /project-api/projects/{projectId}/milestones/{milestoneId}
##### Request
* Parameters

|항목명|항목명(영문)|항목크기(최소값)|항목구분|샘플데이터|비고|
|---|----|---|--|---|--|
|프로젝트 아이디|projecId|Long|필수|1|식별자|
|마일스톤 아이디|milestoneId|45(2)|필수|sampleMileStone|추가할 마일스톤|

* Body

|항목명|항목명(영문)|항목크기(최소값)|항목구분|샘플데이터|비고|
|---|----|---|--|---|--|
|마일스톤 이름|name|45(2)|필수|sampleMileStone|수정된 마일스톤 이름|
|시작일|startDate|LocalDate|필수|2023-06-10||
|종료일|endDate|LocalDate|필수|2023-06-13||

```json
{
    "name":"name",
    "startDate":"2023-06-10",
    "endDate":"2023-06-13"
}
```

##### 요청 에제

```http
PUT /project-api/projects/{projectId}/milestones/{milestoneId}
```

##### Response

* Body

```json
{
	"header": {
		"resultCode": 200,
		"resultMessage": "updated successfully",
		"successful": true
	},
	"result": [
		{
			"id": 1
		}
	],
	"totalCount": 1
}

```

##### HTTP 응답 코드
* 200 OK
* 404 NOT FOUND
    * NotFoundException

### 마일스톤 삭제
#### DELETE /project-api/projects/{projectId}/milestones/{milestoneId}
##### Request
* Parameters

|항목명|항목명(영문)|항목크기(최소값)|항목구분|샘플데이터|비고|
|---|----|---|--|---|--|
|프로젝트 아이디|projecId|Long|필수|1||
|마일스톤 아이디|milestoneId|Long|필수|1|마일스톤|

##### 요청 에제

```http
DELETE /project-api/projects/{projectId}/milestones/{milestoneId}
```

##### Response

* Body

```json
{
	"header": {
		"resultCode": 204,
		"resultMessage": "deleted successfully",
		"successful": true
	},
	"result": [],
	"totalCount": 0
}
```

##### HTTP 응답 코드
* 208 NO CONTENT
* 404 NOT FOUND
    * NotFoundException

### 마일스톤 조회
#### GET /project-api/projects/{projectId}/milestones/{milestoneId}
##### Request
* Parameters

|항목명|항목명(영문)|항목크기(최소값)|항목구분|샘플데이터|비고|
|---|----|---|--|---|--|

##### 요청 에제

```http
```

##### Response

* Body

```json
{
}

```
##### HTTP 응답 코드
* 

#### GET /project-api/projects/{projectId}/milestones
##### Request
* Parameters

|항목명|항목명(영문)|항목크기(최소값)|항목구분|샘플데이터|비고|
|---|----|---|--|---|--|
|페이지|page||옵션|0|페이지 번호, 목록 조회시 필수|
|한 페이지 결과수|numOfRows||옵션|10|한 페이지 결과 수, 목록조회시 필수|

##### 요청 에제

```http
```

##### Response

* Body

```json
{
}

```
##### HTTP 응답 코드
* 

## PROJECT-API > Tag

### 태그 추가
#### POST /project-api/projects/{projectId}/tags
##### Request
* Parameters

|항목명|항목명(영문)|항목크기(최소값)|항목구분|샘플데이터|비고|
|---|----|---|--|---|--|
|프로젝트 아이디|projecId|Long|필수|1|식별자|

* Body

|항목명|항목명(영문)|항목크기(최소값)|항목구분|샘플데이터|비고|
|---|----|---|--|---|--|
|태그 이름|name|45(2)|필수|sampleTagName||
```json
{
    "name" : "name"
}
```
##### 요청 에제

```http
POST /project-api/projects/{projectId}/tags
```

##### Response

* Body

```json
{
	"header": {
		"resultCode": 201,
		"resultMessage": "created successfully",
		"successful": true
	},
	"result": [
		{
			"id": 1
		}
	],
	"totalCount": 1
}
```
##### HTTP 응답 코드
* 201 CREATED
* 404 NOT FOUND
	* NotFoundException
### 태그 수정
#### PUT /project-api/projects/{projectId}/tags/{tagId}
##### Request
* Parameters

|항목명|항목명(영문)|항목크기(최소값)|항목구분|샘플데이터|비고|
|---|----|---|--|---|--|
|프로젝트 아이디|projecId|Long|필수|1|식별자|
|태그 아이디|tagId|Long|필수|1|AI|

* Body

|항목명|항목명(영문)|항목크기(최소값)|항목구분|샘플데이터|비고|
|---|----|---|--|---|--|
|태그 이름|name|45(2)|필수|sampleTagName||
```json
{
    "name" : "name"
}

```

#### 요청 에제

```http
PUT /project-api/projects/{projectId}/tags/{tagId}
```

##### Response

* Body

```json
{
	"header": {
		"resultCode": 200,
		"resultMessage": "updated successfully",
		"successful": true
	},
	"result": [
		{
			"id": 1
		}
	],
	"totalCount": 1
}

```
##### HTTP 응답 코드
* 200 OK
* 404 NOT FOUND
	* NotFoundException

### 태그 삭제
#### DELETE /project-api/projects/{projectId}/tags/{tagId}
##### Request
* Parameters

|항목명|항목명(영문)|항목크기(최소값)|항목구분|샘플데이터|비고|
|---|----|---|--|---|--|
|프로젝트 아이디|projecId|Long|필수|1|식별자|
|태그 아이디|tagId|Long|필수|1|AI|

##### 요청 에제

```http
    DELETE /project-api/projects/{projectId}/tags/{tagId}
```

##### Response

* Body

```json
{
	"header": {
		"resultCode": 204,
		"resultMessage": "deleted successfully",
		"successful": true
	},
	"result": [],
	"totalCount": 0
}
```
##### HTTP 응답 코드
* 204 NO CONTENT
* 404 NOT FOUND
	* NotFoundException

### 태그 조회
#### GET /project-api/projects/{projectId}/tags/{tagId}
##### Request
* Parameters

|항목명|항목명(영문)|항목크기(최소값)|항목구분|샘플데이터|비고|
|---|----|---|--|---|--|

##### 요청 에제

```http
```

##### Response

* Body

```json
{
}

```
##### HTTP 응답 코드
* 

#### GET /project-api/projects/{projectId}/tags
##### Request
* Parameters

|항목명|항목명(영문)|항목크기(최소값)|항목구분|샘플데이터|비고|
|---|----|---|--|---|--|
|페이지|page||옵션|0|페이지 번호, 목록 조회시 필수|
|한 페이지 결과수|numOfRows||옵션|10|한 페이지 결과 수, 목록조회시 필수|

##### 요청 에제

```http
```

##### Response

* Body

```json
{
}

```
##### HTTP 응답 코드
* 

## PROJECT-API > Task
### 업무 추가
#### POST /project-api/projects/{projectId}/tasks
##### Request
* Parameters
|항목명|항목명(영문)|항목크기(최소값)|항목구분|샘플데이터|비고|
|---|----|---|--|---|--|
|프로젝트 아이디|projecId|Long|필수|1|식별자|

* Body

|항목명|항목명(영문)|항목크기(최소값)|항목구분|샘플데이터|비고|
|---|----|---|--|---|--|
|업무 이름|taskName|45(2)|필수|sampleTaskName||
|프로젝트 아이디|projectId|Long|필수|1||
|작성자 아이디|writerId|40(5)|필수|sampleId||
|마일스톤 아이디|milestoneId|Long|필수|1|마일스톤|
```json
{
    "taskName" : "taskName",
    "projectId" : "projectId",
    "writerId" : "writerId",
    "milestoneId" : "milestoneId"

}
```

##### 요청 에제

```http
POST /project-api/projects/{projectId}/tasks
```

##### Response

* Body

```json
{
	"header": {
		"resultCode": 201,
		"resultMessage": "created successfully",
		"successful": true
	},
	"result": [
		{
			"id": 1
		}
	],
	"totalCount": 1
}

```
##### HTTP 응답 코드
* 201 CREATED
* 404 NOT FOUND
	* NotFoundException

### 업무 수정
#### PUT /project-api/projects/{projectId}/tasks/{taskId}
##### Request
* Parameters

|항목명|항목명(영문)|항목크기(최소값)|항목구분|샘플데이터|비고|
|---|----|---|--|---|--|
|프로젝트 아이디|projecId|Long|필수|1||
|업무 아이디|taskId|Long|필수|1||

* Body

|항목명|항목명(영문)|항목크기(최소값)|항목구분|샘플데이터|비고|
|---|----|---|--|---|--|
|업무 이름|taskName|45(2)|필수|sampleTaskName||
|프로젝트 아이디|projectId|Long|필수|1||
|작성자 아이디|writerId|40(5)|필수|sampleId||
|마일스톤 아이디|milestoneId|Long|필수|1|마일스톤|

```json
{
    "taskName" : "taskName",
    "projectId" : 1,
    "writerId" : "writerId",
    "milestoneId" : "milestoneId"
}
```

##### 요청 에제

```http
PUT /project-api/projects/{projectId}/tasks/{taskId}
```

##### Response

* Body

```json
{
	"header": {
		"resultCode": 200,
		"resultMessage": "updated successfully",
		"successful": true
	},
	"result": [
		{
			"id": 1
		}
	],
	"totalCount": 1
}

```
##### HTTP 응답 코드
* 200 OK
* 404 NOT FOUND
	* NotFoundException

### 업무 삭제
#### DELETE /project-api/projects/{projectId}/tasks/{taskId}
##### Request
* Parameters

|항목명|항목명(영문)|항목크기(최소값)|항목구분|샘플데이터|비고|
|---|----|---|--|---|--|
|프로젝트 아이디|projecId|Long|필수|1||
|업무 아이디|taskId|Long|필수|1||

##### 요청 에제

```http
DELETE /project-api/projects/{projectId}/tasks/{taskId}
```

##### Response

* Body

```json
{
	"header": {
		"resultCode": 204,
		"resultMessage": "deleted successfully",
		"successful": true
	},
	"result": [],
	"totalCount": 0
}
```
##### HTTP 응답 코드
* 204 NO CONTENT
* 404 NOT FOUND
	* NotFoundException

### 업무 조회
#### GET /project-api/projects/{projectId}/tasks/{taskId}
##### Request
* Parameters

|항목명|항목명(영문)|항목크기(최소값)|항목구분|샘플데이터|비고|
|---|----|---|--|---|--|

##### 요청 에제

```http
```

##### Response

* Body

```json
{
    [추가]
}

```
##### HTTP 응답 코드
* 

#### GET /project-api/projects/{projectId}/tasks
##### Request
|항목명|항목명(영문)|항목크기(최소값)|항목구분|샘플데이터|비고|
|---|----|---|--|---|--|
|페이지|page||옵션|0|페이지 번호, 목록 조회시 필수|
|한 페이지 결과수|numOfRows||옵션|10|한 페이지 결과 수, 목록조회시 필수|

##### 요청 에제

```http
GET /project-api/projects/{projectId}/tasks
```

##### Response

* Body

```json
{
    [추가]
}

```
##### HTTP 응답 코드

## PROJECT-API > Task_Tag
### 업무에 태그 추가
#### POST /project-api/projects/{projectId}/tasks
### 업무에 태그 수정
#### PUT /project-api/projects/{projectId}/tasks/{taskId}
### 업무에 태그 삭제
#### DELETE /project-api/projects/{projectId}/tasks/{taskId}
### 업무에 태그 조회
#### GET /project-api/projects/{projectId}/tasks/{taskId}
#### GET /project-api/projects/{projectId}/tasks

## PROJECT-API > Commnent