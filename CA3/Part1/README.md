# Class Assigment 3, Part 1: Virtualization with Vagrant

## Virtual Machine Setup and Project Execution Guide

The goal of this assignment is to set up a Virtual Machine (VM) using VirtualBox and execute projects inside it. 
The VM will be used to run projects that require specific configurations or dependencies.


### 1. Creating the Virtual Machine

Create a Virtual Machine using VirtualBox for Apple with arm64 architecture.

### 2. Cloning Repository

Clone your individual repository inside the VM:

```
git clone git@github.com:1231820AnaLino/devops-23-24-PSM-1231820.git
```

### 3. Make updates and install Dependencies

```
sudo apt update
sudo apt install maven
sudo apt install gradle
sudo apt install git
sudo apt install openjdk-11-jdk
sudo apt install spring
```

### 4. Change permissions of the ./gradlew file

```
chmod +rwx ./gradlew
```

### 5. Building and Executing Projects

#### Maven Project (spring boot tutorial basic project)

1. Navigate to the Maven project directory:

cd /devops-23-24-PSM-1231820/CA1/tut-react-and-spring-data-rest/basic

2. Build and run the project:

mvn spring-boot:run

### 6. Accessing Web Applications

Access the applications from the browser in your host machine using the IP address and port:

http://192.168.56.5:8080

#### Gradle Project (gradle_basic_demo)

1. Navigate to the Gradle project directory:

cd /devops-23-24-PSM-1231820/CA2/Part1/gradle_basic_demo

2. Build the project:

gradlew build

3. Run the project in your VM:

   java -cp build/libs/basic_demo-0.1.0.jar basic_demo.ChatServerApp <server port> (59001)

4. Run a client in your host machine to connect to the server running in the VM. Open a terminal and execute the following gradle task:

   ./gradlew runClient

You can perform this task as many times as you want to create multiple clients.

5. The above task assumes the chat server's IP is "localhost" and its port is "59001". Edit the runClient task in the "build.gradle" file with the right IP

### 6. Executing Server and Clients for Certain Projects

For projects like a simple chat application, execute the server inside the VM and the clients in your host machine. This is required because:

- The server needs to run in the VM environment.
- Clients can interact with the server from the host machine.


### 7. Tagging the Repository with the tag ca3-part1 and push the changes

```
git tag ca3-part1
git push --tags
```
