package ast.actions

import com.github.gumtreediff.matchers.Mapping
import com.github.gumtreediff.tree.ITree

class TreeModificationInfo(
    val tree: ITree,
    val childrenInfos: List<TreeModificationInfo>,
    val action: ModificationKind? = null
)

fun <T : ITree> T.assignModifications(
    actions: Set<ModificationKind>,
    mappings: MutableSet<Mapping>
): TreeModificationInfo {
    return TreeModificationInfo(
        this,
        children.map { it.assignModifications(actions, mappings) },
        actions.find { action -> action.node === this || mappings.firstOrNull { it.first === action.node && it.second === this } != null }
    )
}