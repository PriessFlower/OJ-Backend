package com.zrx.sys.model.entity.table;
import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

/**
 * 系统用户 实体类 表定义
 */
public class SysUserTableDef extends TableDef {

    public static final SysUserTableDef SYS_USER = new SysUserTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");
    public final QueryColumn SALT = new QueryColumn(this, "salt");
    public final QueryColumn EMAIL = new QueryColumn(this, "email");
    public final QueryColumn AVATAR = new QueryColumn(this, "avatar");
    public final QueryColumn GENDER = new QueryColumn(this, "gender");
    public final QueryColumn MOBILE = new QueryColumn(this, "mobile");
    public final QueryColumn STATUS = new QueryColumn(this, "status");
    public final QueryColumn CREATOR = new QueryColumn(this, "creator");
    public final QueryColumn UPDATER = new QueryColumn(this, "updater");
    public final QueryColumn NICK_NAME = new QueryColumn(this, "nick_name");
    public final QueryColumn PASSWORD = new QueryColumn(this, "password");
    public final QueryColumn REAL_NAME = new QueryColumn(this, "real_name");
    public final QueryColumn USERNAME = new QueryColumn(this, "username");
    public final QueryColumn INTRODUCE = new QueryColumn(this, "introduce");
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, SALT, EMAIL, AVATAR, GENDER, MOBILE, STATUS, CREATOR, UPDATER, NICK_NAME, PASSWORD, REAL_NAME, USERNAME, INTRODUCE, CREATE_TIME, UPDATE_TIME};

    public SysUserTableDef() {
        super("", "sys_user");
    }
}
