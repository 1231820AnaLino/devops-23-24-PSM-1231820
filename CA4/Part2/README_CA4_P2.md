# CA4, Part 2 - Containers with Docker

## Aims
In this part of the assignment we intend to use Docker to setup a containerized environment to execute the gradle 
version of the spring basic tutorial application performed in CA2 part2.

## Instructions

### Create a Dockerfile for the database

Create a Dockerfile to construct the image for the database. This file will define the environment and commands to 
build and run the database inside a Docker container and this container is used to execute the H2 server database

```Dockerfile
FROM gradle:jdk17 AS builder

WORKDIR /usr/src/devops/

ADD https://search.maven.org/remotecontent?filepath=com/h2database/h2/2.2.224/h2-2.2.224.jar h2.jar

EXPOSE 8082
EXPOSE 9092

# Using the H2 Shell 
# Create a new database called devops and create a new user called sa with password test
RUN ["java", "-cp", "h2.jar", "org.h2.tools.Shell", "-url", "jdbc:h2:~/data/devops", "-user", "devops", "-password", "devops"]

ENTRYPOINT ["java", "-cp", "h2.jar", "org.h2.tools.Server", "-web", "-webAllowOthers", "-tcp", "-tcpAllowOthers", "-ifNotExists"]
```

### Create a Dockerfile for the web application

Create a Dockerfile to construct the image for the web application. This file will define the environment and commands to
build and run the web application inside a Docker container.

```Dockerfile
FROM gradle:jdk17 AS builder

WORKDIR /usr/src/devops/

COPY CA2/Part2/react-and-spring-data-rest-basic/build .

EXPOSE 8080

ENTRYPOINT ["java","-jar","./libs/react-and-spring-data-rest-basic-0.0.1-SNAPSHOT.jar"]
```

### Create a docker-compose file

Create a docker-compose file to define the services that will be used in the application. This file will define the
services that will be used in the application and the ports that will be exposed.

```yaml
services:
  database:
    build:
      context: .
      dockerfile: Dockerfile_db
    ports:
      - "8082:8082"
      - "9092:9092"
    env_file:
      - common.env

  web:
    build:
      context: ../../
      dockerfile: CA4/Part2/Dockerfile_web
    ports:
      - "8080:8080"
    env_file:
      - common.env
        
    volumes:
       db_data:

```

#### Build and Run the Docker container
To build and run a Docker container using the Docker Compose use the following command:
```
docker compose up
```

#### Publich the images to Docker Hub
Firstly you need to tag the images. Then you can push the images to your Docker Hub repository.
docker tag part2-database 1231820/devops-part2-db:latest
docker tag part2-web 1231820/devops-part2-web:latest

Then you can push the Images to Docker Hub repositories:
docker push 1231820/devops-part2-db:latest
docker push 1231820/devops-part2-web:latest


#### Create and use a volume for the database

Use a volume with the db container to get a copy of the database file by using the
exec to run a shell in the container and copying the database file to the volume.

Firstly run the services defined in the compose.yml:
```
docker-compose up -d
```
Access the shell of the database container:
```
docker-compose exec database bash
```
Then inside the container, copy the database file to the directory mounted to the volume:
```
cp /root/data/devops.mv.db /usr/src/db-backup
```

### Alternative solution to Docker: Kubernetes

Kubernetes is an open-source container orchestration platform that automates the deployment, scaling, 
and management of containerized applications. It can be considered an alternative or complement to Docker, 
as it provides additional features for managing containerized applications at scale.

Docker is a platform for building, sharing, and running containerized applications, providing consistency across 
different environments. Kubernetes, on the other hand, is an orchestration tool for managing containerized 
applications at scale, automating deployment, scaling, and management tasks across clusters of machines. 
While Docker focuses on individual containers, Kubernetes excels at managing distributed containerized applications 
in production environments. Together, they offer a powerful combination for modern software development and deployment

Similar to Docker, Kubernetes can be used to containerize and deploy applications.
Kubernetes provides more advanced features for managing containers at scale, making it suitable for large-scale 
production deployments.
With Kubernetes, you can define deployment configurations, services, and networking rules using YAML manifests, 
providing more flexibility and control over containerized applications.

#### After completing the assignment, tag the repository with `ca4-part2`:

```sh
git tag ca4-part2
git push origin ca4-part2
```


