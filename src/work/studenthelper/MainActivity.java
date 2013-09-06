package work.studenthelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button homeButton1 = (Button) findViewById(R.id.homeButton1);
		Button homeButton2 = (Button) findViewById(R.id.homeButton2);

		homeButton1.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View v) {
				Intent next = new Intent();
				next.setClass(MainActivity.this, GpaActivity.class);
				MainActivity.this.startActivity(next);
				MainActivity.this.finish();
			}
		});

		homeButton2.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View v) {
				Intent next = new Intent();
				next.setClass(MainActivity.this, OjActivity.class);
				MainActivity.this.startActivity(next);
				MainActivity.this.finish();

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
