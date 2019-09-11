package ast.actions

import com.github.gumtreediff.actions.model.*
import com.github.gumtreediff.tree.ITree

sealed class ModificationKind(open val node: ITree) {

    companion object {
        private var lastModeId = 0
        private var lastUpdateId = 0

        fun from(action: Action): ModificationKind = when (action) {
            is Update -> UpdateModification(action.node, action.node.label, action.value, lastUpdateId++)
            is Move -> MoveModification(action.node, lastModeId++)
            is Insert -> InsertModification(action.node)
            is Delete -> DeleteModification(action.node)
            else -> UnknownModification(action.node)
        }
    }
}

class UpdateModification(node: ITree, val from: String, val to: String, val id: Int) : ModificationKind(node) {
    override fun toString() = "updated: \"$from\" -> \"$to\" | #$id"
}

class MoveModification(node: ITree, val id: Int) : ModificationKind(node) {
    override fun toString() = "moved | #$id"
}

class InsertModification(node: ITree) : ModificationKind(node) {
    override fun toString() = "inserted"
}

class DeleteModification(node: ITree) : ModificationKind(node) {
    override fun toString() = "deleted"
}

class UnknownModification(node: ITree) : ModificationKind(node) {
    override fun toString() = "unknown"
}