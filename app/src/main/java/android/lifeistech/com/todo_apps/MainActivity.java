package android.lifeistech.com.todo_apps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    public Realm realm;

    public ListView listView;
    public TextView titleMain;

    private RealmResults<ToDo> results;
    private List<ToDo> items;
    private ToDoAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();
        listView = findViewById(R.id.listView);
        titleMain = findViewById(R.id.titleMain);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int itemId =item.getItemId();
        switch(itemId){
            case R.id.menuAll:
                titleMain.setText("All TO-DOs");
                results = realm.where(ToDo.class).findAll();
                break;
            case R.id.menuActive:
                titleMain.setText("Active TO-DOs");
                results = realm.where(ToDo.class).equalTo("isChecked", false).findAll();
                break;
            case R.id.menuComplete:
                titleMain.setText("Complete TO-DOs");
                results = realm.where(ToDo.class).equalTo("isChecked", true).findAll();
                break;
        }
        items = realm.copyFromRealm(results);
        ToDoAdapter adapter = new ToDoAdapter(this, R.layout.layout_item_todo, items);
        listView.setAdapter(adapter);

        return super.onOptionsItemSelected(item);
    }

    public void create(View v){
        Intent intent = new Intent(this, CreateActivity.class);
        startActivity(intent);
    }

    public void setToDoList(){
        results = realm.where(ToDo.class).findAll();
        items = realm.copyFromRealm(results);
        adapter = new ToDoAdapter(this, R.layout.layout_item_todo, items);

        listView.setAdapter(adapter);
    }


}
