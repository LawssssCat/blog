/**
 * 
 */

//登录
  $(function() {
      $('.ui.form')
        .form({
          fields: {
            email: {
              identifier  : 'usernameId', //标识 name
              rules: [
                {
                  type   : 'empty', //非空
                  prompt : 'Please enter your username'
                },
                /* {
                  type   : 'email', //email格式
                  prompt : 'Please enter a valid e-mail'
                } */
              ]
            },
            password: {
              identifier  : 'passwordId', //标识 name
              rules: [
                {
                  type   : 'empty', //非空
                  prompt : 'Please enter your password'
                },
                {
                  type   : 'length[6]', //长度 6
                  prompt : 'Your password must be at least 6 characters'
                }
              ]
            }
          },//fields
          setting : {
        	  keyboardShortcuts: true
          } , 
          onSuccess : doLogin , 
          onFailure: function(formErrors, fields){
        	  console.log("formErrors" , formErrors);
        	  console.log("fields" , fields);
        	  return false ;
          }
        })
      ;//form
    })
  ;
  
  function doLogin() {
	  var params={
			  username : $("#usernameId").val(), 
			  password:$('#passwordId').val(),
			  isRememberMe:$("#rememberMeId").prop("checked")
	  }
	  console.log("params" , params);
	  var url = "/user/doLogin" ; 
	  $.post(url , params , function(result) {
		  if(result.state==1){//登录成功！
			  //转跳到index页面
			  location.href="/system?t="+Math.random();//避免缓存
		  }else{
			  var a = $('.message .close').closest('.message') ; 
			  if(a.hasClass("hidden")) {
				  a.transition('fade') ;
			  }
			  $('#login-exception-msg-box').html(result.message);
		  }
	  });
	  return false ; //防止刷新时重复提交
  }
//关闭提示
  $('.message .close')
  .on('click', function() {
    $(this)
      .closest('.message')
      .transition('fade')
    ;
  })
  ;