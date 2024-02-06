const DataType = require('sequelize');
const sequelize = require('../database/connect');
const User = require('./User');
const Book = require('./Book');

const Owned = sequelize.define('owned');

module.exports = Owned;