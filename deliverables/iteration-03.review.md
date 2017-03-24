# Minerva/Team 22

## Iteration 03 - Review & Retrospect

 * When: March 23, 2017 1:00 pm
 * Where: BA2185

## Process - Reflection

#### Decisions that turned out well

List process-related (i.e. team organization) decisions that, in retrospect, turned out to be successful.

*We had more frequent coding meetings. This has allowed everyone on the team to be on the same page regarding what's going on in the source code and how the different pieces are being connected together.

*Trello has still worked well in order to allow us to work in an "agile-like" process.

*Slack has provided a great form of communication for general announcements after each team member has worked on a task.

*When small technicalities were too tedious to type on Slack, we used Discord (https://discordapp.com/) in order to have a voice conversations

 * 2 - 4 decisions.
 * Ordered from most to least important.
 * Explain why (i.e. give a supporting argument) you consider a decision to be successful.
 * Feel free to refer/link to process artifact(s).

#### Decisions that did not turn out as well as we hoped

List process-related (i.e. team organization) decisions that, in retrospect, were not as successful as you thought they would be.
*Since we wanted everyone to work on something, we think some small amount of redundancy might have been introduced to our Java model.

*We tried to implement the Java HTTP server using Java's built-in HTTP handling data structures but realized this is a bad idea. Instead, we have used a framework called Spark (http://sparkjava.com/) to take care of all the HTTP requests on the Java server. The extra time that this has taken us has been the main cost. The two applications are now correctly sending JSON to one another.

*We have been very focused on implementing timetable filtering features in the Java app without having connected the Java app with the Node app first (but we are confident that the logic of our implementation is correct !).
 * 2 - 4 decisions.
 * Ordered from most to least important.
 * Feel free to refer/link to process artifact(s).


#### Planned changes

List any process-related changes you are planning to make (if there are any)

 * Ordered from most to least important.
 * Explain why you are making a change.


## Product - Review

#### Goals and/or tasks that were met/completed:

*toJSONString()

*timetable generation

*filtering algorithms

 * From most to least important.
 * Refer/link to artifact(s) that show that a goal/task was met/completed.
 * If a goal/task was not part of the original iteration plan, please mention it.

#### Goals and/or tasks that were planned but not met/completed:

   *We thought we would have a minimum viable product by this deliverable 
   
   *the server...
 * From most to least important.
 * For each goal/task, explain why it was not met/completed.      
   e.g. Did you change your mind, or did you just not get to it yet?

## Meeting Highlights

Going into the next iteration, our main insights are:

*Make sure the users can see all the possible permutations of their timetables.

*Check that the filtering algorithms are working correctly.

*Adding additional features such as checking that the courses the user inputs are actually in the UofT courses dataset

 * 2 - 4 items
 * Short (no more than one short paragraph per item)
 * High-level concepts that should guide your work for the next iteration.
 * These concepts should help you decide on where to focus your efforts.
 * Can be related to product and/or process.
