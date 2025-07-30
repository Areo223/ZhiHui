package org.areo.zhihui.utils;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.areo.zhihui.utils.enums.DeletedEnum;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        // 插入时自动填充 createTime 和 updateTime
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());

        // 枚举字段默认值（如果为null）
        this.strictInsertFill(metaObject, "deleted", DeletedEnum.class, DeletedEnum.NOT_DELETED);

        if (metaObject.getValue("currentCapacity") == null && (metaObject.getValue("maxCapacity") != null)) {
            this.strictInsertFill(metaObject, "currentCapacity", Integer.class, (Integer) metaObject.getValue("maxCapacity"));
        } else {
            this.strictInsertFill(metaObject, "currentCapacity", Integer.class, 0);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 更新时自动填充 updateTime
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    }
}