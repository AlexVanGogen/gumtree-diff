package ast.printer

import ast.ModificationLabeledTree
import com.github.gumtreediff.tree.ITree
import com.github.gumtreediff.tree.TreeContext

interface TreePrinter {
    fun print(context: TreeContext, tree: ITree): String {
        return StringBuilder()
            .appendln(printCurrentNode(context, tree))
            .append(printChildren(context, tree))
            .toString()
    }

    fun printCurrentNode(context: TreeContext, tree: ITree): String
    fun printChildren(context: TreeContext, tree: ITree): String
}

fun TreeContext.acceptPrinter(printer: TreePrinter): String = printer.print(this, root)

fun ModificationLabeledTree.acceptPrinter(printer: TreePrinter, context: TreeContext): String = printer.print(context, this)