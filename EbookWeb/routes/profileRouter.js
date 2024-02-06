const express = require('express');
const router = express.Router();
const db = require('../database/db');
const {body, validationResult} = require('express-validator');

module.exports = router;

function isLogin(req, res, next){
    if(!req.session.isLogin){
        res.redirect('login');
    }else{
       next(); 
    }
}

router.get('/profile', isLogin, async(req, res)=>{
    const user = await db.User.findByPk(req.session.userid);

    res.locals.isLogin = req.session.isLogin
    res.render('profile', {user: user});
    
});

router.post('/profile_edit', isLogin, async(req, res)=>{
    const user = await db.User.findByPk(req.session.userid);
    console.log("====Debut====");
    console.log(req.body);
    await user.set({
        name: req.body.name,
        phone: req.body.phone,
        gender: req.body.gender,
        password: req.body.password
    });
    await user.save();
    res.redirect('/profile');
});
