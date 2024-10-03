package com.example.practika2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AppointmentActivity extends AppCompatActivity {

    EditText editName, editPhone;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        // Инициализация полей для ввода
        editName = findViewById(R.id.edit_name);
        editPhone = findViewById(R.id.edit_phone);
        btnSubmit = findViewById(R.id.btn_submit);

        // Обработка нажатия на кнопку "Записаться"
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Получение данных из полей ввода
                String name = editName.getText().toString().trim();
                String phone = editPhone.getText().toString().trim();

                // Проверка на заполненность полей
                if (name.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(AppointmentActivity.this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
                } else {
                    // Сохранение данных в SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences("Appointments", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("name", name);
                    editor.putString("phone", phone);
                    editor.apply();

                    // Вывод сообщения об успешной записи
                    Toast.makeText(AppointmentActivity.this, "Запись успешно сохранена!", Toast.LENGTH_SHORT).show();

                    // Очищаем поля после записи
                    editName.setText("");
                    editPhone.setText("");
                }
            }
        });
    }
}
