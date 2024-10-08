package com.example.practika2;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AppointmentActivity extends AppCompatActivity {

    EditText editName, editPhone;
    DatePicker datePicker;
    TimePicker timePicker;
    Spinner serviceSpinner;
    Button btnSubmit;
    DBHelper dbHelper;

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
        dbHelper = new com.example.practika2.DBHelper(this);

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
                String name = editName.getText().toString().trim();
                String phone = editPhone.getText().toString().trim();
                String service = serviceSpinner.getSelectedItem().toString();

                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1;
                int year = datePicker.getYear();
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();

                // Проверка на пустые поля
                if (name.isEmpty()) {
                    editName.setError("Введите ваше имя");
                } else if (phone.isEmpty()) {
                    editPhone.setError("Введите номер телефона");
                } else if (!phone.matches("\\d{10}")) {
                    editPhone.setError("Введите корректный номер телефона");
                } else {
                    // Подтверждение записи
                    new AlertDialog.Builder(AppointmentActivity.this)
                            .setTitle("Подтверждение записи")
                            .setMessage("Вы уверены, что хотите записаться на " + service + " на дату " +
                                    day + "/" + month + "/" + year + " в " + hour + ":" + minute + "?")
                            .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    boolean isInserted = dbHelper.insertAppointment(name, phone, service,
                                            day + "/" + month + "/" + year, hour + ":" + minute);

                                    if (isInserted) {
                                        Toast.makeText(AppointmentActivity.this, "Запись успешно сохранена!", Toast.LENGTH_SHORT).show();
                                        editName.setText("");
                                        editPhone.setText("");
                                    } else {
                                        Toast.makeText(AppointmentActivity.this, "Ошибка при записи", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .setNegativeButton("Отмена", null)
                            .show();
                }
            }
        });
    }
}
