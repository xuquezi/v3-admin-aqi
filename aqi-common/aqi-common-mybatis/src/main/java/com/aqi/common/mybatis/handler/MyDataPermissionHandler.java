package com.aqi.common.mybatis.handler;

import cn.hutool.core.util.StrUtil;
import com.aqi.common.secure.annotation.DataPermission;
import com.aqi.common.secure.enums.DataScopeEnum;
import com.aqi.common.secure.utils.SecureUtil;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;

import java.lang.reflect.Method;

@Slf4j
public class MyDataPermissionHandler implements DataPermissionHandler {
    @Override
    @SneakyThrows
    public Expression getSqlSegment(Expression where, String mappedStatementId) {
        // 超级管理员不受数据权限控制
        if (SecureUtil.isAdmin()) {
            return where;
        }
        Class<?> clazz = Class.forName(mappedStatementId.substring(0, mappedStatementId.lastIndexOf(StringPool.DOT)));
        String methodName = mappedStatementId.substring(mappedStatementId.lastIndexOf(StringPool.DOT) + 1);
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            DataPermission annotation = method.getAnnotation(DataPermission.class);
            if (ObjectUtils.isNotEmpty(annotation) && (method.getName().equals(methodName) || (method.getName() + "_COUNT").equals(methodName))) {
                return dataScopeFilter(annotation.deptAlias(), annotation.deptIdColumnName(), annotation.userAlias(), annotation.userIdColumnName(), where);
            }
        }
        return where;
    }

    @SneakyThrows
    public static Expression dataScopeFilter(String deptAlias, String deptIdColumnName, String userAlias, String userIdColumnName, Expression where) {
        String deptColumnName = StrUtil.isNotBlank(deptAlias) ? (deptAlias + StringPool.DOT + deptIdColumnName) : deptIdColumnName;
        String userColumnName = StrUtil.isNotBlank(userAlias) ? (userAlias + StringPool.DOT + userIdColumnName) : userIdColumnName;
        // 获取当前用户的数据权限
        Integer dataScope = SecureUtil.getDataScope();
        DataScopeEnum dataScopeEnum = DataScopeEnum.getDataScopeEnum(dataScope.intValue());
        Long deptId, userId;
        String appendSqlStr;
        switch (dataScopeEnum) {
            case ALL:
                return where;
            case DEPT:
                deptId = SecureUtil.getDeptId();
                appendSqlStr = deptColumnName + StringPool.EQUALS + deptId;
                break;
            case SELF:
                userId = SecureUtil.getUserId();
                appendSqlStr = userColumnName + StringPool.EQUALS + userId;
                break;
            // 默认部门及子部门数据权限
            default:
                deptId = SecureUtil.getDeptId();
                appendSqlStr = deptColumnName + " IN ( SELECT dept_id FROM sys_dept WHERE dept_id = " + deptId + " OR '" + deptId + "'= ANY (STRING_TO_ARRAY(tree_path, ',')))";
                break;
        }
        if (StrUtil.isBlank(appendSqlStr)) {
            return where;
        }
        Expression appendExpression = CCJSqlParserUtil.parseCondExpression(appendSqlStr);
        if (where == null) {
            return appendExpression;
        }
        return new AndExpression(where, appendExpression);
    }
}
