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
			if (!login.isLoginSuccessful()) { //û�ܵ�¼�ɹ�
				Toast.makeText(GpaMainActivity.this, "�������˴����ѧ������", Toast.LENGTH_LONG).show();
				Intent next = new Intent();
				next.setClass(GpaMainActivity.this, GpaActivity.class);
				GpaMainActivity.this.startActivity(next);
				GpaMainActivity.this.finish();
			}

			///��ҳ��ʾѧ���Ļ�����Ϣ/
			PersonInfo personInfo = login.getPerson();
			StringBuffer personInfoMessage = new StringBuffer();
			basicInfoTitle.setText("��ӭ����" + personInfo.getName());
			personInfoMessage.append("����ѧ�ţ�" + personInfo.getStuid());
			personInfoMessage.append('\n');
			personInfoMessage.append("�����Ա�" + personInfo.getSex());
			personInfoMessage.append('\n');
			personInfoMessage.append("����Ժϵ��" + personInfo.getSchool());
			personInfoMessage.append('\n');
			personInfoMessage.append("����רҵ��" + personInfo.getMajor());
			personInfoMessage.append('\n');
			personInfoMessage.append("���İ༶��" + personInfo.getClassid());
			basicInfo.setText(personInfoMessage);

			gpaSubmitButton = (Button) findViewById(R.id.gpaChooseSubmit);
			gpaListChoose = (TextView) findViewById(R.id.gpaListChoose);
			gpaList = (Spinner) findViewById(R.id.gpaList);
			list.add("��ѧ�ڳɼ�");
			list.add("ͨʶ������");
			list.add("ѧ�ƽ�����");
			list.add("רҵ������");
			list.add("רҵ�����");
			list.add("רҵѡ�޿�");
			list.add("�������ʿ�");
			list.add("��ѧ���ʿ�");
			list.add("������Կ�");
			list.add("�ƻ���γ�");
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			gpaList.setAdapter(adapter);
			gpaList.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

				public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					gpaListChoose.setText("��ѡ���ˣ�");
					arg0.setVisibility(View.INVISIBLE);

				}

				public void onNothingSelected(AdapterView<?> arg0) {
					arg0.setVisibility(View.VISIBLE);
				}
			});

			/* �����˵�����������ѡ����¼����� */
			gpaList.setOnTouchListener(new Spinner.OnTouchListener() {
				public boolean onTouch(View arg0, MotionEvent arg1) {
					arg0.setVisibility(View.INVISIBLE);
					return false;
				}
			});

			/* �����˵�����������ѡ���ı��¼����� */
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
						if (gpaNext.equals("��ѧ�ڳɼ�")) {
							id = 9;
						}
						else if (gpaNext.equals("ͨʶ������")) {
							id = 0;
						}
						else if (gpaNext.equals("ѧ�ƽ�����")) {
							//ve = login.getPastTerm()[1];
							id = 1;
						}
						else if (gpaNext.equals("רҵ������")) {
							//ve = login.getPastTerm()[2];
							id = 2;
						}
						else if (gpaNext.equals("רҵ�����")) {
							//ve = login.getPastTerm()[3];
							id = 3;
						}
						else if (gpaNext.equals("רҵѡ�޿�")) {
							//ve = login.getPastTerm()[4];
							id = 4;
						}
						else if (gpaNext.equals("�������ʿ�")) {
							//ve = login.getPastTerm()[5];
							id = 5;
						}
						else if (gpaNext.equals("��ѧ���ʿ�")) {
							//ve = login.getPastTerm()[6];
							id = 6;
						}
						else if (gpaNext.equals("������Կ�")) {
							//ve = login.getPastTerm()[7];
							id = 7;
						}
						else if (gpaNext.equals("�ƻ���γ�")) {
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
