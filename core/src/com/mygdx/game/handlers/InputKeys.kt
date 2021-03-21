package com.mygdx.game.handlers

class InputKeys {
    /**
     * Key mapping for keyboard that supported in this game.
     */
    enum class ButtonKey {
        LEFT,
        RIGHT,
        UP,
        DOWN,
        ENTER,
        ESCAPE,
        SPACE,
        SHIFT
    }

    /**
     * Key mapping for mouse that supported in this game.
     */
    enum class MouseKey {
        LEFT,
        RIGHT
    }

    companion object {
        /**
         * Number of player that supported
         */
        const val NUM_CONTROLLER_SUPPORT: Int = 2

        var screenX: Int = 0
        var screenY: Int = 0

        var down: Boolean = false
        var pdown: Boolean = false

        var mouseDown: Boolean = false
        var pMouseDown: Boolean = false

        var keys: Array<Boolean> = Array(ButtonKey.values().size, { false })
        var pkeys: Array<Boolean> = Array(ButtonKey.values().size, { false })

        var mouseKeys: Array<Boolean> = Array(MouseKey.values().size, { false })
        var pMouseKeys: Array<Boolean> = Array(MouseKey.values().size, { false })

        fun update() {
            // update previous and current down for general
            pdown = down
            pMouseDown = mouseDown

            // update previous and current down for specific keys
            for (i in 0..ButtonKey.values().size - 1) {
                pkeys[i] = keys[i]
            }

            for (i in 0..MouseKey.values().size - 1) {
                pMouseKeys[i] = mouseKeys[i]
            }

        }

        /** isDown - specific **/
        fun isButtonDown(key: ButtonKey): Boolean {
            return keys[key.ordinal]
        }
        fun isMouseDown(key: MouseKey): Boolean {
            return mouseKeys[key.ordinal]
        }

        /** isPressed - specific **/
        fun isButtonPressed(key: ButtonKey): Boolean {
            return keys[key.ordinal] && !pkeys[key.ordinal]
        }
        fun isMousePressed(key: MouseKey): Boolean {
            return mouseKeys[key.ordinal] && !pMouseKeys[key.ordinal]
        }

        /** setKey **/
        fun setButtonKey(key: ButtonKey, b: Boolean) {
            keys[key.ordinal] = b
        }
        fun setMouseKey(key: MouseKey, b: Boolean) {
            mouseKeys[key.ordinal] = b
        }

        /** isDown **/
        fun isButtonDown(): Boolean {
            return down
        }
        fun isMouseDown(): Boolean {
            return mouseDown
        }

        /** isPressed **/
        fun isButtonPressed(): Boolean {
            return down && !pdown
        }
        fun isMousePressed(): Boolean {
            return mouseDown && !pMouseDown
        }

        /** isReleased **/
        fun isButtonReleased(): Boolean {
            return pdown && !down
        }
        fun isMouseReleased(): Boolean {
            return pMouseDown && !mouseDown
        }
    }
}