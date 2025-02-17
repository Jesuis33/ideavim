/*
 * IdeaVim - Vim emulator for IDEs based on the IntelliJ platform
 * Copyright (C) 2003-2022 The IdeaVim authors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package org.jetbrains.plugins.ideavim.ex.parser.expressions

import com.maddyhome.idea.vim.vimscript.model.datatypes.VimInt
import com.maddyhome.idea.vim.vimscript.model.datatypes.VimString
import com.maddyhome.idea.vim.vimscript.parser.VimscriptParser
import org.jetbrains.plugins.ideavim.ex.evaluate
import org.junit.Test
import kotlin.test.assertEquals

class OperatorPrecedenceTest {

  @Test
  fun `boolean operators`() {
    assertEquals(VimInt(0), VimscriptParser.parseExpression("0 || 1 && 0")!!.evaluate())
  }

  @Test
  fun `boolean operators 2`() {
    assertEquals(VimInt(0), VimscriptParser.parseExpression("!1 || 0")!!.evaluate())
  }

  @Test
  fun `concatenation and multiplication`() {
    assertEquals(VimString("410"), VimscriptParser.parseExpression("4 . 5 * 2")!!.evaluate())
  }

  @Test
  fun `concatenation and multiplication 2`() {
    assertEquals(VimString("202"), VimscriptParser.parseExpression("4 * 5 . 2")!!.evaluate())
  }

  @Test
  fun `arithmetic operators`() {
    assertEquals(VimInt(6), VimscriptParser.parseExpression("2 + 2 * 2")!!.evaluate())
  }

  @Test
  fun `comparison operators`() {
    assertEquals(VimInt(1), VimscriptParser.parseExpression("10 < 5 + 29")!!.evaluate())
  }

  @Test
  fun `sublist operator`() {
    assertEquals(VimString("ab"), VimscriptParser.parseExpression("'a' . 'bc'[0]")!!.evaluate())
  }

  @Test
  fun `not with sublist`() {
    assertEquals(VimInt(0), VimscriptParser.parseExpression("!{'a': 1}['a']")!!.evaluate())
  }
}
