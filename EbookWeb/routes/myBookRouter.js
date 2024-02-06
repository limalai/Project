const express = require('express');
const { Owned } = require('../database/db');
const router = express.Router();
const db = require('../database/db');

module.exports = router;

function isLogin(req, res, next){
    if(!req.session.isLogin){
        res.redirect('/login');
    }else{
        next();
    }
}

router.get('/mybook', isLogin, async(req, res)=>{
    const uid = req.session.userid;
    const user = await db.User.findOne({
        where:{id:uid},
        include: {
            model: db.Owned,
            include: {
                model:db.Book,
                include:['booktype']
            },
        }
    });

    const user_index = {
        name: user.name,
        coin: user.coin
    }

    res.locals.isLogin = req.session.isLogin;
    res.render('product_mine', {user: user_index, mybook:user.owneds});
});
router.get('/myfav', isLogin, async(req, res)=>{
    const uid = req.session.userid;
    const user = await db.User.findOne({
        where:{id:uid},
        include: {
            model: db.Wishlist,
            include: {
                model:db.Book,
                include:['booktype']
            },
        }
    });


    const user_index = {
        name: user.name,
        coin: user.coin
    }

    res.locals.isLogin = req.session.isLogin;
    res.render('product_fav', {user: user_index, myfav:user.wishlists});
    
});
router.get('/del_myfav/:id', isLogin, async(req, res)=>{
    const bid = req.params.id;
    const uid = req.session.userid;
    const wish = await db.Wishlist.findOne({where:{userId:uid, bookId:bid}});
    wish.destroy();
    res.redirect("/myfav");
});
