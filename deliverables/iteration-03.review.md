# Minerva/Team 22

## Iteration 03 - Review & Retrospect

 * When: March 23, 2017 1:00 pm
 * Where: BA2185

## Process - Reflection

#### Decisions that turned out well

  * We had more frequent coding meetings. This has allowed everyone on the team to be on the same page regarding what's going on in the source code and how the different pieces are being connected together.

  * Trello has still worked well in order to allow us to work in an "agile-like" process.

  * Slack has provided a great form of communication for general announcements after each team member has worked on a task.
  
  ![Slack](https://github.com/csc301-winter-2017/project-team-22/blob/master/deliverableImages/slack3review.PNG)

  * When small technicalities were too tedious to type on Slack, we used Discord (https://discordapp.com/) in order to have a voice conversations

#### Decisions that did not turn out as well as we hoped

  * Since we wanted everyone to work on something, we think some small amount of redundancy might have been introduced to our Java model.

  * We tried to implement the Java HTTP server using Java's built-in HTTP handling data structures but realized this is a bad idea. Instead, we have used a framework called Spark (http://sparkjava.com/) to take care of all the HTTP requests on the Java server. The extra time that this has taken us has been the main cost. The two applications are now correctly sending JSON to one another.

  * We have been very focused on implementing timetable filtering features in the Java app without having connected the Java app with the Node app first (but we are confident that the logic of our implementation is correct!).

* Adding deadlines to the Trello board has not worked out very well. Most tickets do not have any deadlines.

 ![Trello](https://github.com/csc301-winter-2017/project-team-22/blob/master/deliverableImages/trello3Review.PNG)


#### Planned changes

* We don't think we should be making any process-related changes at this point. We believe by next week we will finally have a minimum viable product ready to showcase. However, some in charge of the node app are taking a look at the Java app (and vice versa) to make sure the inter-server connection works as expected.


## Product - Review

#### Goals and/or tasks that were met/completed:

  * We are properly generating all the timetable permutations, given a course selection. 
  (https://github.com/csc301-winter-2017/project-team-22/blob/master/TimetableGen/src/generation/TimetableGenerator.java)

  * We have implemented filtering algorithms (based on number of days off, time in between classes...) for displaying the timetables.
  (https://github.com/csc301-winter-2017/project-team-22/blob/master/TimetableGen/src/generation/TimetableCompare.java)

  * The Javascript on the front end now stores user data in sessionStorage so that information persists across pages.
  (https://github.com/csc301-winter-2017/project-team-22/tree/master/webApp/assets/scripts)
  
  * General UI of the web application has been improved.
  (https://github.com/csc301-winter-2017/project-team-22/tree/master/webApp/public)

#### Goals and/or tasks that were planned but not met/completed:

   * We thought we would have a minimum viable product by this deliverable 
   
   * The implementation of the Java server has been a major roadblock. We have only just set up a skeleton HTTP server.
   (https://github.com/csc301-winter-2017/project-team-22/blob/master/TimetableGen/src/http/RestServer.java)

## Meeting Highlights

Going into the next iteration, our main insights are:

* Make sure the users can see all the possible permutations of their timetables.

* Check that the filtering algorithms are working correctly.

* Adding additional features such as checking that the courses the user inputs are actually in the UofT courses dataset (display an 
  informative message if the user's input is invalid)

