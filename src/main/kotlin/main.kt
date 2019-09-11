import ast.createTreesWithModificationLabels
import java.io.File

const val TEST_DATA_DIR = "testData"

fun main() {
    val src = arrayOf("src", TEST_DATA_DIR, "java", "1", "src.java").joinToString(File.separator)
    val dst = arrayOf("src", TEST_DATA_DIR, "java", "1", "dst.java").joinToString(File.separator)

    val (srcLabeledTreeText, dstLabeledTreeText) = createTreesWithModificationLabels(src, dst)

    println("$src:")
    println(srcLabeledTreeText)
    println()
    println("$dst:")
    println(dstLabeledTreeText)
}