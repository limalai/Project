const express = require('express');
const router = express.Router();
const multer = require('multer');
const db = require('../database/db');
const {body, validationResult} = require('express-validator');
const fs = require('fs');

module.exports = router;

function isAdmin(req, res, next){
    if(!req.session.isAdmin){
        res.redirect('/login');
    }else{
        next();
    }
}

const storage = multer.diskStorage({
    destination:function(req, file, cb){
        if(file.fieldname=='img'){
            cb(null, './public/image/book');
        }else{
            cb(null, './public/pdf/book');
        }
        
    },
    filename:function(req, file, cb){
        const ext = file.mimetype.split('/')[1];
        cb(null, file.fieldname+'-'+Date.now()+'.'+ext);
    }
});
const upload = multer({
    storage: storage,
    fileFilter: function (req, file, cb) {
        // Check if the uploaded file is a PDF file
        if (file.mimetype !== 'application/pdf' && !file.mimetype.startsWith('image/')) {
          return cb(new Error('Only PDF files are allowed'));
        }
        cb(null, true);
      }
});

//add book type page
router.get("/add_type", isAdmin, (req, res)=>{
    res.render('./admin/type_add');
});

//book type page view
router.get("/alltype", isAdmin, async(req, res)=>{
    const booktype = await db.BookType.findAll({include:['book']});
    console.log("---Debud---");
    res.render('./admin/type_all', {booktype: booktype});
});

//action for add type
router.post('/insert_type', isAdmin, [
    body('type').not().isEmpty().withMessage('กรุณากรอกข้อมูล').bail()
    .custom((value)=>{
        return db.BookType.findOne({where:{name: value}}).then((result)=>{
            if(result){
                return Promise.reject('ข้อมูลนี้ถูกสร้างขึ้นแล้ว');
            }
            return true;
        });
    })
    ],
    async(req, res)=>{
        const validator_result = validationResult(req);
        if(!validator_result.isEmpty()){
            let errs = validator_result.errors.map((err)=>{
                return err.msg;
            });
            return res.render('./admin/type_add', {
                errs: errs,
                old_data: req.body
            });
        }
        //create book_type object and save to db
        const data = await db.BookType.create({
            name: req.body.type,
        });

        res.redirect('/alltype');
});

//add book page
router.get("/add_book", isAdmin, async(req, res)=>{
    const booktype = await db.BookType.findAll();
    res.render('./admin/product_add', {booktype:booktype});
});
//action for add book 
router.post('/insert_book', isAdmin, [
    body('name').not().isEmpty().withMessage("กรุณากรอกชื่อหนังสือ"),
    body('type').not().isEmpty().withMessage("กรุณาเลือกหมวดหมู่ของหนังสือ"),
    body('no_page').not().isEmpty().withMessage("กรุณากรอกจำนวนหน้า").bail()
        .isNumeric().withMessage("กรุณากรอกจำนวนหน้าเป็นตัวเลข").bail()
        .isInt({min:1}).withMessage("จำนวนหน้าต้องมี 1 หน้าขึ้นไป"), 
    body('price').not().isEmpty().withMessage("กรุณากรอกราคา").bail()
        .isNumeric().withMessage("กรุณากรอกราคาเป็นตัวเลข").bail()
        .isInt({min:0}).withMessage("ราคาไม่สามารถติดลบได้"),
    body('bdetail').not().isEmpty().withMessage("กรุณากรอกรายละเอียด"),
    ],
    async(req, res)=>{
        const validator_result = validationResult(req);
        if(!validator_result.isEmpty()){
            let errs = validator_result.errors.map((err)=>{
                return err.msg;
            });
            const booktype = await db.BookType.findAll();
            return res.render('./admin/product_add', {
                errs: errs,
                old_data: req.body,
                booktype:booktype
            });
        }
        //create book and save to db
        const book = await db.Book.create({
            name: req.body.name,
            price: req.body.price,
            no_page: req.body.no_page,
            description: req.body.bdetail,
            typeId: req.body.type
        });
        const bid = book.id;
        res.redirect(`/add_file/${bid}`);
});

router.get("/edittype/:id", isAdmin, async(req, res)=>{
    const booktype = await db.BookType.findOne({where:{id:req.params.id}});
    res.render('./admin/type_edit', {type:booktype});
});

router.post('/edit_type/:id', isAdmin, [
    body('type').not().isEmpty().withMessage('กรุณากรอกข้อมูล').bail()
    .custom((value, {req})=>{
        return db.BookType.findOne({where:{name: value}}).then((result)=>{
            if(result && result.id!=req.params.id){
                return Promise.reject('ข้อมูลนี้ถูกสร้างขึ้นแล้ว');
            }
            return true;
        });
    })
    ],
    async(req, res)=>{
        const booktype = await db.BookType.findOne({where:{id:req.params.id}});
        const validator_result = validationResult(req);
        if(!validator_result.isEmpty()){
            let errs = validator_result.errors.map((err)=>{
                return err.msg;
            });
            return res.render('./admin/type_edit', {
                errs: errs,
                old_data: req.body,
                type:booktype
            });
        }
        
        const type = await db.BookType.findOne({where:{id:req.params.id}});
        await type.set({
            name: req.body.type
        });
        type.save();

        res.redirect('/alltype');
});
//add file page
router.get("/add_file/:id", isAdmin, async(req, res)=>{
    const bid=req.params.id;
    res.render("./admin/file_add", {bookid:bid});
});
//action for add file
router.post("/insert_file/:id", isAdmin,
    upload.fields([{
        name:'img', maxCount: 1
    }, {
        name: 'pdf', maxCount: 1
    }]),
    async(req, res)=>{
    const bid=req.params.id;
    console.log("+++Debug+++");
    console.log(req.files.img);
    if(!(req.files.img && req.files.pdf)){
        if(req.files.img){
            fs.unlink("./public/image/book/"+req.files.img[0].filename, err => {if (err) {throw err}});
        }
        if(req.files.pdf){
            fs.unlink("./public/pdf/book/"+req.files.pdf[0].filename, err => {if (err) {throw err}});
        }

        return res.render("./admin/file_add", {
            errs: ["กรุณาเพิ่มไฟล์รูปภาพและไฟล์หนังสือ"],
            bookid:bid
        });
    }
    const book = await db.Book.findByPk(req.params.id);
    await book.set({
        image_url: req.files.img[0].filename,
        file_url: req.files.pdf[0].filename
    });
    await book.save();
    res.redirect("/allbook");
});

//show all book
router.get('/allbook', isAdmin, async(req, res)=>{
    const books = await db.Book.findAll({include:['booktype', {model: db.Owned}]});
    res.render('./admin/product_all', {books:books});
});
//edit book page
router.get('/editbook/:id', isAdmin, async(req, res)=>{
    const bid = req.params.id;
    const book = await db.Book.findByPk(bid, {include:['booktype']});
    const booktype = await db.BookType.findAll();
    res.render('./admin/product_edit', {book:book, booktype:booktype});
});
//action for edit book
router.post('/edit_book/:id', isAdmin, [
    body('name').not().isEmpty().withMessage("กรุณากรอกชื่อหนังสือ"),
    body('type').not().isEmpty().withMessage("กรุณาเลือกหมวดหมู่ของหนังสือ"),
    body('no_page').not().isEmpty().withMessage("กรุณากรอกจำนวนหน้า").bail()
        .isNumeric().withMessage("กรุณากรอกจำนวนหน้าเป็นตัวเลข").bail()
        .isInt({min:1}).withMessage("จำนวนหน้าต้องมี 1 หน้าขึ้นไป"), 
    body('price').not().isEmpty().withMessage("กรุณากรอกราคา").bail()
        .isNumeric().withMessage("กรุณากรอกราคาเป็นตัวเลข").bail()
        .isInt({min:0}).withMessage("ราคาไม่สามารถติดลบได้"),
    body('bdetail').not().isEmpty().withMessage("กรุณากรอกรายละเอียด"),
    ],
    async(req, res)=>{
        const bid = req.params.id;
        const book = await db.Book.findByPk(bid, {include:['booktype']});
        const booktype = await db.BookType.findAll();
        console.log("---Debug---");
        console.log(req.body.type);
        const validator_result = validationResult(req);
        if(!validator_result.isEmpty()){
            let errs = validator_result.errors.map((err)=>{
                return err.msg;
            });
            const booktype = await db.BookType.findAll();
            return res.render('./admin/product_edit', {
                errs: errs,
                book:book, 
                booktype:booktype
            });
        }
        
        await book.set({
            name: req.body.name,
            price: req.body.price,
            no_page: req.body.no_page,
            description: req.body.bdetail,
            typeId: req.body.type
        });

        await book.save();
        res.redirect("/allbook");
});
//edit file page
router.get('/editfile/:id', isAdmin, async(req, res)=>{
    const bid = req.params.id;
    res.render('./admin/file_edit', {bookid: bid});
});
//action for edit file
router.post('/edit_file/:id', isAdmin,
    upload.fields([{
        name:'img', maxCount: 1
    }, {
        name: 'pdf', maxCount: 1
    }]),
    async(req, res)=>{
        const bid = req.params.id;
        const book = await db.Book.findByPk(bid);
        if(req.files.img){
            const old_url = book.image_url;
            if(fs.existsSync(old_url)){
                fs.unlink("/image/book/"+old_url, err => {if (err) {throw err}});
            }
            book.set({image_url:req.files.img[0].filename});
        }
        if(req.files.pdf){
            const old_url = book.file_url;
            if(fs.existsSync(old_url)){
                fs.unlink("/pdf/book/"+old_url, err => {if (err) {throw err}});
            }
            book.set({file_url:req.files.pdf[0].filename});
        }
        await book.save();
        res.redirect("/allbook");
});

//delete
router.get('/delete_book/:id', async(req, res)=>{
    const book = await db.Book.findByPk(req.params.id);
    book.destroy();
    res.redirect('/allbook');
});

router.get('/delete_type/:id', async(req, res)=>{
    const type = await db.BookType.findByPk(req.params.id);
    type.destroy();
    res.redirect('/alltype');
});