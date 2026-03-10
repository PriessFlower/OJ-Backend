package com.zrx.sys.model.entity.table;
import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 * 系统角色 实体类 表定义
 */
public class SysRoleTableDef extends TableDef {

    public static final SysRoleTableDef SYS_ROLE = new SysRoleTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");
    public final QueryColumn SORT = new QueryColumn(this, "sort");
    public final QueryColumn DEPT_ID = new QueryColumn(this, "dept_id");
    public final QueryColumn REMARK = new QueryColumn(this, "remark");
    public final QueryColumn CREATOR = new QueryColumn(this, "creator");
    public final QueryColumn DEL_FLAG = new QueryColumn(this, "del_flag");
    public final QueryColumn UPDATER = new QueryColumn(this, "updater");
    public final QueryColumn ROLE_CODE = new QueryColumn(this, "role_code");
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, SORT, DEPT_ID, REMARK, CREATOR, UPDATER, ROLE_CODE, CREATE_TIME, UPDATE_TIME};

    public SysRoleTableDef() {
        super("", "sys_role");
    }
}
