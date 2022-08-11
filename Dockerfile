FROM openjdk:11

COPY . /app

WORKDIR /app

RUN javac TemperatureApplication.java

CMD ["java", "TemperatureApplication"]