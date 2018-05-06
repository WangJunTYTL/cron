$(function () {

    $("#submitMenu").click(function () {
        var name = $("#name").val();
        var cronExpression = $("#cronExpression").val();
        var data = {"name": name, "cronExpression": cronExpression}
        $.post("/cron/create", data, function (data) {
            alert(data.message);
        });
    });

});