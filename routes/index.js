const router = require('express').Router();
const admin = require('../modules/firebase.js');
const bodyParser = require("body-parser");
const urlencodedParser = bodyParser.urlencoded({extended: false});

router.get('/', (req, res) => {
    var id = 1;
    var idLevel = 1;
    var idCard = 1;
    admin.database().ref("/decks/" + id + "/levels/" + idLevel + "/").on("value",(snapshot) => {
        var level = snapshot.val();
        res.render('index',{
            target: level.id1.target,
            mnemonic: level.id1.mnemonic,
            desc: level.id1.description
        });
    });   
});



router.post('/:id/:lessons/',urlencodedParser,(req, res) => {
    admin.database().ref().set({
        deck:{
            1:{
                name: "qweewq",
                desc: "sadwadwqdqw"
            }
        }
    });
    res.redirect("/");
});

module.exports = router;
module.exports.URL = "/";