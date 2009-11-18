package net.droidsolutions.droidcharts;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;

public class StartActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// BarChartCategoryExampletView mView = new
		// BarChartCategoryExampletView(this);
		// BarChartExampleView mView = new BarChartExampleView(this);
		LineChartExampleView mView = new LineChartExampleView(this);
		
		mView.setOnCreateContextMenuListener(new ChartsContextMenuListener());
		String title = "My Chart";
		

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(mView);

	}
	
	public static class ChartsContextMenuListener implements OnCreateContextMenuListener{

		@Override
		public void onCreateContextMenu(ContextMenu menu, View v,
				ContextMenuInfo menuInfo) {
			menu.setHeaderTitle("Chart Examples");
			menu.add(1, 1, 1, "Pie Chart");
			menu.add(1, 2, 2, "Bar Chart");
			menu.add(1, 3, 3, "Category Bar Chart");
			menu.add(1, 4, 4, "Line Chart");
			
		}
		
	}


	public boolean onContextItemSelected(MenuItem aItem) {
		if (aItem.getItemId() == 1) {
			PieChartExampleView mView = new PieChartExampleView(this);
			mView.setOnCreateContextMenuListener(new ChartsContextMenuListener());
			setContentView(mView);
			return true;
		}

		if (aItem.getItemId() == 2) {
			BarChartExampleView mView = new BarChartExampleView(this);
			mView.setOnCreateContextMenuListener(new ChartsContextMenuListener());
			setContentView(mView);
			return true;
		}

		if (aItem.getItemId() == 3) {
			BarChartCategoryExampletView mView = new BarChartCategoryExampletView(
					this);
			mView.setOnCreateContextMenuListener(new ChartsContextMenuListener());
			setContentView(mView);
			return true;
		}
		
		if (aItem.getItemId() == 4) {
			LineChartExampleView mView = new LineChartExampleView(
					this);
			mView.setOnCreateContextMenuListener(new ChartsContextMenuListener());
			setContentView(mView);
			return true;
		}

		return false;
	}
}