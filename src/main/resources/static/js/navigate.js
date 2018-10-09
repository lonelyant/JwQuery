$(window).on("load resize", function() {
	var h = window.innerHeight || document.body.clientHeight || document.documentElement.clientHeight;
	var w = window.innerWidth || document.body.clientWidth || document.documentElement.clientWidth;
	console.log(w + " " + h)
    if (w<400){
	    if(h>780){
            $(".mui-content").css("margin-top", h * 0.18);
        }else {
            $(".mui-content").css("margin-top", h * 0.05);
        }
    }else {
        $(".mui-content").css("margin-top", h * 0.18);
    }
});


$(function() {

    document.getElementById('logout').addEventListener('tap', function() {
        mui.confirm('确定要注销你的登录吗？', '注销确认', ['注销','取消'], function(e) {
            if (e.index === 0) {
                //登出
                mui.openWindow({
                    url: '/logout'
                });
            }
        })


    });

});
