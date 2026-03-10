package com.zrx.model.entity.table;
import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

public class OjProblemTableDef extends TableDef {

    public static final OjProblemTableDef OJ_PROBLEM = new OjProblemTableDef();

    public final QueryColumn ID = new QueryColumn(this, "id");
    public final QueryColumn TAGS = new QueryColumn(this, "tags");
    public final QueryColumn TITLE = new QueryColumn(this, "title");
    public final QueryColumn ANSWER = new QueryColumn(this, "answer");
    public final QueryColumn CONTENT = new QueryColumn(this, "content");
    public final QueryColumn CREATOR = new QueryColumn(this, "creator");
    public final QueryColumn DEL_FLAG = new QueryColumn(this, "del_flag");
    public final QueryColumn UPDATER = new QueryColumn(this, "updater");
    public final QueryColumn THUMB_NUM = new QueryColumn(this, "thumb_num");
    public final QueryColumn FAVOUR_NUM = new QueryColumn(this, "favour_num");
    public final QueryColumn JUDGE_CASE = new QueryColumn(this, "judge_case");
    public final QueryColumn SUBMIT_NUM = new QueryColumn(this, "submit_num");
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");
    public final QueryColumn DIFFICULTY = new QueryColumn(this, "difficulty");
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");
    public final QueryColumn ACCEPTED_NUM = new QueryColumn(this, "accepted_num");
    public final QueryColumn ANS_LANGUAGE = new QueryColumn(this, "ans_language");
    public final QueryColumn JUDGE_CONFIG = new QueryColumn(this, "judge_config");

    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, TAGS, TITLE, ANSWER, CONTENT, CREATOR, UPDATER, THUMB_NUM, FAVOUR_NUM, JUDGE_CASE, SUBMIT_NUM, CREATE_TIME, DIFFICULTY, UPDATE_TIME, ACCEPTED_NUM, ANS_LANGUAGE, JUDGE_CONFIG};

    public OjProblemTableDef() {
        super("", "oj_problem");
    }
}
