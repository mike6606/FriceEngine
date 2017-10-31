package org.frice.platform

import org.frice.obj.AbstractObject
import org.frice.utils.time.FTimeListener
import org.frice.utils.time.FTimer
import java.util.*

interface FriceGame {

	val timeListeners: LinkedList<FTimeListener>
	val timeListenerDeleteBuffer: ArrayList<FTimeListener>
	val timeListenerAddBuffer: ArrayList<FTimeListener>
	val layers: Array<org.frice.platform.Layer>

	val drawer: org.frice.platform.adapter.JvmDrawer

	var fpsCounter: Int
	var fpsDisplay: Int
	var fpsTimer: FTimer

	/**
	 * not implemented yet.
	 * currently it's same as paused.
	 */
	var stopped: Boolean
	var debug: Boolean

	/** a general purpose instance for generating random numbers */
	val random: Random

	/** if true, the engine will collect all objects which are invisible from game window. */
	var autoGC: Boolean

	/** if true, there will be a fps calculating on the bottom-left side of window. */
	var showFPS: Boolean
	var loseFocus: Boolean
	var loseFocusChangeColor: Boolean
	var millisToRefresh: Int

	/** do the delete and add work, to prevent Exceptions */
	fun processBuffer() {
		layers.forEach(Layer::processBuffer)
		timeListeners.addAll(timeListenerAddBuffer)
		timeListeners.removeAll(timeListenerDeleteBuffer)
		timeListenerDeleteBuffer.clear()
		timeListenerAddBuffer.clear()
	}

	fun onInit()
	fun onLastInit()
	fun onRefresh()
	fun onExit()
	fun customDraw(g: FriceDrawer)
	fun onFocus()
	fun onLoseFocus()

	/** add TimeListeners using vararg */
	fun addTimeListener(vararg listeners: FTimeListener) = listeners.forEach { this addTimeListener it }

	/**
	 * add a time listener.
	 *
	 * @param listener time listener to be added
	 */
	infix fun addTimeListener(listener: FTimeListener) = timeListenerAddBuffer.add(listener)

	/** removes all auto-executed time listeners */
	fun clearTimeListeners() = timeListenerDeleteBuffer.addAll(timeListeners)

	/** remove TimeListeners using vararg */
	fun removeTimeListener(vararg listeners: FTimeListener) = listeners.forEach { removeTimeListener(it) }

	/**
	 * removes specified listener
	 *
	 * @param listener the listener
	 */
	infix fun removeTimeListener(listener: FTimeListener) = timeListenerDeleteBuffer.add(listener)

	/** remove Objects using vararg */
	fun removeObject(layer: Int, vararg objs: AbstractObject) = objs.forEach { removeObject(layer, it) }

	/**
	 * removes single object.
	 * this method is safe.
	 *
	 * @param obj will remove objects which is equal to it.
	 */
	fun removeObject(layer: Int, obj: AbstractObject) = layers[layer].removeObject(obj)

	/**
	 * clears all objects.
	 * this method is safe.
	 */
	fun clearObjects() = layers.forEach(org.frice.platform.Layer::clearObjects)

	/**
	 * clears all objects in one layer.
	 * this method is safe.
	 */
	fun clearObjects(layer: Int) = layers[layer].clearObjects()

	/** adds an object to game, to be shown on game window. */
	fun addObject(layer: Int, obj: AbstractObject) = layers[layer].addObject(obj)

	/** add Objects using vararg */
	fun addObject(layer: Int, vararg objs: AbstractObject) = objs.forEach { addObject(layer, it) }

	fun addObject(vararg objs: AbstractObject) = addObject(0, *objs)
	fun removeObject(vararg objs: AbstractObject) = removeObject(0, *objs)

	fun drawEverything(bgg: org.frice.platform.adapter.JvmDrawer)
	fun clearScreen()

	/**
	 * get a screenShot.
	 *
	 * @return screen cut as an image
	 */
	fun getScreenCut() = org.frice.resource.image.ImageResource(drawer.friceImage)
}