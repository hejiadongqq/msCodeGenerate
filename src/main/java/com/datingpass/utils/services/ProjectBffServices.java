package com.datingpass.utils.services;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author: Albert
 * @date: 2021-08-30 3:58 PM
 * @desc:
 */
public interface ProjectBffServices {
    /**
     * 创建bff项目
     * @param request
     * @throws Exception
     */
    void makeBff(MakeBffRequest request) throws Exception;

    @Data
    class MakeBffRequest {
        /**
         * entity的类名
         */
        @ApiModelProperty("entity名,类名 区分大小写")
        @NotBlank(message = "name不能为空!")
        private String name;

        @ApiModelProperty("是否覆盖")
        private Boolean overlaid = false;

    }
}
