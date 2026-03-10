package com.zrx.model.entity.table;
import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

public class ProblemCommentTableDef extends TableDef {

    public static final ProblemCommentTableDef PROBLEM_COMMENT = new ProblemCommentTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");
    public final QueryColumn TAGS = new QueryColumn(this, "tags");
    public final QueryColumn CONTENT = new QueryColumn(this, "content");
    public final QueryColumn DEL_FLAG = new QueryColumn(this, "del_flag");
    public final QueryColumn AUTHOR_ID = new QueryColumn(this, "author_id");
    public final QueryColumn PARENT_ID = new QueryColumn(this, "parent_id");
    public final QueryColumn PROBLEM_ID = new QueryColumn(this, "problem_id");
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, TAGS, CONTENT, AUTHOR_ID, PARENT_ID, PROBLEM_ID, CREATE_TIME};

    public ProblemCommentTableDef() {
        super("", "oj_problem_comment");
    }
}
