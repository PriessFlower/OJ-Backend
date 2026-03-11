# 多环境配置说明

## 配置文件结构

```
oj-api/src/main/resources/
├── application.yml              # 主配置文件
├── application-dev.yml          # 开发环境配置
└── application-prod.yml         # 生产环境配置（如需要）

oj-sys/src/main/resources/
├── application-security.yml     # 旧的安全配置（可删除）
├── application-security-dev.yml # 开发环境安全配置
└── application-security-prod.yml# 生产环境安全配置
```

## 环境切换方式

### 方式一：修改 application.yml（推荐开发时使用）

编辑 `oj-api/src/main/resources/application.yml`：

```yaml
spring:
  profiles:
    active: dev  # 改为 prod 即可切换到生产环境
```

### 方式二：启动参数（推荐生产部署使用）

```bash
# 开发环境
java -jar oj-api.jar --spring.profiles.active=dev

# 生产环境
java -jar oj-api.jar --spring.profiles.active=prod
```

### 方式三：环境变量

```bash
# Linux/Mac
export SPRING_PROFILES_ACTIVE=prod
java -jar oj-api.jar

# Windows
set SPRING_PROFILES_ACTIVE=prod
java -jar oj-api.jar
```

### 方式四：Maven 启动

```bash
# 开发环境
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# 生产环境
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

## 配置差异说明

### 开发环境 (dev)

**特点**：方便调试、快速开发

- **Token 有效期**：1天（86400秒）
- **多地登录**：允许（`is-concurrent: true`）
- **操作日志**：开启（`is-log: true`）
- **Redis**：本地 localhost:6379
- **MinIO**：本地服务 http://localhost:9000
- **Bucket**：oj-bucket-dev

### 生产环境 (prod)

**特点**：安全、稳定

- **Token 有效期**：7天（604800秒）
- **多地登录**：不允许（`is-concurrent: false`）
- **操作日志**：关闭（`is-log: false`）
- **Redis**：支持环境变量配置
- **MinIO**：远程服务，支持环境变量
- **Bucket**：oj-bucket

## 生产环境敏感信息配置

生产环境建议使用环境变量来配置敏感信息，而不是直接写在配置文件中。

### 设置环境变量

```bash
# Linux/Mac (.bashrc 或 .bash_profile)
export REDIS_HOST=your-redis-host
export REDIS_PORT=6379
export REDIS_PASSWORD=your-redis-password
export MINIO_ENDPOINT=http://your-minio-server:9000
export MINIO_ACCESS_KEY=your-access-key
export MINIO_SECRET_KEY=your-secret-key
```

```powershell
# Windows (PowerShell)
$env:REDIS_HOST="your-redis-host"
$env:REDIS_PASSWORD="your-redis-password"
$env:MINIO_ENDPOINT="http://your-minio-server:9000"
$env:MINIO_SECRET_KEY="your-secret-key"
```

### Docker 部署时传入环境变量

```bash
docker run -d \
  -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e REDIS_HOST=redis-server \
  -e REDIS_PASSWORD=your-password \
  -e MINIO_ENDPOINT=http://minio:9000 \
  -e MINIO_SECRET_KEY=your-secret \
  --name oj-serve \
  oj-serve:latest
```

### Docker Compose 配置

```yaml
version: '3.8'
services:
  oj-serve:
    image: oj-serve:latest
    ports:
      - "8080:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=prod
      - REDIS_HOST=redis
      - REDIS_PASSWORD=${REDIS_PASSWORD}
      - MINIO_ENDPOINT=http://minio:9000
      - MINIO_SECRET_KEY=${MINIO_SECRET_KEY}
    depends_on:
      - redis
      - minio
```

## 验证当前环境

启动项目后，查看日志中的输出：

```
The following 1 profile is active: "dev"
```

或访问 Actuator 端点（如果已启用）：
```
GET http://localhost:8080/api/actuator/env
```

## 注意事项

1. **不要提交敏感信息**：生产环境的密码、密钥等不要直接写在配置文件中，使用环境变量
2. **.gitignore 配置**：确保 `application-*-local.yml` 等本地配置文件不被提交
3. **配置优先级**：环境变量 > 启动参数 > 配置文件
4. **旧配置文件**：`application-security.yml` 可以删除，已被拆分为 dev 和 prod 版本

## 添加新环境

如需添加测试环境（test）：

1. 创建 `application-security-test.yml`
2. 在 `application.yml` 中添加���
```yaml
spring:
  profiles:
    group:
      dev:
        - security-dev
      test:
        - security-test
      prod:
        - security-prod
```
3. 启动时指定：`--spring.profiles.active=test`
