package GetScore;

import java.util.Vector;
import java.util.regex.Matcher;

public class GPARegex
{
	@SuppressWarnings("unused")
	static private String pattern_tr = "";
	static private String pattern_past_term = "<TD ALIGN=\"center\">(\\d{8})</TD>\n<T[^>]+>(.*?)</TD>\n<TD ALIGN=\"center\">([\\.\\d]+?)</TD>\n<TD ALIGN=\"center\">(.*?)</TD>\n<TD ALIGN=\"center\">(.*?)</TD>\n<TD ALIGN=\"center\">([.\\d]+?)</TD>\n<TD ALIGN=\"center\">(.*?)</TD>\n<TD ALIGN=\"center\">(.*?)</TD>";
    static private String pattern_this_term = "<TR>[\\S\\s]*?\n<TD ALIGN=\"center\">(.+?)</TD>\n<TD ALIGN=\"center\">(.+?)</TD>\n<TD ALIGN=\"center\">(.+?)</TD>\n<TD ALIGN=\"center\">(.+?)</TD>\n<TD ALIGN=\"center\">(.+?)</TD>\n<TD ALIGN=\"center\">(.+?)</TD>\n<TD ALIGN=\"center\">(.+?)</TD>\n<TD ALIGN=\"center\">(.+?)</TD>\n<TD ALIGN=\"center\"></TD>\n</TR>";
    static private String pattern_user = "姓名：(.*?)\\((.*?)\\)，学号：(\\d{10})，班级：(\\d{8})\\((.*?)\\s(.*?)\\)";
    public PersonInfo getUserinfo(String Text)
    {
    	PersonInfo p = new PersonInfo();
    	Regex reg = new Regex(pattern_user);
    	Matcher mat = reg.getMatch(Text);	
    	p.setName(mat.group(1));
    	p.setSex(mat.group(2));
    	p.setStuid(mat.group(3));
    	p.setClassid(mat.group(4));
    	p.setSchool(mat.group(5));
    	p.setMajor(mat.group(6));
    	return p;
    }
    public Vector<Result> getResultsofThisTerm(String Text,String uid)   //本学期成绩
    {
    	Vector<Result> res = new Vector<Result>(); //处理原始信息
    	res.clear();
    	Regex reg  = new Regex(pattern_this_term);
    	while(reg.isMatch(Text))
    	{
    		Matcher tmp = reg.getMatch(Text);
    		Result p = new Result();
    		p.setCid(tmp.group(1));
    		p.setSname(tmp.group(2));
    		p.setNum(tmp.group(3));
    		p.setSpoint(tmp.group(4));
    		p.setStype(tmp.group(5));
    		p.setScore(tmp.group(6));
    		p.setGradepoint(tmp.group(7));
    		p.setType(tmp.group(8));
    		Text = Text.replace(tmp.group(), "*");
    		res.add(p);
    	}
    	return res;
    }
    public Vector<Result> getResultsofPastTerm(String Text,String uid)   //本学期成绩
    {
    	Vector<Result> res = new Vector<Result>(); //处理原始信息
    	res.clear();
    	Regex reg  = new Regex(pattern_past_term);
    	while(reg.isMatch(Text))
    	{
    		Matcher tmp = reg.getMatch(Text);
    		Result p = new Result();
    		p.setCid(tmp.group(1));
    		p.setSname(tmp.group(2));
    		p.setSpoint(tmp.group(3));
    		p.setStype(tmp.group(4));
    		p.setScore(tmp.group(5));
    		p.setGradepoint(tmp.group(6));
    		p.setType(tmp.group(7));
    		Text = Text.replace(tmp.group(), "*");
    		res.add(p);
    	}
    	return res;
    }
}
