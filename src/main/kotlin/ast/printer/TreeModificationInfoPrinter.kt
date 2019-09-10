package ast.printer

import ast.actions.TreeModificationInfo
import com.github.gumtreediff.tree.TreeContext

interface TreeModificationInfoPrinter {
    fun print(context: TreeContext, treeInfo: TreeModificationInfo): String
}

fun TreeModificationInfo.acceptPrinter(printer: TreeModificationInfoPrinter, context: TreeContext): String = printer.print(context, this)