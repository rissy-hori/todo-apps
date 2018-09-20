package android.lifeistech.com.todo_apps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;

public class CreateActivity extends AppCompatActivity {

    // ToDoの新規作成画面　(Main->Create)

    public Realm realm;

    public EditText titleEditText;
    public EditText contentEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        // realmを開く
        realm = Realm.getDefaultInstance();

        // 関連付け
        titleEditText = (EditText) findViewById(R.id.titleEditText);
        contentEditText = (EditText) findViewById(R.id.contentEditText);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //realmを閉じる
        realm.close();
    }


    // 入力データを元にToDoを作る
    public void create(View view){
        // 1) タイトルを取得する
        String title = titleEditText.getText().toString();

        // 2) 日付を取得する
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.JAPANESE);
        String updateDate = sdf.format(date);

        // 3) 内容を取得する
        String content = contentEditText.getText().toString();

        // 4) 1)~3)を保存する
        save(title, updateDate, content);

        //画面を終了する(CreateActivityの終了)
        finish();
    }


    // 入力データをRealmに保存する
    public void save(final String title, final String updateDate, final String content){
        // todoを保存する
        realm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm bgRealm){
                ToDo todo = realm.createObject(ToDo.class);
                todo.title = title;
                todo.updateDate = updateDate;
                todo.content = content;
            }
        });
    }


}
