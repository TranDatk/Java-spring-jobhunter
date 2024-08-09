# Stage 1: Build the application
FROM gradle:8.7-jdk17 AS build
COPY --chown=gradle:gradle . /datk/jobhunter
WORKDIR /datk/jobhunter

#skip task: test
RUN gradle clean build -x test --no-daemon

# Stage 2: Run the application
FROM openjdk:17-slim
EXPOSE 8080
COPY --from=build /datk/jobhunter/build/libs/*.jar /datk/spring-boot-job-hunter.jar
ENTRYPOINT ["java", "-jar", "/datk/spring-boot-job-hunter.jar"]
