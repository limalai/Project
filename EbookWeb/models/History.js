const DataType = require('sequelize');
const sequelize = require('../database/connect');

const History = sequelize.define( "history", {
    id:{
        type: DataType.INTEGER(10).ZEROFILL,
        allowNull: false,
        autoIncrement: true,
        primaryKey: true,
    },
    date:{
        type: DataType.DATEONLY,
        defaultValue: DataType.NOW
    },
    time:{
        type: DataType.TIME,
        defaultValue: DataType.NOW
    },
    coin:{
        type: DataType.INTEGER.UNSIGNED,
        allowNull: false,       
    },
    paymentAmout:{
        type: DataType.INTEGER.UNSIGNED,
        allowNull: false
    }
},
{

});

module.exports = History;