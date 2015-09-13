package com.example.hellolibgdx;
import java.util.Date;

import android.os.SystemClock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Image;



public class Utils {
	static Date date = new Date();
	/**
	 * ������Ϸ��ƽ̨����ȡ��ǰʱ��
	 * @return
	 */
	public static long getMilisTimes(){
		switch (Gdx.app.getType()) {
		case Android://�����ǰ����Androidƽ̨��
			return SystemClock.elapsedRealtime();
		case Desktop://������������滷����
			return System.currentTimeMillis();
		default:
			return 0;
		}
	}
	/**
	 * �ж����������Ƿ����غ�
	 * @param image1
	 * @param image2
	 * @return
	 */
	public static boolean isOverlap(Image image1,Image image2){
		if((image1.getX() >= image2.getX() && image1.getX() <= image2.getX() + image2.getWidth()) && (image1.getY() >= image2.getY() && image1.getY() <= image2.getY() + image2.getWidth())){
			return true;//�������������Ѿ��غ�
		}
		return false;//������������û���غ�
	}
}
