"use strict";

function addFallTable (){
  $("#fall-table").remove();
  $("#fall-div").append('<table class="table" id="fall-table"> \
                          <tr> \
                            <th></th> \
                            <th>Monday</th> \
                            <th>Tuesday</th> \
                            <th>Wednesday</th> \
                            <th>Thursday</th> \
                            <th>Friday</th> \
                          </tr> \
                        </table>');
                        
  for (var i = 8; i <= 21; i++){
    var fall_row = "<tr> \
                      <th>" + i + ":00</th> \
                      <td id='Fall-MONDAY-" + i + "'></td> \
                      <td id='Fall-TUESDAY-" + i + "'></td> \
                      <td id='Fall-WEDNESDAY-" + i + "'></td> \
                      <td id='Fall-THURSDAY-" + i + "'></td> \
                      <td id='Fall-FRIDAY-" + i + "'></td> \
                    </tr>"
            
    $("#fall-table").append(fall_row);
  }
}

function addWinterTable (){
  $("#winter-table").remove();
  $("#winter-div").append('<table class="table" id="winter-table"> \
                            <tr> \
                              <th></th> \
                              <th>Monday</th> \
                              <th>Tuesday</th> \
                              <th>Wednesday</th> \
                              <th>Thursday</th> \
                              <th>Friday</th> \
                            </tr> \
                          </table>');
                          
  
  for (var i = 8; i <= 21; i++){
      var winter_row = "<tr> \
                          <th>" + i + ":00</th> \
                          <td id='Winter-MONDAY-" + i + "'></td> \
                          <td id='Winter-TUESDAY-" + i + "'></td> \
                          <td id='Winter-WEDNESDAY-" + i + "'></td> \
                          <td id='Winter-THURSDAY-" + i + "'></td> \
                          <td id='Winter-FRIDAY-" + i + "'></td> \
                        </tr>"
      $("#winter-table").append(winter_row);
  }                         
}

/*
 * Fills in the timetables on the results page. 
 * Courses is a list of courses from the API. Section is one of "LEC", "TUT",
 * "PRA". Semester is one of "Fall" or "Winter"
 */
function fillTable(courses, section, semester){
  for (var i = 0; i < courses.length; i++){

    var course = courses[i]
    var classes = courses[i].classes[0];
    
    if (classes.hasOwnProperty(section)){
      var times = classes[section].times;
      
      for (var j = 0; j < times.length; j++){
        var id = "#" + semester + "-" + times[j].day + "-" + times[j].start;
        console.log(id);
        $(id).append("<p><b>" + course.courseCode + "</b></p>" + 
                      "<p>" + classes[section].classCode + "</p>" + 
                      "<p>" + times[j].start + ":00-" + times[j].end + ":00</p>" );
        $(id).addClass("filled" + i)
        $(id).attr("rowspan", parseInt(times[j].end) - parseInt(times[j].start))
      }
    }
  }
}

$(document).ready(function(){
  addFallTable();
  addWinterTable();
  
  var fall1 = timetables.configurations[0].timetables[0].semesters.Fall.courses;
  var winter1 = timetables.configurations[0].timetables[0].semesters.Winter.courses;
  
  var fall2 = timetables.configurations[1].timetables[0].semesters.Fall.courses;
  var winter2 = timetables.configurations[1].timetables[0].semesters.Winter.courses;
  
  fillTable(fall1, "LEC", "Fall");
  fillTable(fall1, "TUT", "Fall");
  fillTable(winter1, "LEC", "Winter");
  fillTable(winter1, "TUT", "Winter");
  
  $("#config1").click(function() {
		addFallTable();
    addWinterTable();
    
    fillTable(fall1, "LEC", "Fall");
    fillTable(fall1, "TUT", "Fall");
    fillTable(winter1, "LEC", "Winter");
    fillTable(winter1, "TUT", "Winter");
	});
  
  $("#config2").click(function() {
		addFallTable();
    addWinterTable();
    
    fillTable(fall2, "LEC", "Fall");
    fillTable(fall2, "TUT", "Fall");
    fillTable(winter2, "LEC", "Winter");
    fillTable(winter2, "TUT", "Winter");
	});
  
  $("#back").click(function(){
    location.href='/'
  });
})

var timetables = 
{
    configurations: [
        {
            configurationNumber: 0,
            timetables: [ 
                {
                    semesters: {
                        Fall: {
                            courses: [
                                {
                                    courseCode: "CSC309H1",
                                    classes: [
                                        {
                                            LEC: {
                                                classCode: "L0201",
                                                times: [
                                                    {
                                                        day: "MONDAY",
                                                        start: "15",
                                                        end: "16"
                                                    },
                                                    {
                                                        day: "WEDNESDAY",
                                                        start: "15",
                                                        end: "16"
                                                    }
                                                ]
                                            },
                                            TUT: {
                                                classCode: "T0201",
                                                times: [
                                                    {
                                                        day: "FRIDAY",
                                                        start: "15",
                                                        end: "16"
                                                    }
                                                ]
                                            }
                                        }
                                    ]
                                },
                                {
                                    courseCode: "CSC369H1",
                                    classes: [
                                        {
                                            LEC: {
                                                classCode: "L0101",
                                                times: [
                                                    {
                                                        day: "MONDAY",
                                                        start: "14",
                                                        end: "15"
                                                    },
                                                    {
                                                        day: "WEDNESDAY",
                                                        start: "14",
                                                        end: "15"
                                                    },
                                                    {
                                                        day: "FRIDAY",
                                                        start: "14",
                                                        end: "15"
                                                    }
                                                ]
                                            }
                                        }
                                    ]
                                },
                                {
                                    courseCode: "CSC324H1",
                                    classes: [
                                        {
                                            LEC: {
                                                classCode: "L0101",
                                                times: [
                                                    {
                                                        day: "MONDAY",
                                                        start: "11",
                                                        end: "12"
                                                    },
                                                    {
                                                        day: "WEDNESDAY",
                                                        start: "11",
                                                        end: "12"
                                                    },
                                                    {
                                                        day: "FRIDAY",
                                                        start: "11",
                                                        end: "12"
                                                    }
                                                ]
                                            }
                                        }
                                    ]
                                },
                                {
                                    courseCode: "CSC373H1",
                                    classes: [
                                        {
                                            LEC: {
                                                classCode: "L0101",
                                                times: [
                                                    {
                                                        day: "MONDAY",
                                                        start: "10",
                                                        end: "11"
                                                    },
                                                    {
                                                        day: "WEDNESDAY",
                                                        start: "10",
                                                        end: "11"
                                                    },
                                                    {
                                                        day: "FRIDAY",
                                                        start: "10",
                                                        end: "11"
                                                    }
                                                ]
                                            }
                                        }
                                    ]
                                }
                            ]
                        }, 
                        Winter: {
                            courses: [
                                {   
                                    courseCode: "GGR273H1",
                                    classes: [
                                        {
                                            LEC: {
                                                classCode: "L0101",
                                                times: [
                                                    {
                                                        day: "TUESDAY",
                                                        start: "10",
                                                        end: "12"
                                                    }
                                                ]
                                            }
                                        }
                                    ]
                                },
                                {
                                    courseCode: "CSC301H1",
                                    classes: [
                                        {
                                            LEC: {
                                                classCode: "L0101",
                                                times: [
                                                    {
                                                        day: "TUESDAY",
                                                        start: "12",
                                                        end: "14"
                                                    },
                                                    {
                                                        day: "THURSDAY",
                                                        start: "13",
                                                        end: "14"
                                                    }
                                                ]
                                            }
                                        }
                                    ]
                                },
                                {
                                    courseCode: "CSC358H1",
                                    classes: [
                                        {
                                            LEC: {
                                                classCode: "L0201",
                                                times: [
                                                    {
                                                        day: "WEDNESDAY",
                                                        start: "10",
                                                        end: "11"
                                                    },
                                                    {
                                                        day: "THURSDAY",
                                                        start: "15",
                                                        end: "17"
                                                    }
                                                ]
                                            }
                                        }
                                    ]
                                },
                                {
                                    courseCode: "CSC320H1",
                                    classes: [
                                        {
                                            LEC: {
                                                classCode: "L0101",
                                                times: [
                                                    {
                                                        day: "WEDNESDAY",
                                                        start: "18",
                                                        end: "21"
                                                    }
                                                ]
                                            }
                                        }
                                    ]
                                }
                            ]
                        }
                    }
                }
            ]
        },
        {
            configurationNumber: 1,
            timetables: [ 
                {
                    semesters: {
                        Fall: {
                            courses: [
                                {
                                    courseCode: "CSC309H1",
                                    classes: [
                                        {
                                            LEC: {
                                                classCode: "L0201",
                                                times: [
                                                    {
                                                        day: "MONDAY",
                                                        start: "15",
                                                        end: "16"
                                                    },
                                                    {
                                                        day: "WEDNESDAY",
                                                        start: "15",
                                                        end: "16"
                                                    }
                                                ]
                                            },
                                            TUT: {
                                                classCode: "T0201",
                                                times: [
                                                    {
                                                        day: "FRIDAY",
                                                        start: "15",
                                                        end: "16"
                                                    }
                                                ]
                                            }
                                        }
                                    ]
                                },
                                {
                                    courseCode: "CSC369H1",
                                    classes: [
                                        {
                                            LEC: {
                                                classCode: "L0101",
                                                times: [
                                                    {
                                                        day: "MONDAY",
                                                        start: "18",
                                                        end: "21"
                                                    }
                                                ]
                                            }
                                        }
                                    ]
                                },
                                {
                                    courseCode: "CSC324H1",
                                    classes: [
                                        {
                                            LEC: {
                                                classCode: "L0101",
                                                times: [
                                                    {
                                                        day: "MONDAY",
                                                        start: "11",
                                                        end: "12"
                                                    },
                                                    {
                                                        day: "WEDNESDAY",
                                                        start: "11",
                                                        end: "12"
                                                    },
                                                    {
                                                        day: "FRIDAY",
                                                        start: "11",
                                                        end: "12"
                                                    }
                                                ]
                                            }
                                        }
                                    ]
                                },
                                {
                                    courseCode: "CSC373H1",
                                    classes: [
                                        {
                                            LEC: {
                                                classCode: "L0101",
                                                times: [
                                                    {
                                                        day: "MONDAY",
                                                        start: "10",
                                                        end: "11"
                                                    },
                                                    {
                                                        day: "WEDNESDAY",
                                                        start: "10",
                                                        end: "11"
                                                    },
                                                    {
                                                        day: "FRIDAY",
                                                        start: "10",
                                                        end: "11"
                                                    }
                                                ]
                                            }
                                        }
                                    ]
                                }
                            ]
                        }, 
                        Winter: {
                            courses: [
                                {   
                                    courseCode: "GGR273H1",
                                    classes: [
                                        {
                                            LEC: {
                                                classCode: "L0101",
                                                times: [
                                                    {
                                                        day: "TUESDAY",
                                                        start: "10",
                                                        end: "12"
                                                    }
                                                ]
                                            }
                                        }
                                    ]
                                },
                                {
                                    courseCode: "CSC301H1",
                                    classes: [
                                        {
                                            LEC: {
                                                classCode: "L0101",
                                                times: [
                                                    {
                                                        day: "TUESDAY",
                                                        start: "12",
                                                        end: "14"
                                                    },
                                                    {
                                                        day: "THURSDAY",
                                                        start: "13",
                                                        end: "14"
                                                    }
                                                ]
                                            }
                                        }
                                    ]
                                },
                                {
                                    courseCode: "CSC358H1",
                                    classes: [
                                        {
                                            LEC: {
                                                classCode: "L0201",
                                                times: [
                                                    {
                                                        day: "WEDNESDAY",
                                                        start: "10",
                                                        end: "11"
                                                    },
                                                    {
                                                        day: "THURSDAY",
                                                        start: "15",
                                                        end: "17"
                                                    }
                                                ]
                                            }
                                        }
                                    ]
                                },
                                {
                                    courseCode: "CSC320H1",
                                    classes: [
                                        {
                                            LEC: {
                                                classCode: "L0101",
                                                times: [
                                                    {
                                                        day: "WEDNESDAY",
                                                        start: "18",
                                                        end: "21"
                                                    }
                                                ]
                                            }
                                        }
                                    ]
                                }
                            ]
                        }
                    }
                }
            ]
        }
    ]
}