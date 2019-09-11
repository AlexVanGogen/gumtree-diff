package ast.printer

import ast.ModificationLabeledTree
import com.github.gumtreediff.tree.ITree
import com.github.gumtreediff.tree.TreeContext

class ModificationLabeledTreePrinter(private val modificationInfoIndent: Int) : SimpleTreePrinter() {

    override fun printCurrentNode(context: TreeContext, tree: ITree): String {
        val astNodeInfo = super.printCurrentNode(context, tree)
        val labeledTree = tree as? ModificationLabeledTree ?: return astNodeInfo

        val builder = StringBuilder()
            .append(super.printCurrentNode(context, tree))
        labeledTree.action?.let {
            builder
                .append(" ".repeat(modificationInfoIndent - astNodeInfo.length))
                .append("[$it]")
        }
        return builder.toString()
    }
}