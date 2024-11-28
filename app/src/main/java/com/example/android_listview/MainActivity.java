package com.example.android_listview;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // достаем элементы из разметки
        EditText editText = findViewById(R.id.editText);
        Button addButton = findViewById(R.id.addButton);
        ListView listView = findViewById(R.id.listView);

        // инициализация списка и адаптера
        list = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        // добавление текста из EditText в ListView
        addButton.setOnClickListener(v -> {
            String text = editText.getText().toString().trim();
            if (!text.isEmpty()) {
                list.add(text);
                adapter.notifyDataSetChanged(); // обновляем ListView
                editText.setText(""); // очистим поле ввода
            } else {
                Toast.makeText(this, "Enter text, please", Toast.LENGTH_SHORT).show();
            }
        });

        // короткое нажатие для отображения текста элемента
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String item = list.get(position);
            Toast.makeText(this, "You selected: " + item, Toast.LENGTH_SHORT).show();
        });

        // долгое нажатие для удаления элемента
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            list.remove(position); // удаляем элемент из списка
            adapter.notifyDataSetChanged(); // обновляем ListView
            Toast.makeText(this, "Item removed", Toast.LENGTH_SHORT).show();
            return true; // возвращаем true, чтобы событие было обработано
        });
    }
}
