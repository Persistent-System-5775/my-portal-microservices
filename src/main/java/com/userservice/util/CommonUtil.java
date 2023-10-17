package com.userservice.util;

import net.bytebuddy.utility.RandomString;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Paths;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.DAYS;

public class CommonUtil {

	@Autowired
	HttpServletRequest httpServletRequest;

	@Autowired
	Environment environment;



	public static boolean isStringNotNullandEmpty(String s) {
		return (s != null && !"".equalsIgnoreCase(s.trim()));
	}

	public static String billMonth() {
		// Getting the current date value
		LocalDate currentdate = LocalDate.now();
		System.out.println("Current date: " + currentdate);
		// Getting the current day
		int currentDay = currentdate.getDayOfMonth();
		System.out.println("Current day: " + currentDay);
		// Getting the current month
		Month currentMonth = currentdate.getMonth();
		System.out.println("Current month: " + currentMonth);
		// getting the current year
		int currentYear = currentdate.getYear();
		System.out.println("Current month: " + currentYear);

		return currentMonth + " - " + currentYear;
	}

	public static boolean isDirectoryExists(String directoryPath)

	{
		if (!Paths.get(directoryPath).toFile().isDirectory()) {
			return false;
		}
		return true;
	}

	public static boolean saveProfilePic(File file, String fileName, String docType, int count) throws IOException {
		FileOutputStream fos = null;
		String[] splitImages = fileName.split(",");
		byte[] imageByte = Base64.decodeBase64(splitImages[0]);
		String directory = file.getPath() + "/" + splitImages[1];
		try {
			fos = new FileOutputStream(directory);
			fos.write(imageByte);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (docType.equalsIgnoreCase("NRC") || docType.equalsIgnoreCase("PASSPORT")) {
			if (count == 2)
				fos.close();
		}
		if (docType.equalsIgnoreCase("PROFILE_PIC")) {
			if (count == 1)
				fos.close();
		}

		return true;
	}

	public String getHostname() {
		InetAddress ip;
		String hostname = null;
		String portNo = null;

		try {
			ip = InetAddress.getLocalHost();
			hostname = ip.getHostName();
			portNo = environment.getProperty("server.port");
			System.out.println("Your current IP address : " + ip);
			System.out.println("Your current Hostname : " + hostname);
			System.out.println("Your current portNo : " + portNo);

		} catch (UnknownHostException e) {

			e.printStackTrace();
		}
		return hostname + ":" + portNo;
	}

	public String getURLWithContextPath() {
		String applicationURL = httpServletRequest.getRequestURL().toString();
		System.out.println("applicationURL: " + applicationURL);
		System.out.println(httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":"
				+ httpServletRequest.getServerPort() + httpServletRequest.getContextPath());
		return httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":"
				+ httpServletRequest.getServerPort() + httpServletRequest.getContextPath();
	}

	public static String randomDecimalString(int ndigits) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < ndigits; i++) {
			result.append(randomDecimalDigit());
		}
		return result.toString();
	}

	public static char randomDecimalDigit() {
		char digits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		return digits[(int) Math.floor(Math.random() * 10)];
	}

	public static String currentDateTime() {
		String newDelhiDateTimePattern = "ddMMyyyy_HHmmss";
//		String formattedDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHmmssSSS"));
		String formattedDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern(newDelhiDateTimePattern));
		return formattedDateTime;
	}

	public static LocalDateTime getCurrentDateTime() {
		LocalDateTime currentDateTime = LocalDateTime.now();
		return currentDateTime;
	}

	public static LocalDate getCurrentDate() {
		LocalDate currentDate = LocalDate.now();
		return currentDate;
	}
	public static Month getCurrentMonth() {
		LocalDate currentDate = LocalDate.now();
		return currentDate.getMonth();
	}

	public static long findTimeDifference(LocalDateTime otpTimeStamp, LocalDateTime otpValidity) {
		LocalDateTime now = LocalDateTime.now();
//		LocalDateTime sixMinutesBehind = now.minusMinutes(6);
		Duration duration = Duration.between(otpValidity, now);
		long seconds = (int) ChronoUnit.SECONDS.between(now, otpValidity); 
		long diff = Math.abs(duration.toMinutes());
		System.out.println("diff in mins==> "+diff);
		System.out.println("diff in secs==> "+seconds);
		return seconds;
	
	}
	public static Long findDateDifferenceWithCurrentDate(LocalDate taskPlannedEndDate) {
		LocalDate currentDate = LocalDate.now();
		/*Days d = Days.daysBetween(currentDate, dateToCompare).getDays();
		Days d = Days.daysBetween(startDate, endDate).getDays();
		Duration duration = Duration.between(dateToCompare, LocalDate.now());
		long diff = Math.abs(duration.toDays());*/
		long daysBetween = DAYS.between(currentDate, taskPlannedEndDate);
		return daysBetween;
	}
	
	public static long findOtpTimeDifference(LocalDateTime otpTimeStamp, LocalDateTime otpValidity) {

		LocalDateTime now = LocalDateTime.now();
//		LocalDateTime sixMinutesBehind = now.minusMinutes(6);
		Duration duration = Duration.between(now, otpTimeStamp.minusSeconds(100));
		long diff = Math.abs(duration.toMinutes());
		System.out.println(diff);
		return diff;
	}

	public static long findOTPWaitTimeDifference(String waitingTime, LocalDateTime otpValidity) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

//		otpValidity = LocalDateTime.parse("2020-11-17 15:00:00", formatter).plusMinutes(Long.parseLong(waitingTime));
		long diff = 0L;
		Duration duration = Duration.between(LocalDateTime.now(), otpValidity.plusMinutes(Long.parseLong(waitingTime)));
		diff = Math.abs(duration.toMinutes());
//		if(diff<1)
//			diff=Math.abs(duration.getSeconds());
//		
//		System.out.println("min=> "+diff);
		System.out.println("Seconds=> "+Math.abs(duration.getSeconds()));
		return diff;
	}

	public static boolean isValidEmail(String email) {
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		return email.matches(regex);
	}

	public static String getHostName() {
		InetAddress ip;
		String hostname = null;
		try {
			ip = InetAddress.getLocalHost();
			String s1hostAddress = ip.getHostAddress();
			hostname = ip.getHostName();
			System.out.println("Your current Hostname : " + hostname);

		} catch (UnknownHostException e) {

			e.printStackTrace();
		}
		return hostname;
	}

	public static String getHostAddress() {
		InetAddress ip;
		String hostAddress = null;
		try {
			ip = InetAddress.getLocalHost();
			hostAddress = ip.getHostAddress();
			System.out.println("Your current hostAddress : " + hostAddress);

		} catch (UnknownHostException e) {

			e.printStackTrace();
		}
		return hostAddress;
	}

	public static boolean isMsisdnValid(String phoneNumber) {
//		final String MM_PHONE = "^((09|\\+?950?9|\\+?95950?9)\\d{7,9})$";
		final String MM_PHONE = "^(\\d{8,11})$";
		return (phoneNumber != null) ? phoneNumber.matches(MM_PHONE) : false;
	}

	public static String getTimeStamp() {
		return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
	}

	public static LocalDate convertStringToLocalDate(String date){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(date, formatter);
		return localDate;
	}

	public static Long convertPolicyTermInMonths(String dueDateMode, Long policyTerm){
		if(dueDateMode.equals("3")){
			policyTerm = policyTerm * 12;
		}
		else if(dueDateMode.equals("5")){
			policyTerm = policyTerm * 2;
		}
		else {
			return 	policyTerm ;
		}
		return policyTerm;
	}

	public static String generateUuid () {
		UUID uuid = UUID.randomUUID();
		String uuidAsString = uuid.toString();
		System.out.println("Your UUID is: " + uuidAsString);
		return uuidAsString;
	}

	public static String getSiteURL() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String siteURL = request.getRequestURL().toString();
		return siteURL.replace(request.getServletPath(), "");
	}

	public static String generateRandomCode() {
		return RandomString.make(64);
	}

	public static LocalDate getFirstDateOfTheMonth(LocalDate inputDate){
		YearMonth month = YearMonth.from(inputDate);
		return month.atDay(1);

	}
	public static LocalDate getLastDateOfTheMonth(LocalDate inputDate){
		YearMonth month = YearMonth.from(inputDate);
		return month.atEndOfMonth();
	}

	public static boolean checkIfCurrentDatIsBetweenTwoDates(){
		LocalDate currentDate = LocalDate.now();
		LocalDate start = LocalDate.of(currentDate.getYear() , currentDate.getMonth() , 1 ) ;
//		LocalDate stop = LocalDate.of(currentDate.getYear() , currentDate.getMonth(), currentDate.range(start) ) ;
		return true;
	}

}
