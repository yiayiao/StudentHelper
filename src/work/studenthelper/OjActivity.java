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

		//��һ�������һ�������б����list��������ӵ�����������б�Ĳ˵���
		list.add("����");
		list.add("����");
		list.add("���");
		list.add("����");
		myTextView = (TextView) findViewById(R.id.chosseOJ);  //chooseOJ�ֶ�
		mySpinner = (Spinner) findViewById(R.id.ojList);          //������
		//tips = (TextView) findViewById(R.id.tips);            //��ʾ�ֶ�
		btn = (Button) findViewById(R.id.submit);             //ȷ�ϰ�ť;
		problemId = (EditText) findViewById(R.id.problemId);
		//�ڶ�����Ϊ�����б���һ����������������õ���ǰ�涨���list��
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
		//��������Ϊ���������������б�����ʱ�Ĳ˵���ʽ��
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		//���Ĳ�������������ӵ������б���
		mySpinner.setAdapter(adapter);
		//���岽��Ϊ�����б����ø����¼�����Ӧ���������Ӧ�˵���ѡ��
		mySpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				/* ����ѡmySpinner ��ֵ����myTextView �� */
				myTextView.setText("��ѡ����");
				/* ��mySpinner ��ʾ */
				arg0.setVisibility(View.VISIBLE);
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				myTextView.setText("NONE");
				arg0.setVisibility(View.VISIBLE);
			}
		});

		/* �����˵�����������ѡ����¼����� */
		mySpinner.setOnTouchListener(new Spinner.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				/* ��mySpinner ���أ�������Ҳ���ԣ����Լ����� */
				v.setVisibility(View.INVISIBLE);
				return false;
			}
		});

		/* �����˵�����������ѡ���ı��¼����� */
		mySpinner.setOnFocusChangeListener(new Spinner.OnFocusChangeListener() {
			public void onFocusChange(View v, boolean hasFocus) {
				v.setVisibility(View.VISIBLE);
			}
		});

		//�����¼���Ӧ  
		btn.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				//Toast��ʾ�ؼ�  
				/*
				 * Toast.makeText(MainActivity.this,
				 * "TextView������ַ����˸ı�,��ע�⵽����?",
				 * Toast.LENGTH_LONG).show();
				 */
				//��TextView�����ַ����ı�  
				String OJ = mySpinner.getSelectedItem().toString();
				/* ����Ϊ�յ��ж� */
				if (problemId == null) {
					Toast.makeText(OjActivity.this,
						"����д�������",
						Toast.LENGTH_LONG).show();
				}
				if (problemId.getText().toString().equals("")) {
					Toast.makeText(OjActivity.this,
						"����д�������",
						Toast.LENGTH_LONG).show();
				}

				try {

					String id = problemId.getText().toString();
					Boolean f = false;
					int tid = Integer.parseInt(id);
					if (OJ == "����") {
						if (tid >= 1000 && tid <= 1592) {
							Intent i = new Intent();
							i.setClass(OjActivity.this, ProblemInfoActivity.class);
							i.putExtra("OJ", OJ);//Intent��ѹ��һ���ֵ��
							i.putExtra("problemId", id);//Intent��ѹ��һ���ֵ��
							OjActivity.this.startActivity(i);
							OjActivity.this.finish();
							f = true;
						}
					}
					if (OJ == "����") {
						if (tid >= 1000 && tid <= 4022) {
							Intent i = new Intent();
							i.setClass(OjActivity.this, ProblemInfoActivity.class);
							i.putExtra("OJ", OJ);//Intent��ѹ��һ���ֵ��
							i.putExtra("problemId", id);//Intent��ѹ��һ���ֵ��
							OjActivity.this.startActivity(i);
							OjActivity.this.finish();
							f = true;
						}
					}
					if (OJ == "���") {
						if (tid >= 1000 && tid <= 3630) {
							Intent i = new Intent();
							i.setClass(OjActivity.this, ProblemInfoActivity.class);
							i.putExtra("OJ", OJ);//Intent��ѹ��һ���ֵ��
							i.putExtra("problemId", id);//Intent��ѹ��һ���ֵ��
							OjActivity.this.startActivity(i);
							OjActivity.this.finish();
							f = true;
						}
					}
					if (OJ == "����") {
						if (tid >= 1000 && tid <= 4216) {
							Intent i = new Intent();
							i.setClass(OjActivity.this, ProblemInfoActivity.class);
							i.putExtra("OJ", OJ);//Intent��ѹ��һ���ֵ��
							i.putExtra("problemId", id);//Intent��ѹ��һ���ֵ��
							OjActivity.this.startActivity(i);
							OjActivity.this.finish();
							f = true;
						}
					}
					if (f == false) {
						Toast.makeText(OjActivity.this,
							"û�������Ŷ",
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
