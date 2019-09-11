import ast.actions.*
import ast.printer.PrettyTreeModificationInfoPrinter
import ast.printer.acceptPrinter
import ast.printer.SimpleTreePrinter
import com.github.gumtreediff.client.Run
import com.github.gumtreediff.gen.Generators
import kotlin.math.max
import com.github.gumtreediff.actions.ActionGenerator
import com.github.gumtreediff.matchers.Matchers
import java.io.File

const val TEST_DATA_DIR = "testData"

fun main() {
    Run.initGenerators()
    val src = arrayOf("src", TEST_DATA_DIR, "java", "1", "src.java").joinToString(File.separator)
    val dst = arrayOf("src", TEST_DATA_DIR, "java", "1", "dst.java").joinToString(File.separator)
    val srcAstContext = Generators.getInstance().getTree(src)
    val dstAstContext = Generators.getInstance().getTree(dst)
    val srcAstText = srcAstContext.acceptPrinter(SimpleTreePrinter())
    val dstAstText = dstAstContext.acceptPrinter(SimpleTreePrinter())
    val modificationInfoIndent = max(
        srcAstText.maxLineLength(),
        dstAstText.maxLineLength()
    ) + 10

    val srcRoot = srcAstContext.root
    val dstRoot = dstAstContext.root
    val m = Matchers.getInstance().getMatcher(srcRoot, dstRoot) // retrieve the default matcher
    m.match()
    val g = ActionGenerator(srcRoot, dstRoot, m.mappings)
    g.generate()
    val actions = g.actions.map { ModificationKind.from(it) }
    val srcActions = actions.filter { it is MoveModification || it is DeleteModification || it is UpdateModification }.toSet()
    val dstActions = actions.filter { it is MoveModification || it is InsertModification || it is UpdateModification }.toSet()

    val mappingsAsSet = m.mappingsAsSet
    val srcTreeModificationInfo = srcRoot.assignModifications(srcActions, mappingsAsSet)
    val dstTreeModificationInfo = dstRoot.assignModifications(dstActions, mappingsAsSet)

    println("$src:")
    println(
        srcTreeModificationInfo.acceptPrinter(
            PrettyTreeModificationInfoPrinter(modificationInfoIndent),
            srcAstContext
        )
    )
    println()
    println("$dst:")
    println(
        dstTreeModificationInfo.acceptPrinter(
            PrettyTreeModificationInfoPrinter(modificationInfoIndent),
            dstAstContext
        )
    )
}

private fun String.maxLineLength() = this.split(System.lineSeparator()).maxBy { it.length }?.length ?: 0