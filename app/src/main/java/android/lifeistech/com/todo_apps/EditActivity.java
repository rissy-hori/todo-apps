package android.lifeistech.com.todo_apps;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;

public class EditActivity extends AppCompatActivity {

    // 編集画面 (Main -> Detail -> Edit)

    public Realm realm;

    public EditText titleEditText;
    public EditText contentEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        realm = Realm.getDefaultInstance();
        titleEditText = findViewById(R.id.titleEditText);
        contentEditText = findViewById(R.id.contentEditText);

        showData();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int itemId = item.getItemId();
        if(itemId == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void showData(){
        final ToDo todo = realm.where(ToDo.class).equalTo("updateDate",
                getIntent().getStringExtra("updateDate")).findFirst();
        // テキスト表示
        titleEditText.setText(todo.title);
        contentEditText.setText(todo.content);
    }


    // 入力データを元にToDoを作る
    public void recreate(View view){
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

        //画面を終了する(EditActivityの終了)
        finish();
    }


    // 入力データをRealmに保存する
    public void save(final String title, final String updateDate, final String content){
        // todoを保存する
        realm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm bgRealm){
                ToDo todo = realm.where(ToDo.class).equalTo("updateDate",
                        getIntent().getStringExtra("updateDate")).findFirst();
                todo.title = title;
                todo.updateDate = updateDate;
                todo.content = content;
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        //realmを閉じる
        realm.close();
    }
}
