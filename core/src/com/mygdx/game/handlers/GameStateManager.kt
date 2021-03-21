package com.mygdx.game.handlers
import com.badlogic.gdx.utils.Disposable
import com.mygdx.game.Game
import com.mygdx.game.states.GameState

import java.util.Stack

/**
 * Created by haxpor on 5/14/17.
 */

class GameStateManager(game: Game): Disposable {
    var game: Game
        private set
    private var gameStates: Stack<GameState>

    init {
        this.game = game
        this.gameStates = Stack<GameState>()
    }

    fun update(dt: Float) {
        this.gameStates.peek().update(dt)
    }

    fun resize(width: Int, height: Int) {
        this.gameStates.peek().resize(width, height)
    }

    fun render() {
        for (state in this.gameStates) {
            state.render()
        }
    }

    fun setState(state: GameState) {
        // dispose all gamestates first
        this.gameStates.forEach { it.dispose() }
        // clear all gamestates
        this.gameStates.clear()
        // set new one via push
        this.pushState(state)
    }

    fun pushState(state: GameState) {
        this.gameStates.push(state)
    }

    fun popState() {
        val g = this.gameStates.pop()
        g.dispose()
    }

    override fun dispose() {
        gameStates.forEach { it.dispose() }
    }
}