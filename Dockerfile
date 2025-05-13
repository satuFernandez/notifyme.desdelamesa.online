FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn package -DskipTests && \
    rm -rf /root/.m2/repository/

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

#RUN apk --no-cache add curl && \
#    rm -rf /var/cache/apk/* && \
#    addgroup -S notifyme && adduser -S notifyme -G notifyme

RUN addgroup -S notifyme && adduser -S notifyme -G notifyme

COPY --from=build /app/target/*.jar app.jar

RUN chown -R notifyme:notifyme /app

USER notifyme:notifyme

EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=default

ENTRYPOINT ["java", \
            "-XX:+UseContainerSupport", \
            "-XX:MaxRAMPercentage=75.0", \
            "-Djava.security.egd=file:/dev/./urandom", \
            "-jar", "app.jar"]