package com.svtask1;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {			
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment implements OnClickListener{		
		
		private Button reloadButton;
		private Button pushMeButton;
		private TextView messageView;
		private CountDownTimer timer10sec;
		private CountDownTimer timer30sec;
		private int pressedCounter;
		private Boolean timer10Status = false;
		private Animation anim = null;			
		
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,	false);
			
			reloadButton = (Button)rootView.findViewById(R.id.buttonReload);
			pushMeButton = (Button)rootView.findViewById(R.id.buttonPushMe);
			messageView = (TextView)rootView.findViewById(R.id.textViewMessage);					
			
			reloadButton.setOnClickListener(this);
			pushMeButton.setOnClickListener(this);
			reloadButton.setVisibility(View.GONE);
			timer10sec = new CountDownTimer(Constants.TIMER_IN_FUTURE10, Constants.TIMER_INTERVAL) {
				
				@Override
				public void onTick(long millisUntilFinished) {					
					
				}
				
				@Override
				public void onFinish() {
					reload();					
				}
			};
			
			timer30sec = new CountDownTimer(Constants.TIMER_IN_FUTURE30, Constants.TIMER_INTERVAL) {
				@Override
				public void onTick(long millisUntilFinished) {					
					
				}
				
				@Override
				public void onFinish() {
					reload30();					
				}
			};
			
			return rootView;
		}

		@Override
		public void onClick(View v) {			
			switch (v.getId()) {
			case R.id.buttonPushMe : {
				if(!timer10Status) {
					timer10sec.start();
					timer10Status = true;					
				}
				else {
					timer10sec.cancel();
					timer10sec.start();
				}
				pressedCounter ++;
				checkPreseedCounter();
				break;
			}
			case R.id.buttonReload : 
				reload();				
				break;
			default:
				break;
			}
		}
		
		private void checkPreseedCounter(){
			switch (pressedCounter) {
			case Constants.ONE_PRESS: messageView.setText(getText(R.string.label_one));				
				break;
			case Constants.TWO_PRESSES: messageView.setText(getText(R.string.label_two));				
				break;
			case Constants.THREE_PRESSES: messageView.setText(getText(R.string.label_three));				
				break;
			case Constants.SIXT_PRESSES: messageView.setText(getText(R.string.label_sixt));				
				break;
			case Constants.TEN_PRESSES: messageView.setText(getText(R.string.label_ten));				
				break;
			case Constants.TWENTY_PRESSES: { 
				messageView.setText(getText(R.string.label_twenty));
				pushMeButton.setEnabled(false);	
				if(timer10Status){
					timer30sec.start();
					timer10sec.cancel();
					timer10Status = false;
				}
				break;
			}
			default:
				break;
			}
		}
		
		private void reload() {				
			pressedCounter = 0;
			timer10Status = false;				
			pushMeButton.setEnabled(true);
			reloadButton.setVisibility(View.GONE);
			messageView.setText(R.string.hello_world);
		}
		
		private void reload30() {			
			anim = (Animation)AnimationUtils.loadAnimation(getActivity(), R.anim.leftright);				
			reloadButton.startAnimation(anim);
			reloadButton.setVisibility(View.VISIBLE);							
		}
	}	

}
