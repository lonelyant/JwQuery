$(function() {

	mui(document.body).on('tap', '.mui-btn', function(e) {
		var check = true;
		mui("#login input").each(function() {
			//若当前input为空，则alert提醒 
			if (!this.value || this.value.trim() == "") {
				var label = this.previousElementSibling;
				mui.alert(label.innerText + " 不能为空或包含特殊字符！");
				check = false;
				return false;
			}
		}); //校验通过，继续执行业务逻辑 
		if (check) {
			mui(this).button('loading');
			var isSave = false;
			if (mui("#isSavePwd")[0].classList.contains('mui-active'))isSave = true;
			$.ajax({
				url:'/askLogin',
                data: {
                    account:mui("#account")[0].value,
                    password:mui("#password")[0].value,
                    isSavePwd:isSave
                },
                dataType: 'json', //服务器返回json格式数据
                headers:{'Content-Type':'application/json'},
                success: function(data) {
                    //服务器返回响应，根据响应结果，分析是否登录成功；
					if(data.status){
						window.location.href='/navigate';
					}else {
						mui.alert(data.info);
					}
                    console.log(data);
                    mui("#submit").button('reset');
                },
                error: function(xhr, type, errorThrown) {
                    //异常处理；
                    var errormsg = "服务器可能去蹦迪了 o(╥﹏╥)o\n重试如果仍无法登录，请联系Ant\nQQ：891575283\n\n错误原因：";

                    if (type == "abort") {
                        errormsg += "与服务器无法通讯！";
                    } else if (type == "timeout") {
                        errormsg += "服务器响应超时！";
                    } else {
                        errormsg += type;
                    }
                    mui.alert(errormsg);
                    mui("#submit").button('reset');
                }
			})
		}
	});
});
