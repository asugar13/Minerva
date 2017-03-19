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

/**
 * Display any courses already selected by the user in this session.
 *
 * For when the user refreshes the page or clicks the Let's redesign button 
 * on the results page.
 *
 */
function redesign(){
  $.each(sessionStorage, function(key, value){
    if (sessionStorage.hasOwnProperty(key)){
      var course = searchCourses(key);
      addCourseEntry(course);
    }
  });
}

/**
 * Add a new entry to the selected courses table. Includes course description
 * in the first column and semester restrictions in the second column.
 *
 * @param {object} course
 */
function addCourseEntry(course){
  var removeButton =   
  '<td><button type="button" class="btn btn-default" id="' + course.courseCode + '-remove"> \
    <span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span> \
  </button></td></tr>';
  
  var description = '<tr id="' + course.courseCode + '"> \
        <td>' + formatCourseDescription(course) + '</td>';

  if (course.semesters[0] == "Y"){
    $('#course-table').append(
      description + 
      '<td> \
        <input type="radio" name="' + course.courseCode + '" value="None" checked="checked"> No Preference <br> \
      </td>' + 
      removeButton);
  } else {
    $('#course-table').append(
      description + 
      '<td> \
        <input type="radio" name="' + course.courseCode + '" value="F"> Fall <br> \
        <input type="radio" name="' + course.courseCode + '" value="S"> Winter <br> \
        <input type="radio" name="' + course.courseCode + '" value="None" checked="checked"> No Preference <br> \
      </td>' + 
      removeButton);
  }
  
  $("#" + course.courseCode + "-remove").click(function () {
    $("#" + course.courseCode).remove();
    sessionStorage.removeItem(course.courseCode);
  });
  
  $('input[name="' + course.courseCode + '"][value="' + sessionStorage.getItem(course.courseCode) + '"]').prop("checked", true);;
    
  numCourses++;
}

$(document).ready(function(){
  redesign();
  
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
      sessionStorage.setItem(course.courseCode, 'None');
      addCourseEntry(course);
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
