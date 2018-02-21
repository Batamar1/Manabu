const router = require('express').Router();
const bodyParser = require("body-parser");
const firebase = require(__basePath + '/modules/firebase.js');
const db = firebase.database();

const urlencodedParser = bodyParser.urlencoded({extended: false});

router.get('/',(req, res) =>{
    db.ref("/decks/").on("value",(snapshot) => {
        var decks = snapshot.val();
        console.log(decks);
    }); 
});

router.get('/:id/lessons/', (req, res) => {
    var array = [];
    var i = 0;
    db.ref("/decks/" + req.params.id + "/levels/").on("value",(snapshot) => {
        snapshot.forEach(element => {
            if(element.val().public){
                array[i] = element.val();
                i+=1;
            }
        });;
        res.send(array);
    });   
});

router.get('/', (req, res) =>{
  req.session.destroy();
  res.redirect('/');
});

module.exports = router;