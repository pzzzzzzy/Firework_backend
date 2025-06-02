# API 文檔

## 目錄
1. [認證相關 API](#認證相關-api)
2. [部門相關 API](#部門相關-api)
3. [收藏夾相關 API](#收藏夾相關-api)
4. [文件相關 API](#文件相關-api)

## 認證相關 API

### 用戶登入
- **URL**: `/api/auth/login`
- **方法**: `POST`
- **請求體**:
  ```json
  {
<<<<<<< HEAD
    "username": "string",  // 用戶名
    "password": "string"   // 密碼
  }
  ```
- **響應**:
  ```json
  {
    "token": "string",     // JWT認證令牌
    "user": {
      "id": "string",      // 用戶ID
      "username": "string", // 用戶名
      "role": "string"     // 用戶角色
    }
  }
  ```
=======
    "phone": "string",    // 手機號碼
    "password": "string"  // 密碼
  }
  ```
- **密碼要求**：
  - 長度：6-16位
  - 必須包含字母和數字
  - 格式：`/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,16}$/`
- **手機號要求**：
  - 必須是有效的中國手機號
  - 格式：`/^1[3-9]\d{9}$/`
- **成功響應**:
  ```json
  {
    "code": 200,          // 狀態碼
    "message": "string",  // 提示信息
    "data": {
      "token": "string",  // JWT認證令牌
      "userInfo": {
        "phone": "string",     // 手機號
        "username": "string",  // 用戶名
        "role": "string",      // 用戶角色
        "avatar": "string"     // 頭像URL
      }
    }
  }
  ```
- **錯誤響應**:
  ```json
  {
    "code": 400,          // 錯誤狀態碼
    "message": "string",  // 錯誤信息
    "data": null
  }
  ```
- **可能的錯誤情況**：
  - 手機號和密碼不能為空
  - 手機號或密碼錯誤
- **認證流程**：
  - 登入成功後，前端會將token保存在localStorage中
  - 後續所有需要認證的請求都會在請求頭中帶上token：
    ```
    Authorization: Bearer <token>
    ```
  - 如果token過期或無效，會自動跳轉到登入頁面
- **安全要求**：
  - 密碼傳輸需要加密
  - token需要設置過期時間
  - 需要防止暴力破解（可以限制登入嘗試次數）
  
>>>>>>> 5cd355a547469223b773e2e5d6c25f23906527c0

### 用戶註冊
- **URL**: `/api/auth/register`
- **方法**: `POST`
- **請求體**:
  ```json
  {
<<<<<<< HEAD
    "username": "string",  // 用戶名
    "password": "string",  // 密碼
    "email": "string"      // 電子郵件
  }
  ```
- **響應**:
  ```json
  {
    "message": "string",   // 註冊成功提示信息
    "user": {
      "id": "string",      // 用戶ID
      "username": "string" // 用戶名
    }
  }
  ```
=======
  "phone": "string",      // 手機號碼（必填）
  "username": "string",   // 用戶名（必填）
  "password": "string"    // 密碼（必填，6-16位字符/數字）
  }
  ```
- **響應**:
成功响应
  ```json
  {
    {
    "code": 200,
    "message": "註冊成功",
    }
  }
  ```
响应失败
  ```json
  {
    {
  "code": 400,
  "message": "錯誤信息",
  "data": null
    }
  }
  ```


>>>>>>> 5cd355a547469223b773e2e5d6c25f23906527c0

## 部門相關 API

### 獲取所有部門及其課程信息
- **URL**: `/api/departments`
- **方法**: `GET`
- **響應**:
  ```json
  {
<<<<<<< HEAD
    "departments": [
      {
        "id": "string",    // 部門ID
        "name": "string",  // 部門名稱
        "courses": [
          {
            "id": "string",          // 課程ID
            "name": "string",        // 課程名稱
            "description": "string"  // 課程描述
=======
    "code": 200,           // 狀態碼
    "message": "獲取成功",  // 響應消息
    "data": [
      {
        "id": "number",    // 部門ID
        "name": "string",  // 部門名稱
        "courses": [
          {
            "id": "number",          // 課程ID
            "title": "string",       // 課程名稱
            "description": "string", // 課程描述
            "date": "string"         // 課程日期
>>>>>>> 5cd355a547469223b773e2e5d6c25f23906527c0
          }
        ]
      }
    ]
  }
  ```

### 獲取單個部門的課程信息
- **URL**: `/api/departments/{departmentId}/courses`
- **方法**: `GET`
- **參數**:
  - `departmentId`: 部門ID（路徑參數）
- **響應**:
  ```json
  {
    "id": "string",    // 部門ID
    "name": "string",  // 部門名稱
    "courses": [
      {
        "id": "string",          // 課程ID
        "name": "string",        // 課程名稱
        "description": "string"  // 課程描述
      }
    ]
  }
  ```

## 收藏夾相關 API


### 獲取用戶收藏夾信息
- **URL**: `/api/user/favorite`
- **方法**: `GET`
- **需要認證**: 是
- **權限要求**: 已登入用戶
- **響應**:
  ```json
  {
    "code": 200,           // 狀態碼
    "message": "success",  // 響應消息
    "data": {
      "id": "string",      // 收藏夾ID
      "userId": "string",  // 所屬用戶ID
      "name": "string",    // 收藏夾名稱
      "description": "string", // 收藏夾描述
      "isPublic": boolean, // 是否公開
      "resourceCount": number, // 資源數量
      "createdAt": "string",   // 創建時間
      "updatedAt": "string",   // 更新時間
      "resources": [           // 收藏的資源列表
        {
          "id": "string",      // 資源ID
          "title": "string",   // 資源名稱
          "fileType": "string", // 文件類型
          "size": number,      // 文件大小（字節）
          "uploadTime": "string" // 上傳時間
        }
      ]
    }
  }
  ```


### 更新收藏夾信息
- **URL**: `/api/user/favorite`
- **方法**: `PUT`
- **請求體**:
  ```json
  {
  "name": "string",        // 收藏夾名稱
  "description": "string", // 收藏夾描述
  "isPublic": boolean      // 是否公開（可選）
}

- **響應**:
  ```json
  {
  "code": 200,           // 狀態碼
  "message": "更新成功",  // 響應消息
  "data": {
    "id": "string",      // 收藏夾ID
    "userId": "string",  // 所屬用戶ID
    "name": "string",    // 更新後的收藏夾名稱
    "description": "string", // 更新後的收藏夾描述
    "isPublic": boolean, // 更新後的公開狀態
    "resourceCount": number, // 資源數量
    "createdAt": "string",   // 創建時間
    "updatedAt": "string"    // 更新時間
  }
}
  ```
```
### 添加資源到收藏夾
- **URL**: `/api/favorite/resources`
- **方法**: `POST`
- **請求體**:
  ```json
  {
  "resourceId": "string"  // 要添加的資源ID
}
  
- **響應**:
  ```json
  {
  "code": 200,           // 狀態碼
  "message": "添加成功",  // 響應消息
  "data": {
    "id": "string",      // 收藏記錄ID
    "favoriteId": "string", // 收藏夾ID
    "resourceId": "string", // 資源ID
    "resourceName": "string", // 資源名稱
    "resourceType": "string", // 資源類型
    "createdAt": "string"    // 收藏時間
  }
}

### 從收藏夾移除資源
- **URL**: `/api/favorite/resources/{resourceId}`
- **方法**: `DELETE`
- **參數**:
  - `resourceId`: 資源ID（路徑參數）
- **響應**:
  ```json
  {
  "code": 200,           // 狀態碼
  "message": "移除成功",  // 響應消息
  "data": {
    "id": "string",      // 被移除的收藏記錄ID
    "resourceId": "string", // 被移除的資源ID
    "removedAt": "string"   // 移除時間
  }
}
  ```


## 文件相關 API

### 獲取課程文件列表
- **URL**: `/api/files/courses/{courseId}/files`
- **方法**: `GET`
- **參數**:
  - `courseId`: 課程ID（路徑參數）
- **響應**:
  ```json
  {
    "files": [
      {
        "id": "string",        // 文件ID
        "name": "string",      // 文件名稱
        "size": "number",      // 文件大小（字節）
        "type": "string",      // 文件類型
        "uploadTime": "string" // 上傳時間（ISO 8601格式）
      }
    ]
  }
  ```

### 搜索文件
- **URL**: `/api/files/search`
- **方法**: `GET`
- **參數**:
  - `keyword`: 搜索關鍵字（查詢參數）
- **響應**:
  ```json
  {
    "files": [
      {
        "id": "string",        // 文件ID
        "name": "string",      // 文件名稱
        "size": "number",      // 文件大小（字節）
        "type": "string",      // 文件類型
        "uploadTime": "string" // 上傳時間（ISO 8601格式）
      }
    ]
  }
  ```

### 上傳分片
- **URL**: `/api/files/chunk`
- **方法**: `POST`
- **請求頭**:
  - `Content-Type: multipart/form-data`
- **請求體**:
  - `file`: 文件分片（二進制數據）
  - `chunkNumber`: 分片序號（從1開始）
  - `totalChunks`: 總分片數
  - `identifier`: 文件唯一標識（用於合併時識別）
- **響應**:
  ```json
  {
    "message": "string"  // 上傳成功提示信息
  }
  ```

### 合併分片
- **URL**: `/api/files/merge`
- **方法**: `POST`
- **請求體**:
  ```json
  {
    "identifier": "string",    // 文件唯一標識
    "filename": "string",      // 文件名稱
    "totalChunks": "number"    // 總分片數
  }
  ```
- **響應**:
  ```json
  {
    "message": "string",  // 合併成功提示信息
    "fileId": "string"    // 合併後的文件ID
  }
  ```

## 錯誤處理

所有API在發生錯誤時都會返回以下格式的響應：

```json
{
  "error": {
    "code": "string",    // 錯誤代碼
    "message": "string"  // 錯誤描述
  }
}
```

常見的錯誤代碼：
- 400: 請求參數錯誤
- 401: 未授權（未登入或token過期）
- 403: 禁止訪問（權限不足）
- 404: 資源不存在
- 500: 服務器內部錯誤

## 認證

除了登入和註冊接口外，所有其他接口都需要在請求頭中包含認證令牌：

```
Authorization: Bearer <token>
```

## 注意事項

1. 所有時間相關的字段都使用ISO 8601格式（例如：`2024-03-14T12:00:00Z`）
2. 文件上傳支持斷點續傳，建議分片大小為2MB
3. 所有接口都支持跨域請求（CORS）
4. 響應數據格式統一為JSON
5. 所有字符串類型的ID都使用UUID格式
6. 文件上傳時需要進行文件類型驗證，只允許特定類型的文件上傳
7. 所有接口的響應時間應該控制在3秒以內
8. 分頁接口的默認頁大小為20條記錄 