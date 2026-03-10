package com.zrx.model.entity.table;
import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

public class OjPostThumbTableDef extends TableDef {

    public static final OjPostThumbTableDef OJ_POST_THUMB = new OjPostThumbTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");
    public final QueryColumn POST_ID = new QueryColumn(this, "post_id");
    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, POST_ID, USER_ID, CREATE_TIME};

    public OjPostThumbTableDef() {
        super("", "oj_post_thumb");
    }
}