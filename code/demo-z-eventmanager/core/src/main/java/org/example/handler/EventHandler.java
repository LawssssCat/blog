package org.example.handler;

import org.example.constant.EventNameEnum;
import org.example.model.po.EventPo;

public interface EventHandler {
    boolean isHandle(EventNameEnum eventName);

    /**
     *
     * @return true 处理完成
     */
    boolean handle(EventNameEnum eventName, EventPo eventPo);

    /**
     * 清理相关数据
     */
    void clean(EventNameEnum eventName, EventPo eventPo);
}
