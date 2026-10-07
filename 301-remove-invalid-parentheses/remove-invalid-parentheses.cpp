class Solution {
public:
    unordered_set<string> ans;

    void dfs(const string& s, int idx, int leftRem, int rightRem,
             int balance, string& curr) {

        // Invalid prefix
        if (balance < 0)
            return;

        // No more characters
        if (idx == s.size()) {
            if (leftRem == 0 && rightRem == 0 && balance == 0) {
                ans.insert(curr);
            }
            return;
        }

        char c = s[idx];

        if (c == '(') {
            // Option 1: remove this '('
            if (leftRem > 0) {
                dfs(s, idx + 1, leftRem - 1, rightRem,
                    balance, curr);
            }

            // Option 2: keep this '('
            curr.push_back(c);
            dfs(s, idx + 1, leftRem, rightRem,
                balance + 1, curr);
            curr.pop_back();

        } else if (c == ')') {
            // Option 1: remove this ')'
            if (rightRem > 0) {
                dfs(s, idx + 1, leftRem, rightRem - 1,
                    balance, curr);
            }

            // Option 2: keep this ')' only if it has a matching '('
            if (balance > 0) {
                curr.push_back(c);
                dfs(s, idx + 1, leftRem, rightRem,
                    balance - 1, curr);
                curr.pop_back();
            }

        } else {
            // Letters are always kept
            curr.push_back(c);
            dfs(s, idx + 1, leftRem, rightRem,
                balance, curr);
            curr.pop_back();
        }
    }

    vector<string> removeInvalidParentheses(string s) {
        int leftRem = 0;
        int rightRem = 0;

        // Find the minimum number of '(' and ')' to remove.
        for (char c : s) {
            if (c == '(') {
                leftRem++;
            } 
            else if (c == ')') {
                if (leftRem > 0)
                    leftRem--;
                else
                    rightRem++;
            }
        }

        string curr;
        dfs(s, 0, leftRem, rightRem, 0, curr);

        return vector<string>(ans.begin(), ans.end());
    }
};