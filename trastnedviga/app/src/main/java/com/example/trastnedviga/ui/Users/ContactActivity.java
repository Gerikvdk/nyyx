package com.example.trastnedviga.ui.Users;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.trastnedviga.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.paperdb.Paper;

public class ContactActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);


        CardView cardtel;
        CardView inst;
        CardView potop;
        CardView web;
        CardView location;


        cardtel = (CardView) findViewById(R.id.smart);
        location = (CardView) findViewById(R.id.location);
        inst = (CardView) findViewById(R.id.inst);
        potop = (CardView) findViewById(R.id.potop);
        web = (CardView) findViewById(R.id.web);


        web.setOnClickListener(this);
        location.setOnClickListener(this);
        potop.setOnClickListener(this);
        inst.setOnClickListener(this);
        cardtel.setOnClickListener(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paper.book().destroy();
                Intent loginIntent = new Intent(ContactActivity.this, HomeActivity.class);
                startActivity(loginIntent);
            }
        });






    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.web:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://joystickcompany2.ukit.me"));
                startActivity(intent);
                break;
            case R.id.inst:
                intent = new Intent();
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://instagram.com/gerik.vdk?utm_medium=copy_link"));
                startActivity(intent);
                break;
            case R.id.smart:
                intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+7 (914) 070-00-96"));
                startActivity(intent);
                break;
                case R.id.location:
                    String url = "https://www.google.ru/maps/search/вгуэс/@43.4425757,131.4825442,9z/data=!3m1!4b1";
                    intent = new Intent(android.content.Intent.ACTION_VIEW,Uri.parse(url));
                    startActivity(intent);
                break;
                case R.id.potop:
                    Intent email = new Intent(Intent.ACTION_SEND);
                    email.putExtra(Intent.EXTRA_EMAIL, new String[]{"dimaakulichd6618@gmail.com"});
                    email.putExtra(Intent.EXTRA_SUBJECT, "ОПИШИТЕ ТЕМУ ПИСЬМА!");
                    email.putExtra(Intent.EXTRA_TEXT, "Здравствуйте! " +"\n"+
                            "Темы для отправления письма." +"\n"+"\n"+
                            "1.Нужна помощь специалиста."+"\n"+" Оставьте пожалуйста ваш номер телефона и перезвоним вам." +"\n"+"\n"+
                            "2.Не работает приложение."+"\n"+" Опишите проблему и по возможности предоставьте медиа материал." +"\n"+ "\n"+
                            "3.Хочу сотрудничать."+"\n"+" Опишите предоставляемые услуги или товар." +"\n"+ "\n"+
                            "4.Есть ли вакансии. "+"\n"+"Укажите ваш город и предоставьте резюме в документе." +"\n"+ "\n"+
                            "5.Другое." +"\n"+ "\n"+
                            "Здравствуйте!");
                    email.setType("message/rfc822");
                    startActivity(Intent.createChooser(email, " Выберите удобную вам почту:"));
                    break;
        }

    }
}