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
    Button example;

    int counter = 1;
    int i = 0;
    int j = 1;

    int[][] adjMatrix =

        {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        };

    int[][] adjDefault =
            {
                    {0, 10, 15, 20,  0, 0, 0, 0, 0, 0, 0, 0},
                    {10, 0, 35, 250, 0, 0, 0, 0, 0, 0, 0, 0},
                    {15, 35, 0, 30,  0, 0, 0, 0, 0, 0, 0, 0},
                    {20, 250, 30, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }
            };

    ArrayList <Integer> nodeList;

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
        example = findViewById(R.id.example);


        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter--;

                if(counter < 2){
                    previous.setEnabled(false);
                }
                if(counter<12){
                    next.setEnabled(true);
                }

                nodes.setText(String.valueOf(counter));
            }
        });

        previous.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                counter -= 10;

                if(counter < 2){
                    previous.setEnabled(false);
                }
                if(counter<12){
                    next.setEnabled(true);
                }

                nodes.setText(String.valueOf(counter));
                return true;
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;

                if(counter > 1){
                    previous.setEnabled(true);
                }

                if(counter > 11){
                    next.setEnabled(false);
                }

                nodes.setText(String.valueOf(counter));
            }
        });

        next.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                counter += 10;

                if(counter > 1){
                    previous.setEnabled(true);
                }

                if(counter > 11){
                    next.setEnabled(false);
                }


                nodes.setText(String.valueOf(counter));
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

                nodeList = new ArrayList<>(counter);

                for(int i=0;i<counter;i++){
                    nodeList.add(i);
                }
            }
        });

        setDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = "";

                int edgeCost = Integer.parseInt(distance.getText().toString());

                adjMatrix[i][j] = edgeCost;
                adjMatrix[j][i] = edgeCost;

                if(j == counter-1){
                    i += 1;
                    j = i;
                }

                if (i<counter-1) {
                    message = "Enter Edge Cost of : " + nodeList.get(i) + " - " + nodeList.get(++j);
                }
                else{
                    setDistance.setEnabled(false);
                    Intent intent = new Intent(SetDistance.this, MainActivity.class);

                    Bundle bundle = new Bundle();
                    bundle.putInt("nodes", counter);
                    bundle.putSerializable("matrix", adjMatrix);

                    intent.putExtras(bundle);
                    startActivity(intent);
                }

                textView.setText(message);
            }
        });

        example.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetDistance.this, MainActivity.class);

                Bundle bundle = new Bundle();
                bundle.putInt("nodes", counter);
                bundle.putSerializable("matrix", adjDefault);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

}