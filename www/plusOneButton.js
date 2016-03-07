var exec = cordova.require('cordova/exec');

var plusOneButton = {
    show: function(params) {
        exec(null, null, 'PlusOneButtonPlugin', 'show', [params]);
    },
    hide: function() {
        exec(null, null, 'PlusOneButtonPlugin', 'hide', []);
    }
};

module.exports = plusOneButton;
