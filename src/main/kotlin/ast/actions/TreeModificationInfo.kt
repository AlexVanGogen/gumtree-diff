package ast.actions

import com.github.gumtreediff.actions.model.Action
import com.github.gumtreediff.tree.ITree

class TreeModificationInfo(
    val tree: ITree,
    val childrenInfos: List<TreeModificationInfo>,
    val action: Action? = null
)

fun <T : ITree> T.assignModifications(actions: Set<Action>): TreeModificationInfo {
    return TreeModificationInfo(
        this,
        children.map { it.assignModifications(actions) },
        actions.find { it.node === this }
    )
}