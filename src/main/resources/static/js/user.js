var base_url = window.location.origin;


var $selector = $('#userForm'),
    form = $selector.parsley();

$selector.find('#registerBtn').click(function () {
    form.validate();
});

var password = document.getElementById("password")
    , confirm_password = document.getElementById("confirm_password");

var username = document.getElementById("username");

var exists;


function taken() {

    var url = base_url + "/api/custom/users/exists?username=" + $('#username').val();

    var hr = new XMLHttpRequest();
    hr.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            console.log(this.responseText);
            if (this.responseText == "false") {

                $("#registerBtn").removeAttr("disabled");
            } else {
                $("#registerBtn").attr("disabled", "disabled");
                alert("Username taken!");
            }


        }
    };
    hr.open("GET", url, true);
    hr.send();


}

