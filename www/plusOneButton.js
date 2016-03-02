var exec = cordova.require('cordova/exec');

var plusOneButton = {
    show: function(params) {


        exec(null, null, 'PlusOneButtonPlugin', 'show', [params]);
    }
};

module.exports = plusOneButton;
