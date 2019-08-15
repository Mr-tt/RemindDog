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
		
		
		while (true) {
			run();
			Thread.sleep(1000);
		}
		
//		String[] ss = {"07:53:19","11:55:57","13:44:09","13:44:12",};
//		judgeIsSign(ss, "4");
	}

	public static void run() throws Exception {

		Date date = new Date();

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

		String time = sdf.format(date);
//		System.err.println(time);

		switch (time) {
		case "07:55:30":
//		case "10:07:30":
			String[] allSignTime1 = login();
			judgeIsSign(allSignTime1, "1");
			break;
		case "11:50:30":
			String[] allSignTime2 = login();
			judgeIsSign(allSignTime2, "2");
			break;
		case "13:55:30":
			String[] allSignTime3 = login();
			judgeIsSign(allSignTime3, "3");
			break;
		case "17:35:30":
			String[] allSignTime4 = login();
			judgeIsSign(allSignTime4, "4");
			break;

		}

	}

	/**
	 * 模拟登录获取cookie和sessionid
	 */
	public static String[] login() throws IOException {
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

		return getData(res.cookie("sessionidadms"));

//		return res.cookie("sessionidadms");

	}

	/**
	 * 
	 * @param cookie
	 * @return 返回所有的打卡时间，存储为String[]
	 * @throws IOException
	 */
	public static String[] getData(String cookie) throws IOException {
		
		Date date = new Date();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		String time = sdf.format(date);
		

//		String url = "http://10.1.1.251:8000/selfatt/grid/selfatt/CardTimes/?page=1&rp=20&sortname=undefined&sortorder=undefined&query=&qtype=&UserIDs=715&ComeTime=2019-08-01&EndTime=2019-08-13";
		String url = "http://10.1.1.251:8000/selfatt/grid/selfatt/CardTimes/";
		Document document1 = Jsoup.connect(url).ignoreContentType(true).cookie("sessionidadms", cookie)
				.data("page", "1", "rp", "20", "UserIDs", "715", "ComeTime", time, "EndTime", time)
				.get();

		String s = document1.body().text();
		String[] ss = s.split("Time\": \"")[1].split(",\", \"userid\"")[0].split(",");

		System.err.println(ss[0]);
		System.err.println(ss[1]);
		System.err.println(ss[2]);
		return ss;
	}

	/**
	 * Description: 判断一个时间是否在一个时间段内 </br>
	 *
	 * @param nowTime   当前时间 </br>
	 * @param beginTime 开始时间 </br>
	 * @param endTime   结束时间 </br>
	 * @throws ParseException
	 */
	private static boolean belongCalendar(String nowTime1, String beginTime1, String endTime1) throws ParseException {

		SimpleDateFormat dateFormat = new SimpleDateFormat("kk:mm:ss");
		Date nowTime = dateFormat.parse(nowTime1);
		Date beginTime = dateFormat.parse(beginTime1);
		Date endTime = dateFormat.parse(endTime1);

		Calendar date = Calendar.getInstance();
		date.setTime(nowTime);

		Calendar begin = Calendar.getInstance();
		begin.setTime(beginTime);

		Calendar end = Calendar.getInstance();
		end.setTime(endTime);

		return date.after(begin) && date.before(end);
	}
	
	
	/**
	 * 判断该时刻是否打卡，若未打卡则发送一封邮件提醒
	 * @param allSignTime 获取到的所有打卡时间
	 * @param nowTime	现在的时间 
	 * @throws ParseException
	 */
	public static void judgeIsSign(String[] allSignTime, String nowTime) throws ParseException {
		boolean flag = false;
		for (String s : allSignTime) {
			switch (nowTime) {
			
			case "1":
				flag = belongCalendar(s, "7:00:00", "8:10:10");
				if(flag) {
					System.err.println("已打卡");
					return;
				}
				break;

			case "2":
				flag = belongCalendar(s, "11:50:00", "12:30:10");
				if(flag) {
					System.err.println("已打卡");
					return;
				}
				break;
				
			case "3":
				flag = belongCalendar(s, "13:00:00", "14:10:10");
				if(flag) {
					System.err.println("已打卡");
					return;
				}
				break;
				
			case "4":
				flag = belongCalendar(s, "17:20:00", "17:40:10");
				if(flag) {
					System.err.println("已打卡");
					return;
				}
				break;
				
			
			}
		}
		
		if(!flag) {
			//发送邮件提醒打卡
			System.err.println("未打卡！！！");
			try {
				MailUtil.testSendMail("忘记打卡啦！快去打卡！！！", "打卡打卡！！！！");
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}

	}

	public static String getNowDate() {

		Date date = new Date();

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

		String time = sdf.format(date);

		return time;
	}

}
