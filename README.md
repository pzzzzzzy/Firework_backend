# Firework

## 项目简介
Firework 是一个基于 Java 的后端项目，旨在为用户提供丰富的 API 服务，涵盖如用户管理、文件上传、部门管理、资源收藏等功能。该项目使用 Spring Boot 框架构建，结合多种技术栈，具备良好的可扩展性与易用性。该后端服务被设计为为前端应用提供数据支持，能够高效处理请求和管理数据库中的数据。
前端项目链接：https://github.com/Rikkarina/vue-firework/blob/main/README.md#firework---%E8%AF%BE%E7%A8%8B%E8%B5%84%E6%BA%90%E5%85%B1%E4%BA%AB%E5%B9%B3%E5%8F%B0

## 目录结构
```bash
Firework_backend-main/
├── .gitignore                 # Git 忽略文件
├── .idea/                     # IntelliJ IDEA 项目的配置文件
├── docs/                      # 项目的文档，包含 API 文档和规则文档
│   ├── api_documentation.md   # API 接口文档
│   └── general_rules.md       # 项目通用规则
├── pom.xml                    # Maven 配置文件，包含项目依赖
├── README.md                  # 项目概述文件
└── src/                       # 源代码
├── main/                  # 主代码文件
│   ├── java/              # Java 源代码
│   │   └── org/
│   │       └── example/
│   │           ├── App.java                  # 主程序入口
│   │           ├── config/                  # 配置文件
│   │           │   ├── GlobalExceptionHandler.java  # 全局异常处理
│   │           │   └── SecurityConfig.java         # 安全配置
│   │           ├── controller/              # 控制器，处理 HTTP 请求
│   │           │   ├── AuthController.java   # 登录认证控制器
│   │           │   ├── DepartmentController.java  # 部门管理控制器
│   │           │   ├── FavoriteController.java    # 收藏管理控制器
│   │           │   ├── FileController.java        # 文件管理控制器
│   │           │   └── FileUploadController.java  # 文件上传控制器
│   │           ├── entity/                  # 实体类，映射数据库表
│   │           │   ├── Course.java          # 课程实体类
│   │           │   ├── Department.java      # 部门实体类
│   │           │   ├── Favorite.java        # 收藏实体类
│   │           │   ├── FavoriteResource.java # 收藏资源实体类
│   │           │   ├── StudyResource.java   # 学习资源实体类
│   │           │   ├── TestEntity.java      # 测试实体类
│   │           │   ├── User.java            # 用户实体类
│   │           │   └── Version.java         # 版本实体类
│   │           ├── model/                   # 请求/响应模型
│   │           │   ├── DepartmentResponse.java  # 部门响应模型
│   │           │   ├── FavoriteResourceRequest.java  # 收藏资源请求模型
│   │           │   ├── FavoriteResponse.java     # 收藏响应模型
│   │           │   ├── FavoriteUpdateRequest.java  # 收藏更新请求模型
│   │           │   ├── FileListResponse.java       # 文件列表响应模型
│   │           │   ├── LoginRequest.java           # 登录请求模型
│   │           │   ├── LoginResponse.java          # 登录响应模型
│   │           │   ├── RegisterRequest.java        # 注册请求模型
│   │           │   └── RegisterResponse.java       # 注册响应模型
│   │           ├── repository/               # 数据库访问层
│   │           │   ├── CourseRepository.java  # 课程数据访问接口
│   │           │   ├── DepartmentRepository.java  # 部门数据访问接口
│   │           │   ├── FavoriteRepository.java    # 收藏数据访问接口
│   │           │   ├── FavoriteResourceRepository.java  # 收藏资源数据访问接口
│   │           │   ├── StudyResourceRepository.java  # 学习资源数据访问接口
│   │           │   ├── TestRepository.java    # 测试数据访问接口
│   │           │   ├── UserRepository.java   # 用户数据访问接口
│   │           │   └── VersionRepository.java  # 版本数据访问接口
│   │           └── service/                 # 服务层，包含业务逻辑
│   │               ├── FileService.java      # 文件处理服务接口
│   │               ├── FileUploadService.java  # 文件上传服务接口
│   │               └── impl/                 # 服务层实现
│   │                   └── FileServiceImpl.java  # 文件服务实现类
│   └── resources/                  # 配置文件和 SQL 脚本
│       ├── application.properties  # Spring Boot 配置文件
│       ├── application.yml        # 配置文件（YAML 格式）
│       ├── data.sql               # 初始化数据库的数据
│       └── schema.sql             # 数据库表结构
└── test/                           # 测试代码
├── java/                       # Java 测试源代码
│   └── org/
│       └── example/
│           ├── AppTest.java                  # 主程序测试
│           ├── controller/                  # 控制器单元测试
│           │   ├── AuthControllerTest.java  # 登录认证控制器测试
│           │   ├── DepartmentControllerTest.java  # 部门控制器测试
│           │   ├── FavoriteControllerTest.java    # 收藏控制器测试
│           │   ├── FileControllerTest.java        # 文件控制器测试
│           │   └── FileUploadControllerTest.java  # 文件上传控制器测试
│           ├── DatabaseConnectionTest.java    # 数据库连接测试
│           ├── dto/                          # 数据传输对象测试
│           │   ├── LoginRequest.java           # 登录请求测试
│           │   └── LoginResponse.java          # 登录响应测试
│           ├── entity/                       # 实体类测试
│           │   ├── CourseTest.java            # 课程实体类测试
│           │   ├── DepartmentTest.java        # 部门实体类测试
│           │   ├── User.java                  # 用户实体类测试
│           │   └── UserTest.java              # 用户实体类测试
│           └── model/                        # 请求模型测试
│               ├── FavoriteResourceRequestTest.java  # 收藏资源请求模型测试
│               ├── FavoriteResponseTest.java     # 收藏响应模型测试
│               ├── FavoriteUpdateRequestTest.java  # 收藏更新请求模型测试
│               ├── LoginRequestTest.java           # 登录请求模型测试
│               └── RegisterRequestTest.java        # 注册请求模型测试
└── resources/                  # 测试数据文件
├── data.sql               # 测试用数据库数据
└── schema.sql             # 测试用数据库表结构
```

## 技术栈
- **Spring Boot**：用于快速开发 RESTful Web 服务。
- **Spring Security**：提供强大的认证与授权功能。
- **JPA (Java Persistence API)**：用于数据库交互，简化数据库操作。
- **Maven**：项目管理与构建工具。
- **JUnit**：单元测试框架，确保项目的稳定性。
- **H2**：内存型数据库，适用于开发和测试环境。

## 功能简介
- **用户管理**：支持用户的注册、登录、角色权限管理。
- **文件上传与管理**：支持文件的上传、存储及下载。
- **部门管理**：管理组织内的部门信息。
- **资源收藏**：用户可以收藏学习资源，便于后续访问。
- **全局异常处理**：统一处理系统异常，返回统一格式的错误信息。
- **安全配置**：通过 Spring Security 保护系统的安全性，防止未授权的访问。

## 安装与使用
1. 克隆项目到本地：
    ```bash
    git clone https://github.com/your-repo/Firework_backend.git
    ```
2. 进入项目目录并使用 Maven 构建：
    ```bash
    cd Firework_backend
    mvn clean install
    ```
3. 配置数据库连接信息，修改 `application.properties` 或 `application.yml`。
4. 运行项目：
    ```bash
    mvn spring-boot:run
    ```
    项目启动后，默认在 `http://localhost:8080` 上运行。

## API 文档
API 文档请参见 `docs/api_documentation.md`。

## 贡献指南
- Fork 项目。
- 提交 Pull Request，修复 Bug 或新增功能。
- 请确保代码符合项目风格，提供相应的单元测试。
