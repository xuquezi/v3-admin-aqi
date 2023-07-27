package com.aqi.admin.entity.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 下拉选项对象
 */
@ApiModel(value = "下拉选项对象", description = "")
@Data
@NoArgsConstructor
public class Option {

    public Option(String value, String label) {
        this.id = value;
        this.label = label;
    }

    public Option(String value, String label, List<Option> children) {
        this.id = value;
        this.label = label;
        this.children= children;
    }

    @ApiModelProperty(value = "选项的值")
    private String id;

    @ApiModelProperty(value = "选项的标签")
    private String label;

    @ApiModelProperty(value = "子选项列表")
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private List<Option> children;

}