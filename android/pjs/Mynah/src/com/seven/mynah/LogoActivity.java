package com.seven.mynah;

import java.text.NumberFormat;
import java.util.StringTokenizer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.seven.mynah.R;

public class LogoActivity extends Activity{
	
	RelativeLayout rlayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logo);
		
		
	}
	
	//���� ������ �ʿ��� ������ �ε��ϴ� �Լ�.
	public void loadInitialSetup() {
		
		
		//��� �ε��ϰ� �� �ڿ�. ��Ȳ�� �°� �ε�.
		//Intent intent = new Intent(LogoActivity.this, );
		//startActivity(intent);
		finish();
	}
	
	// �� ��Ȳ���� �ʿ����.. A->B���� B->A�� ���� ���̾�α� ����� �� ���.

	@Override 	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		
		
	}
	
}
