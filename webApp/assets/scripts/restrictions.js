"use strict";

$(document).ready(function(){



  $("#submit").click(function(){
    $.ajax({
      url: 'http://127.0.0.1:8400/main',
      type: 'POST',
      crossDomain: true,
      dataType: "json",
      contentType: "application/json; charset=utf-8",
      data: {
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

      success: function(result) {
        console.log(result);
        console.log(typeof result);
        //location.href='/result'

      }
  });

  $("#skip").click(function(){
    location.href='/result'
  });
})
})
