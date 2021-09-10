package com.datingpass.utils.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Albert
 * @date: 2021-08-26 4:01 PM
 * @desc:
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Field {
    /**
     * 字段名
     */
    private String name;
    /**
     * 字段类型
     */
    private String type;

    /**
     * 描述
     */
    private String desc;

    /**
     * 是否枚举
     */
    private Boolean isEnum;


}
