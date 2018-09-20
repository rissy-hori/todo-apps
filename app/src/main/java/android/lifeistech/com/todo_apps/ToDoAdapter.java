package android.lifeistech.com.todo_apps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ToDoAdapter extends ArrayAdapter<ToDo> {

    private LayoutInflater layoutinflater;

    ToDoAdapter(Context context, int textViewResourceId, List<ToDo> objects) {
        super(context, textViewResourceId, objects);
        layoutinflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

       ToDo todo = getItem(position);

        if (convertView == null) {
            convertView = layoutinflater.inflate(R.layout.layout_item_todo_1, null);
        }

        TextView titleText = (TextView) convertView.findViewById(R.id.titleText);
        //TextView contentText = (TextView) convertView.findViewById(R.id.contentText);

        titleText.setText(todo.title);
        //contentText.setText(todo.content);

        return convertView;
    }
}
