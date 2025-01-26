### JWT based authentication
> https://medium.com/@tericcabrel/implement-jwt-authentication-in-a-spring-boot-3-application-5839e4fd8fac

## Steps
- Run the postgres service `docker run -d -p 5432:5432 <IMAGE_NAME>`
- connect to it using `docker exec -it <CONTAINER_ID> psql -U <USERNAME> -d <DATABASE>`

## Commands
- run spring-boot app: mvn sprint-boot:run
- build: mvn clean install