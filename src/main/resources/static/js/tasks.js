// constatnt
var statusId;

var base_url = window.location.origin;

var taskBaseUrl = base_url + "api/tasks";
var statusBaseUrl = base_url + "/api/taskStatuses";
var updatingTaskId;

function setStatusId(id) {
    statusId = id;
    $('#taskName').val("");
    $('#description').val("");
}

// on site validation

var $selector = $('#new-task-modal'),
    form = $selector.parsley();

$selector.find('#updateTask').click(function () {
    form.validate();
});

function updatingTask(id) {
    updatingTaskId = id;
}

function updateTask() {

    var url = taskBaseUrl + "/" + updatingTaskId;

    var xhttp = new XMLHttpRequest();


    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            var task = JSON.parse(this.responseText);

            var updateEl = $("[data-index=" + updatingTaskId + "]").first();
            updateEl.find("#text").find("p").html(task.name);
            updateEl.find("#priority-indicator").removeClass();

            if (task.priority == 0) {
                updateEl.find("#priority-indicator").addClass("priority-indicator priority-indicator-gray");
            } else if (task.priority == 1) {
                updateEl.find("#priority-indicator").addClass("priority-indicator priority-indicator-yellow");
            } else {
                updateEl.find("#priority-indicator").addClass("priority-indicator priority-indicator-red");
            }


        }
    };
    xhttp.open("GET", url, true);
    xhttp.send();
}

// save and fetch tasks

function saveAndFetchTasks() {

    form.subscribe('parsley:form:success', function (e) {
        var taskObj = {};
        taskObj.name = document.getElementById("taskName").value;
        taskObj.description = document.getElementById("description").value;
        taskObj.priority = document.getElementById("priority").value;
        taskObj.status = base_url + "/api/taskStatuses/" + statusId;
        taskObj.position = 999;

        var data = JSON.stringify(taskObj);

        var hr = new XMLHttpRequest();
        hr.open("POST", taskBaseUrl, true);
        hr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
        console.log(hr);

        hr.onreadystatechange = function () {

            if (hr.readyState == 4 && hr.status == "201") {

                task = JSON.parse(this.responseText);
                addTaskEl(task);
                $('#closeUpdateTask').click();

            }
        }

        hr.send(data);
    });


}

function addTaskEl(task) {

    var ul = document.getElementById("sortable" + statusId);

    var li = document.createElement("li");
    li.setAttribute("class", "list-group-item ui-state-default");
    var id = task._links.self.href;
    id = id.replace(taskBaseUrl + "/", "")
    li.setAttribute("data-index", id);
    li.setAttribute("data-position", task.position);

    var div1 = document.createElement("div");
    div1.setAttribute("class", "col-9 float-left");

    var p = document.createElement("p");
    p.setAttribute("class", "m-0");
    p.innerHTML = task.name;
    div1.appendChild(p);

    var div2 = document.createElement("div");
    div2.setAttribute("class", "col-3 float-right");
    var a = document.createElement("a");
    a.href = "/tasks/" + id;
    a.setAttribute("class", "ajax-popup float-right pl-3");
    a.setAttribute("onclick", "updatingTask(" + id + ")");
    var i = document.createElement("i");
    i.setAttribute("class", "fas fa-list");
    var a2 = document.createElement("a");
    a2.setAttribute("onclick", "deleteTask(" + id + ")");
    a2.setAttribute("data-delete-task", id);
    a2.setAttribute("class", "float-right");
    var i2 = document.createElement("i");
    i2.setAttribute("class", "fas fa-trash-alt color-red");
    a.appendChild(i);
    a2.appendChild(i2);
    div2.appendChild(a);
    div2.appendChild(a2);


    var div3 = document.createElement("div");
    if (task.priority == 0) {
        div3.setAttribute("class", "priority-indicator priority-indicator-gray");
    } else if (task.priority == 1) {
        div3.setAttribute("class", "priority-indicator priority-indicator-yellow");
    } else {
        div3.setAttribute("class", "priority-indicator priority-indicator-red");
    }

    li.appendChild(div1);
    li.appendChild(div2);
    li.appendChild(div3);

    ul.appendChild(li);


}

//delete task

function deleteTask(id) {

    if (confirm("Are you sure ?")) {

        var url = taskBaseUrl + "/" + id;
        var hr = new XMLHttpRequest();
        hr.open("DELETE", url, true);
        hr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
        console.log(hr);

        hr.onreadystatechange = function () {

            if (hr.readyState == 4 && hr.status == "204") {

                $('*[data-delete-task="' + id + '"]').parent().parent().remove();

            }
        }

        hr.send();
    }

}

// show task 

$(document).ready(function () {

    $('.ajax-popup').magnificPopup({
        type: 'ajax',
        closeOnBgClick: false,
        preloader: false,
        closeOnContentClick: true,
        enableEscapeKey: true,
        overflowY: 'scroll',
        callbacks: {
            close: function () {
                updateTask();
            }
        }
    });

});


// sortable and update order for tasks

$(function () {

    $("ul.droptrue").sortable({
        connectWith: "ul"
    });

    var status;
    var taskId;
    var newStatus;
    var newSt;

    $(document.getElementsByClassName("list-group")).sortable({

        connectWith: ".list-group",

        start: function (event, ui) {
            status = ui.item.parent().parent().attr('data-id');
            taskId = ui.item.attr('data-index');
            newSt = false;
            console.log("helloop" + ui.item.attr('data-index'));
        },

        update: function (event, ui) {

            newStatus = ui.item.parent().parent().attr('data-id');

            console.log("task id: " + taskId);

            console.log("Initial Status: " + status);

            console.log("New status before if: " + newStatus);

            if (status != newStatus) {
                // updateStatus(taskId, newStatus);
                // console.log($('#sortable' + newStatus).children());
                newSt = true;
            }

        },


        stop: function (event, ui) {

            if (newSt) {
                $('#sortable' + newStatus).children().each(function (index) {

                    if ($(this).attr('data-position') != (index + 1)) {
                        if ($(this).attr('data-index') == ui.item.attr('data-index')) {

                            $(this).attr('data-position', (index + 1));
                            // updateSinglePosition(ui.item.attr('data-index'), $(this).attr('data-position'));
                            updateStatus(taskId, newStatus, (index + 1));

                        } else {
                            $(this).attr('data-position', (index + 1)).addClass('updated');
                        }
                    }


                });
                console.log("Status updated")
            }


            $($(this)).children().each(function (index) {

                if ($(this).attr('data-position') != (index + 1)) {
                    $(this).attr('data-position', (index + 1)).addClass('updated');

                }

            });


            setTimeout(saveNewPositions(), 100);

            console.log("Last");

        }

    }).disableSelection();

});

function updateSinglePosition(id, pos) {

    var object = {
        position: parseInt(pos)
    };

    // alert("data positon: " + item.attr('data-position')+ " id: " + item.attr('data-index'));


    var hr = new XMLHttpRequest();

    // execute after send
    hr.onload = function () {
        if (hr.readyState == 4 && hr.status == "200") {
            console.log(this);
        } else {
            console.error(this);
        }
    }

    var url = base_url + '/api/tasks/' + parseInt(id);
    hr.open("PATCH", url, true);
    hr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    hr.send(JSON.stringify(object));

}


// save changed position

function saveNewPositions() {

    $('.updated').each(function () {
        var object = {
            position: parseInt($(this).attr('data-position'))
        };
        console.log("helloooooooo");
        $(this).removeClass('updated');

        var hr = new XMLHttpRequest();

        // execute after send
        hr.onload = function () {
            if (hr.readyState == 4 && hr.status == "200") {
                console.log(this);
            } else {
                console.error(this);
            }
        }

        var url = base_url + '/api/tasks/' + parseInt($(this).attr('data-index'));
        hr.open("PATCH", url, true);
        hr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
        console.log(hr);
        hr.send(JSON.stringify(object));

    });

}

// save changed status

function updateStatus(taskId, statusId, pos) {

    var data = {
        position: parseInt(pos),
        status: statusBaseUrl + "/" + statusId
    }

    var hrStatus = new XMLHttpRequest();

    // execute after send
    hrStatus.onload = function () {
        if (hrStatus.readyState == 4 && hrStatus.status == "200") {
            console.log("New Status: " + statusId);
            console.log(this);
        } else {
            console.error(this);
        }
    }

    var url = base_url + '/api/tasks/' + taskId;
    hrStatus.open("PATCH", url, true);
    hrStatus.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    console.log(data);
    hrStatus.send(JSON.stringify(data));


}

// new status

function saveAndFetchTasksStatus() {


    // var statusObj = {};
    // statusObj.status = document.getElementById("statusName").value;


    // var data = JSON.stringify(statusObj);

    // var hr = new XMLHttpRequest();
    // hr.open("POST", statusBaseUrl, true);
    // hr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
    // console.log(hr);

    // hr.onreadystatechange = function () {

    //     if (hr.readyState == 4 && hr.status == "201") {

    //         status = JSON.parse(this.responseText);      
    //         addStatusEl(status);

    //     }
    // }

    // hr.send(data);
}

function addStatusEl(status) {

    // var statuses = document.getElementById("statuses");

    // var mainDiv = document.createElement("div");
    // mainDiv.setAttribute("class", "col-sm ui-sortable-handle");
    // mainDiv.setAttribute("style", "position: relative; left: 0px; top: 0px;");

    // var headerLi = document.createElement("li");
    // headerLi.setAttribute("class", "list-group-item active");
    // headerLi.innerHTML = status.status;

    // var ul = document.createElement("ul");
    // ul.id = "sortable" + status.id;
    // ul.setAttribute("class", "list-group droptrue");

    // var footerLi =  document.createElement("li");
    // footerLi.setAttribute("class", "list-group-item");
    // var button = document.createElement("button");
    // button.setAttribute("class", "btn btn-default btn-block text-left");
    // button.setAttribute("data-toggle", "modal");
    // button.setAttribute("data-target","#modelId");
    // button.setAttribute("onclick", "setStatusId(" + status.id + ")");
    // button.innerHTML = "New Task";

    // footerLi.appendChild(button);

    // mainDiv.appendChild(headerLi);
    // mainDiv.appendChild(ul);
    // mainDiv.appendChild(footerLi);

    // statuses.appendChild(mainDiv);

}

// sortable and update status

$(function () {


    $(document.getElementsByClassName("status")).sortable({


        update: function (event, ui) {

            // console.log($(this));
            console.log($(this).children());
            $(this).children().each(function (index) {
                if ($(this).attr('data-position') != (index + 1)) {
                    $(this).attr('data-position', (index + 1)).addClass('updatedStatus');
                }
            });

            saveNewStatusesPositions();

        }
    }).disableSelection();

});

function saveNewStatusesPositions() {

    $('.updatedStatus').each(function () {
        var object = {
            position: parseInt($(this).attr('data-position'))
        };
        $(this).removeClass('updatedStatus');

        var id = parseInt($(this).attr('data-id'));
        console.log($(this));
        console.log(id);

        var hr = new XMLHttpRequest();

        // execute after send
        hr.onload = function () {
            var users = JSON.parse(hr.responseText);
            if (hr.readyState == 4 && hr.status == "200") {
                console.log(this);
            } else {
                console.error(this);
            }
        }

        var url = statusBaseUrl + '/' + id;
        hr.open("PATCH", url, true);
        hr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
        console.log(hr);
        hr.send(JSON.stringify(object));
    });

}