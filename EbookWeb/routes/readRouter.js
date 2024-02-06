const express = require('express');
const router = express.Router();
const db = require('../database/db');

module.exports = router;

function isLogin(req, res, next){
    if(!req.session.isLogin){
        res.redirect('login');
    }else{
       next(); 
    }
}

router.get('/read/:id', isLogin, async(req, res)=>{
    const user = await db.User.findByPk(req.session.userid);
    const bid = req.params.id;
    const own = await db.Owned.findOne({where:{userId:user.id, bookId:bid}});
    const book = await db.Book.findByPk(bid);
    if(own){
        const user_index = {
        name: user.name,
        coin: user.coin
        }
        res.locals.isLogin = req.session.isLogin;
        res.render('read', {user: user_index, book:book});
    }else{
        res.redirect(`/product/${bid}`);
    }
    
});