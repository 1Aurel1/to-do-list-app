var base_url = window.location.origin;

var statusBaseUrl = base_url + "/api/taskStatuses";
var updatingStatusId;
var inputField;
var tr;


var $selector = $('#updateStatusForm'),
    form = $selector.parsley();

$selector.find('#updateStatus').click(function () {
    form.validate();
});


function setUpdatingStatus(id) {
    updatingStatusId = id;
    inputField = document.getElementById("editstatusname");
    tr = document.getElementById("status" + updatingStatusId);
    inputField.value = tr.innerHTML;
    // console.log("Tr: "+  document.getElementById("status"+updatingStatusId).innerHTML );
}

function updateStatus() {

    // console.log("inout"+inputField);
    var data = {
        status: inputField.value
    }

    var hrStatus = new XMLHttpRequest();

    // execute after send
    hrStatus.onload = function () {
        if (hrStatus.readyState == 4 && hrStatus.status == "200") {
            console.log(this);

            tr.innerHTML = inputField.value;
            $("#close").click();

        } else {
            console.error(this);
        }
    }

    var url = statusBaseUrl + "/" + updatingStatusId;
    hrStatus.open("PATCH", url, true);
    hrStatus.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    console.log(data);
    hrStatus.send(JSON.stringify(data));
}

function deleteStatus(id) {

    if (confirm("Are you sure?")) {
        var url = statusBaseUrl + "/" + id;
        var hr = new XMLHttpRequest();
        hr.open("DELETE", url, true);
        hr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
        console.log(hr);

        hr.onreadystatechange = function () {

            if (hr.readyState == 4 && hr.status == "204") {

                $('*[id="status' + id + '"]').parent().remove();

            }
        }

        hr.send();


    }

}

