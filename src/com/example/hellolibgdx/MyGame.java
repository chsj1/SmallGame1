package com.example.hellolibgdx;

import java.util.Random;

import android.os.SystemClock;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class MyGame implements ApplicationListener {
	Stage stage;
	Group gameGroup;
	Jinbi xingxing;//不断掉落的星星
	Image bucketImage;//可以操控的小人
	TextureAtlas atlas;
	TextureRegion bgRegion;
	BgImage bgImage1;//定义一个Image对象。实现背景移动的第一张图
	BgImage bgImage2;//实现背景移动的第二张图
	int scoreNums;
	BitmapFont scoreFont;
	LabelStyle scoreNumsStyle;
	Label scoreNumsLabel;//用来绘制成就条
	Random random;//用来辅助产生随机的横坐标
	Group successGroup;//成功界面Group
	Label successLabel;//提示你成功了的标签
	int targetScores = 3;//成功分数
	Sound dropSound;//捡到星星是的音效.Sound适合长度较短的音频文件
	Music bjMusic;//整个游戏的背景音乐.Music适合长度较长的音频文件..
	Label leftTimeLabel;//用来实现剩余时间逻辑
	long endTime;//用来标记游戏结束的时间
//	long continueTime = 5;//游戏的持续时间
	long continueTime = 60;//游戏的持续时间
	StringBuffer leftTimeSB;
	Group loseGroup;//用于实现失败界面
	Label loseLabel;//用于实现失败的提示信息
	@Override
	public void create() {
		stage = new Stage(480, 800, false);
		random = new Random();
		xingxing = new Jinbi(new Texture(Gdx.files.internal("data/droplet.png")));
		xingxing.setPosition(240, 900);//初始设置一个比当前舞台的高度要高的值，这样就会给玩家一种星星从上面掉下来的感觉
		xingxing.setColor(Color.WHITE);
		bucketImage = new Image(new Texture(Gdx.files.internal("data/bucket.png")));
		/**
		 * BitmapFont(FileHandle fontFile, boolean flip)
		 * fontFile: 指定字体文件的路径
		 * filp: 字体是否翻转
		 */
		scoreFont = new BitmapFont(Gdx.files.internal("font/default.fnt"), false);
		/**
		 * LabelStyle(BitmapFont font, Color fontColor)
		 * font: 字体对象
		 * fontColor: 字体颜色
		 */
		scoreNumsStyle = new LabelStyle(scoreFont, Color.WHITE);
		/**
		 * Label(CharSequence text, LabelStyle style)
		 * text: 这一个label的内容
		 * style: 这一个lablel的风格
		 */
		scoreNumsLabel = new Label("your score is : " + scoreNums, scoreNumsStyle);
		scoreNumsLabel.setPosition(0, 600);
		scoreNumsLabel.setColor(Color.WHITE);
		
		/**
		 * SystemClock.elapsedRealtime():当前系统时间的毫秒数
		 */
//		endTime = SystemClock.elapsedRealtime() + continueTime*1000;
		endTime = Utils.getMilisTimes() + continueTime*1000;
		leftTimeSB = new StringBuffer();
		leftTimeLabel = new Label("left time is : " + continueTime, scoreNumsStyle);
		leftTimeLabel.setPosition(0, 500);
		atlas = new TextureAtlas(Gdx.files.internal("data/movebg.atlas"));
		bgRegion = atlas.findRegion("movebg");//初始化TextureAtlas
		bgImage1 = new BgImage(bgRegion);//初始化第一个bgImage对象
		bgImage1.setSize(480, 800);//设置bgImage的大小。宽度为480,高度为800
		bgImage2 = new BgImage(bgRegion);
		bgImage2.setSize(480, 800);
		bgImage2.setPosition(0, 800);//设置第二章背景图的位置
		gameGroup = new Group();
		gameGroup.addActor(xingxing);
		gameGroup.addActor(bucketImage);
		gameGroup.addActor(scoreNumsLabel);
		gameGroup.addActor(leftTimeLabel);
		addListenerToStageToHandleBucket();
		successLabel = new Label("you have successed.\n tap to continue.", scoreNumsStyle);
		successLabel.setPosition(150, 400);
		successGroup = new Group();
		successGroup.setSize(480, 800);
		successGroup.addActor(successLabel);
		successGroup.setVisible(false);//一开始成功界面不可见
		successGroup.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				//隐藏成功界面
				successGroup.setVisible(false);
				//显示游戏界面
				gameGroup.setVisible(true);
				scoreNums = 0;//清空成绩
				scoreNumsLabel.setText("your score is : " + scoreNums);
				endTime = SystemClock.elapsedRealtime() + continueTime*1000;//重新计算游戏的结束时间
				return true;
			}
		});
		dropSound = Gdx.audio.newSound(Gdx.files.internal("music/drop.wav"));
		bjMusic = Gdx.audio.newMusic(Gdx.files.internal("music/rain.mp3"));//音效的初始化
		bjMusic.setLooping(true);//设置背景音乐循环播放
		bjMusic.play();//开始播放音乐
		loseLabel = new Label("sorry,you have lose de game.\n tap to continue.", scoreNumsStyle);
		loseLabel.setPosition(150, 400);
		loseGroup = new Group();
		loseGroup.setSize(480, 800);
		loseGroup.addActor(loseLabel);
		loseGroup.setVisible(false);
		loseGroup.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				//隐藏失败界面
				loseGroup.setVisible(false);
				//显示游戏界面
				gameGroup.setVisible(true);
				scoreNums = 0;//清空成绩
				scoreNumsLabel.setText("your score is : " + scoreNums);
//				endTime = SystemClock.elapsedRealtime() + continueTime*1000;//重新计算游戏的结束时间
				endTime = Utils.getMilisTimes() + continueTime*1000;//重新计算游戏的结束时间
				return true;
			}
		});
		stage.addActor(bgImage1);//将演员添加到舞台上
		stage.addActor(bgImage2);
		stage.addActor(gameGroup);
		stage.addActor(successGroup);
		stage.addActor(loseGroup);
		Gdx.input.setInputProcessor(stage);
	}
	/**
	 * 给stage添加监听器用来改变小人的位置
	 */
	public void addListenerToStageToHandleBucket(){
		stage.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;//表示事件已经被截取,不在往下传递
			}
			@Override
			public void touchDragged(InputEvent event, float x, float y,
					int pointer) {
				bucketImage.setX(x);//根据手指操控的位置来设置小人的位置..
			}
		});
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}
	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}
	public void pengzhuangJiance(){
		if(Utils.isOverlap(xingxing, bucketImage) == true){//如果发生了碰撞
			dropSound.play();//播放捡到星星时的音效
			//更新成就
			scoreNums++;
			if(scoreNums >= targetScores){//如果达到目标分数
				gameGroup.setVisible(false);//隐藏游戏界面
				successGroup.setVisible(true);//显示成功界面..
			}
			scoreNumsLabel.setText("your score is : " + scoreNums);
			//让星星重新从最高点开始下落..
			xingxing.setPosition(random.nextFloat() * 480 , 900);
		}
	}
	/**
	 * 用于每一帧更新游戏的剩余时间
	 */
	public void updateLeftTime(){
//		long diff = endTime - SystemClock.elapsedRealtime();//获取现在的剩余时间。单位是毫秒数
		long diff = endTime - Utils.getMilisTimes();//获取现在的剩余时间。单位是毫秒数
		if (diff < 0) {//表示游戏已经结束
//			isDoneds[i] = true;
//			leftTimeLabels[i].setVisible(false);
//			jindutiaoImages[i].setVisible(false);
//			workingImages[i].setVisible(false);
//			getMoneyImages[i].setVisible(true);
			// 这时候依然让它先不能显示
			// singleWork = false;// 表示可以按下其他按钮了...
			// removeOtherWorkOnImagesAnimation(i);
			gameGroup.setVisible(false);//隐藏游戏界面
			loseGroup.setVisible(true);//显示失败界面
		}
		leftTimeSB.delete(0, leftTimeSB.length());// 清空之前的内容
		long current = diff / 1000;//计算现在剩余多少秒
		/**
		 * 以下的逻辑就是讲current秒数转换成"00:00:00"的时间格式
		 */
		int hour = (int) (current / (3600));
		int minute = (int) ((current - hour * 3600) / 60);
		int second = (int) (current - hour * 3600 - minute * 60);
		if (hour < 10) {
			leftTimeSB.append('0');
		}
		leftTimeSB.append(hour);
		leftTimeSB.append(':');
		if (minute < 10) {
			leftTimeSB.append('0');
		}
		leftTimeSB.append(minute);
		leftTimeSB.append(':');
		if (second < 10) {
			leftTimeSB.append('0');
		}
		leftTimeSB.append(second);
		// System.out.println("------------->dailyTaskTimesb: " +
		// dailyTaskTimesb);
		leftTimeLabel.setText("the left time is: " + leftTimeSB.toString());
	}
	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);// 设置背景为白色
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);// 清屏
		bgImage1.update();//每一帧都更新第一章背景图和第二张背景图的位置
		bgImage2.update();
		xingxing.update();
		pengzhuangJiance();
		updateLeftTime();
		stage.act();
		stage.draw();
	}
	@Override
	public void resize(int arg0, int arg1) {
	}
	@Override
	public void resume() {
		// TODO Auto-generated method stub
	}
}
