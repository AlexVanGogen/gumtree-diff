package ast

import ast.actions.ModificationKind
import com.github.gumtreediff.matchers.Mapping
import com.github.gumtreediff.tree.ITree
import com.github.gumtreediff.tree.Tree

class ModificationLabeledTree(
    tree: ITree,
    private val labeledChildren: List<ModificationLabeledTree>,
    val action: ModificationKind? = null
) : Tree(tree as? Tree) {
    override fun getChildren(): MutableList<ITree> {
        return labeledChildren.toMutableList()
    }
}

fun <T : ITree> T.assignModifications(
    actions: Set<ModificationKind>,
    mappings: MutableSet<Mapping>
): ModificationLabeledTree {
    return ModificationLabeledTree(
        this,
        children.map { it.assignModifications(actions, mappings) },
        actions.find { action -> action.node === this || mappings.firstOrNull { it.first === action.node && it.second === this } != null }
    )
}