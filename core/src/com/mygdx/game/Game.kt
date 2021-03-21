package com.mygdx.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.game.handlers.*
import com.mygdx.game.states.Mainmenu
import java.io.Console

class Game : ApplicationAdapter() {
    var sb: SpriteBatch? = null
    var img: Texture? = null

    var gsm: GameStateManager? = null
        private set
    var playerSaveFileManager: PlayerSaveFileManager? = null
        private set

    companion object {
        const val TITLE = "Asteroids"
        const val V_WIDTH = 640f
        const val V_HEIGHT = 480f

        var res: Content = Content()
            private set
    }

    override fun create() {
        Gdx.input.inputProcessor = InputKeyProcessor()
        sb = SpriteBatch()
//        img = Texture("badlogic.jpg")

        gsm = GameStateManager(this)

        println("coucou")
        // create player's savefile manager with pre-set of savefile's path
        playerSaveFileManager = PlayerSaveFileManager(Settings.PLAYER_SAVEFILE_RELATIVE_PATH)
        playerSaveFileManager?.sync(Settings.TOTAL_HIGH_SCORES_RECORD)

        println("ici")
        // go to main menu state
        gsm?.setState(Mainmenu(gsm!!))

        println("la")
    }

    override fun render() {
        Gdx.graphics.setTitle(TITLE + " -- FPS: " + Gdx.graphics.framesPerSecond)
        gsm?.update(Gdx.graphics.deltaTime)
        gsm?.render()
        InputKeys.update()
        println("render")
    }

    override fun dispose() {
        sb?.dispose()
        res?.dispose()
        gsm?.dispose()
    }

    override fun resize(width: Int, height: Int) {
        gsm?.resize(width, height)
    }
}