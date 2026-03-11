# GitHub Secrets 配置指南

在 GitHub Actions 中使用生产环境配置需要设置以下 Secrets。

## 如何添加 Secrets

1. 进入你的 GitHub 仓库
2. 点击 **Settings** → **Secrets and variables** → **Actions**
3. 点击 **New repository secret**
4. 添加以下 Secrets

## 必需的 Secrets

### Docker 相关

| Secret 名称 | 说明 | 示例值 |
|------------|------|--------|
| `DOCKER_USERNAME` | Docker Hub 用户名 | `your-dockerhub-username` |
| `DOCKER_PASSWORD` | Docker Hub 密码或 Access Token | `dckr_pat_xxxxx` |

### 服务器相关

| Secret 名称 | 说明 | 示例值               |
|------------|------|-------------------|
| `SERVER_HOST` | 服务器 IP 地址 | `xxx.xx.xx.xx`    |
| `SERVER_USER` | SSH 登录用户名 | `root` 或 `ubuntu` |
| `SERVER_PASSWORD` | SSH 登录密码 | `your-password`   |

### 应用配置相关（生产环境）

| Secret 名称 | 说明 | 示例值 |
|------------|------|--------|
| `REDIS_HOST` | Redis 服务器地址 | `redis.example.com` 或 `localhost` |
| `REDIS_PASSWORD` | Redis 密码 | `your-redis-password` |
| `MINIO_ENDPOINT` | MinIO 服务地址 | `http://minio.example.com:9000` |
| `MINIO_SECRET_KEY` | MinIO 密钥 | `your-minio-secret-key` |

## 可选的 Secrets

如果需要更细粒度的控制，可以添加：

| Secret 名称 | 说明 | 默认值 |
|------------|------|--------|
| `REDIS_PORT` | Redis 端口 | `6379` |
| `MINIO_ACCESS_KEY` | MinIO 访问密钥 | `minio` |
| `MINIO_BUCKET` | MinIO 存储桶名称 | `oj-bucket` |

## 配置示例

### 1. Docker Hub Token 获取

1. 登录 [Docker Hub](https://hub.docker.com/)
2. 点击右上角头像 → **Account Settings**
3. 选择 **Security** → **New Access Token**
4. 创建 Token 并复制（只显示一次）
5. 将 Token 作为 `DOCKER_PASSWORD` 添加到 GitHub Secrets

### 2. 服务器 SSH 配置

**方式一：使用密码（简单但不够安全）**
```
SERVER_HOST: xx.xxx.xx.xx
SERVER_USER: root
SERVER_PASSWORD: your-password
```

**方式二：使用 SSH 密钥（推荐）**

如果要使用 SSH 密钥，需要修改 GitHub Actions 配置：

```yaml
- name: Deploy to Server
  uses: appleboy/ssh-action@v1.0.0
  with:
    host: ${{ secrets.SERVER_HOST }}
    username: ${{ secrets.SERVER_USER }}
    key: ${{ secrets.SSH_PRIVATE_KEY }}  # 使用私钥而不是密码
    script: |
      # 部署脚本...
```

然后添加 Secret：
- `SSH_PRIVATE_KEY`: 你的 SSH 私钥内容（`~/.ssh/id_rsa` 的内容）

### 3. Redis 配置

如果 Redis 在同一台服务器上：
```
REDIS_HOST: localhost
REDIS_PASSWORD: your-redis-password
```

如果 Redis 是独立服务器：
```
REDIS_HOST: redis.example.com
REDIS_PASSWORD: your-redis-password
```

### 4. MinIO 配置

```
MINIO_ENDPOINT: http://xxx.xx.xx.xx:9000
MINIO_SECRET_KEY: your-minio-password
```

## 验证配置

### 1. 本地测试

在本地使用相同的环境变量测试：

```bash
export SPRING_PROFILES_ACTIVE=prod
export REDIS_HOST=localhost
export REDIS_PASSWORD=your-password
export MINIO_ENDPOINT=http://localhost:9000
export MINIO_SECRET_KEY=your-secret

java -jar oj-api/target/*.jar
```

### 2. 查看 GitHub Actions 日志

推送代码后，在 GitHub 仓库的 **Actions** 标签页查看工作流执行情况。

注意：Secrets 的值不会在日志中显示，会被 `***` 替换。

### 3. 服务器验证

SSH 登录服务器后，查看容器环境变量：

```bash
# 查看容器是否运行
docker ps | grep oj-backend

# 查看容器环境变量（敏感信息会显示）
docker inspect oj-backend-container | grep -A 20 "Env"

# 查看容器日志
docker logs oj-backend-container

# 应该看到类似输出：
# The following 1 profile is active: "prod"
```

## 安全建议

1. **不要在代码中硬编码敏感信息**
   - ❌ 不要：直接写在 `application-*.yml` 中
   - ✅ 推荐：使用 GitHub Secrets + 环境变量

2. **定期轮换密钥**
   - 定期更新 Docker Hub Token
   - 定期更新数据库和 Redis 密码

3. **最小权限原则**
   - Docker Hub Token 只给必要的权限
   - 服务器用户使用非 root 用户（如果可能）

4. **备份 Secrets**
   - 在安全的地方（如密码管理器）备份所有 Secrets
   - GitHub Secrets 一旦设置就无法查看原值

## 故障排查

### 问题 1：容器启动失败

```bash
# 查看容器日志
docker logs oj-backend-container

# 常见原因：
# - Redis 连接失败：检查 REDIS_HOST 和 REDIS_PASSWORD
# - MinIO 连接失败：检查 MINIO_ENDPOINT 和 MINIO_SECRET_KEY
# - 数据库连接失败：检查数据库是否可访问
```

### 问题 2：GitHub Actions 部署失败

1. 检查 Secrets 是否都已配置
2. 查看 Actions 日志中的错误信息
3. 确认服务器 SSH 连接正常
4. 确认 Docker Hub 登录成功

### 问题 3：应用使用了错误的环境

```bash
# 进入容器检查
docker exec -it oj-backend-container sh

# 查看环境变量
env | grep SPRING_PROFILES_ACTIVE

# 应该输出：SPRING_PROFILES_ACTIVE=prod
```

## 更新 Secrets

如果需要更新某个 Secret：

1. 进入 **Settings** → **Secrets and variables** → **Actions**
2. 找到要更新的 Secret
3. 点击 **Update**
4. 输入新值并保存
5. 重新触发 GitHub Actions（推送新代码或手动触发）

## 手动触发部署

如果只是更新了 Secrets，不想推送代码，可以在 GitHub Actions 中添加手动触发：

```yaml
on:
  push:
    branches:
      - master
  workflow_dispatch:  # 添加这一行，允许手动触发
```

然后在 GitHub 仓库的 **Actions** 标签页，点击工作流，再点击 **Run workflow** 按钮。
