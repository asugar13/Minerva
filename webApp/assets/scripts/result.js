"use strict";

var timetables;

// The current configuration and timetable indexes the user is on
var configNum;
var fallTimetableNum;
var winterTimetableNum;

/**
 * Adds the buttons for scrolling through the different timetables.
 *
 */
function changeTimetables(){
	$("#cur-config").html(configNum + 1);
	$("#max-config").html(timetables.configurations.length);
	
	$("#cur-fall").html(fallTimetableNum + 1);
	$("#cur-winter").html(winterTimetableNum + 1);
	
	$("#max-fall").html(timetables.configurations[configNum].Fall.timetables.length);
	$("#max-winter").html(timetables.configurations[configNum].Winter.timetables.length);
	
	if (timetables.configurations.length > 0){
    drawTimetable(configNum, fallTimetableNum, "Fall");
    drawTimetable(configNum, winterTimetableNum, "Winter");
  }
}

/**
 * Draws a specified (Fall or Winter) timetable on the page. Specific timetable is given 
 * the combination of the configNum and timetableNum.
 *
 * @param {int} configNum
 * @param {int} timetableNum
 *
 */
function drawTimetable(configNum, timetableNum, semester){
  var timetable = createEmptyTimetable();
  var courses = timetables.configurations[configNum][semester].timetables[timetableNum].courses;

  fillTable(courses, timetable);

  $("#" + semester.toLowerCase() + "-div").empty();
  var renderer = new Timetable.Renderer(timetable);
  renderer.draw("#" + semester.toLowerCase() + "-div"); 
}

/**
 * Sets up an empty timtable with the Timetable.js plugin.
 *
 */
function createEmptyTimetable(){
  var timetable = new Timetable();
  timetable.setScope(9, 21);
  
  timetable.addLocations(['MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY']);
  
  return timetable;
}

/**
 * Fills the given timetable with the given courses.
 *
 * @param {object} courses: Formatted as defined in return of 
 *                          httpObjectExamples/generate-timetable.txt
 * @param {Timetable} timetable: Timetable.js Timetable object
 *
 */
function fillTable(courses, timetable){
  for (var i = 0; i < courses.length; i++){

    var course = courses[i]
    var classes = courses[i].classes[0];
    var section = ["LEC", "TUT", "PRA"];
    
    for (var k = 0; k < 3; k++){
      if (classes.hasOwnProperty(section[k])){
        var times = classes[section[k]].times;
        
        for (var j = 0; j < times.length; j++){
          
          timetable.addEvent(course.courseCode + ", " + classes[section[k]].classCode, 
                              times[j].day, 
                              new Date(2015, 7, 17, parseInt(times[j].start), 0), 
                              new Date(2015, 7, 17, parseInt(times[j].end), 0));
        }
      }
    }
  }
}

/**
 * Returns a generate-timetable request object based on sessionStorage data
 * and data on the page.
 *
 */
function getRequest(){
  var request = {
    filters: [],
    courses: []
  }
  
  // Courses and semester preferences
  $.each(sessionStorage, function(key, value){
    if (sessionStorage.hasOwnProperty(key)){
      
      // Get semester preferences
      var semesters = sessionStorage.getItem(key);
      
      if (semesters == "None") {
        request.courses.push({courseCode: key + "H1", semesters: ["F", "S"]});
      } else if (semesters == "Y"){
        request.courses.push({courseCode: key + "Y1", semesters: [semesters]});
      } else {
        request.courses.push({courseCode: key + "H1", semesters: [semesters]});
      }
    }
  });
  
  // Sorting options
  var children = document.getElementById("sort-options").children;
  for (var i = 0; i < children.length; i++) {
    var id = children[i].getAttribute("id");
    var value = $('input[name="' + id + '"]:checked').val();
    
    if (value != "None"){
      request.filters.push(value);
    }
  }
  
  return request;
}

/**
 * Get timetables from generate-timetable endpoint and display them on the page.
 *
 */
 function getTimetables(){
  var request = getRequest();
    
  console.log("Request:")
  console.log(request);
  
  $.ajax({
    url: 'http://127.0.0.1:8800/generate-timetable',
    type: 'POST',
    crossDomain: true,

    data: JSON.stringify(request),

    success: function(result) {
      timetables = JSON.parse(result);
      
      console.log("Response:")
      console.log(timetables);
      
			configNum = 0;
			fallTimetableNum = 0;
			winterTimetableNum = 0;
			
			changeTimetables();
    }
  });
 }

$(document).ready(function(){
  
  var el = document.getElementById("sort-options");
  var sortable = Sortable.create(el);

  getTimetables();
	
	$("#config-back").click(function(){
    if (configNum > 0){
			configNum--;

			fallTimetableNum = 0;
			winterTimetableNum = 0;
			changeTimetables();
		}
  });
	
	$("#config-next").click(function(){
    if (configNum < timetables.configurations.length - 1){
			configNum++;
			
			fallTimetableNum = 0;
			winterTimetableNum = 0;
			changeTimetables();
		}
  });
	
	$("#fall-back").click(function(){
    if (fallTimetableNum > 0){
			fallTimetableNum--;
			$("#cur-fall").html(fallTimetableNum + 1);
			drawTimetable(configNum, fallTimetableNum, "Fall");
		}
  });
	
	$("#fall-next").click(function(){
		if (fallTimetableNum < timetables.configurations[configNum].Fall.timetables.length - 1){
			fallTimetableNum++;
			$("#cur-fall").html(fallTimetableNum + 1);
			drawTimetable(configNum, fallTimetableNum, "Fall");
		}
  });
	
	$("#winter-back").click(function(){
    if (winterTimetableNum > 0){
			winterTimetableNum--;
			$("#cur-winter").html(winterTimetableNum + 1);
			drawTimetable(configNum, winterTimetableNum, "Winter");
		}
  });
	
	$("#winter-next").click(function(){
		if (winterTimetableNum < timetables.configurations[configNum].Winter.timetables.length - 1){
			winterTimetableNum++;
			$("#cur-winter").html(winterTimetableNum + 1);
			drawTimetable(configNum, winterTimetableNum, "Winter");
		}
  });
  
  $("#sort").click(function(){
    getTimetables();
  });
  
  $("#back").click(function(){
    location.href='/'
  });
})
