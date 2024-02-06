const Sequelize = require('sequelize');

const sequelize = new Sequelize(
    'db_ebook',
    'root',
    '',{
        dialect: 'mysql',
        host:'localhost',
        dialectOptions: {
            useUTC: false, // for reading from database
          },
          timezone: '+07:00', // for writing to database
        define:{
            timestamps: false,
            freezeTableName: true
        }
    },
);

testConnect();

async function testConnect(){
    try {
        await sequelize.authenticate();
        console.log('Connection has been established successfully.');
    } catch (error) {
        console.error('Unable to connect to the database:', error);
    }
}

module.exports = sequelize;
global.sequelize = sequelize;
