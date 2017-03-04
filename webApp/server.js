'use strict';

var express = require('express');
var bodyParser = require('body-parser');
var session = require('express-session');
var routes = require('./routes');
var app = express();

app.use(express.static(__dirname));
app.engine('.html', require('ejs').__express);
app.set('views', __dirname);
app.set('view engine', 'html');

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
    extended: true
}));

// Serve full pages
app.get('/', routes.getIndex);
app.get('/restrictions', routes.getRestrictions);
app.get('/result', routes.getResults);

var server = app.listen(3000, function() {
  console.log('Running on 127.0.0.1:%s', server.address().port);
});
