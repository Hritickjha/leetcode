public class Solution {
    static final int MOD = 1_000_000_007;
    static final int MAX = 100005;  // Max value for n

    static long[] fact = new long[MAX];
    static long[] invFact = new long[MAX];

    // Precompute factorials and inverse factorials
    static {
        fact[0] = 1;
        for (int i = 1; i < MAX; i++) {
            fact[i] = fact[i - 1] * i % MOD;
        }

        invFact[MAX - 1] = modInverse(fact[MAX - 1]);
        for (int i = MAX - 2; i >= 0; i--) {
            invFact[i] = invFact[i + 1] * (i + 1) % MOD;
        }
    }

    // Modular inverse using Fermat's little theorem
    private static long modInverse(long x) {
        return powMod(x, MOD - 2);
    }

    // Fast modular exponentiation
    private static long powMod(long base, long exp) {
        long result = 1;
        while (exp > 0) {
            if ((exp & 1) == 1)
                result = result * base % MOD;
            base = base * base % MOD;
            exp >>= 1;
        }
        return result;
    }

    // nCk mod MOD
    private static long comb(int n, int k) {
        if (k < 0 || k > n) return 0;
        return fact[n] * invFact[k] % MOD * invFact[n - k] % MOD;
    }

    public int countGoodArrays(int n, int m, int k) {
        long ways = comb(n - 1, k);               // Choose k adjacent equal positions
        long pow = powMod(m - 1, n - 1 - k);      // Ways to fill differing values
        long result = ways * pow % MOD * m % MOD; // Multiply with choices for first element
        return (int) result;
    }
}


