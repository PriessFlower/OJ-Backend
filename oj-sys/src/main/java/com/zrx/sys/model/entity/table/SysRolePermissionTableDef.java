package com.zrx.sys.model.entity.table;
import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 * 系统角色权限 实体类 表定义
 */
public class SysRolePermissionTableDef extends TableDef {

    public static final SysRolePermissionTableDef SYS_ROLE_PERMISSION = new SysRolePermissionTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");
    public final QueryColumn ROLE_ID = new QueryColumn(this, "role_id");
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");
    public final QueryColumn PERMISSION_CODE = new QueryColumn(this, "permission_code");

    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, ROLE_ID, CREATE_TIME, PERMISSION_CODE};

    public SysRolePermissionTableDef() {
        super("", "sys_role_permission");
    }
}
