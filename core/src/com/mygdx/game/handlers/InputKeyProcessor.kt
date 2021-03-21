package com.mygdx.game.handlers

import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter

/**
 * Created by haxpor on 5/16/17.
 */
class InputKeyProcessor : InputAdapter() {

    /** Keyboard, Mouse, and Touch **/
    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        InputKeys.screenX = screenX
        InputKeys.screenY = screenY
        return true
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        // keyboard
        InputKeys.screenX = screenX
        InputKeys.screenY = screenY
        InputKeys.down = true

        // mouse
        InputKeys.mouseDown = true
        if (button == Input.Buttons.LEFT) {
            InputKeys.setMouseKey(InputKeys.MouseKey.LEFT, true)
        }
        if (button == Input.Buttons.RIGHT) {
            InputKeys.setMouseKey(InputKeys.MouseKey.RIGHT, true)
        }

        return true
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        // keyboard
        InputKeys.screenX = screenX
        InputKeys.screenY = screenY
        InputKeys.down = false

        // mouse
        InputKeys.mouseDown = false
        if (button == Input.Buttons.LEFT) {
            InputKeys.setMouseKey(InputKeys.MouseKey.LEFT, false)
        }
        if (button == Input.Buttons.RIGHT) {
            InputKeys.setMouseKey(InputKeys.MouseKey.RIGHT, false)
        }

        return true
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        InputKeys.screenX = screenX
        InputKeys.screenY = screenY
        InputKeys.down = true
        InputKeys.mouseDown = true
        return true
    }

    override fun keyDown(keycode: Int): Boolean {
        if (keycode == Input.Keys.ENTER) {
            InputKeys.setButtonKey(InputKeys.ButtonKey.ENTER, true)
        }
        if (keycode == Input.Keys.ESCAPE) {
            InputKeys.setButtonKey(InputKeys.ButtonKey.ESCAPE, true)
        }
        if (keycode == Input.Keys.SPACE) {
            InputKeys.setButtonKey(InputKeys.ButtonKey.SPACE, true)
        }
        if (keycode == Input.Keys.SHIFT_LEFT || keycode == Input.Keys.SHIFT_RIGHT) {
            InputKeys.setButtonKey(InputKeys.ButtonKey.SHIFT, true)
        }
        if (keycode == Input.Keys.LEFT) {
            InputKeys.setButtonKey(InputKeys.ButtonKey.LEFT, true)
        }
        if (keycode == Input.Keys.RIGHT) {
            InputKeys.setButtonKey(InputKeys.ButtonKey.RIGHT, true)
        }
        if (keycode == Input.Keys.UP) {
            InputKeys.setButtonKey(InputKeys.ButtonKey.UP, true)
        }
        if (keycode == Input.Keys.DOWN) {
            InputKeys.setButtonKey(InputKeys.ButtonKey.DOWN, true)
        }
        return true
    }

    override fun keyUp(keycode: Int): Boolean {
        if (keycode == Input.Keys.ENTER) {
            InputKeys.setButtonKey(InputKeys.ButtonKey.ENTER, false)
        }
        if (keycode == Input.Keys.ESCAPE) {
            InputKeys.setButtonKey(InputKeys.ButtonKey.ESCAPE, false)
        }
        if (keycode == Input.Keys.SPACE) {
            InputKeys.setButtonKey(InputKeys.ButtonKey.SPACE, false)
        }
        if (keycode == Input.Keys.SHIFT_LEFT || keycode == Input.Keys.SHIFT_RIGHT) {
            InputKeys.setButtonKey(InputKeys.ButtonKey.SHIFT, false)
        }
        if (keycode == Input.Keys.LEFT) {
            InputKeys.setButtonKey(InputKeys.ButtonKey.LEFT, false)
        }
        if (keycode == Input.Keys.RIGHT) {
            InputKeys.setButtonKey(InputKeys.ButtonKey.RIGHT, false)
        }
        if (keycode == Input.Keys.UP) {
            InputKeys.setButtonKey(InputKeys.ButtonKey.UP, false)
        }
        if (keycode == Input.Keys.DOWN) {
            InputKeys.setButtonKey(InputKeys.ButtonKey.DOWN, false)
        }
        return true
    }
}