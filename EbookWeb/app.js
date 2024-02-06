const express = require("express");
const app = express();
const session = require("express-session");
const path = require('path');
const adminRouter = require('./routes/adminRouter');
const loginRouter = require('./routes/loginRouter');
const indexRouter = require('./routes/indexRouter');
const profileRouter = require('./routes/profileRouter');
const myBookRouter = require('./routes/myBookRouter');
const productRouter = require('./routes/productRouter');
const coinRouter = require('./routes/coinRouter');
const readRouter = require('./routes/readRouter');


const db = require('./database/db');

const myRouter = require('./routes/myRouter');

//set view engine is ejs
app.set('views', path.join(__dirname, "views"));
app.set('view engine', 'ejs');
//use post method
app.use(express.urlencoded({extended:false}));
//set static file is public
app.use(express.static('public'));
//use session
app.use(session({
    secret: "session",
    resave: false,
    saveUninitialized: false,
    maxAge: 3600*1000 // 1 hour
}));
//sequelize sync
//db.sequelize.sync({force:true});
db.sequelize.sync();

//use routes
app.use(adminRouter);
app.use(loginRouter);
app.use(indexRouter);
app.use(profileRouter);
app.use(myBookRouter);
app.use(productRouter);
app.use(coinRouter);
app.use(readRouter);


app.use(myRouter);

//set running port.
const port = 3000;
app.listen(port, ()=>{
    console.log(`Sever is running on port ${port}`)
});

