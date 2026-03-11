# OJ 在线判题系统

一个功能完善的在线判题系统（Online Judge），支持多语言代码提交、自动判题、社区交流等功能。

## 📋 项目概览

本项目是一个基于 Spring Boot 3 的在线编程题目判题平台，提供：
- 多语言代码在线执行（Java、C、C++、Python）
- 基于 Docker 的安全代码沙箱
- 题目管理与提交记录
- 社区讨论与交流功能
- 完善的用户权限管理

## 🛠 技术栈

### 后端框架
- **Spring Boot** 3.2.2 - 核心框架
- **MyBatis-Flex** - 持久层框架
- **MySQL** - 关系型数据库
- **Redis** - 缓存中间件
- **MinIO** - 对象存储服务

### 开发工具
- **Swagger3 + Knife4j** - API 文档
- **Lombok** - 简化 Java 代码
- **MapStruct** - 对象映射

### 部署运维
- **Docker** - 容器化部署
- **GitHub Actions** - CI/CD 自动化

## 📁 项目结构

```
oj-serve/
├── oj-api/              # 核心业务模块（主模块）
│   ├── codesandbox/     # 代码沙箱实现
│   ├── controller/      # 控制器层
│   ├── service/         # 业务逻辑层
│   ├── mapper/          # 数据访问层
│   ├── model/           # 数据模型
│   ├── judge/           # 判题逻辑
│   └── execudeCode/     # 代码执行
├── oj-sys/              # 系统管理模块
│   ├── 用户管理
│   ├── 文件管理
│   └── 权限控制
├── oj-common/           # 公共模块
│   ├── config/          # 配置类
│   ├── constant/        # 常量定义
│   ├── exception/       # 异常处理
│   ├── utils/           # 工具类
│   ├── redis/           # Redis 配置
│   └── minio/           # MinIO 配置
├── static/              # 静态资源
├── logs/                # 日志文件
└── pom.xml              # Maven 父项目配置
```

## 🚀 快速开始

### 环境要求

- **JDK**: 17+
- **Maven**: 3.6+
- **MySQL**: 8.0+
- **Redis**: 6.0+
- **Docker**: 20.0+ (用于代码沙箱)

### 安装步骤

1. **克隆项目**
```bash
git clone <repository-url>
cd oj-serve
```

2. **创建数据库**
```sql
CREATE DATABASE `oj-system` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

3. **配置数据库连接**

编辑 `oj-api/src/main/resources/application-dev.yml`：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/oj-system
    username: your_username
    password: your_password
```

4. **配置 Redis**

确保 Redis 服务已启动，或修改配置文件中的 Redis 连接信息。

5. **编译项目**
```bash
mvn clean install
```

6. **运行项目**
```bash
cd oj-api
mvn spring-boot:run
```

7. **访问应用**
- 应用地址: http://localhost:8080/api
- API 文档: http://localhost:8080/api/swagger-ui.html
- Knife4j 文档: http://localhost:8080/api/doc.html

## 📚 核心功能模块

### 1. 题目管理
- **位置**: `OjProblemController.java`
- **功能**:
  - 题目的增删改查
  - 题目列表分页查询
  - 题目详情展示
  - 题目难度分级

### 2. 代码提交与判题 ⭐
- **位置**: `OjProblemSubmitController.java` + `codesandbox/`
- **功能**:
  - 支持多语言代码提交（Java、C、C++、Python）
  - 基于 Docker 的安全沙箱执行
  - 实时判题结果反馈
  - 执行时间和内存限制
  - 测试用例批量验证

**代码沙箱架构**:
```
CodeSandBox (接口)
├── LocalCodeSandbox (本地沙箱)
└── Docker 实现
    ├── JavaDockerCodeSandbox
    ├── CDockerCodeSandbox
    ├── CPPDockerCodeSandbox
    └── PythonDockerCodeSandbox
```

### 3. 社区功能
- **帖子系统**: `OjPostController.java`
  - 发布、编辑、删除帖子
  - 帖子列表与搜索
- **评论系统**: `OjPostCommentController.java`、`OjProblemCommentController.java`
  - 帖子评论
  - 题目讨论
- **互动功能**: `OjThumbPostController.java`、`OjFavourPostController.java`
  - 点赞
  - 收藏

### 4. 用户系统
- **位置**: `oj-sys` 模块
- **功能**:
  - 用户注册登录
  - 权限管理
  - 文件上传下载
  - 个人信息管理

## 🎓 新人学习路径

### 第一阶段：环境搭建（1-2小时）
1. 按照"快速开始"部分搭建开发环境
2. 成功启动项目并访问 Swagger 文档
3. 熟悉项目的目录结构

### 第二阶段：理解架构（2-3小时）
1. **阅读配置文件**
   - `application.yml` - 主配置
   - `application-dev.yml` - 开发环境配置
   - `application-security.yml` - 安全配置

2. **理解模块划分**
   - oj-common: 公共组件和工具
   - oj-sys: 系统管理功能
   - oj-api: 核心业务逻辑

3. **查看 API 文档**
   - 通过 Swagger UI 了解所有接口
   - 测试几个核心接口

### 第三阶段：核心代码阅读（3-4小时）

**推荐阅读顺序**:

1. **入口与配置**
   - `MainApplication.java` - 应用入口
   - `oj-common/config/` - 配置类

2. **公共组件**
   - `oj-common/reuslt/` - 统一返回结果封装
   - `oj-common/exception/` - 全局异常处理
   - `oj-common/utils/` - 工具类

3. **数据模型**
   - `model/entity/` - 实体类
   - `model/dto/` - 数据传输对象
   - `model/vo/` - 视图对象

4. **核心业务** ⭐
   - `codesandbox/` - 代码沙箱实现（重点）
   - `judge/` - 判题逻辑
   - `execudeCode/` - 代码执行流程
   - `service/impl/` - 业务实现

5. **数据访问**
   - `mapper/` - MyBatis Mapper 接口
   - `resources/mapper/*.xml` - SQL 映射文件

### 第四阶段：数据库设计（1小时）

**核心表结构**:
- `oj_problem` - 题目表
- `oj_problem_submit` - 提交记录表
- `oj_post` - 帖子表
- `oj_post_comment` - 帖子评论表
- `problem_comment` - 题目评论表
- `sys_user` - 用户表
- `sys_file` - 文件表

通过阅读 `resources/mapper/*.xml` 文件了解表结构和查询逻辑。

### 第五阶段：实践开发

#### 入门级任务
- [ ] 为题目添加新的难度等级
- [ ] 优化题目列表的查询性能
- [ ] 添加题目标签功能
- [ ] 实现题目搜索功能优化

#### 进阶级任务
- [ ] 添加代码执行的详细日志记录
- [ ] 实现题目的批量导入导出
- [ ] 添加用户做题统计和排行榜
- [ ] 优化代码沙箱的资源限制

#### 高级任务
- [ ] 支持新的编程语言（如 Go、Rust）
- [ ] 实现在线竞赛模式
- [ ] 添加代码相似度检测（防作弊）
- [ ] 实现分布式判题系统

## 🔧 开发指南

### 配置文件说明

**主配置** (`application.yml`):
- 服务端口: 8080
- 上下文路径: /api
- MyBatis-Flex 配置
- Swagger 配置
- 代码沙箱类型配置

**开发环境** (`application-dev.yml`):
- 数据库连接信息
- Redis 配置
- MinIO 配置

**安全配置** (`application-security.yml`):
- Spring Security 配置
- JWT 配置

### 代码沙箱配置

在 `application.yml` 中配置沙箱类型:
```yaml
codesandbox:
  type: local  # local: 本地沙箱, remote: 远程沙箱
```

### 日志配置

- 日志配置文件: `oj-api/src/main/resources/logback-spring.xml`
- 日志输出目录: `logs/`
- SQL 日志会单独输出到日志文件

### Docker 部署

**本地开发环境**:
```bash
# 构建镜像
docker build -t oj-serve:latest .

# 运行容器（开发环境）
docker run -d -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=dev \
  --name oj-serve \
  oj-serve:latest
```

**生产环境**:
```bash
# 运行容器（生产环境，使用环境变量）
docker run -d -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e REDIS_HOST=your-redis-host \
  -e REDIS_PASSWORD=your-redis-password \
  -e MINIO_ENDPOINT=http://your-minio:9000 \
  -e MINIO_SECRET_KEY=your-secret-key \
  -v /var/run/docker.sock:/var/run/docker.sock \
  --name oj-serve \
  oj-serve:latest
```

### CI/CD

项目配置了 GitHub Actions 自动部署:
- **配置文件**: `.github/workflows/deploy.yml`
- **触发条件**: push 到 master 分支
- **部署环境**: 自动使用生产环境配置（`prod`）
- **配置指南**: 查看 [docs/GITHUB_SECRETS_GUIDE.md](docs/GITHUB_SECRETS_GUIDE.md)

**需要配置的 GitHub Secrets**:
- `DOCKER_USERNAME` / `DOCKER_PASSWORD` - Docker Hub 凭证
- `SERVER_HOST` / `SERVER_USER` / `SERVER_PASSWORD` - 服务器信息
- `REDIS_HOST` / `REDIS_PASSWORD` - Redis 配置
- `MINIO_ENDPOINT` / `MINIO_SECRET_KEY` - MinIO 配置

## 📖 API 文档

### Swagger UI
访问地址: http://localhost:8080/api/swagger-ui.html

### Knife4j (推荐)
访问地址: http://localhost:8080/api/doc.html

特点:
- 更美观的界面
- 支持中文
- 接口分组展示
- 在线调试功能

## 🤝 开发规范

### 代码规范
- 使用 Spring Java Format 插件格式化代码
- 遵循阿里巴巴 Java 开发手册
- 使用 Lombok 简化代码

### 提交规范
- feat: 新功能
- fix: 修复 bug
- docs: 文档更新
- style: 代码格式调整
- refactor: 重构
- test: 测试相关
- chore: 构建/工具链相关

### 分支管理
- master: 主分支（生产环境）
- develop: 开发分支
- feature/*: 功能分支
- hotfix/*: 紧急修复分支

## 🐛 常见问题

### 1. 项目启动失败
- 检查 JDK 版本是否为 17+
- 检查 MySQL 和 Redis 是否正常运行
- 检查配置文件中的数据库连接信息

### 2. 代码沙箱执行失败
- 确保 Docker 服务已启动
- 检查 Docker 镜像是否已拉取
- 查看日志文件排查具体错误

### 3. Swagger 无法访问
- 确认项目已成功启动
- 检查端口 8080 是否被占用
- 访问完整路径: http://localhost:8080/api/swagger-ui.html

## 📞 联系方式

如有问题或建议，欢迎提交 Issue 或 Pull Request。

## 📄 许可证

[根据实际情况添加许可证信息]

---

**祝你学习愉快！🎉**
