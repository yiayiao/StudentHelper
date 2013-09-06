package work.studenthelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GpaActivity extends Activity {


	private EditText	studentNumber;
	private EditText	studentPassword;
	private Button		submitButton;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gpalogin);

		studentNumber = (EditText) findViewById(R.id.studentNumber);
		studentPassword = (EditText) findViewById(R.id.studentPassword);
		submitButton = (Button) findViewById(R.id.gpaLoginSubmit);

		submitButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				String studentNum = studentNumber.getText().toString();
				String studentPass = studentPassword.getText().toString();

				if (studentNum.equals("") || studentPass.equals("")) {
					Toast.makeText(GpaActivity.this, "请填写您的学号密码", Toast.LENGTH_LONG).show();
				}

				else if (studentNum.length() != 10) {
					Toast.makeText(GpaActivity.this, "学号应该是10位数字", Toast.LENGTH_LONG).show();
				}

				else {
					Intent next = new Intent();
					next.setClass(GpaActivity.this, GpaMainActivity.class);
					next.putExtra("studentNum", studentNum);
					next.putExtra("studentPass", studentPass);
					GpaActivity.this.startActivity(next);
					GpaActivity.this.finish();
				}
			}
		});
	}
}
