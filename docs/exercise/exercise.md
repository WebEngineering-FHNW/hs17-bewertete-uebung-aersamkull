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

## Project grading 

< to be filled by lecturer>
