package com.mygdx.game.handlers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.viewport.Viewport

open class UIObject(x: Float, y: Float) {
    var x: Float = x
    var y: Float = y
}

class InputTouchPad(x: Float, y: Float, radius: Float, innerRadius: Float) : UIObject(x, y) {

    interface Touchable {
        /**
         * Event will be called when touch happens, and will send in angle (radians) to this callback
         */
        fun onTouchPadTouch(pad: InputTouchPad, radians: Float)

        /**
         * Event will be called when touch cancelled, or just lifted along with sending in of last
         * angle in randians before cancelling.
         */
        fun onTouchPadCancel(pad: InputTouchPad, lastKnownRadians: Float)
    }

    var radius: Float = radius
    var innerRadius: Float = innerRadius
    var listener: Touchable? = null

    // inner circle
    var innerPadSpeed: Float = 0.5f
    private var isTouchedWithinTouchPad: Boolean = false

    var touchLocation: Vector3 = Vector3(x, y, 0f)
    private var cachedCenterPos: Vector3 = Vector3(x, y, 0f)
    private var touchPadTouchId: Int? = null
    private var isLerpBackToCenter: Boolean = false
    private var isFirstTouch: Boolean = true

    fun update(dt: Float, viewport: Viewport) {

        // loop through 2 touches at max
        for (i in 0..1) {
            if (Gdx.input.isTouched(i)) {
                // set touch location
                var location = Vector3(Gdx.input.getX(i).toFloat(), Gdx.input.getY(i).toFloat(), 0f)
                viewport.camera.unproject(location, viewport.screenX.toFloat(), viewport.screenY.toFloat(),
                        viewport.screenWidth.toFloat(), viewport.screenHeight.toFloat())

                // set touched-flag
                isTouchedWithinTouchPad = cachedCenterPos.dst(location) < this.radius

                // touch within touch pad to register touch
                if (isTouchedWithinTouchPad && isFirstTouch) {
                    // save this touch id
                    touchPadTouchId = i

                    // save touch location
                    touchLocation = location

                    isLerpBackToCenter = false
                    isFirstTouch = false

                    // send in listener
                    listener?.let {
                        it.onTouchPadTouch(this, getTouchAngle(touchLocation.y, touchLocation.x))
                    }

                    break
                }
                // keep track of position changes within touchpad
                else if (isTouchedWithinTouchPad && touchPadTouchId != null && touchPadTouchId == i &&
                        !isFirstTouch) {
                    // save touch location
                    touchLocation = location

                    isLerpBackToCenter = false

                    // send in listener
                    listener?.let {
                        it.onTouchPadTouch(this, getTouchAngle(touchLocation.y, touchLocation.x))
                    }

                    break
                }
                // otherwise touch outside, bound the location to be within touch pad
                // also make sure it's the same touch id that initially initiates this touch process
                else if (!isTouchedWithinTouchPad && touchPadTouchId != null && touchPadTouchId == i &&
                        !isFirstTouch) {

                    // save touch location
                    touchLocation = location

                    // find angle (in radians) between center and target position
                    val radians = getTouchAngle(touchLocation.y, touchLocation.x)
                    // set new position
                    touchLocation.set(cachedCenterPos.x + MathUtils.cos(radians) * radius,
                            cachedCenterPos.y + MathUtils.sin(radians) * radius,
                            0f)

                    isLerpBackToCenter = false

                    // send in listener
                    listener?.let {
                        it.onTouchPadTouch(this, radians)
                    }

                    break
                }
            }
        }

        // try to lerp inner circle back to center location
        // note: pointer index is preserved for touched one
        if (touchPadTouchId != null) {
            if (!Gdx.input.isTouched(touchPadTouchId!!)) {
                // clear touch id and reset states
                touchPadTouchId = null
                isLerpBackToCenter = true
                isTouchedWithinTouchPad = false
                isFirstTouch = true

                listener?.let {
                    it.onTouchPadCancel(this, getTouchAngle(touchLocation.x, touchLocation.y))
                }
            }
        }
    }

    fun render(sr: ShapeRenderer) {
        sr.begin(ShapeRenderer.ShapeType.Line)

        // render base
        sr.circle(x, y, this.radius)

        // render touch location
        if (isLerpBackToCenter) {
            // lerp position back to center
            touchLocation = touchLocation.lerp(cachedCenterPos, innerPadSpeed)

            sr.circle(touchLocation.x, touchLocation.y, this.innerRadius)
        } else {
            sr.circle(touchLocation.x, touchLocation.y, this.innerRadius)
        }

        sr.end()
    }

    private fun getTouchAngle(touchX: Float, touchY: Float): Float {
        return MathUtils.atan2(touchX - cachedCenterPos.y, touchY - cachedCenterPos.x)
    }
}