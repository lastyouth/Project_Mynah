package com.seven.mynah.globalmanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;

public class GlobalFunction {

	public static String SubwayDecode(String code) {
		// ���̽���
		String decode = "";

		switch (code) 
		{
		case "I":
			decode = "��õ1ȣ��";
			break;
		case "K":
			decode = "�����߾Ӽ�";
			break;
		case "B":
			decode = "�д缱";
			break;
		case "A":
			decode = "����ö��";
			break;
		case "G":
			decode = "���ἱ";
			break;
		case "S":
			decode = "�źд缱";
			break;
		case "SU":
			decode = "���μ�";
			break;
		default:
			decode = code + "ȣ��";
			break;
		}

		return decode;
	}

}
