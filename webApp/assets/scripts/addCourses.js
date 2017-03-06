"use strict";

var numCourses = 0;

$(document).ready(function(){
  $("#name").val("");
  
  $("#go").click(function () {
    $("#createElements").addClass("hidden");
    $("#reviewElements").html("");
    $("#reviewElements").removeClass();
    
    var course = $("#course-ID").val();
    var name = "restrictions" + numCourses;
    
    $('#course-table').append(
      '<tr> \
        <td>' + course + '</td> \
        <td> \
          <input type="radio" name="' + name + '"> Fall <br> \
          <input type="radio" name="' + name + '"> Winter <br> \
          <input type="radio" name="' + name + '"> No Preference <br> \
        </td> \
      </tr>');
      
    numCourses++;
  });
  
  $("#submit").click(function(){
    location.href='/restrictions'
  });
	
})