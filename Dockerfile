# Etapa de compilación
FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /app

# Copiar solo el pom.xml primero
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar el resto del código y compilar
COPY src ./src
RUN mvn package -DskipTests && \
    # Limpieza de caché de Maven
    rm -rf /root/.m2/repository/

# Etapa de ejecución
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Primero ejecutar comandos que necesitan privilegios
RUN apk --no-cache add curl && \
    rm -rf /var/cache/apk/* && \
    # Crear usuario no root para seguridad
    addgroup -S notifyme && adduser -S notifyme -G notifyme

# Copiar solo el JAR necesario
COPY --from=build /app/target/*.jar app.jar

# Establecer permisos
RUN chown -R notifyme:notifyme /app

# Cambiar al usuario no privilegiado DESPUÉS de todas las operaciones que requieren privilegios
USER notifyme:notifyme

# Puerto expuesto por Spring Boot
EXPOSE 8080

# Configuración para usar perfil default (anula SPRING_PROFILES_ACTIVE de docker-compose)
ENV SPRING_PROFILES_ACTIVE=default

# Configuración JVM optimizada para contenedores
ENTRYPOINT ["java", \
            "-XX:+UseContainerSupport", \
            "-XX:MaxRAMPercentage=75.0", \
            "-Djava.security.egd=file:/dev/./urandom", \
            "-jar", "app.jar"]