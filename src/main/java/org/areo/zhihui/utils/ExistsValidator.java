package org.areo.zhihui.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.Data;
import org.areo.zhihui.annotation.Exists;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ExistsValidator implements ConstraintValidator<Exists, Object> {
    @Data
    public static class ValidationErrorResponse {
        private int status;
        private String message;
        private List<FieldErrorDetail> errors;
        private long timestamp;


        @Data
        public static class FieldErrorDetail {
            private String field;
            private Object rejectedValue;
            private String message;

        }
    }
    @Autowired
    private MybatisReflect mybatisReflect;

    private Class<?> entity;
    private String field;

    @Override
    public void initialize(Exists constraintAnnotation) {
        this.entity = constraintAnnotation.entity();
        this.field = constraintAnnotation.field();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // 空值检查交给@NotNull处理
        }
        BaseMapper<Object> mapper = MybatisReflect.getMapper(entity);
        if (mapper == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("未找到对应mapper: " + entity.getName())
                   .addConstraintViolation();
        }
        return mapper != null && mapper.exists(new QueryWrapper<>().eq(field, value));


//        try {
//            Object mapper = getMapperForEntity(entity);
//            QueryWrapper<?> queryWrapper = new QueryWrapper<>();
//            queryWrapper.eq(field, value);
//
//            // 使用Mapper的selectCount方法
//            Integer count = (Integer) mapper.getClass()
//                    .getMethod("selectCount", QueryWrapper.class)
//                    .invoke(mapper, queryWrapper);
//            System.out.println(count);
//            return count != null && count > 0;
//        } catch (Exception e) {
//            // 可根据需要记录错误日志
//            return false;
//        }
    }

//    /**
//     * 根据实体类获取对应的Mapper实例。
//     * 该方法会先从缓存中查找Mapper实例，如果缓存中不存在，则根据命名约定生成Mapper名称，
//     * 并从Spring的应用上下文中获取对应的Bean，最后将其存入缓存。
//     *
//     * @param entityClass 实体类的Class对象
//     * @return 对应的Mapper实例
//     */
//    private Object getMapperForEntity(Class<?> entityClass) {
//        // 从缓存中查找实体类对应的Mapper实例，如果不存在则计算并添加到缓存中
//        return mapperCache.computeIfAbsent(entityClass, key -> {
//            // Mapper命名约定: 实体类名 + "Mapper" (首字母大写)
//            // 生成Mapper的名称，将实体类拼接 "Mapper"
//            String mapperName =
//                    key.getSimpleName() + "Mapper";
//            // 从Spring的应用上下文中获取指定名称的Bean，即对应的Mapper实例
//            return applicationContext.getBean(mapperName);
//        });
//    }
}