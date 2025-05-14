package org.areo.zhihui.pojo.request;

import lombok.Data;

import java.util.Map;

@Data
public class QueryParams<T>{
    // 分页查询参数
    private Integer pageNum = 1;
    private Integer pageSize = 10;
    // 排序参数
    private String sortField;
    private String sortOrder;
    // 查询条件
    private Map<String, T> conditions;

    // 返回字段
    private String[] fields;

}
