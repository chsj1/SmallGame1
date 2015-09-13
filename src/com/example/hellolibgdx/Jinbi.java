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
	public float deltaY = 3f;//ÿһ֡���ǵ���ľ���
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
	 * ���ǵ�����߼�
	 */
	public void update(){
		float positionY  = getY();//��ȡ�����ڵ�λ��
		if(positionY < -150){//����������˳���̨��ʱ��
			positionY = 900;//���������´���ߵ㿪ʼ����
		}else{
			positionY -= deltaY;//���������һ֡��λ��
		}
		setY(positionY);//�������µ�λ��
	}
}
