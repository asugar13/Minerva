# Minerva/Team 22

## Iteration 03 - Review & Retrospect

 * When: March 23, 2017 1:00 pm
 * Where: BA2185

## Process - Reflection

#### Decisions that turned out well

  * We had more frequent coding meetings. This has allowed everyone on the team to be on the same page regarding what's going on in the source code and how the different pieces are being connected together.

  * Trello has still worked well in order to allow us to work in an "agile-like" process.

  * Slack has provided a great form of communication for general announcements after each team member has worked on a task.

  * When small technicalities were too tedious to type on Slack, we used Discord (https://discordapp.com/) in order to have a voice conversations

#### Decisions that did not turn out as well as we hoped

  * Since we wanted everyone to work on something, we think some small amount of redundancy might have been introduced to our Java model.

  * We tried to implement the Java HTTP server using Java's built-in HTTP handling data structures but realized this is a bad idea. Instead, we have used a framework called Spark (http://sparkjava.com/) to take care of all the HTTP requests on the Java server. The extra time that this has taken us has been the main cost. The two applications are now correctly sending JSON to one another.

  * We have been very focused on implementing timetable filtering features in the Java app without having connected the Java app with the Node app first (but we are confident that the logic of our implementation is correct !).



#### Planned changes

List any process-related changes you are planning to make (if there are any)

 * Different framework for handling HTTP requests


## Product - Review

#### Goals and/or tasks that were met/completed:

  * toJSONString()

  * timetable generation

  * filtering algorithms

#### Goals and/or tasks that were planned but not met/completed:

   * We thought we would have a minimum viable product by this deliverable 
   
   * the server...

## Meeting Highlights

Going into the next iteration, our main insights are:

* Make sure the users can see all the possible permutations of their timetables.

* Check that the filtering algorithms are working correctly.

* Adding additional features such as checking that the courses the user inputs are actually in the UofT courses dataset

