try {
	debugger ;
	function getAttr(script, attr, default_val) {//获取自定义配置函数
		var str = script.getAttribute(attr) ; 
        return Number(str) || default_val;
    }
	var waifu = document.getElementById('waifu');  // 当前加载的script
    config = {
    		lib: getAttr(waifu, "lib", "/dist/lib"), //自定义lib路径
    		project: getAttr(waifu, "project", "/")
    };
    
    $("<link>")//解析 css 路径
    .attr({
    	href: config.lib+"/live2d-demo/assets/waifu.css", 
    	rel: "stylesheet", 
    	type: "text/css"
    })
    .appendTo('head');
    
    $('body')//添加waifu主体
    .append('<div class="waifu" style="z-index:500 !important;">'+ //最前面
    			'<div class="waifu-tips"></div>'+
    			'<canvas id="live2d" class="live2d"></canvas>'+
    			'<div class="waifu-tool">'+
    				'<span class="fui-home"></span> '+ //主页
    				'<span class="fui-chat"></span> '+//chat
    				'<span class="fui-eye"></span> '+ //换人
    				'<span class="fui-user"></span> '+ //换装
    				'<span class="fui-photo"></span> '+ //照相
    				'<span class="fui-info-circle"></span> '+ //信息
    				'<span class="fui-cross"></span>'+ //关闭
				'</div>'+
			'</div>');
    
    
    $.ajax({url: config.lib+"/live2d-demo/assets/waifu-tips.js", dataType:"script", cache: true, success: function() {
        $.ajax({url: config.lib+"/live2d-demo/assets/live2d.js", dataType:"script", cache: true, success: function() {
            /* 可直接修改部分参数 */
        	live2d_settings['homePageUrl']          = config.project;       // 主页地址，可选 'auto'(自动), '{URL 网址}'
        	live2d_settings['aboutPageUrl']         = config.project+"blog/about";   // 关于页地址, '{URL 网址}' ,例如 'https://www.fghrsh.net/post/123.html'(来源-大佬博客)
            /* 在 initModel 前添加 */
            initModel(config.lib+"/live2d-demo/assets/waifu-tips.json");
            
            if(typeof waifuCallback === 'function') waifuCallback();
        }});
    }});
    
    
    
} catch(err) { console.log("[Error] JQuery is not defined.") }
