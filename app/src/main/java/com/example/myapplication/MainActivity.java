package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    static String pathString = "";
    static int N = 12;

    static int[] optimal_path = new int[N + 1];

    static boolean[] visited = new boolean[N];

    static int final_res = Integer.MAX_VALUE;

    public int[][] adj =
            {
            {0, 10, 15, 20,  0,0,0,0,0,0,0,0},
            {10, 0, 35, 25, 0,0,0,0,0,0,0,0},
            {15, 35, 0, 30, 0,0,0,0,0,0,0,0},
            {20, 25, 30, 0,  0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0, 0,0,0,0,0,0},
            {0,0,0,0,0,0, 0,0,0,0,0,0},
            {0,0,0,0,0,0, 0,0,0,0,0,0},
            {0,0,0,0,0,0, 0,0,0,0,0,0},
            {0,0,0,0,0,0, 0,0,0,0,0,0},
            {0,0,0,0,0,0, 0,0,0,0,0,0},
            {0,0,0,0,0,0, 0,0,0,0,0,0},
            {0,0,0,0,0,0, 0,0,0,0,0,0},
            };

    Button showCost;
    Button showPath;
    TextView textView;
    TextView textView1;
    TextView optimal_path_text;

    int numberOfNodes = 0;
    int[][] edgeCost;
    String s = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showCost = findViewById(R.id.show_cost_button);
        showPath = findViewById(R.id.show_path_button);
        textView = findViewById(R.id.cost);
        textView1 = findViewById(R.id.number_of_nodes);
        optimal_path_text = findViewById(R.id.path);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        numberOfNodes = bundle.getInt("nodes");
        edgeCost = (int[][]) bundle.getSerializable("matrix");

        adj = edgeCost;

        s = "Number of Nodes : " + numberOfNodes;

        textView1.setText(s);

        N = numberOfNodes;

        TSP(adj);
        path();

        showCost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(String.valueOf(final_res));
            }
        });

        showPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optimal_path_text.setText(" ");
                optimal_path_text.setText(pathString);
            }
        });

    }

    static void path(){
        for (int i = 0; i < N; i++) {
            pathString += optimal_path[i] + " -> ";
        }
        pathString += optimal_path[0];
    }
    
    static int firstMin(int[][] adj, int i)
    {
        int min = Integer.MAX_VALUE;
        for (int k = 0; k < N; k++)
            if (adj[i][k] < min && i != k)
                min = adj[i][k];
        return min;
    }
    
    static int secondMin(int[][] adj, int i)
    {
        int first = Integer.MAX_VALUE, second = Integer.MAX_VALUE;
        for (int j=0; j<N; j++)
        {
            if (i == j)
                continue;

            if (adj[i][j] <= first)
            {
                second = first;
                first = adj[i][j];
            }
            else if (adj[i][j] <= second &&
                    adj[i][j] != first)
                second = adj[i][j];
        }
        return second;
    }
    
    static void TSPRec(int[][] adj, int curr_bound, int curr_weight, int level, int[] curr_path)
    {
        if (level == N)
        {

            if (adj[curr_path[level - 1]][curr_path[0]] != 0)
            {
                int curr_res = curr_weight + adj[curr_path[level-1]][curr_path[0]];
                
                if (curr_res < final_res)
                {
                    System.arraycopy(curr_path, 0, optimal_path, 0, N);

                    optimal_path[N] = curr_path[0];
                    final_res = curr_res;
                }
            }
            return;
        }

        for (int i = 0; i < N; i++)
        {
            if (adj[curr_path[level-1]][i] != 0 && !visited[i])
            {
                int temp = curr_bound;
                curr_weight += adj[curr_path[level - 1]][i];
                
                if (level==1)
                    curr_bound -= ((firstMin(adj, curr_path[level - 1]) + firstMin(adj, i))/2);
                else
                    curr_bound -= ((secondMin(adj, curr_path[level - 1]) + firstMin(adj, i))/2);
                
                if (curr_bound + curr_weight < final_res)
                {
                    curr_path[level] = i;
                    visited[i] = true;

                    TSPRec(adj, curr_bound, curr_weight, level + 1, curr_path);
                }
                
                curr_weight -= adj[curr_path[level-1]][i];
                curr_bound = temp;

                Arrays.fill(visited,false);
                for (int j = 0; j <= level - 1; j++)
                    visited[curr_path[j]] = true;
            }
        }
    }
    
    static void TSP(int[][] adj)
    {
        int[] curr_path = new int[N + 1];

        int curr_bound = 0;
        Arrays.fill(curr_path, -1);
        Arrays.fill(visited, false);
        
        for (int i = 0; i < N; i++)
            curr_bound += (firstMin(adj, i) +
                    secondMin(adj, i));

        curr_bound = (curr_bound==1)? curr_bound/2 + 1 : curr_bound/2;
        
        visited[0] = true;
        curr_path[0] = 0;

        TSPRec(adj, curr_bound, 0, 1, curr_path);
    }
    
}
