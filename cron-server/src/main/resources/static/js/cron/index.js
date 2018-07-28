$(function () {

    $("#submitMenu").click(function () {
        var name = $("#name").val();
        var cronExpression = $("#cronExpression").val();
        var serviceId = $("#serviceId").val();
        var data = {"name": name, "cronExpression": cronExpression, "serviceId": serviceId}
        $.post("/cron/create/job", data, function (data) {
            alert(data.message);
        });
    });

});