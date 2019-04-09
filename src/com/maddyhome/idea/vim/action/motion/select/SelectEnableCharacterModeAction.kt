package com.maddyhome.idea.vim.action.motion.select

import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Editor
import com.maddyhome.idea.vim.VimPlugin
import com.maddyhome.idea.vim.action.VimCommandAction
import com.maddyhome.idea.vim.command.Command
import com.maddyhome.idea.vim.command.CommandState
import com.maddyhome.idea.vim.command.MappingMode
import com.maddyhome.idea.vim.group.visual.vimSetSystemSelectionSilently
import com.maddyhome.idea.vim.handler.EditorActionHandlerBase
import com.maddyhome.idea.vim.helper.EditorHelper
import com.maddyhome.idea.vim.helper.vimLastColumn
import javax.swing.KeyStroke

/**
 * @author Alex Plate
 */

private object SelectEnableCharacterModeActionHandler : EditorActionHandlerBase() {
    override fun execute(editor: Editor, context: DataContext, cmd: Command): Boolean {
        editor.caretModel.runForEachCaret { caret ->
            val lineEnd = EditorHelper.getLineEndForOffset(editor, caret.offset)
            caret.run {
                vimSetSystemSelectionSilently(offset, (offset + 1).coerceAtMost(lineEnd))
                moveToOffset((offset + 1).coerceAtMost(lineEnd))
                vimLastColumn = visualPosition.column
            }
        }
        return VimPlugin.getVisualMotion().enterSelectionMode(editor, CommandState.SubMode.VISUAL_CHARACTER)
    }
}

class SelectEnableCharacterModeAction : VimCommandAction(SelectEnableCharacterModeActionHandler) {
    override fun getMappingModes(): MutableSet<MappingMode> = MappingMode.N

    override fun getKeyStrokesSet(): MutableSet<MutableList<KeyStroke>> = parseKeysSet("gh")

    override fun getType(): Command.Type = Command.Type.OTHER_READONLY
}