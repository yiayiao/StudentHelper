package GetScore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class Login
{
	final static String[]	pages09				=
												{
												/* "ͨʶ������" */"http://jwc.njust.edu.cn:6666/pls/wwwbks/bkscjcx.tsjykcx",
												/* "ѧ�ƽ�����" */"http://jwc.njust.edu.cn:6666/pls/wwwbks/bkscjcx.xkjykcx",
												/* "רҵ������" */"http://jwc.njust.edu.cn:6666/pls/wwwbks/bkscjcx.zyjckcx",
												/* "רҵ�����" */"http://jwc.njust.edu.cn:6666/pls/wwwbks/bkscjcx.zyfxkcx",
												/* "רҵѡ�޿�" */"http://jwc.njust.edu.cn:6666/pls/wwwbks/bkscjcx.zyxxkcx",
												/* "�������ʿ�" */"http://jwc.njust.edu.cn:6666/pls/wwwbks/bkscjcx.rwszkcx",
												/* "��ѧ���ʿ�" */"http://jwc.njust.edu.cn:6666/pls/wwwbks/bkscjcx.kxszkcx",
												/* "������Կ�" */"http://jwc.njust.edu.cn:6666/pls/wwwbks/bkscjcx.wgyykcx",
												/* "�ƻ���γ�" */"http://jwc.njust.edu.cn:6666/pls/wwwbks/bkscjcx.jhwkcx"
												};
	final static String[]	pages08				=
												{
		/* "ͨʶ����������" */"http://jwc.njust.edu.cn:6666/pls/wwwbks/bkscjcx.tsjyjckcx",
		/* "ѧ�ƻ�����" */"http://jwc.njust.edu.cn:6666/pls/wwwbks/bkscjcx.xkjckcx",
		/* "����ʵ����ѧ����" */"http://jwc.njust.edu.cn:6666/pls/wwwbks/bkscjcx.jzsjjxhjkcx",
		/* "ѧ��ѡ�޿�" */"http://jwc.njust.edu.cn:6666/pls/wwwbks/bkscjcx.xkxxkcx",
		/* "רҵ���޿�" */"http://jwc.njust.edu.cn:6666/pls/wwwbks/bkscjcx.zybxkcx",
		/* "רҵѡ�޿�" */"http://jwc.njust.edu.cn:6666/pls/wwwbks/bkscjcx.zyxxkcx",
		/* "�Ļ����ʿ�" */"http://jwc.njust.edu.cn:6666/pls/wwwbks/bkscjcx.whszkcx",
		/* "����ѡ�޿�" */"http://jwc.njust.edu.cn:6666/pls/wwwbks/bkscjcx.ggxxkcx",
		/* "�ƻ���γ�" */"http://jwc.njust.edu.cn:6666/pls/wwwbks/bkscjcx.jhwkcx"
												};
	final static String		url_login_action	= "http://jwc.njust.edu.cn:6666/pls/wwwbks/bkscjcx.login";
	final static String		url_login_message	= "http://jwc.njust.edu.cn:6666/pls/wwwbks/bkscjcx.loginmessage";
	final static String		url_redirected		= "index.html";
	final static String		url_this_term		= "http://jwc.njust.edu.cn:6666/pls/wwwbks/bkscjcx.curscopre";

	private boolean			loginSuccessful;
	private PersonInfo		person;																				//������Ϣ
	@SuppressWarnings("unchecked")
	private Vector<Result>	pastTerm[]			= new Vector[9];													//��ȥ�ɼ�������pages����
	private Vector<Result>	thisTerm;																				//��ѧ�ڳɼ�

	public PersonInfo getPerson() {
		return person;
	}

	public void setPerson(PersonInfo person) {
		this.person = person;
	}

	public Vector<Result>[] getPastTerm() {
		return pastTerm;
	}

	public void setPastTerm(Vector<Result>[] pastTerm) {
		this.pastTerm = pastTerm;
	}

	public Vector<Result> getThisTerm() {
		return thisTerm;
	}

	public void setThisTerm(Vector<Result> thisTerm) {
		this.thisTerm = thisTerm;
	}


	public Login(String id, String pwd) throws IOException, Exception {

		if (id == null || pwd == null) {
			setLoginSuccessful(false);
		}
		if (id.equals("") || pwd.equals("")) {
			setLoginSuccessful(false);
		}

		//ȷ����¼�˿�
		HttpClient client = new HttpClient();
		client.getHostConfiguration().setHost("jwc.njust.edu.cn", 6666);
		PostMethod post = new PostMethod(url_login_action);
		//����Ҫ�ļ�ֵ��д��
		NameValuePair stuid = new NameValuePair("stuid", id);
		NameValuePair stupwd = new NameValuePair("pwd", pwd);
		//��POST��������������ֵ��
		post.setRequestBody(new NameValuePair[] { stuid, stupwd });
		client.executeMethod(post);
		//��ȡ��������
		byte[] buff = readInputStream(post.getResponseBodyAsStream());
		post.releaseConnection();
		String Html = new String(buff,"GBK");
		//�鿴��ȥ�ɼ�����ַ,�����꼶�Ĳ�ͬ�ֱ���page09 ��page08��ֵ
		String[] pages;
		if (Html.length() == 0) {
			setLoginSuccessful(false);
		}
		else if (Html.length() >= 100) {
			setLoginSuccessful(false);
		}
		else
		{
			setLoginSuccessful(true);
			if (!id.substring(0, 1).equals("0") || id.substring(0, 2).equals("09")) {
				pages = pages09;
			}
			else {
				pages = pages08;
			}
			//ץȡ�û���Ϣ��ʹ�á��ƻ���γ̡���ҳ�������Ϣ,��Ϊ���꼶�����ҳ��ͨ�õ�
			String jihuawai = "http://jwc.njust.edu.cn:6666/pls/wwwbks/bkscjcx.jhwkcx";
			post = new PostMethod(jihuawai);
			client.executeMethod(post);
			buff = readInputStream(post.getResponseBodyAsStream());
			post.releaseConnection();
			Html = new String(buff, "GBK");
			//ץȡ������Ϣ
			GPARegex rex = new GPARegex();
			//person = rex.getUserinfo(Html);
			setPerson(rex.getUserinfo(Html));
			//���ѧ�ڵĳɼ�
			post = new PostMethod(url_this_term);
			client.executeMethod(post);
			buff = readInputStream(post.getResponseBodyAsStream());
			post.releaseConnection();
			Html = new String(buff, "GBK");
			//thisTerm = rex.getResultsofThisTerm(Html, id);
			setThisTerm(rex.getResultsofThisTerm(Html, id));
			//��ȡ���޵ĳɼ�
			int cnt = pages.length;
			for (int i = 0; i < cnt; i++) {
				String URL = pages[i];
				post = new PostMethod(URL);
				client.executeMethod(post);
				buff = readInputStream(post.getResponseBodyAsStream());
				post.releaseConnection();
				Html = new String(buff, "GBK");
				pastTerm[i] = rex.getResultsofPastTerm(Html, id);
			}
		}
	}

	/**************edit by ydy************************************************************************/

	//��¼�����������˺����룬����-1��ʾ��¼ʧ��-2�������,1
	public int doLogin(String id, String pwd) throws IOException, Exception
	{
		if (id == null || pwd == null)
		{
			return -1;
		}
		if (id == "" || pwd == "")
		{
			return -1;
		}
		//ȷ����¼�˿�
		HttpClient client = new HttpClient();
		client.getHostConfiguration().setHost("jwc.njust.edu.cn", 6666);
		PostMethod post = new PostMethod(url_login_action);
		//����Ҫ�ļ�ֵ��д��
		NameValuePair stuid = new NameValuePair("stuid", id);
		NameValuePair stupwd = new NameValuePair("pwd", pwd);
		//��POST��������������ֵ��
		post.setRequestBody(new NameValuePair[] { stuid, stupwd });
		client.executeMethod(post);

		//��ȡ��������
		byte[] buff = readInputStream(post.getResponseBodyAsStream());
		post.releaseConnection();
		String Html = new String(buff);
		//System.out.println(Html);
		String[] pages;       //�鿴��ȥ�ɼ�����ַ
		if (Html.length() == 0)
		{
			//System.out.println("aaaaaaa");
			return -1;
		}
		else if (Html.length() >= 100)
		{
			//System.out.println("aaaaaaa");
			return -2;
		}
		else
		{
			if (id.substring(0, 2).equals("09")
				|| id.substring(0, 2).equals("10")
				|| id.substring(0, 2).equals("11")
				|| id.substring(0, 2).equals("12")
				|| id.substring(0, 2).equals("13"))
			{
				pages = pages09;
			}
			else
			{
				pages = pages08;
			}
			//ץȡ�û���Ϣ��ʹ�á��ƻ���γ̡���ҳ�������Ϣ,��Ϊ���꼶�����ҳ��ͨ�õ�
			String jihuawai = "http://jwc.njust.edu.cn:6666/pls/wwwbks/bkscjcx.jhwkcx";
			post = new PostMethod(jihuawai);
			client.executeMethod(post);
			buff = readInputStream(post.getResponseBodyAsStream());
			post.releaseConnection();
			Html = new String(buff);
			//ץȡ������Ϣ
			GPARegex rex = new GPARegex();
			person = rex.getUserinfo(Html);

			//���ѧ�ڵĳɼ�
			post = new PostMethod(url_this_term);
			client.executeMethod(post);
			buff = readInputStream(post.getResponseBodyAsStream());
			post.releaseConnection();
			Html = new String(buff);
			thisTerm = rex.getResultsofThisTerm(Html, id);
			int cnt = pages.length;
			for (int i = 0; i < cnt; i++)
			{
				String URL = pages[i];
				post = new PostMethod(URL);
				client.executeMethod(post);
				buff = readInputStream(post.getResponseBodyAsStream());
				post.releaseConnection();
				Html = new String(buff);
				pastTerm[i] = rex.getResultsofPastTerm(Html, id);
			}
			return 1;
		}
	}

	//��������һ���������ȽϿ���
	public static byte[] readInputStream(InputStream inputStream) throws Exception
	{

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inputStream.read(buffer)) != -1)
		{
			outputStream.write(buffer, 0, len);
		}
		inputStream.close();
		return outputStream.toByteArray();
	}

	public void show()
	{
		System.out.println("ѧ����Ϣ");
		System.out
			.println("��� " + person.getClassid() + " ���� ��" + person.getName() + " רҵ��" + person.getMajor() + " ");
		System.out.println(" ѧԺ��" + person.getSchool() + " �Ա�" + person.getSex() + " ѧ�ã�" + person.getStuid() + " ");
		System.out.println("��ѧ�ڳɼ�");
		int cnt = thisTerm.size();

		//System.out.println(cnt);


		for (int i = 0; i < cnt; i++)
		{
			Result tmp = thisTerm.elementAt(i);
			//�γ̺�
			/*
			 * private String sname; //�γ�����
			 * private String num; //�����
			 * private String spoint;//�γ�ѧ��
			 * private String stype; //����
			 * private String score; //�ɼ�
			 * private String gradepoint; //����
			 * private String type; //����
			 */
			System.out.println("�γ̺ţ�" + tmp.getCid() + " �γ�����:" + tmp.getSname());
			System.out.println("����ţ�" + tmp.getNum() + " �γ�ѧ��:" + tmp.getSpoint());
			System.out.println("���ͣ�" + tmp.getStype() + " �ɼ�:" + tmp.getScore());
			System.out.println("���㣺" + tmp.getGradepoint() + " ���:" + tmp.getType());
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");
		}
		cnt = pastTerm.length;
		for (int i = 0; i < cnt; i++)
		{
			int num = pastTerm[i].size();
			for (int j = 0; j < num; j++)
			{
				Result tmp = pastTerm[i].elementAt(j);
				//�γ̺�
				System.out.println("�γ̺ţ�" + tmp.getCid() + " �γ�����:" + tmp.getSname());
				System.out.println("����ţ�" + tmp.getNum() + " �γ�ѧ��:" + tmp.getSpoint());
				System.out.println("���ͣ�" + tmp.getStype() + " �ɼ�:" + tmp.getScore());
				System.out.println("���㣺" + tmp.getGradepoint() + " ���:" + tmp.getType());
			}
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");
		}

	}

	public boolean isLoginSuccessful() {
		return loginSuccessful;
	}

	public void setLoginSuccessful(boolean loginSuccessful) {
		this.loginSuccessful = loginSuccessful;
	}
}
