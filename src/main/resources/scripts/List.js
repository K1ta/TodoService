var templateId = 'listItemTemplate';
var dataSelector = '.list-element_data';
var removeSelector = '.list-element_remove';
var listSelector = '.index-list';

function ListConstructor() {
    list = document.body.querySelector(listSelector);

    list.handleEvent = function (e) {
        removeItem(e.target.parentElement)
    }

}

function addItem(ItemData) {
    var div = document.createElement("div");
    div.innerHTML = document.getElementById(templateId).innerHTML;
    var newElem = div.children[0];
    div.removeChild(newElem);
    newElem.querySelector(dataSelector).innerHTML = ItemData.data;
    newElem.querySelector(removeSelector).addEventListener('click', list);
    newElem.id = ItemData.id;

    list.appendChild(newElem);
}

function removeItem(e) {
    var data = e.querySelector(".list-element_data");
    var ItemData = {
        id: e.id,
        data: data.innerHTML
    }
    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'http://localhost:8080/todo/remove', true);
    xhr.setRequestHeader('Content-type', 'application/json');
    alert(JSON.stringify(ItemData));
    xhr.send(JSON.stringify(ItemData));
    if (e != null) {
        e.remove();
    }
}

function saveIntoDB(ItemData) {
    //save into db
    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'http://localhost:8080/todo/add', true);
    xhr.setRequestHeader('Content-type', 'application/json');
    xhr.send(JSON.stringify(ItemData));
}