package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SetDistance extends AppCompatActivity {

    EditText editText;
    TextView textView;
    TextView nodes;
    Button previous;
    Button next;

    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_distance);


        nodes = findViewById(R.id.nodes);
        previous = findViewById(R.id.previous);
        next = findViewById(R.id.next);
        editText = findViewById(R.id.editText);


        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter--;
                nodes.setText(Integer.toString(counter));
            }
        });

        previous.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                counter -= 10;
                nodes.setText(Integer.toString(counter));
                return true;
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                nodes.setText(Integer.toString(counter));
            }
        });

        next.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                counter += 10;
                nodes.setText(Integer.toString(counter));
                return true;
            }
        });

    }

    public void findTSP(View view){

        Intent intent = new Intent(SetDistance.this, MainActivity.class);

        Bundle bundle = new Bundle();
        bundle.putInt("nodes", counter);

        intent.putExtras(bundle);
        startActivity(intent);

    }
}
