var haystack = ['Zig', 'Zag', 'Wally', 'Ronald', 'Bush', 'Krusty', 'Charlie', 'Bush', 'Bozo']
var needles = ['Bush', 'Washington']

for (var z in needles) {
    var found = false;
    for (var j in haystack) {
        if (haystack[j] == needles[z]) {
            found = true;
            break;
        }
    }
    if (found)
        print(needles[z] + " appears at index " + j + " in the haystack");
    else
        throw needles[z] + " does not appear in the haystack"
}