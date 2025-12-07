/**
 * @param {number} low
 * @param {number} high
 * @return {number}
 */
var countOdds = function(low, high) {
    // If both low and high are even, the number of odds is exactly half
    // Otherwise, it's half rounded up
    return Math.floor((high - low) / 2) + (low % 2 || high % 2 ? 1 : 0);
};
