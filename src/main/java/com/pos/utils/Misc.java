package com.pos.utils;

public class Misc {
    public static int getColumn(int column) {
        if (column == 3) {
            return 0;
        }
        return column + 1;
    }

    public static int getRow(int column, int row) {
        if (column == 0) {
            return row + 1;
        }
        return row;
    }
}
