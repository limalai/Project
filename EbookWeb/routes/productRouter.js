const express = require('express');
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

router.get('/product/:id', async (req, res)=>{
    const bid = req.params.id;
    const uid = req.session.userid;
    const book = await db.Book.findByPk(bid, {include:['booktype']});
    const user = await db.User.findByPk(uid);
    
    res.locals.bookid = bid;
    if(user){
        const user_index = {
        name: user.name,
        coin: user.coin
        }
        const isOwned = await db.Owned.findOne({where:{userId:uid, bookId:bid}});
        const isFav = await db.Wishlist.findOne({where:{userId:uid, bookId:bid}});
        res.locals.isOwned = (isOwned)?true:false;
        res.locals.isFav = (isFav)?true:false;
        res.locals.isLogin = req.session.isLogin;
        res.render('product', {user: user_index, book:book});
    }else{
        res.locals.isOwned = false;
        res.locals.isFav = false;
        res.locals.isLogin = req.session.isLogin;
        res.render('product', {book: book});
    }
}); 

router.get('/add_owned/:id', isLogin, async(req, res)=>{
    const bid = req.params.id;
    const uid = req.session.userid;
    const user = await db.User.findByPk(uid);
    const book = await db.Book.findByPk(bid);
    if(user.coin<book.price){
        res.redirect(`/product/${bid}`);
    }else{
        user.coin -= book.price;
        user.save();
        const own = await db.Owned.create({
            userId: uid,
            bookId: bid
        });
        res.redirect(`/product/${bid}`);
    }
});

router.get('/add_fav/:id', isLogin, async(req, res)=>{
    const bid = req.params.id;
    const uid = req.session.userid;
    const wish = await db.Wishlist.create({
        userId: uid,
        bookId: bid
    });
    res.redirect(`/product/${bid}`);
});
router.get('/del_fav/:id', isLogin, async(req, res)=>{
    const bid = req.params.id;
    const uid = req.session.userid;
    const wish = await db.Wishlist.findOne({where:{userId:uid, bookId:bid}});
    wish.destroy();
    res.redirect(`/product/${bid}`);
});