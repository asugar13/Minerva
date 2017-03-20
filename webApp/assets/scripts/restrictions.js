"use strict";

$(document).ready(function(){
  $("#submit").click(function(){
    $.ajax({
      url: 'http://127.0.0.1:8800/main',
      type: 'GET',
      //data: {"yo": "HEY"},
      crossDomain: true,

      success: function(result) {
        console.log(result);
        //location.href='/result'

      }
  });

  $("#skip").click(function(){
    location.href='/result'
  });
})
})
