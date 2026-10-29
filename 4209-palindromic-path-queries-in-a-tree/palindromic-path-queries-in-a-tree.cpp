class Solution {
public:
    vector<bool> palindromePath(int n, vector<vector<int>>& edges,
                                string s, vector<string>& queries) {
        vector<vector<int>> adj(n);
        for (auto& e : edges) {
            adj[e[0]].push_back(e[1]);
            adj[e[1]].push_back(e[0]);
        }

        vector<int> parent(n, -1), depth(n, 0);
        vector<int> sz(n, 1), heavy(n, -1);
        vector<int> head(n), pos(n);
        vector<int> order;
        order.reserve(n);

        // Build parent, depth, and traversal order iteratively.
        vector<int> st = {0};
        parent[0] = 0;

        while (!st.empty()) {
            int u = st.back();
            st.pop_back();
            order.push_back(u);

            for (int v : adj[u]) {
                if (v == parent[u]) continue;
                parent[v] = u;
                depth[v] = depth[u] + 1;
                st.push_back(v);
            }
        }

        // Calculate subtree sizes and heavy children.
        for (int i = n - 1; i >= 0; --i) {
            int u = order[i];
            int bestSize = 0;

            for (int v : adj[u]) {
                if (parent[v] != u) continue;

                sz[u] += sz[v];
                if (sz[v] > bestSize) {
                    bestSize = sz[v];
                    heavy[u] = v;
                }
            }
        }

        // Assign HLD heads and linear positions.
        int timer = 0;
        vector<pair<int, int>> chains = {{0, 0}};

        while (!chains.empty()) {
            auto [start, h] = chains.back();
            chains.pop_back();

            for (int u = start; u != -1; u = heavy[u]) {
                head[u] = h;
                pos[u] = timer++;

                for (int v : adj[u]) {
                    if (parent[v] == u && v != heavy[u]) {
                        chains.push_back({v, v});
                    }
                }
            }
        }

        // Segment tree: XOR of character masks.
        vector<int> seg(4 * n, 0);

        function<void(int, int, int)> build =
            [&](int node, int l, int r) {
                if (l == r) {
                    seg[node] = 1 << (s[order[0]] - 'a');
                    // The node at this position is found below.
                    return;
                }
                int mid = (l + r) / 2;
                build(node * 2, l, mid);
                build(node * 2 + 1, mid + 1, r);
                seg[node] = seg[node * 2] ^ seg[node * 2 + 1];
            };

        // Construct the linearized string correctly.
        vector<int> base(n);
        for (int u = 0; u < n; ++u) {
            base[pos[u]] = 1 << (s[u] - 'a');
        }

        function<void(int, int, int)> init =
            [&](int node, int l, int r) {
                if (l == r) {
                    seg[node] = base[l];
                    return;
                }
                int mid = (l + r) / 2;
                init(node * 2, l, mid);
                init(node * 2 + 1, mid + 1, r);
                seg[node] = seg[node * 2] ^ seg[node * 2 + 1];
            };

        init(1, 0, n - 1);

        function<void(int, int, int, int, int)> update =
            [&](int node, int l, int r, int idx, int val) {
                if (l == r) {
                    seg[node] = val;
                    return;
                }
                int mid = (l + r) / 2;
                if (idx <= mid)
                    update(node * 2, l, mid, idx, val);
                else
                    update(node * 2 + 1, mid + 1, r, idx, val);

                seg[node] = seg[node * 2] ^ seg[node * 2 + 1];
            };

        function<int(int, int, int, int, int)> querySeg =
            [&](int node, int l, int r, int ql, int qr) -> int {
                if (ql <= l && r <= qr) return seg[node];

                int mid = (l + r) / 2;
                int res = 0;

                if (ql <= mid)
                    res ^= querySeg(node * 2, l, mid, ql, qr);
                if (qr > mid)
                    res ^= querySeg(node * 2 + 1, mid + 1, r, ql, qr);

                return res;
            };

        auto pathMask = [&](int u, int v) {
            int mask = 0;

            while (head[u] != head[v]) {
                if (depth[head[u]] < depth[head[v]])
                    swap(u, v);

                mask ^= querySeg(1, 0, n - 1, pos[head[u]], pos[u]);
                u = parent[head[u]];
            }

            if (depth[u] > depth[v]) swap(u, v);

            mask ^= querySeg(1, 0, n - 1, pos[u], pos[v]);
            return mask;
        };

        vector<bool> answer;

        for (const string& q : queries) {
            stringstream ss(q);
            string type;
            int u, v;
            ss >> type >> u;

            if (type == "update") {
                char c;
                ss >> c;
                s[u] = c;
                update(1, 0, n - 1, pos[u], 1 << (c - 'a'));
            } else {
                ss >> v;
                int mask = pathMask(u, v);

                // At most one character may have odd frequency.
                answer.push_back((mask & (mask - 1)) == 0);
            }
        }

        return answer;
    }
};