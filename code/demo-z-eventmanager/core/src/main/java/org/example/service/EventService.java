package org.example.service;

import org.example.constant.EventNameEnum;

public interface EventService<T> {
    /**
     * 创建事件
     *
     * @param eventName 事件名称
     * @param param 事件参数
     * @param context 事件上下文
     * @return 创建的事件编号
     */
    Integer createEvent(EventNameEnum eventName, Object param, Object context);

    boolean handleEvent(EventNameEnum eventName);

    /**
     * 关闭符合 eventName + param 的事件
     * （相关 handler 需要实现：关闭时，线程中正在运行的相关任务数据将回滚）
     */
    void cancelEvent(EventNameEnum eventName, Object param);

    /**
     * 关闭相关事件，清理选相关事件所有数据
     *
     * @param eventName
     */
    void cleanEventData(EventNameEnum eventName, Object param);
}
