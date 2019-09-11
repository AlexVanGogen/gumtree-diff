package ast.printer

import com.github.gumtreediff.tree.ITree
import com.github.gumtreediff.tree.TreeContext

open class SimpleTreePrinter : TreePrinter {

    private var indent = 0

    override fun printCurrentNode(context: TreeContext, tree: ITree): String {
        val label = tree.label.ifNonEmpty { "\"$this\" " }
        return "    ".repeat(indent) + "${context.getTypeLabel(tree)} $label[${tree.pos}-${tree.endPos}]"
    }

    override fun printChildren(context: TreeContext, tree: ITree): String {
        val builder = StringBuilder()
        withIndent { builder.append(tree.children.joinToString(separator = "") { print(context, it) }) }
        return builder.toString()
    }

    private inline fun withIndent(block: () -> Unit) {
        indent++
        block()
        indent--
    }

    private inline fun String.ifNonEmpty(defaultValue: String.() -> String) = if (isNotEmpty()) defaultValue() else this
}