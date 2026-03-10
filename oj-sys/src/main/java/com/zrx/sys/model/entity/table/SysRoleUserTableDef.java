package com.zrx.sys.model.entity.table;
import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 * 角色用户关系 实体类 表定义
 */
public class SysRoleUserTableDef extends TableDef {

    public static final SysRoleUserTableDef SYS_ROLE_USER = new SysRoleUserTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");
    public final QueryColumn ROLE_ID = new QueryColumn(this, "role_id");
    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");
    public final QueryColumn CREATOR = new QueryColumn(this, "creator");
    public final QueryColumn UPDATER = new QueryColumn(this, "updater");
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, ROLE_ID, USER_ID, CREATOR, UPDATER, CREATE_TIME, UPDATE_TIME};

    public SysRoleUserTableDef() {
        super("", "sys_role_user");
    }
}
