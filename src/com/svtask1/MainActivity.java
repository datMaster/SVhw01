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
		private CountDownTimer timer;
		private int pressedCounter;
		private Boolean timerStatus = false;
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
			timer = new CountDownTimer(Constants.TIMER_IN_FUTURE, Constants.TIMER_INTERVAL) {
				
				@Override
				public void onTick(long millisUntilFinished) {					
					switch ((int)millisUntilFinished / Constants.ONE_SECOND) {
					case Constants.PUSH_30_SECOND_TO_END:
						if(pressedCounter == Constants.ONE_PRESS)
							reload();
						break;
					case Constants.PUSH_10_SECOND_TO_END : 
						reload(); 
						break;
					default:
						break;
					}
				}
				
				@Override
				public void onFinish() {
					reload();					
				}
			};
			
			return rootView;
		}

		@Override
		public void onClick(View v) {			
			switch (v.getId()) {
			case R.id.buttonPushMe : {
				if(!timerStatus) {
					timer.start();
					timerStatus = true;					
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
				anim = (Animation)AnimationUtils.loadAnimation(getActivity(), R.anim.leftright);
				reloadButton.startAnimation(anim);
				reloadButton.setVisibility(View.VISIBLE);				
				pushMeButton.setEnabled(false);	
				break;
			}
			default:
				break;
			}
		}
		
		private void reload() {				
			pressedCounter = 0;
			timerStatus = false;
			timer.cancel();			
			pushMeButton.setEnabled(true);
			reloadButton.setVisibility(View.GONE);
			messageView.setText(R.string.hello_world);
		}				
	}	

}
