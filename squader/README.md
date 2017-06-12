# squader


### Note
I am working on seperating concerns. We want to keep our code DRY and reduce coupling (try to avoid spagghetti code.)

The project is seperated into three folders: `java/`, `resources/`, and `webapp/`.  

The `java/` folder contains the main package `com.patricksstars.squad` which will contain all of our code.  Inside of this package, we will have a `beans/` folder for interacting with web pages.
The `dao/` folder will have all the DataAccessObjects, which are essentially getters and setters to the database.  The `models/` folder will have classes to represent our data, `project`, `user`, `match`, etc..  The `filters` folder contains filters for intercepting http requests and responses to help with security.

The `webapp/` folder contains our .xhtml templates.  These are the pages that are rendered dynamically through jsf and the beans in our `java/` folder.  In the `webapp/` folder, there is `pages/` which contains templates for the individual views in our application and `templates/` which contains templates for the layout and common components of the site.  We also have the `WEB-INF` folder which contians .xml configuration files for jsf, glassfish, and the web. Tread carefully here.

In the root of this repo, the `pom.xml` file is our Maven Project Object Model (defines the dependencies our project uses.)


Let me know if you have any questions about this stuff. This is what me and Mike talked about doing on Tuesday night.
## todo
##### Anyone take these, send a message in slack if you are going to take one on.
1. Create Tables

 1. Teams Table

		• Should have columns `userId`, `projectId`, `permissions` (0=owner, 1=pm, 2=squad, 3=noob), `roles` (varchar, just a string ie 'frontend')

		• This way users can belong to multiple projects.

		• Also, new users to a team are automatically permission level 3. That's a kind of limbo, where they can view some team details but aren't officially on the team.

		• Teams are used to associate users with projects, and manage user permissions/roles in the projects.

 2. Matches Table

		• columns: `projectId`, `userId`, and boolean `matched`.
		• This table is used to determine whether a user has matched with a project already. Used in ProjectDAO.

2. DataAccessObjects
These classes are meant to facilitate populating our Beans with data from the database.
	1. Project DAO

		* Getter: `User`: "Find all projects I haven't seen/matched"

		• `select * from projects where projects.pid != matches.pid and projects.pid != matches.uid`

		• I'm thinking this would be a join on projects and matches.

		• Returns ArrayList of Project objects.

				note: I think constructing a Project object requires more queries. I'll put a note in Project.java in the `models/` folder.

		* Getter: `PM`: "Show all users who have matched with my project."

			• `select * from projects where projects.pid == matches.pid`

			• ^ That's another join on matches and projects.

			• Returns ArrayList of User objects.

		* Getter: User: "Get all projects that I've matched with."

			• `select * from projects join teams where projects.uid == uid AND teams.permission == 3`

			• Find all projects that the user is a part of the team, but still permission level 3 (that means a PM has matched with them).

			• Returns ArrayList of Project objects.

		* Getter: User: "Get all projects that I am a part of the team."

			• `select * from projects join teams where projects.uid == uid AND teams.permission > 3`

			• Find all projects that the user is an official member of the team.

			• Returns ArrayList of Project objects.


1. Beans

	1. Project Bean

		• Get all projects that I'm a part of as Project objects from ProjectDAO.
		
	2. Matchmaking Bean
		• User: Get all projects that I haven't seen yet.
		• PM: Get all users that have matched yes with my projects.
