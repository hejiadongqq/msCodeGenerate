package com.datingpass.utils.services;

import com.datingpass.utils.vo.Field;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author: Albert
 * @date: 2021-08-16 11:06 AM
 * @desc:
 */
public interface EntityServices {


    /**
     * 根据entity创建相关的类信息
     * @param request
     * @throws Exception
     */
    void makeEntity(MakeEntityRequest request) throws Exception;

    /**
     * 根据entity更新相关的类信息
     * @param request
     * @throws Exception
     */
    void updateEntity(MakeEntityRequest request) throws Exception;

    /**
     * 移动备份文件到备份目录
     * @throws Exception
     */
    void moveBackup() throws Exception;

    @Data
    class MakeEntityRequest {
        /**
         * entity的类名
         */
        @ApiModelProperty("entity名,类名 区分大小写")
        @NotBlank(message = "entityName不能为空!")
        private String entityName;

    }

    @Builder
    @Data
    class NewOldField {
        Field newField;
        Field oldField;
    }
}
