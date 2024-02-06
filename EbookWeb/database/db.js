const Sequelize = require('sequelize');
const sequelize = require('./connect');

const db = {};
db.Sequelize = Sequelize;
db.sequelize = sequelize;

db.BookType = require('../models/BookType.js');
db.Book = require('../models/Book.js');
db.User = require('../models/User');
db.Wishlist = require('../models/Wishlist');
db.Owned = require('../models/Owned');
db.History = require('../models/History');

db.BookType.hasMany(db.Book, {foreignKey:"typeId", as:"book"});
db.Book.belongsTo(db.BookType, {foreignKey:"typeId", as:"booktype"});

db.User.hasMany(db.History, {foreignKey:"userId", as:"history"});
db.History.belongsTo(db.User, {foreignKey:"userId", as:"user"});

db.User.hasMany(db.Owned);
db.Owned.belongsTo(db.User);
db.Book.hasMany(db.Owned);
db.Owned.belongsTo(db.Book);

db.User.hasMany(db.Wishlist);
db.Wishlist.belongsTo(db.User);
db.Book.hasMany(db.Wishlist);
db.Wishlist.belongsTo(db.Book);

module.exports = db;