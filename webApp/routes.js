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

/**
 * Render the restrictions page.
 *
 * @param {object} req request object
 * @param {object} res response object
 */
exports.getRestrictions = function(req, res){
  res.render('./restrictions');
}

/**
 * Render the results page.
 *
 * @param {object} req request object
 * @param {object} res response object
 */
exports.getResults = function(req, res){
  res.render('./result');
}