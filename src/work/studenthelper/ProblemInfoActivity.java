package work.studenthelper;

import java.io.IOException;

import com.example.blackberry.CatchProblemLy;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ProblemInfoActivity extends Activity {

	private WebView		httpBody;
	private TextView	inputSample;
	private WebView		httpBehindInput;
	private TextView	outputSample;
	private WebView		httpBehindOutput;

	private Button		btn;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_oj_showproblem);

		btn = (Button) findViewById(R.id.back);            //»∑»œ∞¥≈•;
		httpBody = (WebView) findViewById(R.id.httpBody);
		inputSample = (TextView) findViewById(R.id.inputsample);
		httpBehindInput = (WebView) findViewById(R.id.httpBehindInput);
		outputSample = (TextView) findViewById(R.id.outputsample);
		httpBehindOutput = (WebView) findViewById(R.id.httpBehindOutput);


		String OJ = this.getIntent().getCharSequenceExtra("OJ").toString();
		String Id = this.getIntent().getCharSequenceExtra("problemId").toString();

		/* edit by coolness */
		try {
			CatchProblemLy htmlNead = new CatchProblemLy(OJ, Id);

			httpBody.getSettings().setJavaScriptEnabled(true);
			httpBody.getSettings().setDefaultTextEncodingName("utf-8");
			httpBody.loadData("<html>" + htmlNead.getHttpBody() + "</html>", "text/html", "utf-8");

			inputSample.setText(htmlNead.getInputSample());

			httpBehindInput.getSettings().setJavaScriptEnabled(true);
			httpBehindInput.getSettings().setDefaultTextEncodingName("utf-8");
			httpBehindInput.loadData("<html>" + htmlNead.getHttpBehindInput() + "</html>", "text/html", "utf-8");

			outputSample.setText(htmlNead.getOutputSample());

			httpBehindOutput.getSettings().setJavaScriptEnabled(true);
			httpBehindOutput.getSettings().setDefaultTextEncodingName("utf-8");
			httpBehindOutput.loadData("<html>" + htmlNead.getHttpBehindInput() + "</html>", "text/html", "utf-8");
			//id.setText(htmlString);
		}
		catch (IOException e) {
			Toast.makeText(ProblemInfoActivity.this,
				e.toString(),
				Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		catch (Exception e) {
			Toast.makeText(ProblemInfoActivity.this,
				e.toString(),
				Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}

		btn.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent();
				i.setClass(ProblemInfoActivity.this, OjActivity.class);
				ProblemInfoActivity.this.startActivity(i);
				ProblemInfoActivity.this.finish();
			}
		});
	}
}