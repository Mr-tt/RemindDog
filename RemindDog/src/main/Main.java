package main;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Main {

	public static void main(String[] args) throws Exception {
//		String cookie = login();
//		getData1(cookie);
		
//		String s ="{\"rows\": [{\"card_date\": \"2019-08-13\", \"name\": \"\\u53f6\\u6d77\\u6d9b\", \"ClockInTime\": \"07:59:36,12:06:31,13:24:09,\", \"userid\": \"715\", \"times\": \"3\", \"badgenumber\": \"000005895\", \"DeptName\": \"\\u4fe1\\u606f\\u5904/\\u8ba1\\u7b97\\u673a\\u4e2d\\u5fc3\", \"id\": \"715\"}], \"total\": 1, \"page\": 1}";
//		
//		String[] ss = s.split("Time\": \"")[1].split(",\", \"userid\"")[0].split(",");
//		System.err.println(ss[0]);
//		System.err.println(ss[1]);
//		System.err.println(ss[2]);
		
//		String data1 = "08:59:35";
//		String data2 = "07:30:36";
//		String data3 = "08:59:36";
//		SimpleDateFormat dateFormat = new SimpleDateFormat("kk:mm:ss");
//		Date nowDate = dateFormat.parse(data1);
//		Date start = dateFormat.parse(data2);
//		Date end = dateFormat.parse(data3);
//		
//		boolean r = belongCalendar(nowDate, start, end);
//		System.err.println(r);
		while(true) {
			run();
			Thread.sleep(1000);
		}
	}

	
	public static void run() {
		
		Date date = new Date();
		
		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
		
		String time=sdf.format(date);
		System.err.println(time);
		switch (time) {
		case "7:55:30":
			System.err.println("time over");//判断当前时间是否打卡
			
			break;
		case "12:10:30":
			System.err.println("time over");
			break;
		case "13:55:30":
			System.err.println("time over");
			break;
		case "17:35:30":
			System.err.println("time over");
			break;

		}
		
	}
	
	
	/**
	 * 模拟登录获取cookie和sessionid
	 */
	public static String login() throws IOException {
		String urlLogin = "http://10.1.1.251:8000/selfservice/login/";
		
		Connection connect = Jsoup.connect(urlLogin);
		// 伪造请求头

		connect.header("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		connect.header("Accept-Encoding", "gzip, deflate");
		connect.header("Accept-Language", "zh,en-US;q=0.9,en;q=0.8,zh-CN;q=0.7");
		connect.header("Cache-Control", "no-cache");
		connect.header("Connection", "keep-alive");
		// connect.header("Content-Length", "86");
		connect.header("Content-Type", "application/x-www-form-urlencoded");
		connect.header("Host", "www.cdyfy.com:8080");
		connect.header("Pragma", "no-cache");
		connect.header("Referer", "http://www.cdyfy.com:8080/templates/index/hrlogon.jsp");
		connect.header("Upgrade-Insecure-Requests", "1");
		connect.header("User-Agent",
				"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
		// 携带登陆信息
//	        connect.data("logon.x","link","username", "人事专员","password", "r8692712");
		connect.data("username", "05895", "password", "123456");

		// 请求url获取响应信息
		Response res = connect.method(Method.POST).execute();// 执行请求
		// 获取返回的cookie
		System.err.println(res.cookie("sessionidadms"));

		System.out.println(res.statusCode());

		System.err.println(res.body().toString());
		return res.cookie("sessionidadms");
		
		
	}

	public static void getData1(String cookie) throws IOException {

//		String url = "http://10.1.1.251:8000/selfatt/grid/selfatt/CardTimes/?page=1&rp=20&sortname=undefined&sortorder=undefined&query=&qtype=&UserIDs=715&ComeTime=2019-08-01&EndTime=2019-08-13";
		String url = "http://10.1.1.251:8000/selfatt/grid/selfatt/CardTimes/";
		Document document1 = Jsoup.connect(url).ignoreContentType(true).cookie("sessionidadms", cookie)
				.data("page", "1", "rp", "20", "UserIDs", "715", "ComeTime", "2019-08-13", "EndTime", "2019-08-13")
				.get();
		
		String s = document1.body().text();
		String[] ss = s.split("Time\": \"")[1].split(",\", \"userid\"")[0].split(",");
		
		System.err.println(ss[0]);
		System.err.println(ss[1]);
		System.err.println(ss[2]);
	}
	
	
    /**
     * Description: 判断一个时间是否在一个时间段内 </br>
     *
     * @param nowTime 当前时间 </br>
     * @param beginTime 开始时间 </br>
     * @param endTime 结束时间 </br>
     */
    private static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        
        
        return date.after(begin) && date.before(end);
    }
    
    
    public static String getNowDate() {
    	
    	Date date=new Date();

		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");

		String time=sdf.format(date);

		return time;
    }

	

}
