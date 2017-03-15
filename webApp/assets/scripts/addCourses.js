"use strict";

var numCourses = 0;

/**
 * Search for the given courseCode in the GET /course-information response.
 *
 * @param {String} courseCode
 */
function searchCourses (courseCode){
  for (var i = 0; i < courses.courses.length; i++){
    var course = courses.courses[i];
    if (course.courseCode.substring(0, 6) == courseCode.toUpperCase().substring(0, 6)){
      return course
    }
  }
  
  return null;
}

/**
 * Return a human readable string version of the given course object.
 *
 * Object is formatted as specified in httpObjectExamples/course-information.txt
 *
 * @param {object} course
 */
function formatCourseDescription(course){
  return '<b>Course Code: </b>' + course.courseCode +
         '<br><b>Name: </b>' + course.name +
         '<br><b>Description: </b>' + course.description +
         '<br><b>Breadth: </b>' + course.breadths +
         '<br><b>Prerequisites: </b>' + course.prerequisites +
         '<br><b>Exclusions: </b>' + course.exclusions 
}

$(document).ready(function(){
  
  $("#go").click(function () {
    
    var courseCode = $("#course-ID").val();
    var course = searchCourses(courseCode);
    
    // Error messages for trying to add a course that doesn't exist
    if (course == null){
    
      $("#alert").removeClass("hidden");
      $("#alert").html("Whoops, it looks like you didn't enter a valid course code.");
    
    // Error message for trying to add a course that's already been added 
    } else if (sessionStorage.getItem(course.courseCode) != null) { 
    
      $("#alert").removeClass("hidden");
      $("#alert").html("Whoops, it looks like you've already added this course.");
    
    } else {
      
      $("#alert").addClass("hidden");
      sessionStorage.setItem(course.courseCode, '');

      var name = "restrictions" + numCourses;
      $('#course-table').append(
        '<tr> \
          <td>' + formatCourseDescription(course) + '</td> \
          <td> \
            <input type="radio" name="' + course.courseCode + '" value="F"> Fall <br> \
            <input type="radio" name="' + course.courseCode + '" value="S"> Winter <br> \
            <input type="radio" name="' + course.courseCode + '" value="None" checked="checked"> No Preference <br> \
          </td> \
        </tr>');
        
      numCourses++;
    }
  });
  
  $("#submit").click(function(){
    $.each(sessionStorage, function(key, value){
      if (sessionStorage.hasOwnProperty(key)){
        
        // Get semester preferences
        sessionStorage.setItem(key, $('input[name="' + key + '"]:checked').val());
      }
    });
    location.href='/restrictions'
  });
	
})

var courses =     
{
  courses: [
    {
      courseCode: "CSC302H1",
      name: "Intro to qwer",
      description: "cool course",
      breadths: [5, 1],
      prerequisites: "CSC301H1 or cGPA of 3.0+",
      exclusions: "",
      semesters: ["F", "S"]
    },
    {
      courseCode: "CSC373H1",
      name: "Intro to asdf",
      description: "coolish course",
      breadths: [3],
      prerequisites: "CSC369H1 or cGPA of 3.0+",
      exclusions: "CSC374H1",
      semesters: ["S"]
    },
    {
      courseCode: "CSC300Y1",
      name: "Intro to zxcv",
      description: "course",
      breadths: [],
      prerequisites: "",
      exclusions: "",
      semesters: ["Y"]
    }
  ]
}
