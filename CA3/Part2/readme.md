# Class Assigment 3, Part 2: Virtualization with Vagrant

### Overview

In this part of the assignment, it was used Vagrant to create a multi-machine environment with two virtual machines to
execute the tutorial Spring.
The first VM is a MySQL database and the second VM is a Spring Boot application that connects to the database.
The Spring Boot application is a simple REST API that allows to create, read, update and delete records in the database.

## Getting Started

To get started, follow these steps:

### 1. Create a new directory in the 'devops-23-24-PSM-1231820' package:

```
mkdir -p CA3/Part2
```

### 2. Clone the Vagrant multi-spring-tut-demo repository into the package CA3::

```
git clone https://bitbucket.org/pssmatos/vagrant-multi-spring-tut-demo.git
```

### 3. Move the Vagrandfile into the CA3/Part2 directory.

### 4. Follow the instructions provided in the README of the downloaded repository:

- Install and Configure Vagrant

```
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
brew install vagrant
vargrand --version
``` 

### 5. Update the vagrant file for the jdk-17 and the section for cloning our own repository:
```bash
# See: https://manski.net/2016/09/vagrant-multi-machine-tutorial/
# for information about machine names on private network
Vagrant.configure("2") do |config|
  config.vm.box = "ubuntu/bionic64"

  # This provision is common for both VMs
  config.vm.provision "shell", inline: <<-SHELL
    sudo apt-get update -y
    sudo apt-get install -y iputils-ping avahi-daemon libnss-mdns unzip \
        openjdk-17-jdk-headless
    # ifconfig
  SHELL

  #============
  # Configurations specific to the database VM
  config.vm.define "db" do |db|
    db.vm.box = "ubuntu/bionic64"
    db.vm.hostname = "db"
    db.vm.network "private_network", ip: "192.168.56.11"

    # We want to access H2 console from the host using port 8082
    # We want to connet to the H2 server using port 9092
    db.vm.network "forwarded_port", guest: 8082, host: 8082
    db.vm.network "forwarded_port", guest: 9092, host: 9092

    # We need to download H2
    db.vm.provision "shell", inline: <<-SHELL
      wget https://repo1.maven.org/maven2/com/h2database/h2/1.4.200/h2-1.4.200.jar
    SHELL

    # The following provision shell will run ALWAYS so that we can execute the H2 server process
    # This could be done in a different way, for instance, setiing H2 as as service, like in the following link:
    # How to setup java as a service in ubuntu: http://www.jcgonzalez.com/ubuntu-16-java-service-wrapper-example
    #
    # To connect to H2 use: jdbc:h2:tcp://192.168.33.11:9092/./jpadb
    db.vm.provision "shell", :run => 'always', inline: <<-SHELL
      java -cp ./h2*.jar org.h2.tools.Server -web -webAllowOthers -tcp -tcpAllowOthers -ifNotExists > ~/out.txt &
    SHELL
  end

  #============
  # Configurations specific to the webserver VM
  config.vm.define "web" do |web|
    web.vm.box = "ubuntu/bionic64"
    web.vm.hostname = "web"
    web.vm.network "private_network", ip: "192.168.56.10"

    # We set more ram memmory for this VM
    web.vm.provider "virtualbox" do |v|
      v.memory = 1024
    end

    # We want to access tomcat from the host using port 8080
    web.vm.network "forwarded_port", guest: 8080, host: 8080

    web.vm.provision "shell", inline: <<-SHELL, privileged: false
      # sudo apt-get install git -y
      # sudo apt-get install nodejs -y
      # sudo apt-get install npm -y
      # sudo ln -s /usr/bin/nodejs /usr/bin/node
      sudo apt install -y tomcat9 tomcat9-admin
      # If you want to access Tomcat admin web page do the following:
      # Edit /etc/tomcat9/tomcat-users.xml
      # uncomment tomcat-users and add manager-gui to tomcat user

      # Change the following command to clone your own repository!
      git clone https://github.com/1231820AnaLino/devops-23-24-PSM-1231820.git
      cd devops-23-24-PSM-1231820/CA2/Part2/react-and-spring-data-rest-basic
      chmod u+x gradlew
      ./gradlew clean build
      ./gradlew bootRun
     nohup ./gradlew bootRun > /home/vagrant/spring-boot-app.log 2>&1 &
      # To deploy the war file to tomcat9 do the following command:
      sudo cp ./build/libs/basic-0.0.1-SNAPSHOT.war /var/lib/tomcat9/webapps
    SHELL
  end
end

```

### 6. Update the application.properties file in the spring-boot application to connect to the database:
```bash
server.servlet.context-path=/react-and-spring-data-rest-basic-0.0.1-SNAPSHOT
spring.data.rest.base-path=/api

spring.datasource.url=jdbc:h2:tcp://192.168.56.11:9092/./jpadb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.h2.console.settings.web-allow-others=true
```

### 7. To start the VMs go to the directory where the Vagrantfile is located through your terminal and run the following command to start the VMs:
```
vagrant up
```

### 8. Test the Spring application and test the H2 console:
- In the host you can open the spring web application using:
  - http://localhost:8080/  

- You can also open the H2 console using:
  - http://localhost:8082/

For the connection string use: jdbc:h2:tcp://192.168.56.11:9092/./jpadb

### 9. To stop the VMs:
```
vagrant halt
```

### 10. To destroy the VMs:
```
vagrant destroy -f
```
Sometimes the vagrand up does not work, so you need to destroy the VMs using this command and start again.


### Analysis of the alternative Solution - VMware

VirtualBox, is open-source and free, making it ideal for personal use and smaller-scale virtualization. 
It's user-friendly with a simple interface, suitable for running virtual machines on desktops. 
In contrast, VMware, from VMware Inc., offers both free (like VMware Workstation Player) and commercial 
(VMware Workstation Pro, VMware vSphere) versions. 

VMware is known for its polished interface, advanced features (like snapshotting and live migration), and robustness, 
making it preferred for professional and enterprise environments requiring scalability and extensive functionality. 
VMware's ecosystem includes solutions for server virtualization (vSphere) and cloud-based virtualization, 
whereas VirtualBox excels in desktop integration and development/testing scenarios. 
Ultimately, the choice between them depends on specific needs, preferences, and the scale of virtualization deployment.

### Implementation of the alternative Solution - VMware

1. Make download and install VMware Workstation Player on your computer

2. Create a Vangrad file with the following configuration:
```bash
Vagrant.configure("2") do |config|
  # Specify the VMware provider
  config.vm.provider "vmware_desktop" do |v|
    v.vmx["memsize"] = "1024"  # Memory settings for the VM
  end

  config.vm.define "db" do |db|
    db.vm.box = "ubuntu/bionic64"
    db.vm.hostname = "db"
    db.vm.network "private_network", ip: "192.168.56.11"

    db.vm.provider "vmware_desktop" do |v|
      v.vmx["memsize"] = "1024"  # Memory settings for the VM
    end

    db.vm.network "forwarded_port", guest: 8082, host: 8082
    db.vm.network "forwarded_port", guest: 9092, host: 9092

    db.vm.provision "shell", inline: <<-SHELL
      sudo apt-get update -y
      sudo apt-get install -y iputils-ping avahi-daemon libnss-mdns unzip openjdk-17-jdk-headless

      wget https://repo1.maven.org/maven2/com/h2database/h2/1.4.200/h2-1.4.200.jar

      java -cp ./h2*.jar org.h2.tools.Server -web -webAllowOthers -tcp -tcpAllowOthers -ifNotExists > ~/out.txt &
    SHELL
  end

  config.vm.define "web" do |web|
    web.vm.box = "ubuntu/bionic64"
    web.vm.hostname = "web"
    web.vm.network "private_network", ip: "192.168.56.10"

    web.vm.provider "vmware_desktop" do |v|
      v.vmx["memsize"] = "2048"  # Memory settings for the VM (increased for web server)
    end

    web.vm.network "forwarded_port", guest: 8080, host: 8080

    web.vm.provision "shell", inline: <<-SHELL, privileged: false
      sudo apt-get update -y
      sudo apt-get install -y git nodejs npm
      sudo ln -s /usr/bin/nodejs /usr/bin/node
      sudo apt install -y tomcat9 tomcat9-admin

      git clone https://github.com/1231820AnaLino/devops-23-24-PSM-1231820.git
      cd devops-23-24-PSM-1231820/CA2/Part2/react-and-spring-data-rest-basic
      chmod u+x gradlew
      ./gradlew clean build
      nohup ./gradlew bootRun > /home/vagrant/spring-boot-app.log 2>&1 &

      sudo cp ./build/libs/basic-0.0.1-SNAPSHOT.war /var/lib/tomcat9/webapps
    SHELL
  end
end
```

### 11. Mark the repository with the tag ca3-part2 and push the changes
```
    git tag ca3-part2
    git push origin ca3-part2
```