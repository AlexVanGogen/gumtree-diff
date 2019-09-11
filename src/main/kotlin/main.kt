import ast.createTreesWithModificationLabels
import java.lang.UnsupportedOperationException

fun main(args: Array<String>) {
    if (args.size != 2) {
        println("Error: expected two arguments: path to source code and path to destination code")
        return
    }
    val src = args[0]
    val dst = args[1]

    val (srcLabeledTreeText, dstLabeledTreeText) = try {
        createTreesWithModificationLabels(src, dst)
    } catch (e: UnsupportedOperationException) {
        println("Error: cannot generate tree for one of input files")
        return
    }

    println("$src:")
    println(srcLabeledTreeText)
    println()
    println("$dst:")
    println(dstLabeledTreeText)
}