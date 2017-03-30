"use strict";

$(document).ready(function(){

  $("#submit").click(function(){

    $.ajax({
      url: 'http://127.0.0.1:8800/course-information',
      type: 'GET',
      crossDomain: true,

      success: function(result) {
        console.log(JSON.parse(result));
      }
    });

    $.ajax({
      url: 'http://127.0.0.1:8800/generate-timetable',
      type: 'POST',
      crossDomain: true,

      data: JSON.stringify({
          courses: [
              {
                  courseCode: "CSC301H1",
                  semesters: ["F"]
              },
              {
                  courseCode: "CSC302H1",
                  semesters: ["S"]
              },
              {
                  courseCode: "CSC373H1",
                  semesters: ["F", "S"]
              },
              {
                  courseCode: "PSY100Y1",
                  semesters: ["Y"]
              }
          ]
        }),

      success: function(result) {
        console.log(JSON.parse(result));
      }
    });
  });

  $("#skip").click(function(){
    location.href='/result'
  });
})

