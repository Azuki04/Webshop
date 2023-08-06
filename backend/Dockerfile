FROM maven:latest
LABEL authors="syvietdao"

WORKDIR /backend

COPY . .

RUN mvn clean install

CMD ["mvn", "spring-boot:run"]
