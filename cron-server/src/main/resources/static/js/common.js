$(function () {
    $(".CronNavMenu").click(function () {
        var nowNavMenu = $(this).attr("id");
        $.cookie("nowNavMenu", nowNavMenu, {
            path: '/'
        });
    });

    var menuId = $.cookie("nowNavMenu");
    $("#" + menuId).addClass("active");

});


