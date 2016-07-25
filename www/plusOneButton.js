var exec = cordova.require('cordova/exec');

var plusOneButton = {
    show: function(params) {
        exec(null, null, 'PlusOneButtonPlugin', 'show', [params]);
    },
    hide: function() {
        exec(null, null, 'PlusOneButtonPlugin', 'hide', []);
    },
    onClick: function(onClick) {
        if(typeof(onClick) !== 'function'){
            console.log('onClick callback is not a function');
        } else {
            exec(onClick, null, 'PlusOneButtonPlugin', 'onClick', []);
        }
    }
};

module.exports = plusOneButton;
