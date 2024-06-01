package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;




public class MainActivity extends AppCompatActivity {
    private Button add;
    private AlertDialog dialog;
    private LinearLayout layout;
    private RelativeLayout splashLayout;
    private ScrollView mainLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add=findViewById(R.id.add);
        layout=findViewById(R.id.container);
        splashLayout = findViewById(R.id.splashLayout);
        mainLayout = findViewById(R.id.mainLayout);

        buildDialog();
        // Handle the splash screen delay and fade out
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                splashLayout.animate().alpha(0.0f).setDuration(1000);
                mainLayout.setVisibility(View.VISIBLE);
            }
        },2000);// 2 secs delay

        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                dialog.show();
            }
        });
    }
    public void buildDialog(){
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog,null);

        final EditText name = view.findViewById(R.id.nameEdit);
        builder.setView(view);
        builder.setTitle("Enter your Task")
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addCard(name.getText().toString());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        dialog = builder.create();

    }
    private void addCard(final String taskName){
        final View view = getLayoutInflater().inflate(R.layout.card, null);
        TextView nameView = view.findViewById(R.id.name);
        Button delete = view.findViewById(R.id.delete);
        Button complete = view.findViewById(R.id.complete);
        nameView.setText(taskName);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteConfirmationDialog(view);
            }
        });

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.removeView(view);
                Toast.makeText(MainActivity.this, "Task Completed! Great Job!", Toast.LENGTH_SHORT).show();
            }
        });
        layout.addView(view);
    }
    private void showDeleteConfirmationDialog(final View cardView) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Task")
                .setMessage("Are you sure you want to delete this task?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        layout.removeView(cardView);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}