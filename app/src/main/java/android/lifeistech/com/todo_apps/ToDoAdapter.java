package android.lifeistech.com.todo_apps;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

public class ToDoAdapter extends ArrayAdapter<ToDo> {

    private LayoutInflater layoutinflater;
    private Context context;

    public ToDoAdapter(Context context, int textViewResourceId, List<ToDo> objects) {
        super(context, textViewResourceId, objects);
        layoutinflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //context.startActivity(intent)のため
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_item_todo, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final ToDo todo = getItem(position);

        if(todo != null){
            viewHolder.titleText.setText(todo.title);

            viewHolder.layout.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(view.getContext(), DetailActivity.class);                // MainActivity.thisだとエラー
                    intent.putExtra("updateDate", todo.updateDate);
                    // 作ったIntentを使って詳細画面に遷移する view.getContext()
                    context.startActivity(intent);
                }
            });

            viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (todo.isChecked == false){
                        // task complete

                        Snackbar.make(view, "Task marked complete", Snackbar.LENGTH_SHORT).show();
                        viewHolder.layout.setBackgroundColor(Color.GRAY);
                        todo.isChecked = true;
                    } else {
                        // task active
                        Snackbar.make(view, "Task marked active", Snackbar.LENGTH_SHORT).show();
                        viewHolder.layout.setBackgroundColor(Color.WHITE);
                        todo.isChecked = false;
                    }
                }
            });
        }
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
