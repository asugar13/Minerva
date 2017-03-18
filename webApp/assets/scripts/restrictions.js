"use strict";

$(document).ready(function(){
  $("#submit").click(function(){
    $.ajax({
      url: '/admin',
      type: 'POST',
      data: {username: "HEY"},
      success: function(result) {
        location.href='/result'

      }
  });

  $("#skip").click(function(){
    location.href='/result'
  });
})
