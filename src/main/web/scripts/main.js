document.addEventListener('DOMContentLoaded', init);

var list;
var input;

function init() {
    ListConstructor();
    InputConstructor();
    loadItems();
}

function loadItems() {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "http://localhost:8080/todo/all", true);
    xhr.send();
    xhr.onreadystatechange = function () {
        if (xhr.readyState !== 4) return;
        if (xhr.status === 200) {
            for (var i = 0; i < JSON.parse(xhr.responseText).length; i++) {
                addItem(JSON.parse(xhr.responseText)[i]);
            }
        }
    }
}