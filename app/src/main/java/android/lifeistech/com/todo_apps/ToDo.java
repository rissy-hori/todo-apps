package android.lifeistech.com.todo_apps;

import io.realm.RealmObject;

public class ToDo extends RealmObject{

    // タイトル
    public String title;
    // 日付
    public String updateDate;
    // 内容
    public String content;
    // checkBox
    public boolean isChecked;


}


