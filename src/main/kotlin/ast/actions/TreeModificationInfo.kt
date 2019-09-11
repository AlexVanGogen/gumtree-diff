package ast.actions

import com.github.gumtreediff.tree.ITree

class TreeModificationInfo(
    val tree: ITree,
    val childrenInfos: List<TreeModificationInfo>,
    val action: ModificationKind? = null
)

fun <T : ITree> T.assignModifications(actions: Set<ModificationKind>): TreeModificationInfo {
    return TreeModificationInfo(
        this,
        children.map { it.assignModifications(actions) },
        actions.find { it.node === this }
    )
}