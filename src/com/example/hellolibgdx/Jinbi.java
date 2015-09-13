package com.example.hellolibgdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;

public class Jinbi extends Image {
	public float deltaY = 3f;//每一帧星星掉落的距离
	public float getDeltaY() {
		return deltaY;
	}
	public void setDeltaY(float deltaY) {
		this.deltaY = deltaY;
	}
	/** Creates an image with no region or patch, stretched, and aligned center. */
	public Jinbi () {
		this((Drawable)null);
	}
	/** Creates an image stretched, and aligned center.
	 * @param patch May be null. */
	public Jinbi (NinePatch patch) {
		this(new NinePatchDrawable(patch), Scaling.stretch, Align.center);
	}
	/** Creates an image stretched, and aligned center.
	 * @param region May be null. */
	public Jinbi (TextureRegion region) {
		this(new TextureRegionDrawable(region), Scaling.stretch, Align.center);
	}
	/** Creates an image stretched, and aligned center. */
	public Jinbi (Texture texture) {
		this(new TextureRegionDrawable(new TextureRegion(texture)));
	}
	/** Creates an image stretched, and aligned center. */
	public Jinbi (Skin skin, String drawableName) {
		this(skin.getDrawable(drawableName), Scaling.stretch, Align.center);
	}
	/** Creates an image stretched, and aligned center.
	 * @param drawable May be null. */
	public Jinbi (Drawable drawable) {
		this(drawable, Scaling.stretch, Align.center);
	}
	/** Creates an image aligned center.
	 * @param drawable May be null. */
	public Jinbi (Drawable drawable, Scaling scaling) {
		this(drawable, scaling, Align.center);
	}
	/** @param drawable May be null. */
	public Jinbi (Drawable drawable, Scaling scaling, int align) {
		setDrawable(drawable);
		setScaling(scaling);
		setAlign(align);
		setWidth(getPrefWidth());
		setHeight(getPrefHeight());
		this.addAction(Actions.forever(Actions.sequence(Actions.parallel(Actions.alpha(0.5f, 0.8f), Actions.scaleTo(1f, 1f, 0.8f)),Actions.parallel(Actions.alpha(1f, 0.8f), Actions.scaleTo(2f, 2f, 0.8f)))));
//	    this.addAction(action);
	}
	/**
	 * 星星掉落的逻辑
	 */
	public void update(){
		float positionY  = getY();//获取它现在的位置
		if(positionY < -150){//档星星落出退出舞台的时候
			positionY = 900;//让星星重新从最高点开始下落
		}else{
			positionY -= deltaY;//计算出它下一帧的位置
		}
		setY(positionY);//设置最新的位置
	}
}
