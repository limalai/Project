const DataType = require('sequelize');
const sequelize = require('../database/connect');
const User = require('./User');
const Book = require('./Book');

const Wishlist = sequelize.define('wishlist');

module.exports = Wishlist;