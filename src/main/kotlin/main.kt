import ast.printer.PrettyTreeModificationInfoPrinter
import ast.printer.acceptPrinter
import ast.actions.assignModifications
import ast.printer.SimpleTreePrinter
import com.github.gumtreediff.client.Run
import com.github.gumtreediff.gen.Generators
import kotlin.math.max
import com.github.gumtreediff.actions.ActionGenerator
import com.github.gumtreediff.actions.model.Delete
import com.github.gumtreediff.actions.model.Insert
import com.github.gumtreediff.actions.model.Move
import com.github.gumtreediff.actions.model.Update
import com.github.gumtreediff.matchers.Matchers


fun main() {
    Run.initGenerators()
    val src = "src.java"
    val dst = "dst.java"
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
    val g = ActionGenerator(srcRoot, dstRoot, m.getMappings())
    g.generate()
    val actions = g.actions
    val srcActions = actions.filter { it is Move || it is Delete || it is Update }.toSet()
    val dstActions = actions.filter { it is Move || it is Insert || it is Update }.toSet()

    val srcTreeModificationInfo = srcRoot.assignModifications(srcActions)
    val dstTreeModificationInfo = dstRoot.assignModifications(dstActions)

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