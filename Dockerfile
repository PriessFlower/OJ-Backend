FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY oj-api/target/*.jar app.jar

# 安装 Docker CLI，用于在容器内调用宿主机 Docker
RUN apk add --no-cache docker-cli

# 暴露端口
EXPOSE 8080

# 设置默认环境为生产环境（可通过 docker run -e 覆盖）
ENV SPRING_PROFILES_ACTIVE=prod

# 启动应用，使用环境变量配置
ENTRYPOINT ["java", "-jar", "app.jar"]