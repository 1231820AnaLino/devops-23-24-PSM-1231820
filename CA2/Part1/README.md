# Tutorial Gradle basic demo 
## Step-by-Step Guide

# Part 1: Build Tools with Gradle

## Overview
This repository contains the implementation for Part 1 of the CA2 assignment, focusing on practicing Gradle using a simple example application.

## Getting Started
To get started, follow these steps:

### 1. Create a new directory in the 'devops-23-24-PSM-1231820' package:
```
mkdir -p CA2/Part1
```

### 2. Clone and commit the Gradle basic demo repository:
```
git clone https://bitbucket.org/pssmatos/gradle_basic_demo.git
```

### 3. Move to the CA2/Part1 directory.

### 4. Remove the . git file:
```
rm -rf .git
```

### 5. Follow the instructions provided in this README to complete the assignment tasks.

### 6. Add a new task to execute the server:

```
task runServer(type:JavaExec, dependsOn: classes){
    group = "DevOps"
    description = "Launches a chat server on localhost:59001"

    classpath = sourceSets.main.runtimeClasspath

    mainClass = 'basic_demo.ChatServerApp'

    args '59001'}
```


### 7. Add a simple unit test and update the Gradle script to execute the test:
```
    @Test
    public void testAppHasAGreeting() {
        App classUnderTest = new App();
        assertNotNull("app should have a greeting", classUnderTest.getGreeting()); }
```

### 8. Add a new task of type Copy to make a backup of the sources of the application.
```
task backupSources(type: Copy) {
    from 'src/main'
    into 'backup'
}
```

### 9. Add a new task of type Zip to make an archive (zip file) of the sources of the application.

```
  task zip(type: Zip) {
    group = "DevOps"
    description = "Zips the chat server log file."

    from 'src/main'
    archiveFileName = 'src.zip'
    destinationDirectory = file('build')}
```

### 10. Mark the repository with the tag ca2-part1 and push the changes
```
    git tag ca2-part1
    git push origin ca2-part1
```

  
