package ast.printer

import com.github.gumtreediff.tree.ITree
import com.github.gumtreediff.tree.TreeContext

interface TreePrinter {
    fun print(context: TreeContext, tree: ITree): String
}

fun TreeContext.acceptPrinter(printer: TreePrinter): String = printer.print(this, root)