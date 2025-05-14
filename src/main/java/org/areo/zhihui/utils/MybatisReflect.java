package org.areo.zhihui.utils;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class MybatisReflect implements BeanPostProcessor {

    private final static Map<String, BaseMapper<?>> MAP_CLASSNAME_TO_MAPPER = new ConcurrentHashMap<>();
    private final static Map<String, String> MAP_MAPPER_NAME_TO_CLASS_NAME = new ConcurrentHashMap<>();


    public static <T> BaseMapper<T> getMapper(String className) {
        return (BaseMapper<T>) MAP_CLASSNAME_TO_MAPPER.get(className);
    }

    public static <T> BaseMapper<T> getMapper(Class<?> t) {
        return (BaseMapper<T>) MAP_CLASSNAME_TO_MAPPER.get(t.getName());
    }

    /**
     * 在 Bean 初始化之前进行后置处理。此方法会检查 Bean 是否为 MapperFactoryBean 类型，
     * 若为该类型，则解析出 Mapper 接口对应的实体类名，并将 Mapper Bean 名称与实体类名的映射关系存入 MAP_MAPPER_NAME_TO_CLASS_NAME。
     *
     * @param bean Bean 实例
     * @param beanName Bean 的名称
     * @return 处理后的 Bean 实例
     * @throws BeansException 处理 Bean 时可能抛出的异常
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // 检查当前 Bean 是否为 MapperFactoryBean 类型
        if (bean instanceof MapperFactoryBean) {
            // 获取 MapperFactoryBean 对应的 Mapper 接口类
            Class<?> mapperInterface = ((MapperFactoryBean<?>) bean).getMapperInterface();
            // 获取 Mapper 接口实现的泛型接口数组
            Type[] genericInterfaces = mapperInterface.getGenericInterfaces();
            // 检查泛型接口数组是否不为空
            if (ObjectUtils.isNotEmpty(genericInterfaces)) {
                // 获取泛型接口的实际类型参数数组
                Type[] actualTypeArguments = ((ParameterizedType) genericInterfaces[0]).getActualTypeArguments();
                // 获取第一个实际类型参数的名称，即 Mapper 对应的实体类名
                String typeName = actualTypeArguments[0].getTypeName();
                // 将 Mapper Bean 名称与实体类名的映射关系存入 MAP_MAPPER_NAME_TO_CLASS_NAME
                MAP_MAPPER_NAME_TO_CLASS_NAME.put(beanName, typeName);
            }

        }
        // 调用父类的 postProcessBeforeInitialization 方法继续处理
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof BaseMapper) {
            String className = MAP_MAPPER_NAME_TO_CLASS_NAME.get(beanName);
            MAP_CLASSNAME_TO_MAPPER.put(className, (BaseMapper<?>) bean);
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

}