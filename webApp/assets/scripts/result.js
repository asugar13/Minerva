"use strict";

var timetables;

/**
 * Adds the buttons for scrolling through the different timetables for a
 * given configNum.
 *
 * @param {int} configNum
 *
 */
function changeTimetables(configNum){
  $("#fall-timetables").empty();
  $("#winter-timetables").empty();
  
  for (var i = 1; i <= timetables.configurations[configNum].Fall.timetables.length; i++){
    let timetableNum = i - 1;
    
    $("#fall-timetables").append(
      '<button type="button" class="btn btn-default" id="fall-timetable' + i + '">' + i + '</button>'
    );
    
    $("#fall-timetable" + i).click(function () {
      drawTimetable(configNum, timetableNum, "Fall");
    });
  }
  
  for (var i = 1; i <= timetables.configurations[configNum].Winter.timetables.length; i++){
    let timetableNum = i - 1;
    
    $("#winter-timetables").append(
      '<button type="button" class="btn btn-default" id="winter-timetable' + i + '">' + i + '</button>'
    );
    
    $("#winter-timetable" + i).click(function () {
      drawTimetable(configNum, timetableNum, "Winter");
    });
  }
}

/**
 * Adds the buttons for scrolling through the different configurations.
 *
 */
function changeConfigurations(){
  $("#configurations").empty();
  
  for (var i = 1; i <= timetables.configurations.length; i++){
    let configNum = i - 1;
    
    $("#configurations").append(
      '<button type="button" class="btn btn-default" id="config' + i + '">' + i + '</button>'
    );
    
    changeTimetables(0);
    
    $("#config" + i).click(function () {
      changeTimetables(configNum);
      drawTimetable(configNum, 0, "Fall");
      drawTimetable(configNum, 0, "Winter");
    });
  }
  
  if (timetables.configurations.length > 0){
    drawTimetable(0, 0, "Fall");
    drawTimetable(0, 0, "Winter");
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
        request.courses.push({courseCode: key + "Y1", semesters: ["F", "S"]});
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
      
      changeConfigurations();
    }
  });
 }

$(document).ready(function(){
  
  var el = document.getElementById("sort-options");
  var sortable = Sortable.create(el);

  getTimetables();
  
  $("#sort").click(function(){
    getTimetables();
  });
  
  $("#back").click(function(){
    location.href='/'
  });
})
