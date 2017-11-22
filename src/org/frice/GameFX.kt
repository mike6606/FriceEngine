package org.frice

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import org.frice.event.*
import org.frice.platform.*
import org.frice.platform.adapter.JfxDrawer
import org.frice.utils.misc.loop
import org.frice.utils.time.FClock
import org.frice.utils.time.FTimer
import java.util.*
import kotlin.concurrent.thread

/**
 * @author ice1000
 * @since v1.5.0
 */
open class GameFX
@JvmOverloads
constructor(
	private val width: Int = 500,
	private val height: Int = 500,
	layerCount: Int = 1) : Application(), FriceGame {
	override fun getWidth() = width
	override fun getHeight() = height

	override var paused = false
		set(value) {
			if (value) FClock.pause() else FClock.resume()
			field = value
		}

	override var stopped = false
		set(value) {
			if (value) FClock.pause() else FClock.resume()
			field = value
		}

	override val layers = Layers(layerCount) { Layer() }
	override var debug = true
	override var autoGC = true
	override var showFPS = true
	override var loseFocus = false
		set(value) = Unit

	private lateinit var stage: Stage
	private lateinit var scene: Scene

	var fpsCounter = 0
	var fpsDisplay = 0
	var fpsTimer = FTimer(1000)

	override var loseFocusChangeColor = true

	private val refresh = FTimer(4)
	override var millisToRefresh: Int
		get () = refresh.time
		set (value) {
			refresh.time = value
		}

	override fun onInit() = Unit
	override fun onLastInit() = Unit
	override fun onRefresh() = Unit
	override fun onMouse(e: OnMouseEvent) = Unit
	override fun customDraw(g: FriceDrawer) = Unit

	override fun getTitle(): String = stage.title
	override fun setTitle(title: String) {
		stage.title = title
	}

	private val canvas = Canvas(width.toDouble(), height.toDouble())
	private val root = StackPane(canvas)
	override val drawer = JfxDrawer(canvas.graphicsContext2D)

	override fun onExit() {
		TODO("not implemented")
	}

	override fun start(stage: Stage) {
		this.stage = stage
		scene = Scene(root, width.toDouble(), height.toDouble())
		canvas.setOnMouseClicked { onMouse(fxMouse(it, MOUSE_CLICKED)) }
		canvas.setOnMouseEntered { onMouse(fxMouse(it, MOUSE_ENTERED)) }
		canvas.setOnMouseExited { onMouse(fxMouse(it, MOUSE_EXITED)) }
		canvas.setOnMousePressed { onMouse(fxMouse(it, MOUSE_PRESSED)) }
		canvas.setOnMouseReleased { onMouse(fxMouse(it, MOUSE_RELEASED)) }
		stage.scene = scene
		onInit()
		stage.show()
		thread {
			onLastInit()
			loop {
				try {
					onRefresh()
					if (!paused && !stopped && refresh.ended()) {
						// TODO("repaint")
						customDraw(drawer)
					}
				} catch (ignored: ConcurrentModificationException) {
				}
			}
		}
	}
}
