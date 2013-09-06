package work.studenthelper;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class GpaShowPairActivity extends Activity {

	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gpa_pair);
		
		
		TextView gpaInfo = (TextView) findViewById(R.id.gpaInfo);
		
		StringBuffer temp = null;
		int results =Integer.parseInt( this.getIntent().getCharSequenceExtra("choose").toString() );
		
		
		
		
		
	}

}
