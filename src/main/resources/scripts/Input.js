var inputSelector = '.index-add-element-input';

function InputConstructor() {
    input = document.body.querySelector(inputSelector);
    input.addEventListener('keypress', input);

    input.handleEvent = function (e) {
        //on press enter
        if (e.keyCode == 13) {
            //no data - no adding
            if (input.value.length == 0) {
                return;
            }

            //get id from db
            var xhr = new XMLHttpRequest();
            var id = 0;
            xhr.open("GET", "http://localhost:8080/todo/id", true);
            xhr.send();
            xhr.onreadystatechange = function () {
                if (xhr.readyState !== 4) return;
                if (xhr.status === 200) {
                    for (var i = 0; i < JSON.parse(xhr.responseText).length; i++) {
                        id = JSON.parse(xhr.responseText);
                    }
                }
            }

            var ItemData = {
                data: input.value,
                id: id
            }
            addItem(ItemData);
            saveIntoDB(ItemData);

            input.value = '';
        }
    }
}