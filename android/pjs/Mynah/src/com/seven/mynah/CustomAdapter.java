package com.seven.mynah;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
 
public class CustomAdapter extends BaseAdapter {
     
    // ���ڿ��� ���� �� ArrayList
    private ArrayList<String>   m_List;
     
    // ������
    public CustomAdapter() {
        m_List = new ArrayList<String>();
    }
 
    // ���� �������� ���� ����
    @Override
    public int getCount() {
        return m_List.size();
    }
 
    // ���� �������� ������Ʈ�� ����, Object�� ��Ȳ�� �°� �����ϰų� ���Ϲ��� ������Ʈ�� ĳ�����ؼ� ���
    @Override
    public Object getItem(int position) {
        return m_List.get(position);
    }
 
    // ������ position�� ID �� ����
    @Override
    public long getItemId(int position) {
        return position;
    }
 
    // ��� �� ������ ����
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();
         
        // ����Ʈ�� ������鼭 ���� ȭ�鿡 ������ �ʴ� �������� converView�� null�� ���·� ��� ��
        if ( convertView == null ) {
            // view�� null�� ��� Ŀ���� ���̾ƿ��� ��� ��
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_list_bus_setting, parent, false);
             
            // TextView�� ���� position�� ���ڿ� �߰�
            TextView text = (TextView) convertView.findViewById(R.id.tvCustomListName);
            text.setText(m_List.get(position));
             
             
            // ����Ʈ �������� ��ġ ���� �� �̺�Ʈ �߻�
            convertView.setOnClickListener(new OnClickListener() {
                 
                @Override
                public void onClick(View v) {
                    // ��ġ �� �ش� ������ �̸� ���
                    Toast.makeText(context, "����Ʈ Ŭ�� : "+m_List.get(pos), Toast.LENGTH_SHORT).show();
                }
            });
             
            // ����Ʈ �������� ��� ��ġ ���� �� �̺�Ʈ �߻�
            convertView.setOnLongClickListener(new OnLongClickListener() {
                 
                @Override
                public boolean onLongClick(View v) {
                    // ��ġ �� �ش� ������ �̸� ���
                    Toast.makeText(context, "����Ʈ �� Ŭ�� : "+m_List.get(pos), Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }
         
        return convertView;
    }
     
    // �ܺο��� ������ �߰� ��û �� ���
    public void add(String _msg) {
        m_List.add(_msg);
    }
     
    // �ܺο��� ������ ���� ��û �� ���
    public void remove(int _position) {
        m_List.remove(_position);
    }
}