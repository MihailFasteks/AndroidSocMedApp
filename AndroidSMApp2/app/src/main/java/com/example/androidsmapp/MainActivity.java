package com.example.androidsmapp;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Дані для списку
        List<Map<String, Object>> chats = new ArrayList<>();

        Map<String, Object> chat1 = new HashMap<>();
        chat1.put("avatar", R.drawable.avatar1);
        chat1.put("name", "Михайло");
        chat1.put("last_message", "Привіт! Як справи?");
        chat1.put("time", "14:35");
        chat1.put("unread_count", 2);
        chat1.put("is_sent", true);
        chat1.put("is_read", false);
        chats.add(chat1);

        Map<String, Object> chat2 = new HashMap<>();
        chat2.put("avatar", R.drawable.avatar2);
        chat2.put("name", "Володимир");
        chat2.put("last_message", "Добре, дякую!");
        chat2.put("time", "Вчора");
        chat2.put("unread_count", 0);
        chat2.put("is_sent", true);
        chat2.put("is_read", true);
        chats.add(chat2);


        // SimpleAdapter
        String[] from = {"avatar", "name", "last_message", "time", "unread_count", "is_sent", "is_read"};
        int[] to = {R.id.avatar, R.id.name, R.id.last_message, R.id.time, R.id.unread_count, 0, 0};

        SimpleAdapter adapter = new SimpleAdapter(this, chats, R.layout.my_item, from, to) {
            @Override
            public void setViewText(android.widget.TextView v, String text) {
                if (v.getId() == R.id.unread_count) {
                    if (text.equals("0")) {
                        v.setVisibility(View.GONE);
                    } else {
                        v.setVisibility(View.VISIBLE);
                        super.setViewText(v, text);
                    }
                } else {
                    super.setViewText(v, text);
                }
            }

            @Override
            public void setViewImage(android.widget.ImageView v, int value) {
                v.setImageResource(value);
            }

            @Override
            public void setViewImage(android.widget.ImageView v, String value) {
                // Для простоти не використовуємо URL, бо SimpleAdapter не підтримує Glide
            }

            @Override
            public View getView(int position, View convertView, android.view.ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                Map<String, Object> item = (Map<String, Object>) getItem(position);
                int unread = (int) item.get("unread_count");
                boolean isSent = (boolean) item.get("is_sent");
                boolean isRead = (boolean) item.get("is_read");

                android.widget.ImageView statusIcon = view.findViewById(R.id.status_icon);

                if (unread == 0 && isSent) {
                    statusIcon.setVisibility(View.VISIBLE);
                    if (isRead) {
                        statusIcon.setImageResource(R.drawable.ic_double_check);
                    } else {
                        statusIcon.setImageResource(R.drawable.ic_check);
                    }
                } else {
                    statusIcon.setVisibility(View.GONE);
                }

                return view;
            }
        };

        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Map<String, Object> item = (Map<String, Object>) getListAdapter().getItem(position);
        Toast.makeText(this, item.get("name").toString(), Toast.LENGTH_SHORT).show();
    }
}
