package com.datingpass.utils.services;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: Albert
 * @date: 2021-09-09 5:40 PM
 * @desc:
 */
public interface ControllerServices {

    /**
     * 根据entity创建相关的类信息
     *
     * @param request
     * @throws Exception
     */
    void makeController(MakeBffRequest request) throws Exception;

    @Data
    class MakeBffRequest extends BaseServices.EntityRequest {
        @ApiModelProperty("是否覆盖")
        private Boolean overlaid = false;

    }
}
