package com.example.blackberry;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

public class CatchProblemLy {

	private String	httpHead;
	private String	httpBody;
	private String	inputSample;
	private String	httpBehindInput	= "<h3 class=\"collaspable\">�������</h3>";
	private String	outputSample;
	private String	httpBehindOutput;
	private String	Html			= null;

	public CatchProblemLy(String OJ, String Id) throws Exception {
		HttpClient client = new HttpClient();
		// ���ô����������ַ�Ͷ˿�      
		String urlOJ = null;
		if (OJ.equals("����")) {
			urlOJ = "Local/";
		}
		else if (OJ.equals("����")) {
			urlOJ = "Pku/";
		}
		else if (OJ.equals("���")) {
			urlOJ = "Zju/";
		}
		else if (OJ.equals("����")) {
			urlOJ = "Hdu/";
		}
		// ʹ�� GET ���� �������������Ҫͨ�� HTTPS ���ӣ���ֻ��Ҫ������ URL �е� http ���� https 
		HttpMethod method = new GetMethod("http://219.230.70.12/" + urlOJ + Id);
		//ʹ��POST����
		client.executeMethod(method);
		byte[] buff = readInputStream(method.getResponseBodyAsStream());
		Html = new String(buff);
		//�ͷ�����
		trans();
		method.releaseConnection();
	}

	//������ʽƥ�䣬���������Ը�ֵ
	public void trans()
	{
		//������ʽ
		String Phead = "<head>([\\s\\S]*?)</head>";
		String Pbody = "<div class=\"problem-body\">([\\s\\S]*?)<div class=\"sample-input\">";
		String PinputSample = "<div class=\"sample-input\">[\\s\\S]*?<pre>([\\s\\S]*?)</pre>[\\s\\S]*?</div>";
		String PoutputSample = "<div class=\"sample-output\">[\\s\\S]*?<pre>([\\s\\S]*?)</pre>[\\s\\S]*?</div>";
		String PhtmlBehindOutput = "<div class=\"hint\">([\\s\\S]*?)</div>";

		Pattern pat = Pattern.compile(Phead);
		Matcher matcher = pat.matcher(Html);
		if (matcher.find()) {
			setHttpHead(getHttpHead() + matcher.group(1));
		}

		pat = Pattern.compile(Pbody);
		matcher = pat.matcher(Html);
		if (matcher.find()) {
			setHttpBody(matcher.group(1));
		}

		pat = Pattern.compile(PinputSample);
		matcher = pat.matcher(Html);
		if (matcher.find()) {
			setInputSample(matcher.group(1));
		}

		pat = Pattern.compile(PoutputSample);
		matcher = pat.matcher(Html);
		if (matcher.find()) {
			setOutputSample(matcher.group(1));
		}

		pat = Pattern.compile(PhtmlBehindOutput);
		matcher = pat.matcher(Html);
		if (matcher.find()) {
			setHttpBehindOutput(matcher.group(1));
		}
	}

	/***********������һ����ȡ���ĺ���************/
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

	/*********������get ��set����****************/
	public String getHttpHead() {
		return httpHead;
	}

	public void setHttpHead(String httpHead) {
		this.httpHead = httpHead;
	}

	public String getHttpBody() {
		return httpBody;
	}

	public void setHttpBody(String httpBody) {
		this.httpBody = httpBody;
	}

	public String getInputSample() {
		return inputSample;
	}

	public void setInputSample(String inputSample) {
		this.inputSample = inputSample;
	}

	public String getHttpBehindInput() {
		return httpBehindInput;
	}

	public void setHttpBehindInput(String httpBehindInput) {
		this.httpBehindInput = httpBehindInput;
	}

	public String getHttpBehindOutput() {
		return httpBehindOutput;
	}

	public void setHttpBehindOutput(String httpBehindOutput) {
		this.httpBehindOutput = httpBehindOutput;
	}

	public String getOutputSample() {
		return outputSample;
	}

	public void setOutputSample(String outputSample) {
		this.outputSample = outputSample;
	}
}
