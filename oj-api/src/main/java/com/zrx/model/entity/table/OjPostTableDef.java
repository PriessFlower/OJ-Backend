package com.zrx.model.entity.table;
import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

public class OjPostTableDef extends TableDef {

    public static final OjPostTableDef OJ_POST = new OjPostTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");
    public final QueryColumn TAGS = new QueryColumn(this, "tags");
    public final QueryColumn ZONE = new QueryColumn(this, "zone");
    public final QueryColumn TITLE = new QueryColumn(this, "title");
    public final QueryColumn CONTENT = new QueryColumn(this, "content");
    public final QueryColumn CREATOR = new QueryColumn(this, "creator");
    public final QueryColumn DEL_FLAG = new QueryColumn(this, "del_flag");
    public final QueryColumn UPDATER = new QueryColumn(this, "updater");
    public final QueryColumn VIEW_NUM = new QueryColumn(this, "view_num");
    public final QueryColumn THUMB_NUM = new QueryColumn(this, "thumb_num");
    public final QueryColumn FAVOUR_NUM = new QueryColumn(this, "favour_num");
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, TAGS, ZONE, TITLE, CONTENT, CREATOR, UPDATER, VIEW_NUM, THUMB_NUM, FAVOUR_NUM, CREATE_TIME, UPDATE_TIME};

    public OjPostTableDef() {
        super("", "oj_post");
    }
}
