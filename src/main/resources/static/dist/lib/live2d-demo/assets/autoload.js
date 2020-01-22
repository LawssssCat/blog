try {
    $("<link>")
    .attr({
    	href: "dist/lib/live2d-demo/assets/waifu.css", 
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
    
    
    $.ajax({url: "dist/lib/live2d-demo/assets/waifu-tips.js", dataType:"script", cache: true, success: function() {
        $.ajax({url: "dist/lib/live2d-demo/assets/live2d.js", dataType:"script", cache: true, success: function() {
            /* 可直接修改部分参数 */
            live2d_settings['hitokotoAPI'] = "jinrishici.com";  // 一言 API
            live2d_settings['modelId'] = 6;                  // 默认模型 ID
            live2d_settings['modelTexturesId'] = 5;          // 默认材质 ID 换装 texture
            live2d_settings['canSwitchModel']       = false;         // 显示 模型切换    按钮，可选 true(真), false(假)
            live2d_settings['canSwitchTextures']    = false;         // 显示 材质切换    按钮，可选 true(真), false(假)
            live2d_settings['modelStorage'] = false;         // 不储存模型 ID
            
            //看板娘样式设置
            live2d_settings['waifuSize']            = '350x300';    // 看板娘大小，例如 '280x250', '600x535'
            live2d_settings['waifuTipsSize']        = '260x100';     // 提示框大小，例如 '250x70', '570x150'
            live2d_settings['waifuFontSize']        = '20px';       // 提示框字体，例如 '12px', '30px'
            live2d_settings['waifuToolFont']        = '14px';       // 工具栏字体，例如 '14px', '36px'
            live2d_settings['waifuToolLine']        = '20px';       // 工具栏行高，例如 '20px', '36px'
            live2d_settings['waifuToolTop']         = '-90px'         // 工具栏顶部边距，例如 '0px', '-60px'
            live2d_settings['waifuMinWidth']        = '768px';      // 面页小于 指定宽度 隐藏看板娘，例如 'disable'(禁用), '768px'
            live2d_settings['waifuEdgeSide']        = 'left:0px';     // 看板娘贴边方向，例如 'left:0'(靠左 0px), 'right:30'(靠右 30px)
            live2d_settings['waifuDraggable']       = 'axis-x';    // 拖拽样式，例如 'disable'(禁用), 'axis-x'(只能水平拖拽), 'unlimited'(自由拖拽)
            live2d_settings['waifuDraggableRevert'] = false;         // 松开鼠标还原拖拽位置，可选 true(真), false(假)
            
            // 其他杂项设置
            live2d_settings['homePageUrl']          = '/doIndexUI';       // 主页地址，可选 'auto'(自动), '{URL 网址}'
            live2d_settings['aboutPageUrl']         = "http://www.baidu.com";   // 关于页地址, '{URL 网址}'
            live2d_settings['screenshotCaptureName']= 'live2d.png'; // 看板娘截图文件名，例如 'live2d.png'
            /* 在 initModel 前添加 */
            initModel("dist/lib/live2d-demo/assets/waifu-tips.json");
        }});
    }});
    
    //modelId-modelTexturesId 
    //2-42 2-47/46 2-51/52
    //6-5
    
    
} catch(err) { console.log("[Error] JQuery is not defined.") }
