install:
	mvn clean install

verify:
	mvn clean verify

run:
	mvn spring-boot:run -e -X

jar:
	java -jar backend/target/backend-0.0.1-SNAPSHOT.jar
