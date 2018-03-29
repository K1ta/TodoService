function a() {
    alert("a is running");
}

document.addEventListener('DOMContentLoaded', init);

function init() {
    var body = document.body;
    var input = body.querySelector(".index-add-element-input");
    var list = body.querySelector(".index-list");
    var templateId = "listItemTemplate";
    input.addEventListener('keypress', input);

    //get data from db
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "http://localhost:8080/todo/all", true);
    xhr.send(null);
    xhr.onreadystatechange = function () {
        if (xhr.readyState != 4) return;
        if (xhr.status == 200) {
            for (var i = 0; i < JSON.parse(xhr.responseText).length; i++) {
                addItem(JSON.parse(xhr.responseText)[i]);
            }
        }
    }

    input.handleEvent = function (e) {
        if (e.keyCode == 13) {
            addItem(input.value);
            //save into db
            xhr = new XMLHttpRequest();
            xhr.open("POST", "http://localhost:8080/todo/add", true);
            xhr.setRequestHeader("Content-type", "application/json");
            model = {
                id : 1000,
                data : input.value
            }
            xhr.send(JSON.stringify(model));
            input.value = "";
        }
    }

    list.handleEvent = function (e) {
        removeItem(e.target.parentElement)
    }

    function addItem(data) {
        var div = document.createElement("div");
        div.innerHTML = document.getElementById(templateId).innerHTML;
        var newElem = div.children[0];
        div.removeChild(newElem);
        newElem.querySelector(".list-element_data").innerHTML = data;
        newElem.querySelector(".list-element_remove").addEventListener('click', list);
        list.appendChild(newElem);
    }

    function removeItem(e) {
        if (e != null) {
            e.remove();
        }
        var data = e.querySelector(".list-element_data");
        //alert(data.innerHTML);
        xhr = new XMLHttpRequest();
        xhr.open("POST", "http://localhost:8080/todo/remove", true);
        xhr.setRequestHeader("Content-type", "application/json");
        xhr.send(data.innerHTML);
    }
}