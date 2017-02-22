# Timetable Generator/Team 22

## Iteration 02

 * Start date: Feb 16, 2017
 * End date: March 9, 2017

## Process

#### Roles & responsibilities

Front End Developer and Back End Developer roles are identical to the roles from iteration-01.plan.md.

  * Front End Developer:
    * Responsibilities:
      * UI for picking courses and picking restrictions (e.g. Which semester)
      * Display generated timetables
      * Allow Course Picking / Adding restrictions
      * Sort timetables by criteria

  * Back End Developer:
    * Responsibilities:
      * Create class to retrieve course offering data
      * Create business objects to represent course and timetable data
      * Design API
      * Create methods to compare timetables to each other
      * Generate all possible feasible timetables

  * Project Manager:
    * Responsibilities:
      * Track what has been done/needs to be done and assign tasks if necessary.
      * Control overall design of system and resolve any issues in regards to how different classes fit together.

#### Events

This is identical to iteration-01.plan.md.

* We will have weekly sync meetings during tutorials 
* We will discuss:
 * What we’ve done
 * What we’re going to do/What needs to be done
 * Any problems or issues we’re facing

#### Artifacts

This is identical to iteration-01.plan.md.

* We will set up a task board on Trello to keep track of what needs to be done and any issues that come up.
* Tasks will be prioritized based on the order they listed in. High priority goes to the top of the list and team members can rearrange tasks as they see fit.
* Team members take whatever tasks they are most comfortable with and move them to the assigned/working on list.
* Trello is integrated with Slack to provide notifications for updates to the board
* We will use Slack to communicate throughout the week when we run into issues
* If issues come up then process can vary slightly, for example if one feature is blocking somebody’s, then they can for somebody to pick up that ticket or to pick it up themselves and finish it asap

#### Git / GitHub workflow
 
* Every team member will fork the master repo.
* After someone finishes a feature, they can make a pull request from their fork to the master repo.
 * If the team member is confident they can merge the request themselves. They may also request a code review and have another team member
 merge the request.
* Conflicts should be resolved by whoever is making the merge.

We hope that this workflow will minimize conflicts while allowing for code reviews if necessary. We trust team members to be able make good
decisions about what code gets merged into the master repo and whether or not they need help.

## Product

#### Goals and tasks

 * Define API for making requests to the back end
 * Define API for sending data to front end
 * Write Data Access Object (DAO) class
 * Write Business Object classes
 * Write Generator class
 * Write HTTP request class
 * Convert mocks-ups to HTML/Javascript.
 * Link front end HTML/Javascript to backend Java API

#### Artifacts
  
 * All classes in TimetableGen folder. That is the folder that contains all the classes for the backend.
 * HTML/Javascript pages that roughly match the mock-ups. This is the UI that user will use to actually make timetables.
 * A video demoing the website.
