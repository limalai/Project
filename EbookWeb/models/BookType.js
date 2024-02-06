const DataType = require('sequelize');
const sequelize = require('../database/connect');

const BookType = sequelize.define( "book_type", {
    id:{
        type: DataType.INTEGER(5).ZEROFILL,
        allowNull: false,
        autoIncrement: true,
        primaryKey: true,
    },
    name: {
        type: DataType.STRING,
        allowNull: false,
        unique: true,
        
    },
},
{

});

module.exports = BookType;