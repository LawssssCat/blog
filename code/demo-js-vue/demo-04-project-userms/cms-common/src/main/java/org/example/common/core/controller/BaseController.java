package org.example.common.core.controller;

import java.beans.PropertyEditorSupport;
import java.util.Date;

import org.example.common.utils.DateUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import lombok.extern.slf4j.Slf4j;

/**
 * web层通用数据处理
 */
@Slf4j
public class BaseController {
    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(DateUtils.parseDate(text));
            }
        });
    }
}
