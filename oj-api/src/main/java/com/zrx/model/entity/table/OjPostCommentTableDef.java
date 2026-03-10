package com.zrx.model.entity.table;
import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

public class OjPostCommentTableDef extends TableDef {

    public static final OjPostCommentTableDef OJ_POST_COMMENT = new OjPostCommentTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");
    public final QueryColumn POST_ID = new QueryColumn(this, "post_id");
    public final QueryColumn CONTENT = new QueryColumn(this, "content");
    public final QueryColumn DEL_FLAG = new QueryColumn(this, "del_flag");
    public final QueryColumn AUTHOR_ID = new QueryColumn(this, "author_id");
    public final QueryColumn PARENT_ID = new QueryColumn(this, "parent_id");
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, POST_ID, CONTENT, AUTHOR_ID, PARENT_ID, CREATE_TIME};

    public OjPostCommentTableDef() {
        super("", "oj_post_comment");
    }
}
