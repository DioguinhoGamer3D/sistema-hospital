FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml ./
RUN mvn -B dependency:go-offline
COPY src ./src
RUN mvn -B clean package -DskipTests \
    && cp target/*-jar-with-dependencies.jar /app/app.jar

FROM eclipse-temurin:21-jre
RUN useradd --system --shell /usr/sbin/nologin app
WORKDIR /app
COPY --from=build /app/app.jar ./app.jar
RUN mkdir -p /app/data && chown -R app:app /app
USER app
EXPOSE 7070
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75", "-jar", "/app/app.jar"]