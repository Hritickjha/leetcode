import java.util.*;

class Solution {
    public int minJumps(int[] nums) {
        int n = nums.length;
        if (n == 1) return 0;
        
        int maxNum = 1_000_000;
        
        // Sieve of Eratosthenes to find primes
        boolean[] isPrime = sieve(maxNum);
        
        // Map prime to list of indices where nums[idx] is divisible by prime
        Map<Integer, List<Integer>> primeToIndices = new HashMap<>();
        
        for (int i = 0; i < n; i++) {
            int num = nums[i];
            // Find all prime factors of num
            int temp = num;
            for (int p = 2; p * p <= temp; p++) {
                if (temp % p == 0) {
                    if (isPrime[p]) {
                        primeToIndices.computeIfAbsent(p, k -> new ArrayList<>()).add(i);
                    }
                    while (temp % p == 0) {
                        temp /= p;
                    }
                }
            }
            if (temp > 1 && isPrime[temp]) {
                primeToIndices.computeIfAbsent(temp, k -> new ArrayList<>()).add(i);
            }
        }
        
        // BFS
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[n];
        Set<Integer> usedPrimes = new HashSet<>();
        
        queue.offer(0);
        visited[0] = true;
        int jumps = 0;
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int s = 0; s < size; s++) {
                int curr = queue.poll();
                
                if (curr == n - 1) {
                    return jumps;
                }
                
                // Adjacent moves
                if (curr + 1 < n && !visited[curr + 1]) {
                    visited[curr + 1] = true;
                    queue.offer(curr + 1);
                }
                if (curr - 1 >= 0 && !visited[curr - 1]) {
                    visited[curr - 1] = true;
                    queue.offer(curr - 1);
                }
                
                // Teleportation move
                int num = nums[curr];
                if (num > 1 && isPrime[num] && !usedPrimes.contains(num)) {
                    usedPrimes.add(num);
                    List<Integer> teleportIndices = primeToIndices.getOrDefault(num, new ArrayList<>());
                    for (int idx : teleportIndices) {
                        if (idx != curr && !visited[idx]) {
                            visited[idx] = true;
                            queue.offer(idx);
                        }
                    }
                }
            }
            jumps++;
        }
        
        return -1; // Should not happen as we can always reach via adjacent moves
    }
    
    private boolean[] sieve(int n) {
        boolean[] isPrime = new boolean[n + 1];
        Arrays.fill(isPrime, true);
        isPrime[0] = isPrime[1] = false;
        
        for (int i = 2; i * i <= n; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j <= n; j += i) {
                    isPrime[j] = false;
                }
            }
        }
        return isPrime;
    }
}