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

    static int optimalValue = Integer.MAX_VALUE;

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
                textView.setText(String.valueOf(optimalValue));
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
    
    static int nextMin(int[][] adj, int i)
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
    
    static void findTSP(int[][] adj, int currentBound, int currentWeight, int level, int[] currentPath)
    {
        if (level == N)
        {
            if (adj[currentPath[level - 1]][currentPath[0]] != 0)
            {
                int curr_res = currentWeight + adj[currentPath[level-1]][currentPath[0]];
                
                if (curr_res < optimalValue)
                {
                    System.arraycopy(currentPath, 0, optimal_path, 0, N);

                    optimal_path[N] = currentPath[0];
                    optimalValue = curr_res;
                }
            }
            return;
        }

        for (int i = 0; i < N; i++)
        {
            if (adj[currentPath[level-1]][i] != 0 && !visited[i])
            {
                int temp = currentBound;
                currentWeight += adj[currentPath[level - 1]][i];
                
                if (level==1)
                    currentBound -= ((firstMin(adj, currentPath[level - 1]) + firstMin(adj, i))/2);
                else
                    currentBound -= ((nextMin(adj, currentPath[level - 1]) + firstMin(adj, i))/2);
                
                if (currentBound + currentWeight < optimalValue)
                {
                    currentPath[level] = i;
                    visited[i] = true;

                    findTSP(adj, currentBound, currentWeight, level + 1, currentPath);
                }
                
                currentWeight -= adj[currentPath[level-1]][i];
                currentBound = temp;

                Arrays.fill(visited,false);
                for (int j = 0; j <= level - 1; j++)
                    visited[currentPath[j]] = true;
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
                    nextMin(adj, i));

        curr_bound = (curr_bound==1)? curr_bound/2 + 1 : curr_bound/2;
        
        visited[0] = true;
        curr_path[0] = 0;

        findTSP(adj, curr_bound, 0, 1, curr_path);
    }
    
}
