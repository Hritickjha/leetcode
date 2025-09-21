/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    private int maxDepth = -1;
    private int leftmostValue;

    public int findBottomLeftValue(TreeNode root) {
        dfs(root, 0);
        return leftmostValue;
    }

    private void dfs(TreeNode node, int depth) {
        if (node == null) return;

        // If we find a new deeper level, record the leftmost value
        if (depth > maxDepth) {
            maxDepth = depth;
            leftmostValue = node.val;
        }

        dfs(node.left, depth + 1);  // Left first (ensures leftmost)
        dfs(node.right, depth + 1);
    }
}
