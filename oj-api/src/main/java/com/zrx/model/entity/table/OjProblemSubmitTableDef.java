package com.zrx.model.entity.table;
import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

public class OjProblemSubmitTableDef extends TableDef {

    public static final OjProblemSubmitTableDef OJ_PROBLEM_SUBMIT = new OjProblemSubmitTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");
    public final QueryColumn CODE = new QueryColumn(this, "code");
    public final QueryColumn OUTPUT = new QueryColumn(this, "output");
    public final QueryColumn STATUS = new QueryColumn(this, "status");
    public final QueryColumn USER_ID = new QueryColumn(this, "user_id");
    public final QueryColumn IS_DELETE = new QueryColumn(this, "is_delete");
    public final QueryColumn LANGUAGE = new QueryColumn(this, "language");
    public final QueryColumn JUDGE_INFO = new QueryColumn(this, "judge_info");
    public final QueryColumn CODE_STATUS = new QueryColumn(this, "code_status");
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");
    public final QueryColumn QUESTION_ID = new QueryColumn(this, "question_id");
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, CODE, OUTPUT, STATUS, USER_ID, LANGUAGE, JUDGE_INFO, CODE_STATUS, CREATE_TIME, QUESTION_ID, UPDATE_TIME};

    public OjProblemSubmitTableDef() {
        super("", "oj_problem_submit");
    }
}