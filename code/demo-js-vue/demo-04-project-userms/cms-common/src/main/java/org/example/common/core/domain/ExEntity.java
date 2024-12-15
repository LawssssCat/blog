package org.example.common.core.domain;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
public class ExEntity extends BaseEntity {
    /**
     * 搜索值
     */
    @JsonIgnore
    private String searchValue;

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Column(name = "UPDATE_TIME")
    private Map<String, Object> params;

    public synchronized Map<String, Object> getParams() {
        if (params == null) {
            params = new HashMap<>();
        }
        return params;
    }

    /**
     * 备注
     */
    private String remark;
}
