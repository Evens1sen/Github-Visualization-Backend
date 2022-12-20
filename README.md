## CS209 Report

**Group Members:** 11912919 马浩原，11912725 周民涛

### Overview

In this project, we choose the open-source repository `cs-self-learning` on GitHub to do analysis. (https://github.com/PKUFlyingPig/cs-self-learning). With many prestigious schools in Europe and the United States making their computer courses of the highest quality open source, self-learning CS has become a highly actionable thing. This repository contains some world-renowned CS courses in great academics and the corresponding course materials for self learning, which is of great significance to our study in computer science.

### Design

In order to construct an application to manage and visualize the data in the GitHub repo. We use the front-end and back-end separation design. The backend is based on Springboot, MyBatis-Plus, MySQL to retrieve and manage the data and offer RESTful API to access the data in and related results in the database. The structure of the backend design is shown below.

```
Controller (RESTful API)	
    |
Service (MyBatis-Plus Repository) - ServiceImpl (The implementation of service)
	|
Mapper (MyBatis-Plus DAO) 
	|
Database (MySQL) - Entity (POJO mapped with database)
```

To manage the data, we create three table: commit, issue, releases, and the corresponding entity class, mapper, service and controller. The table are shown below.

<img decoding="async" src="https://github.com/Evens1sen/Github-Visualization/blob/main/cs209_table.png" style="zoom:50%;" />

In addition, in the beginning, we need to retrieve data from GitHub RESTful API, parse it and store it into our database for analysis. The class `DataLoader` is for data fetching and is shown below.

```java
public class DataLoader {
    public static void loadData(String user, String repo) {
            loadCommit(user, repo);
            loadIssue(user, repo);
            loadRelease(user, repo);
    }
    
    // Send a HTTP request to the target API and return response
    private static String request(String target);
    
    // Load commit data into database
    private static void loadCommit(String user, String repo){
        // Step1: Clear data in the database
        // Step2: Use loop to fetch data in different page
        // Step3: Parse Json with Gson
        // Step4: Store into database
    }
    
    // Similar to loadCommit
    private static void loadIssue(String user, String repo);
    
    // Similar to loadCommit
    private static void loadReleases(String user, String repo);
}
```

### Insight

To be continued...
