package com.example.task61foodrescueapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.task61foodrescueapp.data.DatabaseHelper;
import com.example.task61foodrescueapp.model.Food;

public class AddFoodItem extends AppCompatActivity {
    EditText food_title, food_description,time_added,food_quantity,location;
    CalendarView calendarView;
    ImageView image;
    Button saveButton;
    String date;
    int PICK_IMAGE_REQUEST = 100;
    Uri imageFilePath;
    Bitmap imageToStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_item);

        image = findViewById(R.id.insert_image);
        food_title = findViewById(R.id.imageTitle);
        food_description = findViewById(R.id.imageDescription);
        calendarView = findViewById(R.id.calendarView);
        time_added = findViewById(R.id.timeInputText);
        food_quantity = findViewById(R.id.quantityInputText);
        location = findViewById(R.id.locationInputText);
        saveButton = findViewById(R.id.saveButton);



        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                        date = dayOfMonth + "/" + (month+1) + "/" + year;
                    }
                });

                if (TextUtils.isEmpty(food_title.getText().toString()))
                {
                    Toast.makeText(AddFoodItem.this, "Title Field is Empty", Toast.LENGTH_SHORT).show();
                }

                else if (TextUtils.isEmpty(food_description.getText().toString()))
                {
                    Toast.makeText(AddFoodItem.this, "Description Field is Empty", Toast.LENGTH_SHORT).show();
                }
                else if (date == null)
                {
                    Toast.makeText(AddFoodItem.this, "Date Field is Empty", Toast.LENGTH_SHORT).show();
                }

                else if (TextUtils.isEmpty(time_added.getText().toString()))
                {
                    Toast.makeText(AddFoodItem.this, "Time Field is Empty", Toast.LENGTH_SHORT).show();
                }

                else if ( TextUtils.isEmpty(food_quantity.getText().toString()))
                {
                    Toast.makeText(AddFoodItem.this, "Quantity Field is Empty", Toast.LENGTH_SHORT).show();
                }

                else if (TextUtils.isEmpty(location.getText().toString()))
                {
                    Toast.makeText(AddFoodItem.this, "Location Field is Empty", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent1 = getIntent();
                    int user_Id = intent1.getIntExtra("USER_ID",0);
                    DatabaseHelper db = new DatabaseHelper(AddFoodItem.this);
                    db.insertFoodItem(new Food(user_Id, imageToStore,food_title.getText().toString(),food_description.getText().toString(),date,time_added.getText().toString(),food_quantity.getText().toString(),location.getText().toString()));
                    Intent intent = new Intent(AddFoodItem.this, HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    public void chooseImage(View objectView)
    {
        try
        {
            confirmDialog();
        }
        catch (Exception e)
        {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try
        {
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data!=null && data.getData()!=null);
            {
                imageFilePath = data.getData();
                imageToStore = MediaStore.Images.Media.getBitmap(getContentResolver(),imageFilePath);

                image.setImageBitmap(imageToStore);
            }
        }
        catch (Exception e)
        {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }


    void confirmDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Allow the app to access photos,media and files on your device?");
        builder.setPositiveButton("ALLOW", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent objectIntent = new Intent();
                objectIntent.setType("image/*");

                objectIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(objectIntent,PICK_IMAGE_REQUEST);


            }
        });

        builder.setNegativeButton("DENY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();
    }
}