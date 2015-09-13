package com.example.hellolibgdx;
import java.util.Date;

import android.os.SystemClock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Image;



public class Utils {
	static Date date = new Date();
	/**
	 * 根据游戏的平台来获取当前时间
	 * @return
	 */
	public static long getMilisTimes(){
		switch (Gdx.app.getType()) {
		case Android://如果当前是在Android平台上
			return SystemClock.elapsedRealtime();
		case Desktop://如果这是在桌面环境下
			return System.currentTimeMillis();
		default:
			return 0;
		}
	}
	/**
	 * 判断两个物体是否发生重合
	 * @param image1
	 * @param image2
	 * @return
	 */
	public static boolean isOverlap(Image image1,Image image2){
		if((image1.getX() >= image2.getX() && image1.getX() <= image2.getX() + image2.getWidth()) && (image1.getY() >= image2.getY() && image1.getY() <= image2.getY() + image2.getWidth())){
			return true;//代表两个物体已经重合
		}
		return false;//代表两个物体没有重合
	}
}
