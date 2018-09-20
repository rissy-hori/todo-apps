package android.lifeistech.com.todo_apps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    public Realm realm;

    public ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();
        listView = findViewById(R.id.listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // ListViewのposition番目に対応するメモの情報を持ってきている
                ToDo todo = (ToDo) parent.getItemAtPosition(position);

                // DetailActivityへのIntentを作る(詳細画面に遷移する準備をする)
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);

                // ToDoクラスのupdateDateという変数の値を、"updateDate"という鍵を使ってIntentにセットしている
                intent.putExtra("updateDate", todo.updateDate);
                //intent.putExtra("title", todo.title);      <-不要　
                //intent.putExtra("content", todo.content);  <-不要

                // 作ったIntentを使って詳細画面に遷移する
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        setToDoList();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //realmを閉じる
        realm.close();
    }

    public void create(View v){
        Intent intent = new Intent(this, CreateActivity.class);
        startActivity(intent);
    }

    public void setToDoList(){
        // realmから読み取る
        RealmResults<ToDo> results = realm.where(ToDo.class).findAll();
        List<ToDo> items = realm.copyFromRealm(results);

        // adapterにデータを渡す
        ToDoAdapter adapter = new ToDoAdapter(this, R.layout.layout_item_todo, items);

        // listViewにadapterをセット
        listView.setAdapter(adapter);
    }


}
