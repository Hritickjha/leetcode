import java.util.*;

class Solution {
    private Map<Integer, Integer> freqMap = new HashMap<>();
    private int maxFreq = 0;

    public int[] findFrequentTreeSum(TreeNode root) {
        if (root == null) return new int[0];
        
        dfs(root);

        List<Integer> result = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : freqMap.entrySet()) {
            if (entry.getValue() == maxFreq) {
                result.add(entry.getKey());
            }
        }

        // convert list to array
        int[] res = new int[result.size()];
        for (int i = 0; i < result.size(); i++) {
            res[i] = result.get(i);
        }
        return res;
    }

    private int dfs(TreeNode node) {
        if (node == null) return 0;

        int left = dfs(node.left);
        int right = dfs(node.right);

        int sum = node.val + left + right;
        freqMap.put(sum, freqMap.getOrDefault(sum, 0) + 1);
        maxFreq = Math.max(maxFreq, freqMap.get(sum));

        return sum;
    }
}
