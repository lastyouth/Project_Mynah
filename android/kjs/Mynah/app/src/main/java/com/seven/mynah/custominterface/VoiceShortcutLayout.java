package com.seven.mynah.custominterface;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

import com.seven.mynah.R;

public class VoiceShortcutLayout extends CustomButton{
private View view;
	
	public VoiceShortcutLayout(Context context, CustomButtonsFragment _cbf) 
	{
		super(context);
		// TODO Auto-generated constructor stub
		cbf = _cbf;
		initView();
	}
	
	private void initView() 
	{
		view = inflate(getContext(), R.layout.layout_button_voice, null);

		view.setOnTouchListener(new VoiceTouchListener());
		addView(view);
	}
	
	private final class VoiceTouchListener implements OnTouchListener {
		public boolean onTouch(View view, MotionEvent motionEvent) {
			
			Log.d("Touch", "motionEvent.getAction()" + motionEvent.getAction());
			if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
				view.setAlpha((float) 0.8);
				return true;
			} else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
				return true;
			} else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
				view.setAlpha((float) 1.0);



				return true;
			}
			return true;
		}
	}

	private void myTTS()
	{
		//TextToSpeech m_tts = new TextToSpeech(getContext(), this);
		//http://www.tipssoft.com/bulletin/board.php?bo_table=FAQ&wr_id=953
	}
	
}
