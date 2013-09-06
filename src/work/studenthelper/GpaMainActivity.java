package work.studenthelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import GetScore.Login;
import GetScore.PersonInfo;
import GetScore.Result;
import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class GpaMainActivity extends Activity {

	private Login			login;
	private Spinner			gpaList;
	private TextView		gpaListChoose;
	private Button			gpaSubmitButton;
	private List<String>	list	= new ArrayList<String>();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gpa_main);

		String studentNum = this.getIntent().getCharSequenceExtra("studentNum").toString();
		String studentPass = this.getIntent().getCharSequenceExtra("studentPass").toString();

		TextView basicInfoTitle = (TextView) findViewById(R.id.basicInfoTitle);
		TextView basicInfo = (TextView) findViewById(R.id.basicInfo);

		try {
			login = new Login(studentNum, studentPass);
			if (!login.isLoginSuccessful()) { //没能登录成功
				Toast.makeText(GpaMainActivity.this, "您输入了错误的学号密码", Toast.LENGTH_LONG).show();
				Intent next = new Intent();
				next.setClass(GpaMainActivity.this, GpaActivity.class);
				GpaMainActivity.this.startActivity(next);
				GpaMainActivity.this.finish();
			}

			///本页显示学生的基本信息/
			PersonInfo personInfo = login.getPerson();
			StringBuffer personInfoMessage = new StringBuffer();
			basicInfoTitle.setText("欢迎您：" + personInfo.getName());
			personInfoMessage.append("您的学号：" + personInfo.getStuid());
			personInfoMessage.append('\n');
			personInfoMessage.append("您的性别：" + personInfo.getSex());
			personInfoMessage.append('\n');
			personInfoMessage.append("您的院系：" + personInfo.getSchool());
			personInfoMessage.append('\n');
			personInfoMessage.append("您的专业：" + personInfo.getMajor());
			personInfoMessage.append('\n');
			personInfoMessage.append("您的班级：" + personInfo.getClassid());
			basicInfo.setText(personInfoMessage);

			gpaSubmitButton = (Button) findViewById(R.id.gpaChooseSubmit);
			gpaListChoose = (TextView) findViewById(R.id.gpaListChoose);
			gpaList = (Spinner) findViewById(R.id.gpaList);
			list.add("本学期成绩");
			list.add("通识教育课");
			list.add("学科教育课");
			list.add("专业基础课");
			list.add("专业方向课");
			list.add("专业选修课");
			list.add("人文素质课");
			list.add("科学素质课");
			list.add("外国语言课");
			list.add("计划外课程");
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			gpaList.setAdapter(adapter);
			gpaList.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

				public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					gpaListChoose.setText("您选择了：");
					arg0.setVisibility(View.INVISIBLE);

				}

				public void onNothingSelected(AdapterView<?> arg0) {
					arg0.setVisibility(View.VISIBLE);
				}
			});

			/* 下拉菜单弹出的内容选项触屏事件处理 */
			gpaList.setOnTouchListener(new Spinner.OnTouchListener() {
				public boolean onTouch(View arg0, MotionEvent arg1) {
					arg0.setVisibility(View.INVISIBLE);
					return false;
				}
			});

			/* 下拉菜单弹出的内容选项焦点改变事件处理 */
			gpaList.setOnFocusChangeListener(new Spinner.OnFocusChangeListener() {
				public void onFocusChange(View v, boolean hasFocus) {
					v.setVisibility(View.VISIBLE);
				}
			});

			gpaSubmitButton.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {
					try {

						Intent next = new Intent();
						next.setClass(GpaMainActivity.this, GpaShowPairActivity.class);


						int id = 0;

						String gpaNext = gpaList.getSelectedItem().toString();
						if (gpaNext.equals("本学期成绩")) {
							id = 9;
						}
						else if (gpaNext.equals("通识教育课")) {
							id = 0;
						}
						else if (gpaNext.equals("学科教育课")) {
							//ve = login.getPastTerm()[1];
							id = 1;
						}
						else if (gpaNext.equals("专业基础课")) {
							//ve = login.getPastTerm()[2];
							id = 2;
						}
						else if (gpaNext.equals("专业方向课")) {
							//ve = login.getPastTerm()[3];
							id = 3;
						}
						else if (gpaNext.equals("专业选修课")) {
							//ve = login.getPastTerm()[4];
							id = 4;
						}
						else if (gpaNext.equals("人文素质课")) {
							//ve = login.getPastTerm()[5];
							id = 5;
						}
						else if (gpaNext.equals("科学素质课")) {
							//ve = login.getPastTerm()[6];
							id = 6;
						}
						else if (gpaNext.equals("外国语言课")) {
							//ve = login.getPastTerm()[7];
							id = 7;
						}
						else if (gpaNext.equals("计划外课程")) {
							//ve = login.getPastTerm()[8];
							id = 8;
						}


						next.putExtra("choose", id);
						GpaMainActivity.this.startActivity(next);
						GpaMainActivity.this.finish();
					}
					catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}


	}
}
