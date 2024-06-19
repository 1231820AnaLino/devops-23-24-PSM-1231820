### Step-by-Step Guide for CA5: CI/CD Pipelines with Jenkins
The goal of this Class Assignment (CA) is to implement CI/CD pipelines using Jenkins using the 
”gradle basic demo” developed in CA2.

This guide will be divided into the following sections:
1. **Training with a simple Jenkins pipeline script**
2. **Use our repository (CA2, Part1) to practice with Jenkins**
3. **Create and Configure a New Jenkins Job for the second part of the Repository (Ca2, Part2)**

### 1. Set Up Your Jenkins Environment
**Install Jenkins Using Docker:**

  ```bash
  docker run -d -p 8080:8080 -p 50000:50000 -v jenkins-data:/var/jenkins_home --name=jenkins jenkins/jenkins:lts-jdk17
  ```

**Access to Jenkins and complete the setup steps after put the admin password**
  ```bash
http://localhost:8080
  ```

**Install the Required Plugins for all steps of this CA:**
- Go to the Jenkins dashboard and click on "Manage Jenkins".
- Select "Manage Plugins" and go to the "Available" tab.
- Search for the following plugins and install them:
    - Docker Pipeline
    - HTML Publisher
    - Docker Pipeline
    - Docker API Plugin

**Create a new Jenkins job:**
- Go to the Jenkins dashboard, click on "New Item".
- Enter the job name ('devops_ca5'), select "Pipeline", and click "OK".

**Configure the pipeline:**
- Scroll to the "script" section and enter the following script provided in the CA tutorial:

```groovy
pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out...'
                git 'https://bitbucket.org/pssmatos/gradle_basic_demo'
            }
        }
        stage('Build') {
            steps {
                echo 'Building...'
                sh './gradlew clean build'
            }
        }
        stage('Archiving') {
            steps {
                echo 'Archiving...'
                archiveArtifacts 'build/distributions/*'
            }
        }
    }
}
```
- Click "Save".

**Run the pipeline:**
- Click on "Build Now" to execute the pipeline.
- Check the console output for the pipeline execution.
- Verify that the pipeline runs successfully.


### 2. Use our repository (CA2, Part1) to practice with Jenkins

**Access to Jenkins and create a Jenkinsfile:**
- Create a Jenkinsfile within the package CA5, named ´Jenkinsfile_CA2P1´, with the following content:

```groovy
pipeline {
  agent any
  stages {
    //repository to checkout
    stage('Checkout') {
      steps {
        echo 'Checking out repo...'
        git branch: 'main', url: 'https://github.com/1231820AnaLino/devops-23-24-PSM-1231820'
      }
    }

    //Assemble - This stage will build the project
    stage('Assemble') {
      steps {
        echo 'Assembling project...'
        dir('CA2/Part1/gradle_basic_demo') {
          //Change gradlew permissions
          sh 'chmod +x gradlew'
          sh './gradlew assemble'
        }
      }
    }

    //Test stage to validate project
    stage('Test') {
      steps {
        echo 'Testing project...'
        dir('CA2/Part1/gradle_basic_demo') {
          sh './gradlew test'
        }
      }
    }

    //Archive the generated binary file from the assemble stage
    stage('Archive') {
      steps {
        dir('CA2/Part1/gradle_basic_demo') {
          archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true
        }
      }
    }
  }
}
```
**Commit and push the Jenkinsfile:**
- Commit and push the Jenkinsfile to GitHub.

**Create a new pipeline job in Jenkins:**
- Go to Jenkins dashboard and click on "New Item";
- Create a new pipeline job named `pipeline_ca2_p1`;
- Select the URL to repository in the "Pipeline script from SCM" section:
'https://github.com/1231820AnaLino/devops-23-24-PSM-1231820.git';
- And the Script Path to `CA5/Jenkinsfile_CA2P1`.
- Click "Save".
- Run the pipeline by clicking on "Build Now".
- Check the console output for the pipeline execution.
- Verify that the pipeline runs successfully.


### 3. Create and Configure a New Jenkins Job for the second part of the Repository (Ca2, Part2)

To correctly perform this part of the assignment it is necessary to follow the instructions provided in the link:
  ```bash
https://www.jenkins.io/doc/book/installing/docker/
  ```

Briefly, the steps are:
- Create a bridge network in Docker to allow the Jenkins to communicate with the Docker, 
using the following docker network create command:
    ```bash
    docker network create jenkins
    ```
   
- Download and Run the docker:ding Docker image using the follow command:
    ```bash
docker run \
--name jenkins-docker \
--rm \
--detach \
--privileged \
--network jenkins \
--network-alias docker \
--env DOCKER_TLS_CERTDIR=/certs \
--volume jenkins-docker-certs:/certs/client \
--volume jenkins-data:/var/jenkins_home \
--publish 2376:2376 \
docker:dind \
    ```

- Create a Dockerfile to customize the official Jenkins Docker image as follow:
    ```Dockerfile
FROM jenkins/jenkins:2.452.2-jdk17
USER root
RUN apt-get update && apt-get install -y lsb-release
RUN curl -fsSLo /usr/share/keyrings/docker-archive-keyring.asc \
https://download.docker.com/linux/debian/gpg
RUN echo "deb [arch=$(dpkg --print-architecture) \
signed-by=/usr/share/keyrings/docker-archive-keyring.asc] \
https://download.docker.com/linux/debian \
$(lsb_release -cs) stable" > /etc/apt/sources.list.d/docker.list
RUN apt-get update && apt-get install -y docker-ce-cli
USER jenkins
RUN jenkins-plugin-cli --plugins "blueocean docker-workflow"
    ```

- Build the Docker image:
    ```bash
    docker build -t myjenkins-blueocean:2.452.2-1 .
    ```
- Run the jenkins-blueocean container:
    ```bash
docker run \
--name jenkins-blueocean \
--restart=on-failure \
--detach \
--network jenkins \
--env DOCKER_HOST=tcp://docker:2376 \
--env DOCKER_CERT_PATH=/certs/client \
--env DOCKER_TLS_VERIFY=1 \
--publish 8080:8080 \
--publish 50000:50000 \
--volume jenkins-data:/var/jenkins_home \
--volume jenkins-docker-certs:/certs/client:ro \
myjenkins-blueocean:2.452.2-1
    ```

**Access Jenkins and create a new Jenkinsfile**
- Create a Jenkinsfile within the package CA5, named ´Jenkinsfile_Ca2P2´, with additional stages as the following:

```groovy
pipeline {
    agent any

    environment {
        DIR = 'CA2/Part2/react-and-spring-data-rest-basic'
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out repo...'
                git branch: 'main', url: 'https://github.com/1231820AnaLino/devops-23-24-PSM-1231820.git'
            }
        }

        stage('Assemble') {
            steps {
                echo 'Assembling project...'
                dir(env.DIR) {
                    sh 'chmod +x gradlew'
                    sh './gradlew assemble'
                }
            }
        }

        stage('Test') {
            steps {
                echo 'Testing project...'
                dir(env.DIR) {
                    sh './gradlew test'
                }
            }
        }

        stage('Javadocs') {
            steps {
                echo 'Generating Javadocs...'
                dir(env.DIR) {
                    sh './gradlew javadoc'
                }
                publishHTML(target: [
                        allowMissing: false,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'CA2/Part2/react-and-spring-data-rest-basic/build/docs/javadoc',
                        reportFiles: 'index.html',
                        reportName: 'Javadoc'
                ])
            }
        }

        stage('Archive') {
            steps {
                echo 'Archiving project...'
                dir(env.DIR) {
                    archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true
                }
            }
        }

        stage('Create Dockerfile') {
            steps {
                echo 'Creating Dockerfile...'
                dir(env.DIR) {
                    script {
                        def dockerfileContent = """
                        FROM gradle:jdk17
                        WORKDIR /app
                        COPY build/libs/*.jar app.jar
                        EXPOSE 8080
                        ENTRYPOINT ["java", "-jar", "app.jar"]
                        """
                        writeFile file: 'DockerfileCa2P2', text: dockerfileContent
                    }
                }
            }
        }

        stage('Publish Docker Image') {
            steps {
                echo 'Publishing Docker image...'
                dir(env.DIR) {
                    script {
                        def dockerImage = docker.build("1231820/devops-ca5:${env.BUILD_ID}", "-f DockerfileCa2P2 .")
                        docker.withRegistry('https://index.docker.io/v1/', 'dockerhub_id') {
                            dockerImage.push()
                        }
                    }
                }
            }
        }
    }
}
```

**Commit and push the Jenkinsfile:**
- Commit and push the Jenkinsfile to GitHub.

**Create a new pipeline job in Jenkins:**
- Go to Jenkins dashboard and click on "New Item";
- Create a new pipeline job named `pipeline_ca2_p2`;
- Select the URL to repository in the "Pipeline script from SCM" section:
  'https://github.com/1231820AnaLino/devops-23-24-PSM-1231820.git';
- And the Script Path to `CA5/Jenkinsfile_Ca2P2`.
- Click "Save".
- Run the pipeline by clicking on "Build Now".
- Check the console output for the pipeline execution.
- Verify that the pipeline runs successfully.

**Use a Docker compose to automate the creation of the two services and network**
- Create a compose.yaml file with the following content:

```yml
services:
  docker-dind:
    container_name: jenkins-docker
    image: docker:dind
    privileged: true
    environment:
      - DOCKER_TLS_CERTDIR=/certs
    volumes:
      - jenkins-docker-certs:/certs/client
      - jenkins-data:/var/jenkins_home
    networks:
      jenkins:
        aliases:
          - docker
    ports:
      - 2376:2376

  jenkins-blueocean:
    build: .
    container_name: jenkins-blueocean
    restart: on-failure
    environment:
      - DOCKER_HOST=tcp://docker:2376
      - DOCKER_CERT_PATH=/certs/client
      - DOCKER_TLS_VERIFY=1
    volumes:
      - jenkins-data:/var/jenkins_home
      - jenkins-docker-certs:/certs/client:ro
    networks:
      - jenkins
    ports:
      - 8080:8080
      - 50000:50000

volumes:
  jenkins-docker-certs:
  jenkins-data:

networks:
  jenkins:
```

**Run the Docker compose:**
- Run the following command to start the services:
  ```bash
  docker-compose up
  ```

### **Tag Your Repository**

**Add a tag with `ca5`**.

```bash
git tag ca5
git push origin ca5
```

### Conclusions
The completion of the CA5 assignment on CI/CD pipelines using Jenkins, Docker, and Docker Compose demonstrated the 
ability to set up complex environments to enhance efficiency and reproducibility in software development.
By leveraging Jenkins as our continuous integration and continuous deployment tool, we established a robust pipeline 
capable of automating the entire software delivery process. Docker played a pivotal role in this setup, 
enabling consistent environment provisioning and facilitating seamless container builds across different stages of our 
pipeline. Utilizing Docker Compose further enhanced our infrastructure by orchestrating multiple services, 
including Jenkins itself and Docker containers for build environments, ensuring scalability and efficiency in managing 
our CI/CD workflows. This project not only deepened our understanding of CI/CD principles but also equipped us with 
practical skills in configuring and optimizing development environments for faster and more reliable software releases.