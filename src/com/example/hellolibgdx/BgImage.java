package com.example.hellolibgdx;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;


/**
 * BgImage,处理背景移动的逻辑体
 * @author Administrator
 *
 */
public class BgImage extends Image {
	public float deltaY = 5;//每一帧往下移动的距离
	/**
	 * deltaY对应的setter()与getter()
	 * @return
	 */
	public float getDeltaY() {
		return deltaY;
	}
	public void setDeltaY(float deltaY) {
		this.deltaY = deltaY;
	}
	/**
	 * BgImage的构造函数
	 */
	/** Creates an BgImage stretched, and aligned center.
	 * @param region May be null. */
	public BgImage (TextureRegion region) {
		this(new TextureRegionDrawable(region), Scaling.stretch, Align.center);
	}
	/** @param drawable May be null. */
	public BgImage (Drawable drawable, Scaling scaling, int align) {
		setDrawable(drawable);
		setScaling(scaling);
		setAlign(align);
		setWidth(getPrefWidth());
		setHeight(getPrefHeight());
	}
	/**
	 * 用于更新BgImage的位置,实现移动的效果.
	 */
	public void update(){
		float positionY = this.getY();//获取BgImage对象当前的Y轴上的坐标
		if(positionY < -780){//如果背景已经一处屏幕外
			positionY = 780;//那么让他重新从最高处下落
		}else{//如果还没有一处屏幕外
			positionY -= deltaY;//那么让他下降5个像素
		}
		setY(positionY);//设置背景现在的Y轴上的位置.
	}
}

