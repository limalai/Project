const express = require('express');
const router = express.Router();
const db = require('../database/db');
const {body, validationResult} = require('express-validator');

const adminId = 'c58367c0-9857-4eda-b7d3-57d10d2ebc29';


module.exports = router;

router.get('/signout', async(req, res)=>{
    req.session.isLogin = false;
    req.session.isAdmin = false;
    res.redirect('/login');
});

//api for login page
router.get('/login', async(req, res)=>{
    res.render('login');
});
//action for login page. It's login click.
router.post('/check_login', [
    body('email').isEmail().withMessage("email ไม่ถูกต้อง").bail() //check email format.
    .custom((value)=>{  //check email in database.
        return db.User.findOne({where:{email:value}}).then((result)=>{
            if(!result){
                return Promise.reject("email ไม่ถูกต้อง");
            }
            return true;
        });
    }),
    ],
    async(req, res)=>{
        //check errors part
        const validator_result = validationResult(req); //get result from check req by body.
        if (!validator_result.isEmpty()) { // have errors.
            let errs = validator_result.errors.map((err)=>{ //map errors to array object.
                return err.msg;
            });
            return res.render('login', { 
                errs: errs, 
                old_data: req.body
            });
        }
        //if not errors let's continue
        const user = await db.User.findOne({where:{email: req.body.email}}); //get user by email.
        if(user.password==req.body.password){ // check password
            if(user.id== adminId){
                req.session.isAdmin = true;
                res.redirect('/allbook');
            }else{
                req.session.isLogin = true;
                req.session.userid = user.id;
                res.redirect('/');
            }
        }else{
            res.render('login', { 
                errs: ['รหัสผ่านไม่ถูกต้อง'], 
                old_data: req.body
            });
        }
});


router.get('/register', async(req, res)=>{
    res.render('register');
});
router.post('/create_user', [
    body('name').not().isEmpty().withMessage("กรุณากรอกข้อมูล ชื่อ"),
    body('email').isEmail().withMessage("อีเมลไม่ถูกต้อง").bail()
    .custom((value)=>{
        return db.User.findOne({where:{email:value}}).then((result)=>{
            if(result){
                return Promise.reject("email นี้ถูกใช้แล้ว");
            }
            return true;
        });
    }),
    body('password').isLength({min: 6}).withMessage("รหัสผ่านต้องมีความยาวมากกว่า 6 ตัว"),
    body('confirm').custom((value, {req})=>{
        if(value!=req.body.password){
            return Promise.reject("รหัสผ่านยืนยันไม่ถูกต้อง");
        }
        return true;
    }),
    //body('phone').trim().isNumeric().withMessage('หมายเลขโทรศัพท์ไม่ถูกต้อง')
    ],
    async(req, res)=>{
        //check errors part
        const validator_result = validationResult(req); //get result from check req by body.
        if (!validator_result.isEmpty()) { // have errors.
            let errs = validator_result.errors.map((err)=>{ //map errors to array object.
                return err.msg;
            });
            return res.render('register', { 
                errs: errs, 
                old_data: req.body
            });
        }
        //no have errors
        const user = await db.User.create({
            email: req.body.email,
            password: req.body.password,
            name: req.body.name,
            phone: req.body.phone,
            gender: req.body.gender,

        });
        if(!user){
            res.send("Create user failed.");
        }else{
            res.redirect("/login");
        }
});