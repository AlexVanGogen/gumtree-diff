package ast.printer

import ast.actions.TreeModificationInfo
import com.github.gumtreediff.tree.TreeContext

class PrettyTreeModificationInfoPrinter(val modificationInfoIndent: Int) : TreeModificationInfoPrinter {

    private var indent = 0

    override fun print(context: TreeContext, treeInfo: TreeModificationInfo): String {
        val builder = StringBuilder()
        val tree = treeInfo.tree
        val label = tree.label.ifNonEmpty { "\"$this\" " }
        val astNodeInfo = "    ".repeat(indent) + "${context.getTypeLabel(tree)} $label[${tree.pos}-${tree.endPos}]"
        builder.append(astNodeInfo)
        builder.append(" ".repeat(modificationInfoIndent - astNodeInfo.length))
        treeInfo.action?.let { builder.append("[$it]") }
        builder.appendln()
        withIndent { builder.append(treeInfo.childrenInfos.joinToString(separator = "") { print(context, it) }) }
        return builder.toString()
    }

    private inline fun withIndent(block: () -> Unit) {
        indent++
        block()
        indent--
    }

    private inline fun String.ifNonEmpty(defaultValue: String.() -> String) = if (isNotEmpty()) defaultValue() else this
}