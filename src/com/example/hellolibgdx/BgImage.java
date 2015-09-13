package com.example.hellolibgdx;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;


/**
 * BgImage,�������ƶ����߼���
 * @author Administrator
 *
 */
public class BgImage extends Image {
	public float deltaY = 5;//ÿһ֡�����ƶ��ľ���
	/**
	 * deltaY��Ӧ��setter()��getter()
	 * @return
	 */
	public float getDeltaY() {
		return deltaY;
	}
	public void setDeltaY(float deltaY) {
		this.deltaY = deltaY;
	}
	/**
	 * BgImage�Ĺ��캯��
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
	 * ���ڸ���BgImage��λ��,ʵ���ƶ���Ч��.
	 */
	public void update(){
		float positionY = this.getY();//��ȡBgImage����ǰ��Y���ϵ�����
		if(positionY < -780){//��������Ѿ�һ����Ļ��
			positionY = 780;//��ô�������´���ߴ�����
		}else{//�����û��һ����Ļ��
			positionY -= deltaY;//��ô�����½�5������
		}
		setY(positionY);//���ñ������ڵ�Y���ϵ�λ��.
	}
}

