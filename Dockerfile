FROM gradle:6.0.1-jdk11 as builder
COPY . /project
RUN gradle build

FROM adoptopenjdk/openjdk11:jre-11.0.5_10-alpine as runner
COPY --from=builder /project/build/libs/userRegistrationService-0.0.1-SNAPSHOT.jar /project/userRegistration.jar
WORKDIR /project
EXPOSE 8080
CMD java -jar userRegistration.jar