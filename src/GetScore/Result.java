package GetScore;

public class Result 
{
	private String cid;   //课程号
	private String sname; //课程名称
	private String num;   //课序号
	private String spoint;//课程学分
	private String stype; //类型
	private String score; //成绩
	private String gradepoint; //绩点
	private String type;  //类别
	
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String sid) {
		this.cid = sid;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getSpoint() {
		return spoint;
	}
	public void setSpoint(String spoint) {
		this.spoint = spoint;
	}
	public String getStype() {
		return stype;
	}
	public void setStype(String stype) {
		this.stype = stype;
	}
	public String getGradepoint() {
		return gradepoint;
	}
	public void setGradepoint(String gradepoint) {
		this.gradepoint = gradepoint;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
