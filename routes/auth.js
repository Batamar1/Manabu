const router = require('express').Router();
const bodyParser = require("body-parser");
const firebase = require(__basePath + '/modules/firebase.js');
var auth = require('google-auth-library');



const urlencodedParser = bodyParser.urlencoded({extended: false});

router.post('/login', urlencodedParser,(req, res) =>{
  var client = auth.OAuth2(CLIENT_ID, '', '');
  client.verifyIdToken(
    token,
    CLIENT_ID,  // Specify the CLIENT_ID of the app that accesses the backend
    // Or, if multiple clients access the backend:
    //[CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3],
    function(e, login) {
      var payload = login.getPayload();
      var userid = payload['sub'];
      // If request specified a G Suite domain:
      //var domain = payload['hd'];
    });
  res.send(req.user);
});

router.get('/logout', (req, res) =>{
  req.session.destroy();
  res.redirect('/');
});

module.exports = router;