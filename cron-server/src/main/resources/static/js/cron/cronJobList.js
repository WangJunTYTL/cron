$(function () {

    $("#searchMenu").click(function () {
        var pageNum = $(".pageNavMenu.active").find("a").html()
        var jobName = $("#jobName").val()
        var serviceName = $("#serviceName").val()

        var data = {
            "serviceName": serviceName,
            "jobName": jobName,
            "pageNum": pageNum,
            "isAjax":true
        }
        var $btn = $(this).button('loading');

        $("#resultBody").load("/cron/job/search", data, function () {
            setTimeout(function (args) { $btn.button('reset') }, 500);

        })
    });

    $("body").delegate(".pageNavMenu", 'click', function () {
        $(".pageNavMenu.active").removeClass("active");
        $(this).addClass("active");
        $("#searchMenu").click();
    });

});