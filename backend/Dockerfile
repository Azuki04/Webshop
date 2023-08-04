FROM maven:latest
LABEL authors="syvietdao"

WORKDIR /back_end

COPY . .

RUN mvn clean install

CMD ["mvn", "spring-boot:run"]
