package android.lifeistech.com.todo_apps;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import io.realm.Realm;

public class DetailActivity extends AppCompatActivity {

    // 詳細表示　(Main -> Detail)

    public Realm realm;

    public TextView title, content;
    public CheckBox checkBox;
    public LinearLayout layout;

    public Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        realm = Realm.getDefaultInstance();
        title = findViewById(R.id.title_in_detail);
        content = findViewById(R.id.content_in_detail);
        layout = findViewById(R.id.background);
        checkBox = findViewById(R.id.checkbox);

        checkBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final ToDo todo = realm.where(ToDo.class).equalTo("updateDate",
                        getIntent().getStringExtra("updateDate")).findFirst();
                if (todo.isChecked == false){
                    // task complete

                    Snackbar.make(view, "Task marked complete", Snackbar.LENGTH_SHORT).show();
                    realm.beginTransaction();
                    todo.isChecked = true;
                    realm.commitTransaction();

                } else {
                    // task active
                    Snackbar.make(view, "Task marked active", Snackbar.LENGTH_SHORT).show();
                    realm.beginTransaction();
                    todo.isChecked = false;
                    realm.commitTransaction();
                }
            }
        });

        showDetail();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list_2, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int itemId =item.getItemId();

        if (itemId == R.id.delete){
            final ToDo todo = realm.where(ToDo.class).equalTo("updateDate",
                    getIntent().getStringExtra("updateDate")).findFirst();
            realm.beginTransaction();
            todo.deleteFromRealm();
            realm.commitTransaction();

            finish();
        }

        if(itemId == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public void showDetail(){
        final ToDo todo = realm.where(ToDo.class).equalTo("updateDate",
                getIntent().getStringExtra("updateDate")).findFirst();

        if(todo != null){
            // 表示
            title.setText(todo.title);
            content.setText(todo.content);
            checkBox.setChecked(todo.isChecked);

            // Edit画面遷移の準備
            intent = new Intent(this, EditActivity.class);
            intent.putExtra("updateDate", todo.updateDate);
        }
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
