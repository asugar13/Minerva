'use strict';


/*********************************************************/
/******************* Serve full pages ********************/

/**
 * Render the index page.
 *
 * @param {object} req request object
 * @param {object} res response object
 */
exports.getIndex = function(req, res){
  res.render('./addCourses');
}
