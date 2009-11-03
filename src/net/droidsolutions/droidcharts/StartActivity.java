package net.droidsolutions.droidcharts;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class StartActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ChartView mView = new ChartView(this);
		String title = "My Chart";

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(mView);
	}
}