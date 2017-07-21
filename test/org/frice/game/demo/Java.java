package org.frice.game.demo;

import org.frice.game.Game;
import org.frice.game.anim.move.AccurateMove;
import org.frice.game.obj.button.FText;
import org.frice.game.obj.button.ImageButton;
import org.frice.game.obj.button.SimpleText;
import org.frice.game.obj.sub.ImageObject;
import org.frice.game.resource.graphics.ColorResource;
import org.frice.game.resource.image.ImageResource;

/**
 * Created by Administrator on 2016/9/15 0015.
 *
 * @author ice1000
 */
public class Java extends Game {
	private static final String URL = "https://coding.net/u/ice1000/p/Images/git/raw/master/blog-img/13/a.png";

	@Override
	public void onInit() {
		super.onInit();
//		ImageButton imageButton = new ImageButton(
//				ImageResource.fromWeb("http://img.blog.csdn.net/20151123175207052"),
//				ImageResource.fromWeb("http://img.blog.csdn.net/20151123175205567"),
//				50, 50);
		ImageObject imageObject = new ImageObject(ImageResource.fromWeb(URL), -90, 100);
		ImageObject imageObject1 = new ImageObject(ImageResource.fromWeb(URL), -30, 200);
		ImageObject imageObject2 = new ImageObject(ImageResource.fromWeb(URL), 300, 100);
		SimpleText text = new SimpleText(ColorResource.BLACK, "暗中观察.kt", 50, 200);
		imageObject.addAnim(new AccurateMove(50, 0));
		imageObject.addCollider(imageObject2, imageObject::stopAnims);
		addObject(imageObject, imageObject2, imageObject1);
//		addObject(imageButton);
	}

	@Override
	public void onExit() {
		System.exit(0);
	}

	public static void main(String[] args) {
		new Java();
	}
}
