package ast.printer

import com.github.gumtreediff.tree.ITree
import com.github.gumtreediff.tree.TreeContext

class SimpleTreePrinter : TreePrinter {

    private var indent = 0

    override fun print(context: TreeContext, tree: ITree): String {
        val builder = StringBuilder()
        val label = tree.label.ifNonEmpty { "\"$this\" " }
        builder.appendln("    ".repeat(indent) + "${context.getTypeLabel(tree)} $label[${tree.pos}-${tree.endPos}]")
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