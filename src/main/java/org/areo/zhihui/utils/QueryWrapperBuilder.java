package org.areo.zhihui.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import org.areo.zhihui.pojo.request.QueryParams;

import java.lang.reflect.Method;

public class QueryWrapperBuilder {

    public static <T> QueryWrapper<T> build(QueryParams<T> params,Class<T> entityClass) {
        QueryWrapper<T> wrapper = new QueryWrapper<>();

        // 处理查询条件
        if (params.getConditions() != null) {
            params.getConditions().forEach((field,value) -> {
                if (value != null) {
                    LambdaQueryWrapper<T> lambdaQueryWrapper = wrapper.lambda();
                    try {
                        Method method = entityClass.getMethod("get" + field.substring(0, 1).toUpperCase() + field.substring(1));

                        // 添加等值条件
                        SFunction<T, ?> column = entity -> {
                            try {
                                return method.invoke(entity);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        };
                        lambdaQueryWrapper.eq(column, value);
                    }catch (NoSuchMethodException e) {
                        // 如果没有该字段,使用字符串形式
                        wrapper.eq(field, value);
                    }
                }
            });
        }
        // 处理排序条件
        if (params.getSorts() != null) {
            params.getSorts().forEach(sort -> {
                String[] sortArr = sort.split(",");
                if (sortArr.length == 2) {
                    if ("asc".equalsIgnoreCase(sortArr[1])) {
                        wrapper.orderByAsc(sortArr[0]);
                    } else {
                        wrapper.orderByDesc(sortArr[0]);
                    }
                }
            });
        }

        // 处理返回字段
        if (params.getSelectFields() != null && params.getSelectFields().length > 0) {
            wrapper.select(params.getSelectFields());
        }

        return wrapper;
    }
}
