# Minerva/Group 22


## Iteration 3

 * Start date: March 14, 2017
 * End date: March 23, 2017

## Process

#### Changes from previous iteration

List the most significant changes you made to your process (if any).

* We will have more in person meetings.
  * It's easier to hash out smaller details and keep everyone on the same page if we actually talk in person and can draw diagrams 
  or physically point out pieces of code. 
* We will add a front end manager.
  * Giving someone the power to make final decisions makes overall decision making easier.
* Put due dates on Trello tickets.
  * This will hopefully motivate people to finish tasks sooner.
  * We will try to record when a task was actually done versus its due date, to measure our productivity.
 

#### Roles & responsibilities

  * Front End Developer:
    * Responsibilities:
      * UI for picking courses and picking restrictions (e.g. Which semester)
      * Display generated timetables
      * Allow Course Picking / Adding restrictions

  * Back End Developer:
    * Responsibilities:
      * Create class to retrieve course offering data
      * Create business objects to represent course and timetable data
      * Design API
      * Create methods to compare timetables to each other
      * Generate all possible feasible timetables
      * Sort timetables by criteria

  * Project Manager:
    * Responsibilities:
      * Track what has been done/needs to be done and assign tasks if necessary.
      * Control overall design of system and resolve any issues in regards to how different classes fit together.
      
  * Front End Manager
    * Responsibilities
      * Delegate website tasks if necessary.
      * Make final decisions on UI design.


#### Events

* We will have weekly sync meetings during tutorials 
  * We will discuss:
    * What we’ve done
    * What we’re going to do/What needs to be done
    * Any problems or issues we’re facing
 
* We will have weekly coding meetings.
  * We will code together in person and clear up any questions that have come up.  

#### Artifacts

* We will set up a task board on Trello to keep track of what needs to be done and any issues that come up.
  * We will start to put due dates on Trello tasks.
* Tasks will be prioritized based on the order they listed in. High priority goes to the top of the list and team members can rearrange tasks as they see fit.
* Team members take whatever tasks they are most comfortable with and move them to the assigned/working on list.

![alt text] (https://github.com/csc301-winter-2017/project-team-22/blob/master/deliverableImages/trello3.PNG)
* Trello is integrated with Slack to provide notifications for updates to the board
* We will use Slack to communicate throughout the week when we run into issues
* If issues come up then process can vary slightly, for example if one feature is blocking somebody’s, then they can for somebody to pick up that ticket or to pick it up themselves and finish it asap.

![alt text] (https://github.com/csc301-winter-2017/project-team-22/blob/master/deliverableImages/slack3.PNG)

#### Git / GitHub workflow

This is identical to iteration-02.plan.md. (https://github.com/csc301-winter-2017/project-team-22/blob/master/deliverables/iteration-02.plan.md)

* Every team member will fork the master repo.
* After someone finishes a feature, they can make a pull request from their fork to the master repo.
  * If the team member is confident they can merge the request themselves. They may also request a code review and have another team member
 merge the request.
* Conflicts should be resolved by whoever is making the merge.

We hope that this workflow will minimize conflicts while allowing for code reviews if necessary. We trust team members to be able make good
decisions about what code gets merged into the master repo and whether or not they need help.


## Product

#### Goals and tasks

 * Finish generator classes so that we can actually generate timetables.
 * Finish timetable comparators for filtering timetables.
 * Set up HTTP server
 * Fully implement the Javascript for the front end so that user data is carried through the site.
 * UI enhancements for the front end.

#### Artifacts

 * All classes in TimetableGen folder (https://github.com/csc301-winter-2017/project-team-22/tree/master/TimetableGen/). That is the 
 folder that contains all the classes for the backend.
   * SemesterConfigurationGenerator.java, TimetableConfigurationGenerator.java specifically.
 * Javascript for the website, so that we actually carry all user input through the pages.
   * Specifically everything in https://github.com/csc301-winter-2017/project-team-22/tree/master/webApp/assets/scripts
 * A video demoing the website.
