# Specifications


## 요청
### 메시지
* json 포맷을 사용합니다.
* json body를 포함하여 보내야 하는 메시지의 경우, Content-Type 헤더를 명시해야 합니다.
---

## 응답
```json
{
	"header": {
		"resultCode": "{resultCode}",
		"resultMessage": "{resultMessage}",
		"successful": true
	},
	"result": [
		...
		 payload 	
		...
	],
	"totalCount": 1
}
```
### 응답 코드 종류
* `200` OK (조회, 수정 성공)
* `201` CREATED (생성 성공)
* `204` NO CONTENT (삭제 성공)
* `404` NOT FOUND (NotFoundException)
* `409` CONFLICT (AlreadyExistsException)
* `422` UNPROCESSABLE (ValidationFailedException)

---

## `project-api` > `projects`
### 프로젝트 생성
```http request
POST /project-api/projects
```
 
#### Request
* PathVariables
	* 없음
* Parameters
  * 없음
* Body

  | 항목명        | 타입  | 필수여부 | 길이범위   | 비고    |
  |-------------|------|------|--------|-------|
  | accountId   | `String` | `true` | 4 - 45 | 작성자   |
  | projectName | `String` | `true` | 4 - 45 | 프로젝트명 |
* Example
```json
{
	"accountId": "sampleAccount",
	"projectName": "sampleProject"
}
```

#### Response
* Payload

  | 항목명        | 타입  | 필수여부 | 길이범위 | 비고    |
  |-------------|------|------|------|-------|
  | id   | `Long` | `true` | -    | 프로젝트 식별자   |
* Example
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

---

### 프로젝트 수정
```http request
PUT /project-api/projects/{projectId}
```

#### Request

* PathVariables

  | 항목명        | 타입  | 필수여부 | 길이범위 | 비고    |
	|-------------|------|------|------|-------|
  | projectId   | `Long` | `true` | -    | 프로젝트 식별자   |

* Parameters
  * 없음
  

* Body

  | 항목명              | 타입  | 필수여부 | 길이범위   | 비고                     |
  |------------------|------|------|--------|------------------------|
  | projectName      | `String` | `true` | 4 - 45 | 프로젝트명                  |
  | projectStateCode | `String` | `true` | 2      | 01(활성), 02(휴면), 03(종료) |


#### Response
* Payload
    
  | 항목명        | 타입  | 필수여부 | 길이범위 | 비고    |
  |-------------|------|------|------|-------|
  | id   | `Long` | `true` | -    | 프로젝트 식별자   |
* Example

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

---

### 프로젝트 삭제
```http request
DELETE /project-api/projects/{projectId}
```

#### Request
* PathVariables

  | 항목명        | 타입  | 필수여부 | 길이범위 | 비고    |
	|-------------|------|------|------|-------|
  | projectId   | `Long` | `true` | -    | 프로젝트 식별자   |
* Parameters
  * 없음
* Body
  * 없음

##### Response
* Payload
  * 없음
* Example

    ```json
    {
        "header": {
            "resultCode": 204,
            "resultMessage": "deleted successfully",
            "successful": true
        },
        "result": null,
        "totalCount": 0
    }
    ```

---

### 유저가 속한 프로젝트 전체 조회
```http request
GET /project-api/{accountId}/projects
```

#### Request
* PathVariables

  | 항목명        | 타입  | 필수여부 | 길이범위   | 비고    |
  |-------------|------|------|--------|-------|
  | accountId   | `String` | `true` | 4 - 45 | 유저 식별자   |

* Parameters

  | 항목명  | 타입    | 필수여부    | 길이범위 | 비고      |
  |------|-------|---------|------|---------|
  | page | `int` | `false` | -    | 조회할 페이지 |
  | size | `int` | `false` | -    | 페이지당 조회할 항목 수 |
  | sort | `String` | `false` | -    | 정렬 조건 |
* Body
    * 없음

#### Response

* Payload

  | 항목명                  | 타입  | 필수여부 | 길이범위   | 비고                      |
  |----------------------|------|------|--------|-------------------------|
  | accountId            | `String` | `true` | 4 - 45 | 작성자                     |
  | projectId            | `Long` | `true` |  | 프로젝트 식별자                |
  | projectName          | `String` | `true` | 4 - 45 | 프로젝트명                   |
  | projectStateCode     | `String` | `true` | 2 | 01(활성), 02(휴면), 03(종료)  |
  | accountAuthorityCode | `String` | `true` | 2 | 01(관리자), 02(멤버), 03(손님) |


* Example
    ```json
    {
      "header": {
        "resultCode": 200,
        "resultMessage": "fetched successfully",
        "successful": true
      },
      "result": [
        {
          "accountId": "sampleAccount",
          "projectId": 1,
          "projectName": "sampleProject",
          "projectStateCode": "01",
          "accountAuthorityCode": "01"
        },
        {
          "accountId": "sampleAccount",
          "projectId": 2,
          "projectName": "sampleProject2",
          "projectStateCode": "01",
          "accountAuthorityCode": "02"
        }
      ],
        "totalCount": 2
    }
    ```

---

### 프로젝트 조회
```http request
GET /project-api/projects/{projectId}
```

#### Request
* PathVariables

   | 항목명        | 타입  | 필수여부 | 길이범위   | 비고    |
   |-------------|------|------|--------|-------|
   | projectId   | `Long` | `true` | -    | 프로젝트 식별자   |
* Parameters
    * 없음
* Body
    * 없음



##### Response
* Payload

   | 항목명                  | 타입  | 필수여부 | 길이범위   | 비고                      |
    |----------------------|------|------|--------|-------------------------|
    | projectId            | `Long` | `true` |  | 프로젝트 식별자                |
    | projectName          | `String` | `true` | 4 - 45 | 프로젝트명                   |
    | projectStateCode     | `String` | `true` | 2 | 01(활성), 02(휴면), 03(종료)  |
     
* Example

    ```json
    {
      "header": {
        "resultCode": 200,
        "resultMessage": "fetched successfully",
        "successful": true
      },
      "result": [
        {
          "projectId": 1,
          "projectName": "sampleProject",
          "projectStateCode": "01"
        }
      ],
      "totalCount": 1
    }
    
    ```

---

## `project-api` > `projectAccount`
### 프로젝트 사용자 추가
```http request
POST /project-api/projects/{projectId}/accounts
```

#### Request
* PathVariables

   | 항목명        | 타입  | 필수여부 | 길이범위   | 비고    |
   |-------------|------|------|--------|-------|
   | projectId   | `Long` | `true` | -    | 프로젝트 식별자   |
* Parameters
    * 없음

* Body

    | 항목명 | 타입 | 필수여부 | 길이범위 | 비고                           |
    |---|----|---|--|------------------------------|
    | accountId | `String` | `true` | 4 - 45 | 권한을 부여받는 사용자                 |
     | authorityCode | `String` | `true` | 2 | 01(관리자), 02(멤버), 03(손님) |


#### Response
* Payload
  * 없음
* Example
    
    ```json
    {
        "header": {
            "resultCode": 201,
            "resultMessage": "created successfully",
            "successful": true
        },
        "result": null,
        "totalCount": 0
    }
    
    ```

---

### 프로젝트 사용자 수정
```http request
PUT /project-api/projects/{projectId}/accounts/{accountId}
```

#### Request
* PathVariables

  | 항목명        | 타입  | 필수여부 | 길이범위   | 비고    |
     |-------------|------|------|--------|-------|
  | projectId   | `Long` | `true` | -    | 프로젝트 식별자   |
   | accountId   | `String` | `true` | 4 - 45    | 사용자 식별자   |
* Parameters
    * 없음

* Body
  
    | 항목명 | 타입 | 필수여부 | 길이범위 | 비고                              |
    |---|----|---|--|---------------------------------|
    | authorityCode | `String` | `true` | 2 | 수정될 권한, 01(관리자), 02(멤버), 03(손님) |


#### Response

* Payload
  * 없음

* Example
    ```json
    {
        "header": {
            "resultCode": 200,
            "resultMessage": "updated successfully",
            "successful": true
        },
        "result": null,
        "totalCount": 0
    }
    
    ```

---

### 프로젝트 사용자 삭제
```http
DELETE /project-api/projects/{projectId}/accounts/{accountId}
```
#### Request
* PathVariables

  | 항목명        | 타입  | 필수여부 | 길이범위   | 비고    |
     |-------------|------|------|--------|-------|
  | projectId   | `Long` | `true` | -    | 프로젝트 식별자   |
   | accountId   | `String` | `true` | 4 - 45    | 사용자 식별자   |

* Parameters
    * 없음

* Body
    * 없음

#### Response

* Payload
  * 없음

* Example
    ```json
    {
        "header": {
            "resultCode": 204,
            "resultMessage": "deleted successfully",
            "successful": true
        },
        "result": null,
        "totalCount": 0
    }
    ```

---

### 프로젝트 사용자 권한 조회
```http
GET /project-api/projects/{projectId}/accounts/{accountId}
```
#### Request
* PathVariables

  | 항목명        | 타입  | 필수여부 | 길이범위   | 비고    |
     |-------------|------|------|--------|-------|
  | projectId   | `Long` | `true` | -    | 프로젝트 식별자   |
   | accountId   | `String` | `true` | 4 - 45    | 사용자 식별자   |
* Parameters

  | 항목명  | 타입    | 필수여부    | 길이범위 | 비고      |
      |------|-------|---------|------|---------|
  | page | `int` | `false` | -    | 조회할 페이지 |
  | size | `int` | `false` | -    | 페이지당 조회할 항목 수 |
  | sort | `String` | `false` | -    | 정렬 조건 |

* Body
    * 없음

#### Response
* Payload

   | 항목명                  | 타입  | 필수여부 | 길이범위   | 비고                      |
    |----------------------|------|------|--------|-------------------------|
    | authorityCode     | `String` | `true` | 2 | 01(관리자), 02(멤버), 03(손님)  |

* Example
    ```json
    {
        "header": {
            "resultCode": 200,
            "resultMessage": "fetched successfully",
            "successful": true
        },
        "result": {
            "authorityCode": "01"
        },
        "totalCount": 0
    }
    ```
---

### 프로젝트 사용자 목록 조회
```http
GET /project-api/projects/{projectId}/accounts
```
 
#### Request
* PathVariables

   | 항목명        | 타입  | 필수여부 | 길이범위   | 비고    |
      |-------------|------|------|--------|-------|
      | projectId   | `Long` | `true` | -    | 프로젝트 식별자   |
* Parameters

  | 항목명  | 타입    | 필수여부    | 길이범위 | 비고      |
      |------|-------|---------|------|---------|
  | page | `int` | `false` | -    | 조회할 페이지 |
  | size | `int` | `false` | -    | 페이지당 조회할 항목 수 |
  | sort | `String` | `false` | -    | 정렬 조건 |
* Body
    * 없음

#### Response
* Payload

   | 항목명                  | 타입  | 필수여부 | 길이범위   | 비고                      |
    |----------------------|------|------|--------|-------------------------|
    | projectId     | `Long` | `true` | - | 프로젝트 식별자  |
     | projectName   | `String` | `true` | 4 - 45 | 프로젝트 이름  |
     | accountId   | `String` | `true` | 4 - 45 | 사용자 식별자  |
     | authorityCode   | `String` | `true` | 2 | 01(관리자), 02(멤버), 03(손님)  |
     | authority  | `String` | `true` | 4 - 45 | 권한  |

* Example
    ```json
    {
        "header": {
            "resultCode": 200,
            "resultMessage": "fetched successfully",
            "successful": true
        },
        "result": [
            {
                "projectId": 1,
                "projectName": "sampleProject",
                "accountId": "sampleAccount",
                "authorityCode": "01",
                "authority": "관리자"
            },
            {
                "projectId": 1,
                "projectName": "sampleProject",
                "accountId": "sampleAccount",
                "authorityCode": "02",
                "authority": "멤버"
            },
            {
                "projectId": 1,
                "projectName": "sampleProject",
                "accountId": "sampleAccount",
                "authorityCode": "03",
                "authority": "손님"
            }
        ],
        "totalCount": 3
    }
    ```
---

## `project-api` > `milestones`
### 마일스톤 추가
```http
POST /project-api/projects/{projectId}/milestones
```
#### Request
* PathVariables

  | 항목명        | 타입  | 필수여부 | 길이범위   | 비고    |
     |-------------|------|------|--------|-------|
  | projectId   | `Long` | `true` | -    | 프로젝트 식별자   |
* Parameters
    * 없음
* Body

   | 항목명                  | 타입  | 필수여부 | 길이범위   | 비고                      |
    |----------------------|------|------|--------|-------------------------|
    | name     | `String` | `true` | 4 - 45 | 마일스톤 이름  |
     | startDate   | `String` | `true` | 10 | 시작일  |
     | endDate   | `String` | `true` | 10 | 종료일  |


#### Response

* Payload
   
   | 항목명                 | 타입  | 필수여부 | 길이범위   | 비고                      |
    |----------------------|------|------|--------|-------------------------|
    | id     | `Long` | `true` | - | 마일스톤 식별자  |
* Example
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

---

### 마일스톤 수정
```http
PUT /project-api/projects/{projectId}/milestones/{milestoneId}
```
#### Request
* PathVariables
    
     | 항목명        | 타입  | 필수여부 | 길이범위   | 비고    |
      |-------------|------|------|--------|-------|
      | projectId   | `Long` | `true` | -    | 프로젝트 식별자   |
      | milestoneId   | `Long` | `true` | -    | 마일스톤 식별자   |
* Parameters
    * 없음
* Body

   | 항목명                  | 타입       | 필수여부 | 길이범위   | 비고                      |
    |----------------------|----------|------|--------|-------------------------|
    | name     | `String` | `true` | 4 - 45 | 마일스톤 이름  |
     | startDate   | `Date`   | `true` | -      | 시작일  |
     | endDate   | `Date` | `true` | -      | 종료일  |

#### Response

* Payload
   
   | 항목명                 | 타입  | 필수여부 | 길이범위   | 비고                      |
    |----------------------|------|------|--------|-------------------------|
    | id     | `Long` | `true` | - | 마일스톤 식별자  |

* Example
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

---

### 마일스톤 삭제
```http
DELETE /project-api/projects/{projectId}/milestones/{milestoneId}
```

#### Request
* PathVariables
    
     | 항목명        | 타입  | 필수여부 | 길이범위   | 비고    |
      |-------------|------|------|--------|-------|
      | projectId   | `Long` | `true` | -    | 프로젝트 식별자   |
      | milestoneId   | `Long` | `true` | -    | 마일스톤 식별자   |
* Parameters
    * 없음
* Body
    * 없음
#### Response

* Payload
    * 없음
* Example
    ```json
    {
        "header": {
            "resultCode": 204,
            "resultMessage": "deleted successfully",
            "successful": true
        },
        "result": null,
        "totalCount": 0
    }
    ```
  
---

### 프로젝트의 마일스톤 조회
```http
GET /project-api/projects/{projectId}/milestones
```

#### Request
* PathVariables
    
     | 항목명        | 타입  | 필수여부 | 길이범위   | 비고    |
      |-------------|------|------|--------|-------|
      | projectId   | `Long` | `true` | -    | 프로젝트 식별자   |
* Parameters

  | 항목명  | 타입    | 필수여부    | 길이범위 | 비고      |
  |------|-------|---------|------|---------|
  | page | `int` | `false` | -    | 조회할 페이지 |
  | size | `int` | `false` | -    | 페이지당 조회할 항목 수 |
  | sort | `String` | `false` | -    | 정렬 조건 |
* Body
    * 없음

#### Response
* Payload
    
    | 항목명           | 타입       | 필수여부 | 길이범위   | 비고                      |
     |---------------|----------|------|--------|-------------------------|
     | projectId     | `Long`   | `true` | -      | 프로젝트 식별자  |
     | milestoneId   | `Long`   | `true` | -      | 마일스톤 식별자  |
     | milestoneName | `String` | `true` | 4 - 45 | 마일스톤 이름  |
      | startDate     | `Date`   | `true` | -      | 시작일  |
      | endDate       | `Date`   | `true` | -      | 종료일  |
* Example
    ```json
    {
        "header": {
            "resultCode": 200,
            "resultMessage": "fetched successfully",
            "successful": true
        },
        "result": [
            {
                "projectId": 1,
                "milestoneId": 1,
                "milestoneName": "마일스톤1",
                "startDate": "2021-01-01",
                "endDate": "2021-01-31"
            },
            {
                "projectId": 1,
                "milestoneId": 2,
                "milestoneName": "마일스톤2",
                "startDate": "2021-02-01",
                "endDate": "2021-02-28"
            }
        ],
        "totalCount": 2
    }
    ```

---

## `project-api` > `tags`

### 태그 추가
```http
POST /project-api/projects/{projectId}/tags
```
#### Request
* PathVariables

    | 항목명        | 타입  | 필수여부 | 길이범위   | 비고    |
    |-------------|------|------|--------|-------|
    | projectId   | `Long` | `true` | -    | 프로젝트 식별자   |
* Parameters
    * 없음
* Body

   | 항목명                  | 타입       | 필수여부 | 길이범위   | 비고                      |
    |----------------------|----------|------|--------|-------------------------|
    | name     | `String` | `true` | 4 - 45 | 태그 이름  |

#### Response

* Payload
    
    | 항목명                 | 타입  | 필수여부 | 길이범위   | 비고                      |
     |----------------------|------|------|--------|-------------------------|
     | id     | `Long` | `true` | - | 태그 식별자  |
* Example
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
---

### 태그 수정
```http
PUT /project-api/projects/{projectId}/tags/{tagId}
```

#### Request
* PathVariables
    
  | 항목명        | 타입  | 필수여부 | 길이범위   | 비고    |
    |-------------|------|------|--------|-------|
    | projectId   | `Long` | `true` | -    | 프로젝트 식별자   |
    | tagId   | `Long` | `true` | -    | 태그 식별자   |
* Parameters
    * 없음
* Body

   | 항목명                  | 타입       | 필수여부 | 길이범위   | 비고                      |
    |----------------------|----------|------|--------|-------------------------|
    | name     | `String` | `true` | 4 - 45 | 태그 이름  |


##### Response

* Payload
        
    | 항목명                 | 타입  | 필수여부 | 길이범위   | 비고                      |
    |----------------------|------|------|--------|-------------------------|
    | id     | `Long` | `true` | - | 태그 식별자  |
* Example
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
  
---

### 태그 삭제
```http
DELETE /project-api/projects/{projectId}/tags/{tagId}
```

#### Request
* PathVariables
    
  | 항목명        | 타입  | 필수여부 | 길이범위   | 비고    |
    |-------------|------|------|--------|-------|
    | projectId   | `Long` | `true` | -    | 프로젝트 식별자   |
    | tagId   | `Long` | `true` | -    | 태그 식별자   |
* Parameters
    * 없음
* Body
    * 없음

#### Response

* Payload
    * 없음
* Example
    ```json
    {
        "header": {
            "resultCode": 204,
            "resultMessage": "deleted successfully",
            "successful": true
        },
        "result": null,
        "totalCount": 0
    }
    ```
---

### 프로젝트의 전체 태그 조회
```http
GET /project-api/projects/{projectId}/tags
```

#### Request
* PathVariables
    
  | 항목명        | 타입  | 필수여부 | 길이범위   | 비고    |
    |-------------|------|------|--------|-------|
    | projectId   | `Long` | `true` | -    | 프로젝트 식별자   |
* Parameters

  | 항목명  | 타입    | 필수여부    | 길이범위 | 비고      |
  |------|-------|---------|------|---------|
  | page | `int` | `false` | -    | 조회할 페이지 |
  | size | `int` | `false` | -    | 페이지당 조회할 항목 수 |
  | sort | `String` | `false` | -    | 정렬 조건 |

* Body
    * 없음


#### Response

* Payload

   | 항목명                 | 타입  | 필수여부 | 길이범위   | 비고                      |
    |----------------------|------|------|--------|-------------------------|
    | projectId    | `Long` | `true` | - | 프로젝트 식별자  |
    | tagId     | `Long` | `true` | - | 태그 식별자  |
    | tagName     | `String` | `true` | - | 태그 이름  |
* Example
    ```json
    {
        "header": {
            "resultCode": 200,
            "resultMessage": "fetched successfully",
            "successful": true
        },
        "result": [
            {
                "projectId": 1,
                "tagId": 1,
                "tagName": "태그1"
            },
            {
                "projectId": 1,
                "tagId": 2,
                "tagName": "태그2"
            }
        ],
        "totalCount": 2
    }
    ```

---
### 태스크의 태그 조회
```http
GET /project-api/projects/{projectId}/tasks/{taskId}/tags
```

#### Request
* PathVariables
    
  | 항목명        | 타입  | 필수여부 | 길이범위   | 비고    |
    |-------------|------|------|--------|-------|
    | projectId   | `Long` | `true` | -    | 프로젝트 식별자   |
    | taskId   | `Long` | `true` | -    | 태스크 식별자   |
* Parameters

    | 항목명  | 타입    | 필수여부    | 길이범위 | 비고      |
        |------|-------|---------|------|---------|
    | page | `int` | `false` | -    | 조회할 페이지 |
    | size | `int` | `false` | -    | 페이지당 조회할 항목 수 |
    | sort | `String` | `false` | -    | 정렬 조건 |


#### Response

* Payload

   | 항목명                 | 타입  | 필수여부 | 길이범위   | 비고                      |
    |----------------------|------|------|--------|-------------------------|
    | projectId    | `Long` | `true` | - | 프로젝트 식별자  |
    | taskId     | `Long` | `true` | - | 태스크 식별자  |
    | tagId     | `Long` | `true` | - | 태그 식별자  |
    | tagName     | `String` | `true` | - | 태그 이름  |

* Example
    ```json
    {
        "header": {
            "resultCode": 200,
            "resultMessage": "fetched successfully",
            "successful": true
        },
        "result": [
            {
                "projectId": 1,
                "taskId": 1,
                "tagId": 1,
                "tagName": "태그1"
            },
            {
                "projectId": 1,
                "taskId": 1,
                "tagId": 2,
                "tagName": "태그2"
            }
        ],
        "totalCount": 2
    }
    ```
---

## PROJECT-API > Task
### 업무 추가
```http
POST /project-api/projects/{projectId}/tasks
```

#### Request
* PathVariables
    
  | 항목명        | 타입  | 필수여부 | 길이범위   | 비고    |
    |-------------|------|------|--------|-------|
    | projectId   | `Long` | `true` | -    | 프로젝트 식별자   |
* Parameters
    * 없음
* Body

   | 항목명                 | 타입  | 필수여부 | 길이범위   | 비고                      |
    |----------------------|------|------|--------|-------------------------|
    | taskName    | `String` | `true` | - | 업무 이름  |
    | projectId     | `Long` | `true` | - | 프로젝트 식별자  |
    | writerId     | `Long` | `true` | - | 작성자 식별자  |
    | milestoneId     | `Long` | `false` | - | 마일스톤 식별자  |

#### Response
* Payload

   | 항목명                 | 타입  | 필수여부 | 길이범위   | 비고                      |
    |----------------------|------|------|--------|-------------------------|
    | id    | `Long` | `true` | - | 업무 식별자  |
* Example

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
---

### 업무 수정
```http
PUT /project-api/projects/{projectId}/tasks/{taskId}
```
#### Request
* PathVariables
     
  | 항목명        | 타입  | 필수여부 | 길이범위   | 비고    |
     |-------------|------|------|--------|-------|
     | projectId   | `Long` | `true` | -    | 프로젝트 식별자   |
     | taskId   | `Long` | `true` | -    | 업무 식별자   |
* Parameters
    * 없음

* Body

   | 항목명                 | 타입  | 필수여부 | 길이범위   | 비고                      |
    |----------------------|------|------|--------|-------------------------|
    | taskName    | `String` | `true` | - | 업무 이름  |
    | projectId     | `Long` | `true` | - | 프로젝트 식별자  |
    | writerId     | `Long` | `true` | - | 작성자 식별자  |
    | milestoneId     | `Long` | `false` | - | 마일스톤 식별자  |

```json
{
    "taskName" : "taskName",
    "projectId" : 1,
    "writerId" : "writerId",
    "milestoneId" : "milestoneId"
}
```

#### Response
* Payload

   | 항목명                 | 타입  | 필수여부 | 길이범위   | 비고                      |
    |----------------------|------|------|--------|-------------------------|
    | id    | `Long` | `true` | - | 업무 식별자  |
* Example

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

---

### 업무 삭제
```http
DELETE /project-api/projects/{projectId}/tasks/{taskId}
```

#### Request
* PathVariables
     
  | 항목명        | 타입  | 필수여부 | 길이범위   | 비고    |
     |-------------|------|------|--------|-------|
     | projectId   | `Long` | `true` | -    | 프로젝트 식별자   |
     | taskId   | `Long` | `true` | -    | 업무 식별자   |
* Parameters
    * 없음
* Body
    * 없음

#### Response
* Payload
    * 없음
* Example

    ```json
    {
        "header": {
            "resultCode": 204,
            "resultMessage": "deleted successfully",
            "successful": true
        },
        "result": null,
        "totalCount": 0
    }
    ```
---

### 업무 조회
```http
GET /project-api/projects/{projectId}/tasks/{taskId}
```

#### Request
* PathVariables
     
  | 항목명        | 타입  | 필수여부 | 길이범위   | 비고    |
     |-------------|------|------|--------|-------|
     | projectId   | `Long` | `true` | -    | 프로젝트 식별자   |
     | taskId   | `Long` | `true` | -    | 업무 식별자   |
* Parameters

    | 항목명  | 타입    | 필수여부    | 길이범위 | 비고      |
  |------|-------|---------|------|---------|
  | page | `int` | `false` | -    | 조회할 페이지 |
  | size | `int` | `false` | -    | 페이지당 조회할 항목 수 |
  | sort | `String` | `false` | -    | 정렬 조건 |

#### Response

* Payload

   | 항목명         | 타입              | 필수여부 | 길이범위   | 비고       |
    |-------------|-----------------|------|--------|----------|
    | projectId   | `Long`          | `true` | -      | 프로젝트 식별자 |
    | taskId      | `String`        | `true` | -      | 업무 식별자   |
    | taskTitle   | `String`        | `true` | 4 - 45 | 업무 제목    |
     | taskContent | `String`        | `true` | - 2047 | 업무 내용    |
     | writerId    | `String`        | `true` | -      | 작성자 ID   |
     | createdAt   | `LocalDateTime` | `true` | -      | 작성일      |
* Example

    ```json
    {
        "header": {
            "resultCode": 200,
            "resultMessage": "fetched successfully",
            "successful": true
        },
        "result": [
            {
                "projectId": 1,
                "taskId": 1,
                "taskTitle": "taskTitle",
                "taskContent": "taskContent",
                "writerId": "writerId",
                "createdAt": "2021-08-09T15:00:00"
            }
        ],
        "totalCount": 1
    }
    ```
---