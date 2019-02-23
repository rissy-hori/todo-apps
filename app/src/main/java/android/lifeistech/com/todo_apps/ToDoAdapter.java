package android.lifeistech.com.todo_apps;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import io.realm.Realm;

public class ToDoAdapter extends ArrayAdapter<ToDo> {

    private LayoutInflater layoutinflater;
    public Realm realm;

    public ToDoAdapter(Context context, int textViewResourceId, List<ToDo> objects) {
        super(context, textViewResourceId, objects);
        layoutinflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        final int mPosition = position;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_item_todo, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final ToDo todo = getItem(position);

        if (todo != null) {
            viewHolder.titleText.setText(todo.title);
            viewHolder.checkBox.setChecked(todo.isChecked); // Realmで保存されたisCheckedをチェックボックスにを反映

            if(todo.isChecked){
                viewHolder.layout.setBackgroundColor(Color.GRAY);
            }else{
                viewHolder.layout.setBackgroundColor(Color.WHITE);
            }

            viewHolder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), DetailActivity.class);
                    intent.putExtra("updateDate", todo.updateDate);
                    view.getContext().startActivity(intent);
                }
            });

            viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {

                    final Realm realm = Realm.getDefaultInstance();
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm bgRealm) {
                            // クリックされたToDOに対応するデータを取得
                            ToDo clickedTodo = realm.where(ToDo.class).equalTo("updateDate",
                                    getItem(mPosition).updateDate).findFirst();

                            if (viewHolder.checkBox.isChecked() && clickedTodo.isChecked == false) {
                                clickedTodo.isChecked = true;
                                Snackbar.make(view, "Task marked complete", Snackbar.LENGTH_SHORT).show();
                                viewHolder.layout.setBackgroundColor(Color.GRAY);
                            }
                            if(!viewHolder.checkBox.isChecked() && clickedTodo.isChecked == true){
                                clickedTodo.isChecked = false;
                                Snackbar.make(view, "Task marked active", Snackbar.LENGTH_SHORT).show();
                                viewHolder.layout.setBackgroundColor(Color.WHITE);
                            }
                        }
                    });
                    realm.close();
                }
            });
        }
        Log.d("whatMethod","getView(Adapter)");
        return convertView;
    }

    public static class ViewHolder{
        TextView titleText;
        LinearLayout layout;
        CheckBox checkBox;

        public ViewHolder(View view){
            layout = view.findViewById(R.id.todo_layout);//リニアレイアウトにかえる
            titleText = view.findViewById(R.id.titleText);
            checkBox =view.findViewById(R.id.checkbox);
        }
    }


}
