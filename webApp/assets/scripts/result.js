"use strict";


/*
 * Fills in the given timetable on the results page. 
 * Courses is a list of courses from the API. Section is one of "LEC", "TUT",
 * "PRA".
 */
function fillTable(courses, section, timetable){
  for (var i = 0; i < courses.length; i++){

    var course = courses[i]
    var classes = courses[i].classes[0];
    
    if (classes.hasOwnProperty(section)){
      var times = classes[section].times;
      
      for (var j = 0; j < times.length; j++){
        timetable.addEvent(course.courseCode + ", " + classes[section].classCode, 
                            times[j].day, 
                            new Date(2015, 7, 17, parseInt(times[j].start), 0), 
                            new Date(2015, 7, 17, parseInt(times[j].end), 0));
      }
    }
  }
}

function createEmptyTimetable(){
  var timetable = new Timetable();
  timetable.setScope(9, 21);
  
  timetable.addLocations(['MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY']);
  
  return timetable;
}

$(document).ready(function(){
  
  var fall = createEmptyTimetable();
  var winter = createEmptyTimetable();
  
  var fall1 = timetables.configurations[0].timetables[0].semesters.Fall.courses;
  var winter1 = timetables.configurations[0].timetables[0].semesters.Winter.courses;
  
  var fall2 = timetables.configurations[1].timetables[0].semesters.Fall.courses;
  var winter2 = timetables.configurations[1].timetables[0].semesters.Winter.courses;
  
  fillTable(fall1, "LEC", fall);
  fillTable(fall1, "TUT", fall);
  fillTable(fall1, "PRA", fall);
  fillTable(winter1, "LEC", winter);
  fillTable(winter1, "TUT", winter);
  fillTable(winter1, "PRA", winter);
  
  var renderer = new Timetable.Renderer(fall);
  renderer.draw('#fall-div'); 

  var renderer = new Timetable.Renderer(winter);
  renderer.draw('#winter-div'); 
  
  $("#config1").click(function() {
		addFallTable();
    addWinterTable();
    
    fillTable(fall1, "LEC", fall);
    fillTable(fall1, "TUT", fall);
    fillTable(winter1, "LEC", winter);
    fillTable(winter1, "TUT", winter);
    
    var renderer = new Timetable.Renderer(fall);
    renderer.draw('#fall-div'); 

    var renderer = new Timetable.Renderer(winter);
    renderer.draw('#winter-div'); 
	});
  
  $("#config2").click(function() {
		addFallTable();
    addWinterTable();
    
    fillTable(fall2, "LEC", fall);
    fillTable(fall2, "TUT", fall);
    fillTable(winter2, "LEC", winter);
    fillTable(winter2, "TUT", winter);
    
    var renderer = new Timetable.Renderer(fall);
    renderer.draw('#fall-div'); 

    var renderer = new Timetable.Renderer(winter);
    renderer.draw('#winter-div'); 
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