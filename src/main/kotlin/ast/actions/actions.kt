package ast.actions

import com.github.gumtreediff.actions.model.*
import com.github.gumtreediff.tree.ITree

sealed class ModificationKind(open val node: ITree) {

    companion object {
        fun from(action: Action): ModificationKind = when (action) {
            is Update -> UpdateModification(action.node, action.node.label, action.value)
            is Move -> MoveModification(action.node)
            is Insert -> InsertModification(action.node)
            is Delete -> DeleteModification(action.node)
            else -> UnknownModification(action.node)
        }
    }
}

class UpdateModification(node: ITree, val from: String, val to: String) : ModificationKind(node) {
    override fun toString() = "updated: \"$from\" -> \"$to\""
}

class MoveModification(node: ITree) : ModificationKind(node) {
    override fun toString() = "moved"
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