import java.util.*;

public class Solution {
    public List<Integer> replaceNonCoprimes(int[] nums) {
        List<Integer> stack = new ArrayList<>();
        for (int num : nums) {
            stack.add(num);
            while (stack.size() > 1) {
                int a = stack.get(stack.size() - 1);
                int b = stack.get(stack.size() - 2);
                int g = gcd(a, b);
                if (g > 1) {
                    stack.remove(stack.size() - 1);
                    stack.remove(stack.size() - 1);
                    long lcm = (long) a / g * b; // prevent overflow
                    stack.add((int) lcm);
                } else {
                    break;
                }
            }
        }
        return stack;
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }
}
