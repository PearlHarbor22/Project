# 1. Stage — сборка приложения
FROM maven:3.9.2-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml pom.xml
COPY account/pom.xml account/pom.xml
COPY antifraud/pom.xml antifraud/pom.xml
COPY authorization/pom.xml authorization/pom.xml
COPY history/pom.xml history/pom.xml
COPY profile/pom.xml profile/pom.xml
COPY transfer/pom.xml transfer/pom.xml
COPY public-info/pom.xml public-info/pom.xml
COPY checkstyle.xml checkstyle.xml

RUN mvn -q -N install

RUN mvn -q -f public-info/pom.xml -DskipTests dependency:go-offline

COPY public-info/src public-info/src

RUN mvn -q -f public-info/pom.xml -DskipTests package

# 2. Stage — запуск приложения
FROM eclipse-temurin:17-jre-alpine
COPY --from=build /app/public-info/target/*.jar /app/app.jar
EXPOSE 8091
ENTRYPOINT ["java", "-jar", "/app/app.jar"]