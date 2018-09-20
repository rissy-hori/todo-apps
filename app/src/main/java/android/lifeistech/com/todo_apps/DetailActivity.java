package android.lifeistech.com.todo_apps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import io.realm.Realm;

public class DetailActivity extends AppCompatActivity {

    // 詳細表示　(Main -> Detail)

    public Realm realm;

    public TextView title, content;

    public Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        realm = Realm.getDefaultInstance();
        title = findViewById(R.id.title_in_detail);
        content = findViewById(R.id.content_in_detail);

        showDetail();
    }

    public void showDetail(){
        final ToDo todo = realm.where(ToDo.class).equalTo("updateDate",
                getIntent().getStringExtra("updateDate")).findFirst();

        // テキスト表示
        title.setText(todo.title);
        content.setText(todo.content);

        // Edit画面遷移の準備
        intent = new Intent(this, EditActivity.class);
        intent.putExtra("updateDate", todo.updateDate);
    }

    public void edit(View v){
        startActivity(intent);
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //realmを閉じる
        realm.close();
    }
}
