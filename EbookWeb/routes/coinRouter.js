const express = require('express');
const router = express.Router();
const db = require('../database/db');


module.exports = router;

const coinlist = [
    {
        coin: 100,
        price: 10
    },
    {
        coin: 200,
        price: 20
    },
    {
        coin: 500,
        price: 50
    },
    {
        coin: 1000,
        price: 100
    },
    {
        coin: 1500,
        price: 150
    },
    {
        coin: 2000,
        price: 200
    },
]
function isLogin(req, res, next){
    if(!req.session.isLogin){
        res.redirect('login');
    }else{
       next(); 
    }
}

router.get('/coin',  isLogin, async (req, res)=>{
    const user = await db.User.findByPk(req.session.userid);
    if(user){
        const user_index = {
        name: user.name,
        coin: user.coin
        }
        res.locals.isLogin = req.session.isLogin;
        res.render('coin', {user: user_index, coinlist:coinlist});
    }else{
        res.locals.isLogin = req.session.isLogin;
        res.render('coin', {coinlist:coinlist});
    }
});
router.post('/topup', isLogin, async(req, res)=>{
    const user = await db.User.findByPk(req.session.userid);
    const topup = coinlist[req.body.topup];
    const coin = topup.coin;
    const price = topup.price;
    const history = await db.History.create({
        coin: coin,
        paymentAmout: price,
        userId: user.id
    });
    user.coin += coin;
    user.save();
    res.redirect('/coin');
});

router.get('/history',  isLogin, async(req,res)=>{
    const user = await db.User.findByPk(req.session.userid);
    const history = await db.History.findAll({where:{userId:user.id}});
    if(user){
        const user_index = {
        name: user.name,
        coin: user.coin
        }
        res.locals.isLogin = req.session.isLogin;
        res.render("history_coin", {user: user_index, history:history});
    }else{
        res.locals.isLogin = req.session.isLogin;
        res.render("history_coin");
    } 
});