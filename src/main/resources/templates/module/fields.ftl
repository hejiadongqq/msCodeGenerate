<#list fields as field>
    <#if field.name = "groupId" || field.name = "appId" || field.name = "deleted">
        @ApiModelProperty(value = "${field.desc}", hidden = true)
        @JsonIgnore
    <#else>
        @ApiModelProperty(value = "${field.desc}")
    </#if>
    <#if field.isEnum>
        @Enumerated(EnumType.STRING)
    </#if>
    <#if field.isDateTime>
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    </#if>
    <#if field.isDate>
    </#if>
    private ${field.type} ${field.name};
</#list>