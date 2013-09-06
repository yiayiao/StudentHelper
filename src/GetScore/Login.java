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
												/* "通识教育课" */"http://jwc.njust.edu.cn:6666/pls/wwwbks/bkscjcx.tsjykcx",
												/* "学科教育课" */"http://jwc.njust.edu.cn:6666/pls/wwwbks/bkscjcx.xkjykcx",
												/* "专业基础课" */"http://jwc.njust.edu.cn:6666/pls/wwwbks/bkscjcx.zyjckcx",
												/* "专业方向课" */"http://jwc.njust.edu.cn:6666/pls/wwwbks/bkscjcx.zyfxkcx",
												/* "专业选修课" */"http://jwc.njust.edu.cn:6666/pls/wwwbks/bkscjcx.zyxxkcx",
												/* "人文素质课" */"http://jwc.njust.edu.cn:6666/pls/wwwbks/bkscjcx.rwszkcx",
												/* "科学素质课" */"http://jwc.njust.edu.cn:6666/pls/wwwbks/bkscjcx.kxszkcx",
												/* "外国语言课" */"http://jwc.njust.edu.cn:6666/pls/wwwbks/bkscjcx.wgyykcx",
												/* "计划外课程" */"http://jwc.njust.edu.cn:6666/pls/wwwbks/bkscjcx.jhwkcx"
												};
	final static String[]	pages08				=
												{
		/* "通识教育基础课" */"http://jwc.njust.edu.cn:6666/pls/wwwbks/bkscjcx.tsjyjckcx",
		/* "学科基础课" */"http://jwc.njust.edu.cn:6666/pls/wwwbks/bkscjcx.xkjckcx",
		/* "集中实践教学环节" */"http://jwc.njust.edu.cn:6666/pls/wwwbks/bkscjcx.jzsjjxhjkcx",
		/* "学科选修课" */"http://jwc.njust.edu.cn:6666/pls/wwwbks/bkscjcx.xkxxkcx",
		/* "专业必修课" */"http://jwc.njust.edu.cn:6666/pls/wwwbks/bkscjcx.zybxkcx",
		/* "专业选修课" */"http://jwc.njust.edu.cn:6666/pls/wwwbks/bkscjcx.zyxxkcx",
		/* "文化素质课" */"http://jwc.njust.edu.cn:6666/pls/wwwbks/bkscjcx.whszkcx",
		/* "公共选修课" */"http://jwc.njust.edu.cn:6666/pls/wwwbks/bkscjcx.ggxxkcx",
		/* "计划外课程" */"http://jwc.njust.edu.cn:6666/pls/wwwbks/bkscjcx.jhwkcx"
												};
	final static String		url_login_action	= "http://jwc.njust.edu.cn:6666/pls/wwwbks/bkscjcx.login";
	final static String		url_login_message	= "http://jwc.njust.edu.cn:6666/pls/wwwbks/bkscjcx.loginmessage";
	final static String		url_redirected		= "index.html";
	final static String		url_this_term		= "http://jwc.njust.edu.cn:6666/pls/wwwbks/bkscjcx.curscopre";

	private boolean			loginSuccessful;
	private PersonInfo		person;																				//个人信息
	@SuppressWarnings("unchecked")
	private Vector<Result>	pastTerm[]			= new Vector[9];													//过去成绩，根据pages分类
	private Vector<Result>	thisTerm;																				//本学期成绩

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

		//确定登录端口
		HttpClient client = new HttpClient();
		client.getHostConfiguration().setHost("jwc.njust.edu.cn", 6666);
		PostMethod post = new PostMethod(url_login_action);
		//将需要的键值对写出
		NameValuePair stuid = new NameValuePair("stuid", id);
		NameValuePair stupwd = new NameValuePair("pwd", pwd);
		//给POST方法加入上述键值对
		post.setRequestBody(new NameValuePair[] { stuid, stupwd });
		client.executeMethod(post);
		//读取捕获内容
		byte[] buff = readInputStream(post.getResponseBodyAsStream());
		post.releaseConnection();
		String Html = new String(buff,"GBK");
		//查看过去成绩的网址,根据年级的不同分别用page09 和page08赋值
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
			//抓取用户信息，使用【计划外课程】网页里面的信息,因为各年级这个网页是通用的
			String jihuawai = "http://jwc.njust.edu.cn:6666/pls/wwwbks/bkscjcx.jhwkcx";
			post = new PostMethod(jihuawai);
			client.executeMethod(post);
			buff = readInputStream(post.getResponseBodyAsStream());
			post.releaseConnection();
			Html = new String(buff, "GBK");
			//抓取个人信息
			GPARegex rex = new GPARegex();
			//person = rex.getUserinfo(Html);
			setPerson(rex.getUserinfo(Html));
			//这个学期的成绩
			post = new PostMethod(url_this_term);
			client.executeMethod(post);
			buff = readInputStream(post.getResponseBodyAsStream());
			post.releaseConnection();
			Html = new String(buff, "GBK");
			//thisTerm = rex.getResultsofThisTerm(Html, id);
			setThisTerm(rex.getResultsofThisTerm(Html, id));
			//获取已修的成绩
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

	//登录操作，读入账号密码，返回-1表示登录失败-2密码错误,1
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
		//确定登录端口
		HttpClient client = new HttpClient();
		client.getHostConfiguration().setHost("jwc.njust.edu.cn", 6666);
		PostMethod post = new PostMethod(url_login_action);
		//将需要的键值对写出
		NameValuePair stuid = new NameValuePair("stuid", id);
		NameValuePair stupwd = new NameValuePair("pwd", pwd);
		//给POST方法加入上述键值对
		post.setRequestBody(new NameValuePair[] { stuid, stupwd });
		client.executeMethod(post);

		//读取捕获内容
		byte[] buff = readInputStream(post.getResponseBodyAsStream());
		post.releaseConnection();
		String Html = new String(buff);
		//System.out.println(Html);
		String[] pages;       //查看过去成绩的网址
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
			//抓取用户信息，使用【计划外课程】网页里面的信息,因为各年级这个网页是通用的
			String jihuawai = "http://jwc.njust.edu.cn:6666/pls/wwwbks/bkscjcx.jhwkcx";
			post = new PostMethod(jihuawai);
			client.executeMethod(post);
			buff = readInputStream(post.getResponseBodyAsStream());
			post.releaseConnection();
			Html = new String(buff);
			//抓取个人信息
			GPARegex rex = new GPARegex();
			person = rex.getUserinfo(Html);

			//这个学期的成绩
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

	//读入流的一个方法，比较靠谱
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
		System.out.println("学生信息");
		System.out
			.println("班别： " + person.getClassid() + " 姓名 ：" + person.getName() + " 专业：" + person.getMajor() + " ");
		System.out.println(" 学院：" + person.getSchool() + " 性别：" + person.getSex() + " 学好：" + person.getStuid() + " ");
		System.out.println("本学期成绩");
		int cnt = thisTerm.size();

		//System.out.println(cnt);


		for (int i = 0; i < cnt; i++)
		{
			Result tmp = thisTerm.elementAt(i);
			//课程号
			/*
			 * private String sname; //课程名称
			 * private String num; //课序号
			 * private String spoint;//课程学分
			 * private String stype; //类型
			 * private String score; //成绩
			 * private String gradepoint; //绩点
			 * private String type; //类型
			 */
			System.out.println("课程号：" + tmp.getCid() + " 课程名称:" + tmp.getSname());
			System.out.println("课序号：" + tmp.getNum() + " 课程学分:" + tmp.getSpoint());
			System.out.println("类型：" + tmp.getStype() + " 成绩:" + tmp.getScore());
			System.out.println("绩点：" + tmp.getGradepoint() + " 类别:" + tmp.getType());
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");
		}
		cnt = pastTerm.length;
		for (int i = 0; i < cnt; i++)
		{
			int num = pastTerm[i].size();
			for (int j = 0; j < num; j++)
			{
				Result tmp = pastTerm[i].elementAt(j);
				//课程号
				System.out.println("课程号：" + tmp.getCid() + " 课程名称:" + tmp.getSname());
				System.out.println("课序号：" + tmp.getNum() + " 课程学分:" + tmp.getSpoint());
				System.out.println("类型：" + tmp.getStype() + " 成绩:" + tmp.getScore());
				System.out.println("绩点：" + tmp.getGradepoint() + " 类别:" + tmp.getType());
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
