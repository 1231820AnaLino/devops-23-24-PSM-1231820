# CA4, Part 1 - Containers with Docker

## Aims


## Instructions
The main aims of this part of the assignment is to create docker images and running containers using the chat application from CA2

### Create a new directory in the 'devops-23-24-PSM-1231820' package:
```
mkdir -p CA4/Part1
```

### Install the Docker Desktop application on your computer.
```
https://docs.docker.com/desktop/install/mac-install/
```

# Version 1

### Create the Dockerfile

Create a Dockerfile to execute the chat server using the work performed in the CA2/Part1. 
This is based in the [Gradle Application](https://bitbucket.org/pssmatos/gradle_basic_demo/).
This file will define the environment and commands to build and run the chat server inside a Docker container.


```Dockerfile
# Use a base image with JDK
FROM gradle:jdk21 as builder

# Set the working directory inside the container
WORKDIR /usr/src/devops

# Copy the application source code to the container
ADD ./gradle_basic_demo.zip .

RUN unzip -q ./gradle_basic_demo.zip

# Change the working directory to the copied project directory
WORKDIR ./gradle_basic_demo

# Expose the port that the chat server listens on
EXPOSE 59001

# Build the chat server
RUN ./gradlew clean build

RUN ./gradlew runServer
```

#### Build the Docker Image

Create a docker image with the server inside: 
```
docker build --tag 'devops-part1-image_v1' --file Dockerfile_V1 ../../
```
This has the following explanation:
- `--tag 'devops-part1-image'`: Define the image tag name for easy access.
- `--file Dockerfile`: Docker file config identification.
  -`../../`: Directory that Docker will work with. We must include the parents parent folder to allow docker to get the CA2 content

#### Run the docker container
Run a docker container as follow
```
docker run --publish 59001:59001 devops-part1-image_v1
```
This has the following explanation:
- `run`: instruction to run the image
- `--detach`: Detach the running container from the terminal (will run the container in background)
- `--publish 59001:59001`: Publish the port range between 59001:59001 to allow to stablish communication with the chart server
- `devops-part1-image`: The target image

#### To test the server

1. In another terminal, run the client app from CA2 Part 1 project:

Change path to the CA2/Part1/gradle_basic_demo: 
```
cd <root>/CA2/Part1/gradle_basic_demo
```

2. Build the project to generate the build folder with the jar file: 
```
./gradlew runClient
```

#### list the Docker images:
```
docker images
```

#### Publich the images to Docker Hub
Tag and Push the Images to Docker Hub repositories:
```
docker tag devops-part1-image_v1:latest 1231820/devops-part1-image_v1:latest
docker push 1231820/devops-part1-image_v1:latest
```

# Version 2

### Create the Dockerfile

```Dockerfile
# Use a base image with JDK
FROM gradle:jdk21 AS builder

# Changes the working directory to the gradle_basic_demo
WORKDIR /usr/src/devops/

# Copy the gradle_basic_demo project to the container
COPY CA2/Part1/gradle_basic_demo/build/libs/basic_demo-0.1.0.jar basic_demo-0.1.0.jar

# Expose the port that the chat server listens on
EXPOSE 59001

# Run the chat server using a jar file
ENTRYPOINT ["java","-cp","./basic_demo-0.1.0.jar","basic_demo.ChatServerApp","59001"]
```

#### Build the Docker Image

Create a docker image with the server inside:
```
docker build --tag 'devops-part1_v2-image' --file Dockerfile_V2 ../../
```
This has the following explanation:
- `--tag 'devops-part1-image'`: Define the image tag name for easy access.
- `--file Dockerfile`: Docker file config identification.
  -`../../`: Directory that Docker will work with. We must include the parents parent folder to allow docker to get the CA2 content

#### Run the docker container

Run a docker container as follow
```
docker run --publish 59001:59001 devops-part1_v2-image
```

#### list the Docker images:
```
docker images
```

#### Publich the images to Docker Hub
Tag and Push the Images to Docker Hub repositories:
```
docker tag devops-part1_v2-image:latest 1231820/devops-part1_v2-image:latest
docker push 1231820/devops-part1_v2-image:latest

```

#### Steps that can be performed after Version 1 or Version 2

### List running containers

To list all the docker containers running, can be used the terminal using the following instruction: `docker ps -a`.

`-a`: Option to list all containers (containers that are running and were stopped)

Another alternative is using the Docker Desktop app

### Stop a container

`docker stop <container_id>`: The container ID can be obtained using the `docker ps` command
Another alternative is using the Docker Desktop app

### Remove the docker container

`docker rm <container_id>`
Another alternative is using the Docker Desktop app

### Tagging the Repository

After completing the assignment, tag the repository with `ca4-part1`:

```sh
git tag ca4-part1
git push origin ca4-part1
```