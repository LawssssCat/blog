try {
    $("<link>")
    .attr({
    	href: "/dist/lib/live2d-demo/assets/waifu.css", 
    	rel: "stylesheet", 
    	type: "text/css"
    })
    .appendTo('head');
    
    $('body')
    .append('<div class="waifu">'+
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
    
    
    $.ajax({url: "/dist/lib/live2d-demo/assets/waifu-tips.js", dataType:"script", cache: true, success: function() {
        $.ajax({url: "/dist/lib/live2d-demo/assets/live2d.js", dataType:"script", cache: true, success: function() {
            /* 可直接修改部分参数 */
            /* 在 initModel 前添加 */
            initModel("/dist/lib/live2d-demo/assets/waifu-tips.json");
        }});
    }});
    
    
    
} catch(err) { console.log("[Error] JQuery is not defined.") }
