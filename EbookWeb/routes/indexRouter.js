const express = require('express');
const router = express.Router();
const db = require('../database/db');

module.exports = router;


router.get('/', async (req, res)=>{
    const user = await db.User.findByPk(req.session.userid);
    if(user){
        const user_index = {
        name: user.name,
        coin: user.coin
        }
        res.locals.isLogin = req.session.isLogin;
        res.render('index', {user: user_index});
    }else{
        res.locals.isLogin = req.session.isLogin;
        res.render('index');
    }
});

router.get('/dashboard', async(req, res)=>{
    const type = await db.BookType.findAll({include:['book']});
    
    const user = await db.User.findByPk(req.session.userid);
    if(user){
        const user_index = {
        name: user.name,
        coin: user.coin
        }
        res.locals.isLogin = req.session.isLogin;
        res.render('dashboard', {user: user_index, booktype:type});
    }else{
        res.locals.isLogin = req.session.isLogin;
        res.render('dashboard', {booktype:type});
    }
});



