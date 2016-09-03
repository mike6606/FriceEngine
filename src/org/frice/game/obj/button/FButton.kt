package org.frice.game.obj.button

import org.frice.game.event.OnClickEvent
import org.frice.game.event.OnMouseEvent

/**
 * Created by ice1000 on 2016/8/18.
 * @author ice1000
 * @since v0.3.2
 */

interface  FButton {
	fun onClick(e: OnClickEvent)
	fun onMouse(e: OnMouseEvent)

	var width: Double
	var height: Double

	/**
	 * Created by ice1000 on 2016/8/19.
	 * @author ice1000
	 * @since v0.4
	 */
	interface OnClickListener {
		fun onClick(e: OnClickEvent)
	}

	/**
	 * Created by ice1000 on 2016/8/19.
	 * @author ice1000
	 * @since v0.4
	 */
	interface OnMouseListener {
		fun onMouse(e: OnMouseEvent)
	}
}