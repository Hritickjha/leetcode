class Solution {
public:
    int k, n;
    vector<int> nums;

    struct Node {
        int P;
        int cnt[5][5];
    };
    vector<Node> tree;

    void setLeafValue(Node &nd, int v) {
        int val = v % k;
        nd.P = val;
        for (int r = 0; r < k; r++)
            for (int x = 0; x < k; x++)
                nd.cnt[r][x] = ((r * val) % k == x) ? 1 : 0;
    }

    Node combine(const Node &a, const Node &b) {
        Node res;
        res.P = (a.P * b.P) % k;
        for (int r = 0; r < k; r++) {
            int rr = (r * a.P) % k;
            for (int x = 0; x < k; x++)
                res.cnt[r][x] = a.cnt[r][x] + b.cnt[rr][x];
        }
        return res;
    }

    void pull(int node) {
        tree[node] = combine(tree[2*node], tree[2*node+1]);
    }

    void build(int node, int l, int r) {
        if (l == r) {
            setLeafValue(tree[node], nums[l]);
            return;
        }
        int mid = (l + r) / 2;
        build(2*node, l, mid);
        build(2*node+1, mid+1, r);
        pull(node);
    }

    void update(int node, int l, int r, int pos, int val) {
        if (l == r) {
            setLeafValue(tree[node], val);
            return;
        }
        int mid = (l + r) / 2;
        if (pos <= mid) update(2*node, l, mid, pos, val);
        else update(2*node+1, mid+1, r, pos, val);
        pull(node);
    }

    Node query(int node, int l, int r, int ql, int qr) {
        if (ql <= l && r <= qr) return tree[node];
        int mid = (l + r) / 2;
        if (qr <= mid) return query(2*node, l, mid, ql, qr);
        if (ql > mid) return query(2*node+1, mid+1, r, ql, qr);
        Node left = query(2*node, l, mid, ql, qr);
        Node right = query(2*node+1, mid+1, r, ql, qr);
        return combine(left, right);
    }

    vector<int> resultArray(vector<int>& nums_, int k_, vector<vector<int>>& queries) {
        nums = nums_;
        k = k_;
        n = (int)nums.size();
        tree.assign(4 * n, Node());
        build(1, 0, n - 1);

        vector<int> result;
        result.reserve(queries.size());
        int startResidue = 1 % k;   // <-- fix: identity residue mod k
        for (auto &q : queries) {
            int index = q[0], value = q[1], start = q[2], x = q[3];
            update(1, 0, n - 1, index, value);
            Node res = query(1, 0, n - 1, start, n - 1);
            result.push_back(res.cnt[startResidue][x]);
        }
        return result;
    }
};