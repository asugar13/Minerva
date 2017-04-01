"use strict";

/**
 * Adds the buttons for scrolling through the different timetables for a
 * given configNum.
 *
 * @param {int} configNum
 *
 */
function changeTimetables(configNum){
  $("#timetables").empty();
  
  for (var i = 1; i <= timetables.configurations[configNum].timetables.length; i++){
    let j = i;
    
    $("#timetables").append(
      '<button type="button" class="btn btn-default" id="timetable' + i + '">' + i + '</button>'
    );
    
    $("#timetable" + j).click(function () {
      drawTimetable(configNum, j - 1);
    });
  }
}

/**
 * Draws a fall and winter timetable on the page. Specific timetable is given 
 * the comnination of the configNum and timetableNum.
 *
 * @param {int} configNum
 * @param {int} timetableNum
 *
 */
function drawTimetable(configNum, timetableNum){
  
  var fall = createEmptyTimetable();
  var winter = createEmptyTimetable();
  
  var fallCourses = timetables.configurations[configNum].timetables[timetableNum].semesters.Fall.courses;
  var winterCourses = timetables.configurations[configNum].timetables[timetableNum].semesters.Winter.courses;

  fillTable(fallCourses, fall);
  fillTable(winterCourses, winter);

  $("#fall-div").empty();
  $("#winter-div").empty();
  
  var renderer = new Timetable.Renderer(fall);
  renderer.draw('#fall-div'); 

  var renderer = new Timetable.Renderer(winter);
  renderer.draw('#winter-div'); 
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

$(document).ready(function(){
  
  var el = document.getElementById('items');
  var sortable = Sortable.create(el);
  
  var request = {
    filters: [],
    courses: []
  }
  
  $.each(sessionStorage, function(key, value){
    if (sessionStorage.hasOwnProperty(key)){
      
      // Get semester preferences
      var semesters = sessionStorage.getItem(key);
      
      if (semesters == "Y") {
        request.courses.push({courseCode: key, semesters: ["Y"]});
      } else if (semesters == "F") {
        request.courses.push({courseCode: key, semesters: ["F"]});
      } else if (semesters == "S") {
        request.courses.push({courseCode: key, semesters: ["S"]});
      } else {
        request.courses.push({courseCode: key, semesters: ["F", "S"]});
      }
    }
  });
  
  console.log("Request:")
  console.log(request);
  
  $.ajax({
    url: 'http://127.0.0.1:8800/generate-timetable',
    type: 'POST',
    crossDomain: true,

    data: JSON.stringify(request),

    success: function(result) {
      console.log("Response:")
      console.log(JSON.parse(result));
    }
  });
  
  // Add the buttons for scrolling through different configurations.
  for (var i = 1; i <= timetables.configurations.length; i++){
    let j = i;
    
    $("#configurations").append(
      '<button type="button" class="btn btn-default" id="config' + i + '">' + i + '</button>'
    );
    
    changeTimetables(0);
    
    $("#config" + j).click(function () {
      changeTimetables(j - 1);
      drawTimetable(j - 1, 0);
    });
  }
  
  drawTimetable(0, 0);
  
  $("#back").click(function(){
    location.href='/'
  });
})

var timetables = 
    {
        configurations: [
            {
                configurationNumber: 0,
                timetables: [ //possible timetables
                    {
                        semesters: {
                            Fall: {
                                courses: [
                                    {
                                        courseCode: "PSY100Y1",
                                        classes: [
                                            {
                                                LEC: {
                                                    classCode: "L0101",
                                                    times: [
                                                        {
                                                            day: "MONDAY",
                                                            start: "13",
                                                            end: "15"
                                                        },
                                                        {
                                                            day: "WEDNESDAY",
                                                            start: "13",
                                                            end: "14"
                                                        }
                                                    ]
                                                },
                                                TUT: {
                                                    classCode: "T0101",
                                                    times: [
                                                        {
                                                            day: "FRIDAY",
                                                            start: "13",
                                                            end: "14"
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
                                                PRA: {
                                                    classCode: "P0101",
                                                    times: [
                                                        {
                                                            day: "FRIDAY",
                                                            start: "14",
                                                            end: "15"
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
                                        courseCode: "PSY100Y1",
                                        classes: [
                                            {
                                                LEC: {
                                                    classCode: "L0101",
                                                    times: [
                                                        {
                                                            day: "MONDAY",
                                                            start: "13",
                                                            end: "15"
                                                        },
                                                        {
                                                            day: "WEDNESDAY",
                                                            start: "13",
                                                            end: "14"
                                                        }
                                                    ]
                                                },
                                                TUT: {
                                                    classCode: "T0101",
                                                    times: [
                                                        {
                                                            day: "FRIDAY",
                                                            start: "13",
                                                            end: "14"
                                                        }
                                                    ]
                                                }
                                            }
                                        ]
                                    },
                                    {
                                        courseCode: "CSC302H1",
                                        classes: [
                                            {
                                                LEC: {
                                                    classCode: "L0101",
                                                    times: [
                                                        {
                                                            day: "MONDAY",
                                                            start: "11",
                                                            end: "13"
                                                        },
                                                        {
                                                            day: "WEDNESDAY",
                                                            start: "12",
                                                            end: "13"
                                                        }
                                                    ]
                                                },
                                                TUT: {
                                                    classCode: "T0101",
                                                    times: [
                                                        {
                                                            day: "FRIDAY",
                                                            start: "12",
                                                            end: "13"
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
                                                PRA: {
                                                    classCode: "P0101",
                                                    times: [
                                                        {
                                                            day: "FRIDAY",
                                                            start: "14",
                                                            end: "15"
                                                        }
                                                    ]
                                                }
                                            }
                                        ]
                                    }
                                ]
                            }
                        }
                    },
                    {
                        semesters: {
                            Fall: {
                                courses: [
                                    {
                                        courseCode: "PSY100Y1",
                                        classes: [
                                            {
                                                LEC: {
                                                    classCode: "L0102",
                                                    times: [
                                                        {
                                                            day: "TUESDAY",
                                                            start: "13",
                                                            end: "15"
                                                        },
                                                        {
                                                            day: "THURSDAY",
                                                            start: "13",
                                                            end: "14"
                                                        },
                                                    ]
                                                },
                                                TUT: {
                                                    classCode: "T0101",
                                                    times: [
                                                        {
                                                            day: "FRIDAY",
                                                            start: "13",
                                                            end: "14"
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
                                                PRA: {
                                                    classCode: "P0101",
                                                    times: [
                                                        {
                                                            day: "FRIDAY",
                                                            start: "14",
                                                            end: "15"
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
                                        courseCode: "PSY100Y1",
                                        classes: [
                                            {
                                                LEC: {
                                                    classCode: "L0101",
                                                    times: [
                                                        {
                                                            day: "MONDAY",
                                                            start: "13",
                                                            end: "15"
                                                        },
                                                        {
                                                            day: "WEDNESDAY",
                                                            start: "13",
                                                            end: "14"
                                                        }
                                                    ]
                                                },
                                                TUT: {
                                                    classCode: "T0101",
                                                    times: [
                                                        {
                                                            day: "FRIDAY",
                                                            start: "13",
                                                            end: "14"
                                                        }
                                                    ]
                                                }
                                            }
                                        ]
                                    },
                                    {
                                        courseCode: "CSC302H1",
                                        classes: [
                                            {
                                                LEC: {
                                                    classCode: "L0101",
                                                    times: [
                                                        {
                                                            day: "MONDAY",
                                                            start: "11",
                                                            end: "13"
                                                        },
                                                        {
                                                            day: "WEDNESDAY",
                                                            start: "12",
                                                            end: "13"
                                                        }
                                                    ]
                                                },
                                                TUT: {
                                                    classCode: "T0101",
                                                    times: [
                                                        {
                                                            day: "FRIDAY",
                                                            start: "12",
                                                            end: "13"
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
                                                PRA: {
                                                    classCode: "P0101",
                                                    times: [
                                                        {
                                                            day: "FRIDAY",
                                                            start: "14",
                                                            end: "15"
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
                timetables: [ //possible timetables
                    {
                        semesters: {
                            Fall: {
                                courses: [
                                    {
                                        courseCode: "PSY100Y1",
                                        classes: [
                                            {
                                                LEC: {
                                                    classCode: "L0101",
                                                    times: [
                                                        {
                                                            day: "MONDAY",
                                                            start: "13",
                                                            end: "15"
                                                        },
                                                        {
                                                            day: "WEDNESDAY",
                                                            start: "13",
                                                            end: "14"
                                                        }
                                                    ]
                                                },
                                                TUT: {
                                                    classCode: "T0101",
                                                    times: [
                                                        {
                                                            day: "FRIDAY",
                                                            start: "13",
                                                            end: "14"
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
                                                PRA: {
                                                    classCode: "P0101",
                                                    times: [
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
                                        courseCode: "CSC373H1",
                                        classes: [
                                            {
                                                LEC: {
                                                    classCode: "L0101",
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
                                                PRA: {
                                                    classCode: "P0101",
                                                    times: [
                                                        {
                                                            day: "FRIDAY",
                                                            start: "14",
                                                            end: "15"
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
                                        courseCode: "PSY100Y1",
                                        classes: [
                                            {
                                                LEC: {
                                                    classCode: "L0101",
                                                    times: [
                                                        {
                                                            day: "MONDAY",
                                                            start: "13",
                                                            end: "15"
                                                        },
                                                        {
                                                            day: "WEDNESDAY",
                                                            start: "13",
                                                            end: "14"
                                                        }
                                                    ]
                                                },
                                                TUT: {
                                                    classCode: "T0101",
                                                    times: [
                                                        {
                                                            day: "FRIDAY",
                                                            start: "13",
                                                            end: "14"
                                                        }
                                                    ]
                                                }
                                            }
                                        ]
                                    },
                                    {
                                        courseCode: "CSC302H1",
                                        classes: [
                                            {
                                                LEC: {
                                                    classCode: "L0101",
                                                    times: [
                                                        {
                                                            day: "MONDAY",
                                                            start: "11",
                                                            end: "13"
                                                        },
                                                        {
                                                            day: "WEDNESDAY",
                                                            start: "12",
                                                            end: "13"
                                                        }
                                                    ]
                                                },
                                                TUT: {
                                                    classCode: "T0101",
                                                    times: [
                                                        {
                                                            day: "FRIDAY",
                                                            start: "12",
                                                            end: "13"
                                                        }
                                                    ]
                                                }
                                            }
                                        ]
                                    }
                                ]
                            }
                        }
                    },
                    {
                        semesters: {
                            Fall: {
                                courses: [
                                    {
                                        courseCode: "PSY100Y1",
                                        classes: [
                                            {
                                                LEC: {
                                                    classCode: "L0102",
                                                    times: [
                                                        {
                                                            day: "TUESDAY",
                                                            start: "13",
                                                            end: "15"
                                                        },
                                                        {
                                                            day: "THURSDAY",
                                                            start: "13",
                                                            end: "14"
                                                        }
                                                    ]
                                                },
                                                TUT: {
                                                    classCode: "T0101",
                                                    times: [
                                                        {
                                                            day: "FRIDAY",
                                                            start: "13",
                                                            end: "14"
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
                                                PRA: {
                                                    classCode: "P0101",
                                                    times: [
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
                                        courseCode: "CSC373H1",
                                        classes: [
                                            {
                                                LEC: {
                                                    classCode: "L0101",
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
                                                PRA: {
                                                    classCode: "P0101",
                                                    times: [
                                                        {
                                                            day: "FRIDAY",
                                                            start: "14",
                                                            end: "15"
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
                                        courseCode: "PSY100Y1",
                                        classes: [
                                            {
                                                LEC: {
                                                    classCode: "L0101",
                                                    times: [
                                                        {
                                                            day: "MONDAY",
                                                            start: "13",
                                                            end: "15"
                                                        },
                                                        {
                                                            day: "WEDNESDAY",
                                                            start: "13",
                                                            end: "14"
                                                        }
                                                    ]
                                                },
                                                TUT: {
                                                    classCode: "T0101",
                                                    times: [
                                                        {
                                                            day: "FRIDAY",
                                                            start: "13",
                                                            end: "14"
                                                        }
                                                    ]
                                                }
                                            }
                                        ]
                                    },
                                    {
                                        courseCode: "CSC302H1",
                                        classes: [
                                            {
                                                LEC: {
                                                    classCode: "L0101",
                                                    times: [
                                                        {
                                                            day: "MONDAY",
                                                            start: "11",
                                                            end: "13"
                                                        },
                                                        {
                                                            day: "WEDNESDAY",
                                                            start: "12",
                                                            end: "13"
                                                        }
                                                    ]
                                                },
                                                TUT: {
                                                    classCode: "T0101",
                                                    times: [
                                                        {
                                                            day: "FRIDAY",
                                                            start: "12",
                                                            end: "13"
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