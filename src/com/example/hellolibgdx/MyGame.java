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
	Jinbi xingxing;//���ϵ��������
	Image bucketImage;//���Բٿص�С��
	TextureAtlas atlas;
	TextureRegion bgRegion;
	BgImage bgImage1;//����һ��Image����ʵ�ֱ����ƶ��ĵ�һ��ͼ
	BgImage bgImage2;//ʵ�ֱ����ƶ��ĵڶ���ͼ
	int scoreNums;
	BitmapFont scoreFont;
	LabelStyle scoreNumsStyle;
	Label scoreNumsLabel;//�������Ƴɾ���
	Random random;//����������������ĺ�����
	Group successGroup;//�ɹ�����Group
	Label successLabel;//��ʾ��ɹ��˵ı�ǩ
	int targetScores = 3;//�ɹ�����
	Sound dropSound;//�������ǵ���Ч.Sound�ʺϳ��Ƚ϶̵���Ƶ�ļ�
	Music bjMusic;//������Ϸ�ı�������.Music�ʺϳ��Ƚϳ�����Ƶ�ļ�..
	Label leftTimeLabel;//����ʵ��ʣ��ʱ���߼�
	long endTime;//���������Ϸ������ʱ��
//	long continueTime = 5;//��Ϸ�ĳ���ʱ��
	long continueTime = 60;//��Ϸ�ĳ���ʱ��
	StringBuffer leftTimeSB;
	Group loseGroup;//����ʵ��ʧ�ܽ���
	Label loseLabel;//����ʵ��ʧ�ܵ���ʾ��Ϣ
	@Override
	public void create() {
		stage = new Stage(480, 800, false);
		random = new Random();
		xingxing = new Jinbi(new Texture(Gdx.files.internal("data/droplet.png")));
		xingxing.setPosition(240, 900);//��ʼ����һ���ȵ�ǰ��̨�ĸ߶�Ҫ�ߵ�ֵ�������ͻ�����һ�����Ǵ�����������ĸо�
		xingxing.setColor(Color.WHITE);
		bucketImage = new Image(new Texture(Gdx.files.internal("data/bucket.png")));
		/**
		 * BitmapFont(FileHandle fontFile, boolean flip)
		 * fontFile: ָ�������ļ���·��
		 * filp: �����Ƿ�ת
		 */
		scoreFont = new BitmapFont(Gdx.files.internal("font/default.fnt"), false);
		/**
		 * LabelStyle(BitmapFont font, Color fontColor)
		 * font: �������
		 * fontColor: ������ɫ
		 */
		scoreNumsStyle = new LabelStyle(scoreFont, Color.WHITE);
		/**
		 * Label(CharSequence text, LabelStyle style)
		 * text: ��һ��label������
		 * style: ��һ��lablel�ķ��
		 */
		scoreNumsLabel = new Label("your score is : " + scoreNums, scoreNumsStyle);
		scoreNumsLabel.setPosition(0, 600);
		scoreNumsLabel.setColor(Color.WHITE);
		
		/**
		 * SystemClock.elapsedRealtime():��ǰϵͳʱ��ĺ�����
		 */
//		endTime = SystemClock.elapsedRealtime() + continueTime*1000;
		endTime = Utils.getMilisTimes() + continueTime*1000;
		leftTimeSB = new StringBuffer();
		leftTimeLabel = new Label("left time is : " + continueTime, scoreNumsStyle);
		leftTimeLabel.setPosition(0, 500);
		atlas = new TextureAtlas(Gdx.files.internal("data/movebg.atlas"));
		bgRegion = atlas.findRegion("movebg");//��ʼ��TextureAtlas
		bgImage1 = new BgImage(bgRegion);//��ʼ����һ��bgImage����
		bgImage1.setSize(480, 800);//����bgImage�Ĵ�С�����Ϊ480,�߶�Ϊ800
		bgImage2 = new BgImage(bgRegion);
		bgImage2.setSize(480, 800);
		bgImage2.setPosition(0, 800);//���õڶ��±���ͼ��λ��
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
		successGroup.setVisible(false);//һ��ʼ�ɹ����治�ɼ�
		successGroup.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				//���سɹ�����
				successGroup.setVisible(false);
				//��ʾ��Ϸ����
				gameGroup.setVisible(true);
				scoreNums = 0;//��ճɼ�
				scoreNumsLabel.setText("your score is : " + scoreNums);
				endTime = SystemClock.elapsedRealtime() + continueTime*1000;//���¼�����Ϸ�Ľ���ʱ��
				return true;
			}
		});
		dropSound = Gdx.audio.newSound(Gdx.files.internal("music/drop.wav"));
		bjMusic = Gdx.audio.newMusic(Gdx.files.internal("music/rain.mp3"));//��Ч�ĳ�ʼ��
		bjMusic.setLooping(true);//���ñ�������ѭ������
		bjMusic.play();//��ʼ��������
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
				//����ʧ�ܽ���
				loseGroup.setVisible(false);
				//��ʾ��Ϸ����
				gameGroup.setVisible(true);
				scoreNums = 0;//��ճɼ�
				scoreNumsLabel.setText("your score is : " + scoreNums);
//				endTime = SystemClock.elapsedRealtime() + continueTime*1000;//���¼�����Ϸ�Ľ���ʱ��
				endTime = Utils.getMilisTimes() + continueTime*1000;//���¼�����Ϸ�Ľ���ʱ��
				return true;
			}
		});
		stage.addActor(bgImage1);//����Ա��ӵ���̨��
		stage.addActor(bgImage2);
		stage.addActor(gameGroup);
		stage.addActor(successGroup);
		stage.addActor(loseGroup);
		Gdx.input.setInputProcessor(stage);
	}
	/**
	 * ��stage��Ӽ����������ı�С�˵�λ��
	 */
	public void addListenerToStageToHandleBucket(){
		stage.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;//��ʾ�¼��Ѿ�����ȡ,�������´���
			}
			@Override
			public void touchDragged(InputEvent event, float x, float y,
					int pointer) {
				bucketImage.setX(x);//������ָ�ٿص�λ��������С�˵�λ��..
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
		if(Utils.isOverlap(xingxing, bucketImage) == true){//�����������ײ
			dropSound.play();//���ż�����ʱ����Ч
			//���³ɾ�
			scoreNums++;
			if(scoreNums >= targetScores){//����ﵽĿ�����
				gameGroup.setVisible(false);//������Ϸ����
				successGroup.setVisible(true);//��ʾ�ɹ�����..
			}
			scoreNumsLabel.setText("your score is : " + scoreNums);
			//���������´���ߵ㿪ʼ����..
			xingxing.setPosition(random.nextFloat() * 480 , 900);
		}
	}
	/**
	 * ����ÿһ֡������Ϸ��ʣ��ʱ��
	 */
	public void updateLeftTime(){
//		long diff = endTime - SystemClock.elapsedRealtime();//��ȡ���ڵ�ʣ��ʱ�䡣��λ�Ǻ�����
		long diff = endTime - Utils.getMilisTimes();//��ȡ���ڵ�ʣ��ʱ�䡣��λ�Ǻ�����
		if (diff < 0) {//��ʾ��Ϸ�Ѿ�����
//			isDoneds[i] = true;
//			leftTimeLabels[i].setVisible(false);
//			jindutiaoImages[i].setVisible(false);
//			workingImages[i].setVisible(false);
//			getMoneyImages[i].setVisible(true);
			// ��ʱ����Ȼ�����Ȳ�����ʾ
			// singleWork = false;// ��ʾ���԰���������ť��...
			// removeOtherWorkOnImagesAnimation(i);
			gameGroup.setVisible(false);//������Ϸ����
			loseGroup.setVisible(true);//��ʾʧ�ܽ���
		}
		leftTimeSB.delete(0, leftTimeSB.length());// ���֮ǰ������
		long current = diff / 1000;//��������ʣ�������
		/**
		 * ���µ��߼����ǽ�current����ת����"00:00:00"��ʱ���ʽ
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
		Gdx.gl.glClearColor(1, 1, 1, 1);// ���ñ���Ϊ��ɫ
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);// ����
		bgImage1.update();//ÿһ֡�����µ�һ�±���ͼ�͵ڶ��ű���ͼ��λ��
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
