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
	private String	httpBehindInput	= "<h3 class=\"collaspable\">输出样例</h3>";
	private String	outputSample;
	private String	httpBehindOutput;
	private String	Html			= null;

	public CatchProblemLy(String OJ, String Id) throws Exception {
		HttpClient client = new HttpClient();
		// 设置代理服务器地址和端口      
		String urlOJ = null;
		if (OJ.equals("本地")) {
			urlOJ = "Local/";
		}
		else if (OJ.equals("北大")) {
			urlOJ = "Pku/";
		}
		else if (OJ.equals("浙大")) {
			urlOJ = "Zju/";
		}
		else if (OJ.equals("杭电")) {
			urlOJ = "Hdu/";
		}
		// 使用 GET 方法 ，如果服务器需要通过 HTTPS 连接，那只需要将下面 URL 中的 http 换成 https 
		HttpMethod method = new GetMethod("http://219.230.70.12/" + urlOJ + Id);
		//使用POST方法
		client.executeMethod(method);
		byte[] buff = readInputStream(method.getResponseBodyAsStream());
		Html = new String(buff);
		//释放连接
		trans();
		method.releaseConnection();
	}

	//正则表达式匹配，给各个属性赋值
	public void trans()
	{
		//正则表达式
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

	/***********仅仅是一个读取流的函数************/
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

	/*********仅仅是get 和set方法****************/
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
