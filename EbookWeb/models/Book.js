const DataType = require('sequelize');
const sequelize = require('../database/connect');

 const Book = sequelize.define( "book", {
    id:{
        type: DataType.UUID,
        allowNull: false,
        primaryKey: true,
        defaultValue: DataType.UUIDV4
    },
    name: {
        type: DataType.STRING,
        allowNull: false,
    },
    price: {
        type: DataType.INTEGER.UNSIGNED,
        allowNull: false,
    },
    description: {
        type: DataType.TEXT
    },
    no_page: {
        type: DataType.INTEGER.UNSIGNED
    },
    image_url: {
        type: DataType.TEXT
    },
    file_url: {
        type: DataType.TEXT,
    }
    
},
{
    
});

module.exports = Book;