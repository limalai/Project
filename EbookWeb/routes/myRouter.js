const express = require('express');
const router = express.Router();
const db = require('../database/db');

router.get('/history', async(req,res)=>{
    const user = await db.User.findByPk(req.session.userid);
    if(user){
        const user_index = {
        name: user.name,
        coin: user.coin
        }
        res.locals.isLogin = req.session.isLogin;
        res.render("history_coin", {user: user_index});
    }else{
        res.locals.isLogin = req.session.isLogin;
        res.render("history_coin");
    }
    
});

router.get('/coin', (req, res)=>{
    res.render('coin');
});

router.get('/test', (req, res)=>{
    res.render('open');
});

router.get('/testget',  async(req, res)=>{
    const book = await db.Book.findAll({where:{typeId:1}});
    res.send(book);
});
router.get('/product', (req, res)=>{
    res.render('product');
});

 
module.exports = router;