# WebEngineering Module, Graded Exercise

## Commit Proposal

Matriculation Number: 14-467-146

Project idea short description: 

Task repetition system with changing responsibles

A simple task system in which recurring tasks (= task masters) can be created. The special thing is 
that multiple persons can be assigned. If three persons are assigned for a task, 
a single person has to do the task every 3rd time. 

If there is enough time, task occurences can be edited/deleted. This will cause complexity as the responsibles for following tasks should not be changed and I can't persistently store all occurences (because tasks could be endless, say every two weeks forever).

The repetion system will be as simple as possible. A task master has the following properties:

- Name
- Responsible List
- Start Date
- End Date (optional)
- Repetition Interval Type: Day, Week, Month, Year
- Repetition Interval (a number)
- Description (optional)

There will be views for creating a task master, viewing task occurences (all) and viewing task occurences (own)

As there will be no authentication system, one has to set a username, just as a simple text. 
Therefore the Responsible List is a simple String List, too. This is version 1, if have too much time
I will do authentication via Outlook.com or similar :)

Technical notes:

 - KnockoutJS as a simple frontend library that is not a full blown SPA Framework
 - TypeScript, if that is ok?
 - Is a REST Architecture basically ok or do you prefer forms?
 
## Project confirmation

Confirmed.

Usage of both Knockout and TypeScript ok.
Go for it!

## Project delivery <to be filled by student>

How to start the project: (if other than `grailsw run-app`)

How to test the project:  (if other than `grailsw test-app`)

Project description:      /description.html

External contributions:

Other comments: 

- This application requires a modern browser, IE 11 should work but no older browsers
- If your browser does not support input type date, you have to enter dates as yyyy-MM-dd (or use another browser ;) ). The tool has been tested with Opera
- I checkedin both, the TypeScript and the Javascript files. This leads to more changes but makes the build process independent of TypeScript

I'm particular proud of:

 - Using Bootstrap 4
 - Using Iconic, which is a lightweight Icon-Library
 - Using Java 8 date type
 - It looks good :)
 - There are 4 task types: masters, occurences, exceptions and simple tasks. That all that MS Exchange knows :)
- I used yarn/npm to document all versions used in this project
- Use of media queries to make it work on mobile devices

## Project grading 

Documentation HTML has some issues when run through the validator. Otherwise nice.
All functionality seems to work fine.

The functionality includes a small amount of navigation and and extensive amount of user input.
Validation is done to some extend but e.g. entering an invalid entry as a date produces
```
java.time.format.DateTimeParseException
Caused by
Text 'xxx' could not be parsed at index 0
Around line 132 of grails-app/controllers/mvc/TaskController.groovy
129:			task.responsible = User.get(params.responsible.toInteger()) 
```
Nice, interactive UI.
Otherwise the typical features of a CRUD application (6) plus mobile-friendly design (2).

Commit log is very nice, over a series of days, finishing well before the deadline.
Tests are missing. 
HTML validates with warnings.
Domain classes and Service is nicely documented but TS code only very sparsely.
Naming is generally ok (beware that a domain class "User" can cause issues in some DBMSs, e.g. Postgres.)
There is some duplication (e.g. in the views where using templates might have helped) but not overly so.
There is quite a number of dependencies. Some of them (JQuery, Knockout) make the TS code feel unconventional.

I award 5 extra points for the technical challenge that comes from the SPA approach and the technology mix.
Since we already hit the ceiling, I do not grade more points for other extras.


Congratulations! This is a very good web application.
You know much more about web technology than what we covered in the lectures.
On the other hand, we put a lot of effort in working from tests and it is a pity 
that you chose to ignore this important part of engineering.

6.0
