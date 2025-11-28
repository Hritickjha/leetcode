from collections import defaultdict
import sys
sys.setrecursionlimit(10**7)

class Solution:
    def maxKDivisibleComponents(self, n, edges, values, k):
        graph = defaultdict(list)
        for a, b in edges:
            graph[a].append(b)
            graph[b].append(a)

        def dfs(node, parent):
            subtotal = values[node]
            count = 0

            for nei in graph[node]:
                if nei == parent:
                    continue

                child_sum, child_count = dfs(nei, node)
                count += child_count

                # If child's subtree is divisible by k → cut it
                if child_sum % k == 0:
                    count += 1
                else:
                    subtotal += child_sum

            return subtotal, count

        total_sum, total_count = dfs(0, -1)

        # If whole tree is divisible, root is another component
        if total_sum % k == 0:
            total_count += 1

        return total_count
