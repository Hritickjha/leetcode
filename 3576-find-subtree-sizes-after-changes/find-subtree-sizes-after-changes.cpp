class Solution {
public:
    vector<int> findSubtreeSizes(vector<int>& parent, string s) {
        int n = parent.size();
        vector<vector<int>> children(n);

        for (int i = 1; i < n; i++) {
            children[parent[i]].push_back(i);
        }

        vector<int> newParent(n);
        vector<int> last(26, -1);

        function<void(int)> dfs = [&](int u) {
            int c = s[u] - 'a';
            int old = last[c];

            if (u != 0 && old != -1) {
                newParent[u] = old;
            } else {
                newParent[u] = parent[u];
            }

            last[c] = u;

            for (int v : children[u]) {
                dfs(v);
            }

            last[c] = old;
        };

        dfs(0);

        vector<vector<int>> newChildren(n);
        for (int i = 1; i < n; i++) {
            newChildren[newParent[i]].push_back(i);
        }

        vector<int> ans(n, 1);

        function<int(int)> calc = [&](int u) {
            for (int v : newChildren[u]) {
                ans[u] += calc(v);
            }
            return ans[u];
        };

        calc(0);

        return ans;
    }
};