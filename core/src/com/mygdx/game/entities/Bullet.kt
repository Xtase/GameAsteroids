package com.mygdx.game.entities

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.Pool
import com.badlogic.gdx.utils.viewport.Viewport
import com.mygdx.game.interfaces.IBatchShapeRenderable
import com.mygdx.game.interfaces.IBatchWrapperShapeRenderable

/**
 * Created by haxpor on 7/10/17.
 */
class Bullet: SpaceObject(), Pool.Poolable, IBatchShapeRenderable {
    private var lifeTime: Float = 0.0f
    private var lifeTimer: Float = 0.0f
    var shouldBeRemoved: Boolean = false

    companion object: IBatchWrapperShapeRenderable {
        const val SPEED: Float = 350f
        const val LIFETIME: Float = 1.0f    // in seconds

        private var oldShapeRendererColor: Color? = null

        override fun beginBatchRender(sr: ShapeRenderer) {
            // save old color of renderer, we will set it back later
            oldShapeRendererColor = sr.color
            sr.color = Color.WHITE
            sr.begin(ShapeRenderer.ShapeType.Line)
        }

        override fun endBatchRender(sr: ShapeRenderer) {
            sr.end()

            // set old color back to renderer
            oldShapeRendererColor?.let { sr.color = it }
        }
    }

    fun spawn(x: Float, y: Float, radians: Float) {
        this.x = x
        this.y = y
        this.radians = radians

        dx = MathUtils.cos(radians) * SPEED
        dy = MathUtils.sin(radians) * SPEED

        width = 2
        height = 2

        lifeTime = LIFETIME
        lifeTimer = 0.0f
    }

    override fun reset() {
        x = 0f
        y = 0f
        radians = 0f

        dx = 0f
        dy = 0f

        width = 0
        height = 0

        lifeTime = LIFETIME
        lifeTimer = 0.0f

        shouldBeRemoved = false
    }

    fun update(dt: Float, viewport: Viewport) {
        x += dx * dt
        y += dy * dt

        wrap(viewport)

        lifeTimer += dt
        if (lifeTimer > lifeTime) {
            shouldBeRemoved = true
        }
    }

    /**
     * Suitable for individual render call on its own.
     * Thus no need for caller to prepare state or set anything prior to this call to properly
     * render Bullet.
     */
    fun render(sr: ShapeRenderer) {
        beginBatchRender(sr)
        renderBatch(sr)
        endBatchRender(sr)
    }

    /**
     * Expected caller to set type to begin with ShapeType.Line, thus it can batch rendering
     * several of Bullet
     */
    override fun renderBatch(sr: ShapeRenderer) {
        sr.circle(x - width/2f, y - height/2f, width.toFloat()/2f)
    }
}