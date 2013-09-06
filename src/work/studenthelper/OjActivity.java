package work.studenthelper;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class OjActivity extends Activity {
	private List<String>			list	= new ArrayList<String>();
	private TextView				myTextView;
	private Spinner					mySpinner;
	private ArrayAdapter<String>	adapter;
	private Button					btn;
	private EditText				problemId;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_oj);

		//第一步：添加一个下拉列表项的list，这里添加的项就是下拉列表的菜单项
		list.add("本地");
		list.add("北大");
		list.add("浙大");
		list.add("杭电");
		myTextView = (TextView) findViewById(R.id.chosseOJ);  //chooseOJ字段
		mySpinner = (Spinner) findViewById(R.id.ojList);          //下拉框
		//tips = (TextView) findViewById(R.id.tips);            //提示字段
		btn = (Button) findViewById(R.id.submit);             //确认按钮;
		problemId = (EditText) findViewById(R.id.problemId);
		//第二步：为下拉列表定义一个适配器，这里就用到里前面定义的list。
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
		//第三步：为适配器设置下拉列表下拉时的菜单样式。
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		//第四步：将适配器添加到下拉列表上
		mySpinner.setAdapter(adapter);
		//第五步：为下拉列表设置各种事件的响应，这个事响应菜单被选中
		mySpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				/* 将所选mySpinner 的值带入myTextView 中 */
				myTextView.setText("您选择了");
				/* 将mySpinner 显示 */
				arg0.setVisibility(View.VISIBLE);
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				myTextView.setText("NONE");
				arg0.setVisibility(View.VISIBLE);
			}
		});

		/* 下拉菜单弹出的内容选项触屏事件处理 */
		mySpinner.setOnTouchListener(new Spinner.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				/* 将mySpinner 隐藏，不隐藏也可以，看自己爱好 */
				v.setVisibility(View.INVISIBLE);
				return false;
			}
		});

		/* 下拉菜单弹出的内容选项焦点改变事件处理 */
		mySpinner.setOnFocusChangeListener(new Spinner.OnFocusChangeListener() {
			public void onFocusChange(View v, boolean hasFocus) {
				v.setVisibility(View.VISIBLE);
			}
		});

		//增加事件响应  
		btn.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				//Toast提示控件  
				/*
				 * Toast.makeText(MainActivity.this,
				 * "TextView里的文字发生了改变,你注意到了吗?",
				 * Toast.LENGTH_LONG).show();
				 */
				//将TextView的文字发生改变  
				String OJ = mySpinner.getSelectedItem().toString();
				/* 增加为空的判断 */
				if (problemId == null) {
					Toast.makeText(OjActivity.this,
						"请填写您的题号",
						Toast.LENGTH_LONG).show();
				}
				if (problemId.getText().toString().equals("")) {
					Toast.makeText(OjActivity.this,
						"请填写您的题号",
						Toast.LENGTH_LONG).show();
				}

				try {

					String id = problemId.getText().toString();
					Boolean f = false;
					int tid = Integer.parseInt(id);
					if (OJ == "本地") {
						if (tid >= 1000 && tid <= 1592) {
							Intent i = new Intent();
							i.setClass(OjActivity.this, ProblemInfoActivity.class);
							i.putExtra("OJ", OJ);//Intent中压入一组键值对
							i.putExtra("problemId", id);//Intent中压入一组键值对
							OjActivity.this.startActivity(i);
							OjActivity.this.finish();
							f = true;
						}
					}
					if (OJ == "北大") {
						if (tid >= 1000 && tid <= 4022) {
							Intent i = new Intent();
							i.setClass(OjActivity.this, ProblemInfoActivity.class);
							i.putExtra("OJ", OJ);//Intent中压入一组键值对
							i.putExtra("problemId", id);//Intent中压入一组键值对
							OjActivity.this.startActivity(i);
							OjActivity.this.finish();
							f = true;
						}
					}
					if (OJ == "浙大") {
						if (tid >= 1000 && tid <= 3630) {
							Intent i = new Intent();
							i.setClass(OjActivity.this, ProblemInfoActivity.class);
							i.putExtra("OJ", OJ);//Intent中压入一组键值对
							i.putExtra("problemId", id);//Intent中压入一组键值对
							OjActivity.this.startActivity(i);
							OjActivity.this.finish();
							f = true;
						}
					}
					if (OJ == "杭电") {
						if (tid >= 1000 && tid <= 4216) {
							Intent i = new Intent();
							i.setClass(OjActivity.this, ProblemInfoActivity.class);
							i.putExtra("OJ", OJ);//Intent中压入一组键值对
							i.putExtra("problemId", id);//Intent中压入一组键值对
							OjActivity.this.startActivity(i);
							OjActivity.this.finish();
							f = true;
						}
					}
					if (f == false) {
						Toast.makeText(OjActivity.this,
							"没有这道题哦",
							Toast.LENGTH_LONG).show();
					}
				}
				catch (NumberFormatException ex) {
					ex.printStackTrace();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
