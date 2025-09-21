package org.example.common.core.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;

import org.example.common.constant.Constants;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * Entity基类
 */
@Data
public class BaseEntity {
    @Id
    private Long id;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = Constants.DATE_FORMAT)
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = Constants.DATE_FORMAT)
    @Column(name = "UPDATE_TIME")
    private Date updateTime;
}
