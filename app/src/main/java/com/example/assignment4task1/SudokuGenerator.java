package com.example.assignment4task1;

import android.widget.Toast;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import java.util.*;

public class SudokuGenerator {
    private static final int SIZE = 9;
    private Set<Integer>[][] possibilities;
    private SudokuGrid grid;
    private Random random = new Random();

    public SudokuGenerator() {
        grid = new SudokuGrid();
    }

    public SudokuGrid generate() {
        initializePossibilities();

        while (true) {
            int[] cell = findCellWithLeastPossibilities();
            if (cell == null) {
                break;
            }

            int row = cell[0];
            int col = cell[1];

            List<Integer> options = new ArrayList<>(possibilities[row][col]);
            if (options.isEmpty()) {
                System.out.println("Contradiction occurred. Restarting...");
                return generate();
            }

            int chosenValue = options.get(random.nextInt(options.size()));
            possibilities[row][col] = new HashSet<>(Collections.singleton(chosenValue));

            if (!propagate(row, col, chosenValue)) {
                System.out.println("Contradiction during propagation. Restarting...");
                return generate();
            }
        }

        // Fill the final grid
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                int value = possibilities[row][col].iterator().next();
                grid.setBoxValue(row, col, value);
            }
        }

        return grid;
    }

    @SuppressWarnings("unchecked")
    private void initializePossibilities() {
        possibilities = (Set<Integer>[][]) new Set[SIZE][SIZE];

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                possibilities[row][col] = new HashSet<>();
                for (int i = 1; i <= SIZE; i++) {
                    possibilities[row][col].add(i);
                }
            }
        }
    }
    private int[] findCellWithLeastPossibilities() {
        int minSize = Integer.MAX_VALUE;
        List<int[]> candidates = new ArrayList<>();

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                int size = possibilities[row][col].size();
                if (size > 1 && size < minSize) {
                    minSize = size;
                    candidates.clear();
                    candidates.add(new int[]{row, col});
                } else if (size == minSize) {
                    candidates.add(new int[]{row, col});
                }
            }
        }

        if (candidates.isEmpty()) return null;
        return candidates.get(random.nextInt(candidates.size()));
    }

    private boolean propagate(int row, int col, int value) {
        // Remove value from same row, column, and block
        for (int i = 0; i < SIZE; i++) {
            if (i != col && !removeValue(row, i, value)) return false;
            if (i != row && !removeValue(i, col, value)) return false;
        }

        int blockRowStart = (row / 3) * 3;
        int blockColStart = (col / 3) * 3;

        for (int r = blockRowStart; r < blockRowStart + 3; r++) {
            for (int c = blockColStart; c < blockColStart + 3; c++) {
                if ((r != row || c != col) && !removeValue(r, c, value)) return false;
            }
        }

        return true;
    }

    private boolean removeValue(int row, int col, int value) {
        Set<Integer> cellPossibilities = possibilities[row][col];

        if (cellPossibilities.contains(value)) {
            cellPossibilities.remove(value);

            if (cellPossibilities.isEmpty()) {
                return false; // Contradiction
            }

            // If we just reduced it to a single value, we need to propagate that too
            if (cellPossibilities.size() == 1) {
                int newValue = cellPossibilities.iterator().next();
                return propagate(row, col, newValue);
            }
        }

        return true;
    }
}
