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

### 用戶註冊
- **URL**: `/api/auth/register`
- **方法**: `POST`
- **請求體**:
  ```json
  {
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

## 部門相關 API

### 獲取所有部門及其課程信息
- **URL**: `/api/departments`
- **方法**: `GET`
- **響應**:
  ```json
  {
    "departments": [
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
- **響應**:
  ```json
  {
    "name": "string",        // 收藏夾名稱
    "description": "string", // 收藏夾描述
    "resources": [
      {
        "id": "string",   // 資源ID
        "name": "string", // 資源名稱
        "type": "string"  // 資源類型
      }
    ]
  }
  ```

### 更新收藏夾信息
- **URL**: `/api/user/favorite`
- **方法**: `PUT`
- **請求體**:
  ```json
  {
    "name": "string",        // 收藏夾名稱
    "description": "string"  // 收藏夾描述
  }
  ```
- **響應**:
  ```json
  {
    "message": "string",     // 更新成功提示信息
    "favorite": {
      "name": "string",      // 更新後的收藏夾名稱
      "description": "string" // 更新後的收藏夾描述
    }
  }
  ```

### 添加資源到收藏夾
- **URL**: `/api/favorite/resources`
- **方法**: `POST`
- **請求體**:
  ```json
  {
    "resourceId": "string"  // 要添加的資源ID
  }
  ```
- **響應**:
  ```json
  {
    "message": "string"  // 添加成功提示信息
  }
  ```

### 從收藏夾移除資源
- **URL**: `/api/favorite/resources/{resourceId}`
- **方法**: `DELETE`
- **參數**:
  - `resourceId`: 資源ID（路徑參數）
- **響應**:
  ```json
  {
    "message": "string"  // 移除成功提示信息
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