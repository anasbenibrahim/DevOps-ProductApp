# ============================================================
# ÉTAPE 1 : BUILD → compiler le code et produire le .jar
# ============================================================

FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app


COPY . .


RUN --mount=type=cache,target=/root/.m2 \
    mvn -pl user-service -am clean package -DskipTests

# ============================================================
# ÉTAPE 2 : RUNTIME → image finale, légère, qui exécute le .jar
# ============================================================

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

RUN addgroup -S spring && adduser -S spring -G spring

COPY --from=build /app/user-service/target/*.jar app.jar

USER spring

EXPOSE 5003

ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]