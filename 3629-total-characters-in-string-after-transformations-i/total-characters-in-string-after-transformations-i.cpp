class Solution {
public:
    int lengthAfterTransformations(string s, int t) {
        const int MOD = 1e9 + 7;
        vector<long long> freq(26, 0); // Frequency of each letter

        // Initialize frequency array
        for (char c : s) {
            freq[c - 'a']++;
        }

        // Perform t transformations
        for (int step = 0; step < t; ++step) {
            vector<long long> newFreq(26, 0);

            for (int i = 0; i < 26; ++i) {
                if (freq[i] == 0) continue;

                if (i == 25) { // 'z'
                    newFreq[0] = (newFreq[0] + freq[i]) % MOD; // 'a'
                    newFreq[1] = (newFreq[1] + freq[i]) % MOD; // 'b'
                } else {
                    newFreq[i + 1] = (newFreq[i + 1] + freq[i]) % MOD;
                }
            }

            freq = newFreq;
        }

        // Calculate total characters after transformations
        long long result = 0;
        for (int i = 0; i < 26; ++i) {
            result = (result + freq[i]) % MOD;
        }

        return (int)result;
    }
};

