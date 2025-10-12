import java.util.HashMap;
import java.util.Random;

class Solution {
    private int _m; // Number of rows
    private int _n; // Number of columns
    private int total_zeros; // Current count of available '0' cells
    private Random rand; // Random number generator
    
    // HashMap to manage remapped indices.
    // Key: A "logical" index from the range [0, total_zeros-1]
    // Value: The "actual" 1D index of a cell that currently occupies this logical position.
    // This allows us to "virtually remove" elements by swapping them with the last element
    // in the conceptual list and shrinking the list.
    private HashMap<Integer, Integer> map;

    public Solution(int m, int n) {
        _m = m;
        _n = n;
        total_zeros = m * n; // Initially, all cells are '0'
        rand = new Random(); // Initialize the random number generator
        map = new HashMap<>(); // Initialize the map for remappings
    }
    
    public int[] flip() {
        // 1. Pick a random logical index from the current range of available '0' cells: [0, total_zeros - 1]
        int rand_logical_idx = rand.nextInt(total_zeros);
        
        // 2. Determine the actual 1D index that corresponds to this random logical index.
        // If 'rand_logical_idx' has been remapped (i.e., it's a key in 'map'),
        // it means another cell's value was "moved" here. We use that remapped value.
        // Otherwise, 'rand_logical_idx' still points to its original 1D index.
        int actual_idx_to_flip = map.getOrDefault(rand_logical_idx, rand_logical_idx);
        
        // 3. Decrement the count of available '0' cells. This cell is now '1'.
        total_zeros--;
        
        // 4. To keep the pool of available '0' cells contiguous for future random picks,
        // we conceptually "move" the cell that was at the *new* last logical position (`total_zeros`)
        // into the position of the cell we just picked (`rand_logical_idx`).
        // This effectively removes 'actual_idx_to_flip' from the pool by replacing its logical slot
        // with a cell that is now at the end of the conceptual list, which will then be excluded.
        int value_at_new_last_logical_pos = map.getOrDefault(total_zeros, total_zeros);
        map.put(rand_logical_idx, value_at_new_last_logical_pos);
        
        // 5. Convert the 'actual_idx_to_flip' (1D index) back to 2D coordinates (row, col).
        int r = actual_idx_to_flip / _n;
        int c = actual_idx_to_flip % _n;
        
        return new int[]{r, c};
    }
    
    public void reset() {
        // Clear all remappings and reset the count of '0' cells to the initial total.
        map.clear();
        total_zeros = _m * _n;
    }
}

/**
 * Your Solution object will be instantiated and called as such:
 * Solution obj = new Solution(m, n);
 * int[] param_1 = obj.flip();
 * obj.reset();
 */