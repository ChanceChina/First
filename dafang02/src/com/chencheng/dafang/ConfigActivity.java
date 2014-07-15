package com.chencheng.dafang;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.chencheng.dafang.util.Constant;

public class ConfigActivity extends Activity{
	RadioGroup rg_model,favor_chess ;
	RadioButton pVc;
	RadioButton pVp,leaf_chess,bar_chess;
	CheckBox playerFirst;
	CheckBox music;
	CheckBox canRegret;
	Button btn_con_back;
	SharedPreferences sf ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v("Config-activity","start......");
		/* ����Ϊ�ޱ����� */
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.config);
		sf = this.getSharedPreferences("chenguan_dafang_config", Context.MODE_PRIVATE);
		//�õ������������Ĭ��ֵ
		rg_model = (RadioGroup)findViewById(R.id.play_model_rdiogroup);
		pVc = (RadioButton)findViewById(R.id.playerVScomputer);
		pVp = (RadioButton)findViewById(R.id.playerVSplayer);
		
		//��Ϸģʽ
		if(Constant.IS_MAN_VS_MACHINE){
			pVc.setChecked(true);
		}else{
			pVp.setChecked(true);
		}
		//˭���µ�һ��
		playerFirst = (CheckBox)findViewById(R.id.selffirst);
		if(Constant.IS_PLAYER_FIRST){
			playerFirst.setChecked(true);
		}else{
			playerFirst.setChecked(false);
		}
		
		//�������� 
		music = (CheckBox)findViewById(R.id.ismusic);
		if(Constant.IS_MUSIC){
			music.setChecked(true);
		}else{
			music.setChecked(false);
		}
		//����
		canRegret = (CheckBox)findViewById(R.id.canRegret);
		if(Constant.CAN_REGRET){
			canRegret.setChecked(true);
		}else{
			canRegret.setChecked(false);
		}
		
		//ѡ������
		favor_chess = (RadioGroup) findViewById(R.id.favor_chess);
		leaf_chess = (RadioButton) findViewById(R.id.leaf_chess);
		bar_chess= (RadioButton) findViewById(R.id.bar_chess);
		if(Constant.IS_LEAF_CHESS_FOR_PLAYER01){
			leaf_chess.setChecked(true);
		}else{
			bar_chess.setChecked(true);
		}
		
		//��սģʽ�����¼�
		rg_model.setOnCheckedChangeListener(new android.widget.RadioGroup.OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(RadioGroup rgroup, int checkId) {
				if(pVc.getId() ==checkId){
					Constant.IS_MAN_VS_MACHINE = true;
					Log.v("��ս01",Constant.IS_MAN_VS_MACHINE+"");
				}
				if(pVp.getId() == checkId){
					Constant.IS_MAN_VS_MACHINE = false;
					Log.v("��ս02",Constant.IS_MAN_VS_MACHINE+"");
				}
				sf.edit().putBoolean("IS_MAN_VS_MACHINE", Constant.IS_MAN_VS_MACHINE).commit();
			}
		});
		
		
		//˭���ߵ�һ��
		playerFirst.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton btn, boolean isPlayerFirst) {
				if(isPlayerFirst){
					Constant.IS_PLAYER_FIRST = true;
					Log.v("˭��",Constant.IS_PLAYER_FIRST+"");
				}else{
					Constant.IS_PLAYER_FIRST = false;
					Log.v("˭��02",Constant.IS_PLAYER_FIRST+"");
				}
				sf.edit().putBoolean("isPlayerFirst", Constant.IS_PLAYER_FIRST).commit();
			}
		});
		
		
		//�������� 
		music.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
				if(isChecked){
					Constant.IS_MUSIC = true;
					Log.v("music01",Constant.IS_MUSIC+"");
				}else{
					Constant.IS_MUSIC = false;
					Log.v("music02",Constant.IS_MUSIC+"");
				}
				sf.edit().putBoolean("isMusic", Constant.IS_MUSIC).commit();
			}
		});
		
		//�Ƿ�ɻ���
				canRegret.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
						if(isChecked){
							Constant.CAN_REGRET = true;
							Log.v("CANREGRET",Constant.CAN_REGRET+"");
						}else{
							Constant.CAN_REGRET = false;
							Log.v("CANREGRET",Constant.CAN_REGRET+"");
						}
						sf.edit().putBoolean("CANREGRET", Constant.CAN_REGRET).commit();
					}
				});
		
		//ѡ������
		favor_chess.setOnCheckedChangeListener(new android.widget.RadioGroup.OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(RadioGroup rg, int checkedId) {
				if(checkedId==leaf_chess.getId()){
					Constant.IS_LEAF_CHESS_FOR_PLAYER01 = true;
					Log.v("leaf chess",Constant.IS_LEAF_CHESS_FOR_PLAYER01+"");
				}else if(checkedId == bar_chess.getId()){
					Constant.IS_LEAF_CHESS_FOR_PLAYER01 = false;
					Log.v("bar chess",Constant.IS_LEAF_CHESS_FOR_PLAYER01+"");
				}
				sf.edit().putBoolean("IS_LEAF_CHESS_FOR_PLAYER01", Constant.IS_LEAF_CHESS_FOR_PLAYER01).commit();
			}
			
		});
		
		
		//����
		btn_con_back = (Button)findViewById(R.id.btn_con_back);
		btn_con_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View btn) {
				Intent intent = new Intent(ConfigActivity.this,Main.class);
				ConfigActivity.this.startActivity(intent);
				ConfigActivity.this.finish();
			}
		});
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			Intent intent = new Intent(ConfigActivity.this,Main.class);
			ConfigActivity.this.startActivity(intent);
			ConfigActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
