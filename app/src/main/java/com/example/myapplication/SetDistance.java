package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class SetDistance extends AppCompatActivity {

    EditText distance;
    TextView textView;
    TextView nodes;
    Button previous;
    Button next;
    Button TSPbutton;
    Button setDistance;
    Button setNodes;

    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_distance);


        nodes = findViewById(R.id.nodes);
        previous = findViewById(R.id.previous);
        next = findViewById(R.id.next);
        distance = findViewById(R.id.distance);
        textView = findViewById(R.id.textView);
        TSPbutton = findViewById(R.id.toTSP_button);
        setDistance = findViewById(R.id.setdistance);
        setNodes = findViewById(R.id.setbutton);

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

        setNodes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNodes.setEnabled(false);
                previous.setEnabled(false);
                next.setEnabled(false);
                setDistance.setVisibility(View.VISIBLE);
            }
        });

        setDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int edgeCost = Integer.parseInt(distance.getText().toString());
                textView.setText(Integer.toString(edgeCost));

                Intent intent = new Intent(SetDistance.this, MainActivity.class);

                Bundle bundle = new Bundle();

                bundle.putInt("edge", edgeCost);

                intent.putExtras(bundle);
                startActivity(intent);

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
