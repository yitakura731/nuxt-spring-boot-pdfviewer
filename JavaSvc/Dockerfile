FROM gradle:jdk14 as compile

COPY . /home/java

WORKDIR /home/java

USER root

RUN chown -R gradle /home/java

USER gradle

RUN gradle clean build

FROM openjdk:14-alpine

WORKDIR /home/app/java

COPY --from=compile "/home/java/build/libs/JavaSvc-0.0.1-SNAPSHOT.jar" .

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "/home/app/java/JavaSvc-0.0.1-SNAPSHOT.jar"]