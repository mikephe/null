insert into project(name, description, tags)
values ("Tester 5", "This is project 5", "Java,MySQL,CS 160");
select * from project;
select * from squaders;
insert into teams(pID, uID, permissions, roles)
values(2,2,0,"owner");
select * 
from project inner join teams 
on project.id=teams.pID
where teams.uID=2;
select uID
from project inner join teams
on project.id=teams.pID
where teams.pID=1;
use SQUADER;