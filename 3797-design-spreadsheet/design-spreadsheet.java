import java.util.*;

class Spreadsheet {
    private int[][] grid;  // stores cell values
    private int rows;

    // Constructor
    public Spreadsheet(int rows) {
        this.rows = rows;
        this.grid = new int[rows][26];  // rows x 26 columns (A-Z)
    }

    // Helper: parse cell reference like "B2" into row, col indices
    private int[] parseCell(String cell) {
        char colChar = cell.charAt(0);
        int col = colChar - 'A';  // column index 0-25
        int row = Integer.parseInt(cell.substring(1)) - 1; // 1-indexed → 0-indexed
        return new int[]{row, col};
    }

    // Sets a cell to a given value
    public void setCell(String cell, int value) {
        int[] pos = parseCell(cell);
        grid[pos[0]][pos[1]] = value;
    }

    // Resets a cell to 0
    public void resetCell(String cell) {
        int[] pos = parseCell(cell);
        grid[pos[0]][pos[1]] = 0;
    }

    // Evaluates formula "=X+Y"
    public int getValue(String formula) {
        // remove '='
        formula = formula.substring(1);
        String[] parts = formula.split("\\+");
        return getOperandValue(parts[0]) + getOperandValue(parts[1]);
    }

    // Helper: determine if operand is integer or cell ref
    private int getOperandValue(String operand) {
        if (Character.isDigit(operand.charAt(0))) {
            return Integer.parseInt(operand);
        } else {
            int[] pos = parseCell(operand);
            return grid[pos[0]][pos[1]];
        }
    }

    // For testing
    public static void main(String[] args) {
        Spreadsheet spreadsheet = new Spreadsheet(3);
        System.out.println(spreadsheet.getValue("=5+7")); // 12
        spreadsheet.setCell("A1", 10);
        System.out.println(spreadsheet.getValue("=A1+6")); // 16
        spreadsheet.setCell("B2", 15);
        System.out.println(spreadsheet.getValue("=A1+B2")); // 25
        spreadsheet.resetCell("A1");
        System.out.println(spreadsheet.getValue("=A1+B2")); // 15
    }
}
