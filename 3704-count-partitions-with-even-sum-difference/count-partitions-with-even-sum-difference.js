var countPartitions = function(nums) {
    let totalSum = nums.reduce((a, b) => a + b, 0);

    // If total sum is odd, no partition gives an even difference
    if (totalSum % 2 !== 0) {
        return 0;
    }

    // If total sum is even, all n-1 partitions work
    return nums.length - 1;
};
