public class Solution {
    public long flowerGame(int n, int m) {
        long nn = n;
        long mm = m;

        long oddX = (nn + 1) / 2;   // number of odd x in [1..n]
        long evenX = nn / 2;        // number of even x in [1..n]
        long oddY = (mm + 1) / 2;   // number of odd y in [1..m]
        long evenY = mm / 2;        // number of even y in [1..m]

        return oddX * evenY + evenX * oddY;
    }

    // optional local test
    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.flowerGame(3, 2)); // 3
        System.out.println(s.flowerGame(1, 1)); // 0
    }
}
