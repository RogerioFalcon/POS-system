package com.citaq.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class MainBoardUtil {

	private static final String SMDKV210 = "SMDKV210";
	private static final String RK30 = "RK30BOARD";
	private static final String C500 = "QRD MSM8625Q SKUD";
	public static int delay = 0;

	private static String getCpuHardware() {

		String hardware = "";
		String str = "";
		try {
			Process pp = Runtime.getRuntime().exec("cat /proc/cpuinfo");
			InputStreamReader ir = new InputStreamReader(pp.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			while ((str = input.readLine()) != null) {
				if (str.startsWith("Hardware")) {
					int i = str.indexOf(":");
					hardware = str.substring(i + 1).trim().toUpperCase();
					return hardware;
				}
			}

		} catch (IOException ex) {
			if(Constants.isPrintLog){
				ex.printStackTrace();
			}
		}
		return hardware;
	}

	public static String getModel() {
		String rs = "unknow board";

		String hw = getCpuHardware();

		if (hw.contains(SMDKV210)) {
			rs = "smdkv210";
			delay = 0;
		} else if (hw.contains(RK30)) {
			rs = "rk30sdk";
			delay = 100;
		} else if (hw.contains(C500)) {
			rs = "c500";
			delay = 0;
		}

		return rs;
	}

}
