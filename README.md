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

### Deployment

After completing all the backend interfaces, we will scan the swagger.html collection to generate all the interfaces for the frontend to call. We upload our java project files to the server as a .jar package via meavn jar for public testing.

Our server configuration is as follows.

```
Manufacturer: Alibaba Cloud
Product Name: Alibaba Cloud ECS
Distributor ID: Ubuntu
Description: Ubuntu 22.04.1 LTS
ip address: 119.23.110.145
2 vCPUs, 4GiB
Device Start End Sectors Size Type
/dev/vda1 2048 4095 2048 1M BIOS boot
/dev/vda2 4096 413695 409600 200M EFI System
/dev/vda3 413696 83886046 83472351 39.8G Linux filesystem
```

Install the java environment on the server, configure the data on the server, hang it in the background by means of nohub java -jar app.jar, and access the interface provided by the backend through the public ip address so that we can use the frontend for data presentation and analysis

### Insight

This is our front-end visualization page for the PKUFlyingPig/cs-self-learning repository. We used appsmith as the front-end framework for our project. Appsmith is mainly used to build admin panels, internal tools, dashboards, etc. It allows dragging and dropping UI components to build pages that can create internal applications in no time by connecting to any API, database or GraphQL source and writing logic using JavaScript language.

APPsmith can use 15+ types of DBs, REST APIs or GraphQL as data sources, while supporting multiple authentication protocols such as OAuth 2.0. By configuring the data source and writing query statements corresponding to the data source, you can add, delete, change and check the underlying data.

<img decoding="async" src="https://github.com/Evens1sen/Github-Visualization/blob/main/frontend_1.png" style="zoom:50%;" />

<img decoding="async" src="https://github.com/Evens1sen/Github-Visualization/blob/main/frontend_2.png" style="zoom:50%;" />

By visualizing the crawled github repository, we can see that there are 83 contributors in this repository, and as of 20 December 2022, there are 49 open issues, 204 close issues, and only 1 release so far. The distribution time of all commissions is mainly in the afternoon, 37.88%, and only 4.04% in the evening, 18~24. In all titles and topics, there are some hot words like "lab", "add", "update", "book", "course".

As a learning repository, these buzzwords are very appropriate for the situation.
