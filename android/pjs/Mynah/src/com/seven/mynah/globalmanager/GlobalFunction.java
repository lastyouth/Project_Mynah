package com.seven.mynah.globalmanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;

public class GlobalFunction {

	public static String SubwayDecode(String code) {
		// 케이스문
		String decode = "";

		switch (code) 
		{
		case "I":
			decode = "인천1호선";
			break;
		case "K":
			decode = "경의중앙선";
			break;
		case "B":
			decode = "분당선";
			break;
		case "A":
			decode = "공항철도";
			break;
		case "G":
			decode = "경춘선";
			break;
		case "S":
			decode = "신분당선";
			break;
		case "SU":
			decode = "수인선";
			break;
		default:
			decode = code + "호선";
			break;
		}

		return decode;
	}

}
