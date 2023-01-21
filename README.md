# Problem analysis
<p>Information system provides ticket center service. The program stores and processes ticket distribution data. 
As the system supports two types of users: administrator and clients (organizer, distributor) with different roles to access the functionalities in the system. 
For this purpose, an initial login screen is used, and depending on the role of the account stored in the database, one of the three interfaces is activated: 
admin, organizer or distributor.</p>

# Functional requirements
<ol>
<li>The operations available to the administrator are: 
<ul><li>Creation of organizers and distributors</li>
<li>Verification of existing profiles</li> 
<li>Password change</li>
<li>Delete an existing profile that is inactive (not used in the database)</li></ul></li>
<li>The operations available to the organizer are: 
<ul><li>Checking the profile and correcting personal data</li> 
<li>Creating events</li> 
<li>Edit events by adding places to sell</li> 
<li>Appointment of distributors</li>
<li>Evaluating distributors</li> 
<li>Queries for events and distributors.</li></ul></li>
<li>The operations available to the distributor are:
<ul>
<li>Checking the profile and correcting personal data</li>
<li>Ticket sales via form</li>
<li>Queries for events</li></ul></li>
</ol>

![image](https://user-images.githubusercontent.com/100678443/213858770-b921abd1-5fab-4ef1-9282-254c0bf7b1e2.png)

# Project structure
<p>A multi-layer (three-layer) architecture was used for the implementation of the system. Because in this way the interface, 
application processing and storing the data in a database are separated into separate modules. 
The presentation layer consists of the user interface and the controllers that manage it; 
Business logic controls the functionality of the application by performing various data processing processes; 
The data layer consists of a database server, this is where the information from the database is processed.</p>

![image](https://user-images.githubusercontent.com/100678443/213859325-43e80023-5a3c-48fc-adf9-03820aa4c446.png)

# Technologies and tools used:
<ul>
<li>Maven</li>
<li>Java</li>
<li>JavaFX</li>
<li>Oracle Database</li>
<li>JDBC</li>
<li>Log4J</li>
<li>JUnit</li>
</ul>

# Database: Relational model

![image](https://user-images.githubusercontent.com/100678443/213859870-4f553bc7-d93f-4b65-8499-577458e91a8a.png)

# JavaFX interface resources

![image](https://user-images.githubusercontent.com/100678443/213859908-c92aafc3-cef7-4899-b70a-f8e33e0117cd.png)
<h3> Login screen 

![image](https://user-images.githubusercontent.com/100678443/213860009-a2ff9bdf-03c8-4afb-9565-8c55834010fe.png)
<h3> Admin profile
<p>

![image](https://user-images.githubusercontent.com/100678443/213860013-0d9794e2-b2e0-48a6-9544-798123b0174c.png)
![image](https://user-images.githubusercontent.com/100678443/213860018-a27d2d25-5b5f-477a-82bb-157f655a59ce.png)

<h3> Creation of new profile

![image](https://user-images.githubusercontent.com/100678443/213860022-04112d34-90ed-4f30-8144-1191788da267.png)
</p>

<h3> Notification system

![image](https://user-images.githubusercontent.com/100678443/213860030-28968b89-daa2-4d10-9276-90736950ba58.png)
<h3> Organizer profile
<p>

![image](https://user-images.githubusercontent.com/100678443/213860190-14371f2b-f5d8-4e0f-845b-42520441b181.png)
![image](https://user-images.githubusercontent.com/100678443/213860213-1c810ac6-1b41-4e05-a987-9522c653a7c1.png)
![image](https://user-images.githubusercontent.com/100678443/213860246-965e6ee6-19b0-44fa-aa7a-2db0bfce2c1a.png)
![image](https://user-images.githubusercontent.com/100678443/213860249-56251509-8659-4b82-b62c-64441cfe2576.png)
![image](https://user-images.githubusercontent.com/100678443/213860255-f95c0a2f-3894-4179-8b70-843ba0050996.png)
![image](https://user-images.githubusercontent.com/100678443/213860257-7bd0d8cd-3736-4076-9eea-b7a4bfb4f678.png)
![image](https://user-images.githubusercontent.com/100678443/213860259-e454b2aa-9eb5-483c-a233-bcb1af179686.png)
</p>
<h3> Tickets form

![image](https://user-images.githubusercontent.com/100678443/213860262-e31044be-4f17-4b9c-98d3-d2931ba8132b.png)

# Implementation of event registration module in the system
The module is implemented using Log4J2. log4j2 configuration file:

![image](https://user-images.githubusercontent.com/100678443/213860342-481869dc-4a9f-4f39-b219-773ebd44b866.png)

<p>In the program, logs are used in critical sections to catch fatal errors, especially when working with the database. 
They are also used to store information during program operation. The logs are the basis of working with notifications in the system, 
and for this purpose, specific information is saved in a specific file with the id of the counter organizer or distributor.</p>

# Usage configuration 
<ol>
<li>Database creation:
<br><p>Before using the program Oracle Database server should be created. You can use this link for downloading the Oracle server https://drive.google.com/file/d/1DzJ1T5B5Zl3jMgQzn6mfFErqxGrlxpGs
<br> or <br> https://www.oracle.com/database/technologies/xe-downloads.html
<br> After that you will need to download Oracle SQL developer LINKS:
<br> https://drive.google.com/file/d/1fK6u9i-ZimeK01D7xT89ZO4iQ9TEVjhA/view <br> or
<br> https://www.oracle.com/database/sqldeveloper/
<br> Then you need to create the same connection used in the project!
<br> <b>Username: <i> ADMIN</i>
<br> Password: <i> exit </i></b>
<br> Can use the following guide: https://www.youtube.com/watch?v=58V-w3buWm4&ab_channel=ChituOkoli
</p> </li>
</ol>
