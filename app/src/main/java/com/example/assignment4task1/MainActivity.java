package com.example.assignment4task1;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private GridLayout gridLayout;
    private static final int SIZE = 9;
    private static final int CELL_SIZE_DP = 35;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        gridLayout = findViewById(R.id.grid);

        // Generate and display the Sudoku board
        generateAndDisplaySudoku();
    }
    private void generateAndDisplaySudoku() {
        SudokuGenerator generator = new SudokuGenerator();
        SudokuGrid grid = generator.generate();

        // Clear grid first if it had old children
        gridLayout.removeAllViews();

        // Convert dp to pixels
        int cellSizePx = (int) (CELL_SIZE_DP * getResources().getDisplayMetrics().density);

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                TextView cell = new TextView(this);

                // Get the value from the generated grid
                Integer value = grid.getValue(row, col);
                cell.setText(String.valueOf(value));
                cell.setTextSize(18);
                cell.setTextColor(Color.BLACK);
                cell.setBackgroundColor(Color.LTGRAY);
                cell.setGravity(android.view.Gravity.CENTER);

                // Add some border spacing
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = cellSizePx;
                params.height = cellSizePx;
                params.setMargins(2, 2, 2, 2);
                cell.setLayoutParams(params);

                gridLayout.addView(cell);
            }
        }
    }
}