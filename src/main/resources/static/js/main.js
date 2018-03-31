document.addEventListener('DOMContentLoaded', init);

var inputSelector = '.index-add-element-input';

function init() {
    var input = document.body.querySelector(inputSelector);

    input.addEventListener('keypress', input);

    input.handleEvent = function (e) {
        //on press enter
        if (e.keyCode == 13) {
            alert(input.value);
        }
    }
}