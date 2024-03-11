# Tutorial React.js and Spring Data REST Application
## Step-by-Step Guide

### Introduction

This report details a step-by-step tutorial for creating an application using React.js and Spring Data REST, using version control with Git. The tutorial demonstrates the creation of different versions of the application marked with tags in the repository, also covering the use of branches and merges for development. Also, the report includes an explanation and implementation of Mercurial as an alternative technological solution to Git.

### Step-by-step commands:

1. Create a new repository in GitHub called 'devops-23-24-PSM-1231820';
2. Clone the new repository in the selected package:
    ```
    git clone git@github.com:1231820AnaLino/devops-23-24-PSM-1231820.git
    ```
3. Follow the steps described in git:

    ```
    echo "# devops-23-24-PSM-1231820" >> README.md
    git init
    git add README.md
    git commit -m "first commit"
    git branch -M main
    git remote add origin git@github.com:1231820AnaLino/devops-23-24-PSM-1231820.git
    git push -u origin main
    ```

4. Generate the gitignore file in the website [Toptal Gitignore Generator](https://www.toptal.com/developers/gitignore) with the following parameters:
    - MacOS
    - Maven
    - React
    - IntelliJ
5. Copy the generated content into the `.gitignore` file.
6. Make a commit with the gitignore changes:
    ```
    git commit -m “Update the gitignore file”
    ```
7. Make a clone of the repository containing the Tutorial React.js and Spring Data REST Application:
    ```
    git clone https://github.com/spring-guides/tut-react-and-spring-data-rest.git
    ```
8. Create a folder named `CA1` in your repository:
    ```
    mkdir <path_to_project>/devops-23-24-PSM-1231820/CA1
    ```
9. Copy the `basic` folder from the tutorial into the `CA1` folder:
    ```
    cp -r <path_to_tutorial_code>/tut-react-and-spring-data-rest/basic <path_to_project>/devops-23-24-PSM-1231820/CA1
    ```
10. Remove the git file in this package: `rm -rf .git`
11. Commit and push the changes:
    ```
    git commit -am “move the tutorials code into the CA1"
    git push
    ```
12. Create a Tag with the first version “v1.1.0” and push.
    ```
    git tag v1.1.0
    git push --tags
    ```
13. Create a new field in the employee class to record the number of years he/she was in the company (“jobYears”).
14. Write unit tests to cover all the Employee class scenarios.
15. Update the React component to display the new field “jobYears”.
16. Debug the application to make sure that everything is working as expected.
17. Commit and push the changes.
    ```
    git commit -am "A new field to record the years of the employee in company was created and attribute validation was done. Additionally, it was performed unit tests to this class"
    git push
    ```
18. Create and push a new tag “v1.2.0”
    ```
    git tag v1.2.0
    git push --tags
    ```
19. Mark the repository with the tag ca1-part1.
    ```
    git tag ca1-part1
    git push origin ca1-part1
    ```
20. Create a branch named “email-field”
    ```
    git checkout -b “email-field”
    ```
21. Create a new field in the employee class to record the email (“emailField”) and perform the respective alteration to the code and respective tests.
22. Debug the application to make sure that everything is working as expected.
23. Commit the changes, merge the branch to main and push them
    ```
    git status
    git add .
    git commit -m "A new email field was added to the application and unit tests were adjusted. Closes #6"
    git checkout main
    git pull
    git merge --no-ff --no-squash email-field
    git push
    ```
24. Create and push a new tag “v1.3.0”
    ```
    git tag -a v1.3.0 -m "tag v1.3.0"
    git push –tags
    ```
25. Create a branch called “fix-invalid-email”
    ```
    git checkout -b “fix-invalid-email”
    ```
26. Make the necessary alterations to the code so that it only accepts emails with ‘@’
27. Debug the application to make sure that everything is working as expected.
28. Commit the changes, merge the branch to main and push them to the repository:
    ```
    git status
    git add .
    git commit -m "Closes #8. Fixed email bugs and adjustment of tests”
    git checkout main
    git pull
    git merge --no-ff --no-squash fix-invalid-email
    git push
    ```
29. Create and push a new tag v1.3.1
    ```
    git tag -a v1.3.1 -m "tag v1.3.1"
    git push –tags
    ```
30. Create a new tag to the repository:
    ```
    git tag -a ca1-part2 -m “tag ca1-part2 for the repository”
    git push --tags
    ```

### Description
The CA1 consists in an Assignment to learn how to create the different versions of an application in the git repository. It was used the `Tutorial React.js and Spring Data REST Application` as codebase to demonstrates how git tags works and how they can be used to tag a specific version of the repository state. Additionally, issues were created and, whenever possible, closed within commit messages. The `Tutorial React.js and Spring Data REST Application` consists in a small application containing two parts: the backend written in java (maven) and the frontend written using react.

### Required changes
#### Employee Class
- Add an attribute to store the number of years an employee has been with the company (called ‘jobYears’);
- Update the constructor to include the new attribute;
- Update all relevant methods or validations to accommodate the new attribute.

#### DatabaseLoader Class
- Update the run method to save employee data with the new field on the database.

#### app.js in the api Frontend
- Update the component ‘EmployeeList’ with a new column ‘jobYears’;
- Change the component ‘Employee’ to display the new employee property;

### Running the Application
To run the application:
1. Run the npm watch in the terminal to build the frontend resources;
2. Run the Springboot to start the application;
3. Access the application at http://localhost:8080 in your web browser.

# Alternative Solution for Version Control: Mercurial

Although Mercurial and Git share many characteristics, Mercurial is a Version Control alternative to Git. While Mercurial is marked by its simplicity, Git provides more resources and support. The Mercurial was developed for large projects, is extremely faster and this characteristic was the focus during its development. Mercurial has fewer functions than git, but they are very similar. Furthermore, Mercurial has an interface web and good documentation. Mercurial has around 70 commands but the most used commands are:
• hg init: Initializes a new Mercurial repository.
• hg clone: Clones an existing repository.
• hg add: Adds files to the index to be included in the next commit.
• hg commit: Performs a commit of changes to the repository.
• hg pull: Fetches changes from a remote repository.
• hg push: Pushes local commits to a remote repository.
• hg branch: Creates or lists branches in the repository.
• hg merge: Merges changes from one branch to another.
• hg update: Updates the repository state to a specific revision.
• hg log: Displays the commit history.
• hg status: Displays the current status of files in the repository.
• hg diff: Shows the differences between files in the working directory and the specified revision.
• hg tag: Creates or lists tags in the repository.
• hg revert: Reverts changes to files in the working directory. Although with different terminologies, both Mercurial and Git, support branching and merging operations. Both focus on performance but Git tend to be faster in cetain operations like staging changes, while Mercurial may perform better in others.

# Implementation of Mercurial

To achieve a solution of the assignment, here are the steps to follow:

1. Create a new repository named “devops-23-24-PSM-1231820
2. Clone the repository and initialize:

```
hg clone ssh://hg@bitbucket.org/1231820AnaLino/devops-23-24-PSM-1231820
echo "# devops-23-24-PSM-1231820" >> README.md
hg add README.md
hg commit -m "first commit"
```
3.	Create hgignore file and add relevant entries
4.	Clone the tutorial:
```
hg clone https://example.com/spring-guides/tut-react-and-spring-data-rest
```
5.	Add the basic folder to the CA1 package in “devops-23-24-PSM-1231820”
6.	Remove the .hg file: rm -rf devops-23-24-PSM-1231820/CA1/.hg
7.	Commit changes: 
```
hg add 
hg commit -m "added React.js and Spring Data REST tutorial"
hg push
```
8.	Create an initial tag: hg tag v1.1.0 follow by hg push --tags
9.	Add a new field to the code to record the number of years in the company;
10.	Add unit test;
11.	Update the React components with this field;
12.	Commit and push the changes:
```
hg commit -m "Added jobYears field to the Employee class and updated React component. Implemented unit tests for the Employee class."
hg push
```
13.	Create and push a new tag 1.2.0;
```
hg tag v1.2.0
hg push --tags
```
14.	Mark the repository with a new tag ca1-part1
```
hg tag ca1-part1
hg push --tags
```
15.	Create a new branch “email-field”
```
hg branch email-field
```
16.	Add changes, commit, and merge back to main
```
hg commit -m "Added a new email field to the application and adjusted unit tests"
hg update default
hg merge email-field
hg commit -m "Merge email-field branch into main"
hg push
```
17.	Create and push a new tag "v1.3.0"
```
hg tag v1.3.0
hg push --tags
```
18. Create a new branch "fix-invalid-email"
```
hg branch "fix-invalid-email”
```
19. Perform the required changes to the code and merge back to main as performed in the step 16;
20.	Create and push a new tag v1.3.0
```
hg tag v1.3.1
hg push --tags
```
21.	Mark the repository with a tag ca1-part2
```
Hg tag ca1-part2
Hg push --tags.
```
# Conclusions
While Git and Mercurial are bothe powerful distributed version control systems, the choice between one of them depends on factors such as personal preference, project requirements and teem familiarity. Both offer similar core functionalities but with different approaches. 







