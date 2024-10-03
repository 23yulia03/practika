package com.example.practika2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AppointmentActivity extends AppCompatActivity {

    EditText editName, editPhone;
    DatePicker datePicker;
    TimePicker timePicker;
    Spinner serviceSpinner;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        // Инициализация элементов
        editName = findViewById(R.id.edit_name);
        editPhone = findViewById(R.id.edit_phone);
        datePicker = findViewById(R.id.date_picker);
        timePicker = findViewById(R.id.time_picker);
        serviceSpinner = findViewById(R.id.spinner_service);
        btnSubmit = findViewById(R.id.btn_submit);

        timePicker.setIs24HourView(true); // Установка 24-часового формата

        // Инициализация адаптера для Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.services_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        serviceSpinner.setAdapter(adapter);

        // Обработка нажатия на кнопку "Записаться"
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Получение данных
                String name = editName.getText().toString().trim();
                String phone = editPhone.getText().toString().trim();
                String service = serviceSpinner.getSelectedItem().toString();

                // Получение даты
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1;  // Месяцы начинаются с 0
                int year = datePicker.getYear();

                // Получение времени
                int hour;
                int minute;

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    hour = timePicker.getHour();
                    minute = timePicker.getMinute();
                } else {
                    hour = timePicker.getCurrentHour();
                    minute = timePicker.getCurrentMinute();
                }

                // Проверка заполненности полей
                if (name.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(AppointmentActivity.this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
                } else {
                    // Сохранение данных в SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences("Appointments", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("name", name);
                    editor.putString("phone", phone);
                    editor.putString("service", service);
                    editor.putString("date", day + "/" + month + "/" + year);
                    editor.putString("time", hour + ":" + minute);
                    editor.apply();

                    // Вывод сообщения об успешной записи
                    Toast.makeText(AppointmentActivity.this, "Запись успешно сохранена!", Toast.LENGTH_SHORT).show();

                    // Очистка полей
                    editName.setText("");
                    editPhone.setText("");
                }
            }
        });
    }
}
