# gumtree-diff

Tool for showing differences between two ASTs in terms of primitive operations that convert first AST to second.

## Description

Tool takes paths to two source code files. It uses [GumTree](https://github.com/GumTreeDiff/gumtree) to produce AST for given code and generate set of actions (insert/update/move/delete) that can be executed to get the second AST from the first one.

Such actions are printed to stdout along with both trees. If some node or its subtree was modified in some way, then information about appropriate modification will be shown to the right of this node (see [Examples](#Examples) for clarification).

Currently, Java and JavaScript code is supported.

## Build & run (Unix)

Build:

```
./gradlew installDist
```

Run:

```
./gumtree-diff <path-to-first-source-code> <path-to-second-source-code>
```

## Examples

### Java

In the following example, generic type was removed:

`src.java`
```java
class Doer<T extends Number> {
    public T id(T value) {
        return value;
    }
}
```

`dst.java`
```java
class NumberDoer {
    public Number id(Number value) {
        return value;
    }
}
```

The output is:

`result.txt`
```
src/examples/java/genericClass/src.java:
CompilationUnit [0-87]
    TypeDeclaration "class" [0-87]
        SimpleName "Doer" [6-10]                         [updated: "Doer" -> "NumberDoer" | #0]
        TypeParameter [11-27]                            [deleted]
            SimpleName "T" [11-12]                       [deleted]
            SimpleType "Number" [21-27]                  [moved | #0]
                SimpleName "Number" [21-27]
        MethodDeclaration [35-85]
            Modifier "public" [35-41]
            SimpleType "T" [42-43]                       [deleted]
                SimpleName "T" [42-43]                   [deleted]
            SimpleName "id" [44-46]
            SingleVariableDeclaration [47-54]
                SimpleType "T" [47-48]                   [updated: "T" -> "Number" | #1]
                    SimpleName "T" [47-48]               [updated: "T" -> "Number" | #2]
                SimpleName "value" [49-54]
            Block [56-85]
                ReturnStatement [66-79]
                    SimpleName "value" [73-78]


src/examples/java/genericClass/dst.java:
CompilationUnit [0-85]
    TypeDeclaration "class" [0-85]
        SimpleName "NumberDoer" [6-16]                   [updated: "Doer" -> "NumberDoer" | #0]
        MethodDeclaration [23-83]
            Modifier "public" [23-29]
            SimpleType "Number" [30-36]                  [moved | #0]
                SimpleName "Number" [30-36]
            SimpleName "id" [37-39]
            SingleVariableDeclaration [40-52]
                SimpleType "Number" [40-46]              [updated: "T" -> "Number" | #1]
                    SimpleName "Number" [40-46]          [updated: "T" -> "Number" | #2]
                SimpleName "value" [47-52]
            Block [54-83]
                ReturnStatement [64-77]
                    SimpleName "value" [71-76]

```

### JS

In the following example, code was pushed into function body:

`src.js`
```javascript
var data = JSON.parse('{ "foo": 1, "bar": [10, "apples"] }');

var sample = { "blue": [1,2], "ocean": "water" };
var json_string = JSON.stringify(sample);
```

`dst.js`
```javascript
function foo() {
    var data = JSON.parse('{ "foo": 1, "bar": [10, "apples"] }');

    var sample = { "blue": [1,2], "ocean": "water" };
    var json_string = JSON.stringify(sample);
}
```

The output is:

`result.txt`
```
src/examples/js/function/src.js:
SCRIPT [0-154]
    VAR [0-61]                                                                        [moved | #0]
        VAR [4-60]
            NAME "data" [4-8]
            CALL [11-60]
                GETPROP [11-21]
                    NAME "JSON" [11-15]
                    NAME "parse" [16-21]
                STRING "{ "foo": 1, "bar": [10, "apples"] }" [22-59]
    VAR [63-112]                                                                      [moved | #1]
        VAR [67-111]
            NAME "sample" [67-73]
            OBJECTLIT [76-111]
                COLON [78-91]
                    STRING "blue" [78-84]
                    ARRAYLIT [86-91]
                        NUMBER "1" [87-88]
                        NUMBER "2" [89-90]
                COLON [93-109]
                    STRING "ocean" [93-100]
                    STRING "water" [102-109]
    VAR [113-154]                                                                     [moved | #2]
        VAR [117-153]
            NAME "json_string" [117-128]
            CALL [131-153]
                GETPROP [131-145]
                    NAME "JSON" [131-135]
                    NAME "stringify" [136-145]
                NAME "sample" [146-152]


src/examples/js/function/dst.js:
SCRIPT [0-185]
    FUNCTION [0-185]                                                                  [inserted]
        NAME "foo" [9-12]                                                             [inserted]
        BLOCK [15-185]                                                                [inserted]
            VAR [21-82]                                                               [moved | #0]
                VAR [25-81]
                    NAME "data" [25-29]
                    CALL [32-81]
                        GETPROP [32-42]
                            NAME "JSON" [32-36]
                            NAME "parse" [37-42]
                        STRING "{ "foo": 1, "bar": [10, "apples"] }" [43-80]
            VAR [88-137]                                                              [moved | #1]
                VAR [92-136]
                    NAME "sample" [92-98]
                    OBJECTLIT [101-136]
                        COLON [103-116]
                            STRING "blue" [103-109]
                            ARRAYLIT [111-116]
                                NUMBER "1" [112-113]
                                NUMBER "2" [114-115]
                        COLON [118-134]
                            STRING "ocean" [118-125]
                            STRING "water" [127-134]
            VAR [142-183]                                                             [moved | #2]
                VAR [146-182]
                    NAME "json_string" [146-157]
                    CALL [160-182]
                        GETPROP [160-174]
                            NAME "JSON" [160-164]
                            NAME "stringify" [165-174]
                        NAME "sample" [175-181]
```

You can see other examples in `src/examples` folder.