const DataType = require('sequelize');
const sequelize = require('../database/connect');

const User = sequelize.define('user', {
    id: {
        type: DataType.UUID,
        allowNull: false,
        primaryKey: true,
        defaultValue: DataType.UUIDV4,
    },
    email: {
        type: DataType.STRING,
        allowNull: false,
        unique: true,
        isEmail: true,
    },
    password: {
        type: DataType.STRING,
        allowNull: false,
        min: 8,
    },
    name: {
        type: DataType.STRING,
        allowNull: false,
    },
    phone: {
        type: DataType.CHAR(10),
    },
    coin: {
        type: DataType.INTEGER.UNSIGNED,
        allowNull: false,
        defaultValue: 0,
    },
    gender: {
        type: DataType.STRING
    }
},
{

});

module.exports = User;