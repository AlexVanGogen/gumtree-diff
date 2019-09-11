function binary_search_recursive(a, value, lo, hi) {
    if (hi < lo) { return null; }

    var mid = Math.floor((hi + lo) / 2);

    if (a[mid] < value) {
        return binary_search_recursive(a, value, mid + 1, hi);
    }
    if (a[mid] > value) {
        return binary_search_recursive(a, value, lo, mid - 1);
    }
    return mid;
}