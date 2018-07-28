$(function () {

    $("#submitMenu").click(function () {
        var name = $("#name").val();
        var comment = $("#comment").val();
        var data = {"name": name, "comment": comment, "isSubmit": true}
        $.post("/cron/service/register", data, function (data) {
            alert(data.message);
        });
    });

});