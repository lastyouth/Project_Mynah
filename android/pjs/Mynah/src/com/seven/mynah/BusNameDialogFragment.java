//���


package com.seven.mynah;

import android.R.color;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BusNameDialogFragment extends DialogFragment {

	// ���� �� ���� ����

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		// Get the layout inflater
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.layout_dialogfragment_busname, null);		
		
		//setStyle(STYLE_NO_FRAME, 0);
		//getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0xffffff));
		
		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout
		builder.setView(view)
				.setPositiveButton("Ȯ��",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
							}
						}).setNegativeButton("���", null);
		return builder.create();
	}
	/*
	 * public BusNameDialogFragment() {}
	 * 
	 * @Override public View onCreateView(LayoutInflater inflater, ViewGroup
	 * container, Bundle savedInstanceState) { View view =
	 * inflater.inflate(R.layout.layout_dialogfragment_busname, container);
	 * 
	 * // ���̾ƿ� XML�� �� ���� ����
	 * 
	 * return view; }
	 */
}
