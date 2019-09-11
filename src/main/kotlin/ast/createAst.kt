package ast

import ast.actions.DeleteModification
import ast.actions.InsertModification
import ast.actions.ModificationKind
import ast.printer.ModificationLabeledTreePrinter
import ast.printer.SimpleTreePrinter
import ast.printer.acceptPrinter
import com.github.gumtreediff.actions.ActionGenerator
import com.github.gumtreediff.client.Run
import com.github.gumtreediff.gen.Generators
import com.github.gumtreediff.matchers.Matcher
import com.github.gumtreediff.matchers.Matchers
import com.github.gumtreediff.tree.ITree
import kotlin.math.max

const val MODIFICATION_INFO_MARGIN = 10

fun createTreesWithModificationLabels(
    src: String,
    dst: String
): Pair<String, String> {

    Run.initGenerators()

    val srcAstContext = Generators.getInstance().getTree(src)
    val dstAstContext = Generators.getInstance().getTree(dst)

    val modificationInfoIndent = max(
        srcAstContext.acceptPrinter(SimpleTreePrinter()).maxLineLength(),
        dstAstContext.acceptPrinter(SimpleTreePrinter()).maxLineLength()
    ) + MODIFICATION_INFO_MARGIN

    val srcRoot = srcAstContext.root
    val dstRoot = dstAstContext.root

    val matcher = Matchers.getInstance().getMatcher(srcRoot, dstRoot) // retrieve the default matcher
    matcher.match()
    val actions = getActions(srcRoot, dstRoot, matcher)

    val srcActions = actions.filter { it !is InsertModification }.toSet()
    val dstActions = actions.filter { it !is DeleteModification }.toSet()

    val mappingsAsSet = matcher.mappingsAsSet
    val srcModificationLabeledTree = srcRoot.assignModifications(srcActions, mappingsAsSet)
    val dstModificationLabeledTree = dstRoot.assignModifications(dstActions, mappingsAsSet)

    val srcLabeledTreeText = srcModificationLabeledTree.acceptPrinter(
        ModificationLabeledTreePrinter(modificationInfoIndent),
        srcAstContext
    )
    val dstLabeledTreeText = dstModificationLabeledTree.acceptPrinter(
        ModificationLabeledTreePrinter(modificationInfoIndent),
        dstAstContext
    )

    return Pair(srcLabeledTreeText, dstLabeledTreeText)
}

private fun getActions(
    srcRoot: ITree,
    dstRoot: ITree,
    matcher: Matcher
): List<ModificationKind> {
    val g = ActionGenerator(srcRoot, dstRoot, matcher.mappings)
    g.generate()
    return g.actions.map { ModificationKind.from(it) }
}

private fun String.maxLineLength() = this.split(System.lineSeparator()).maxBy { it.length }?.length ?: 0