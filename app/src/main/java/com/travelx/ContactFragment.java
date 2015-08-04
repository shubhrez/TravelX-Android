package com.travelx;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ContactFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.contact_fragment, container, false);
        ((TextView)rootView.findViewById(R.id.textView3)).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                send_mail();
            }

        });
        ((Button)rootView.findViewById(R.id.button1)).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                send_mail();
            }

        });

        getActivity().invalidateOptionsMenu();
        return rootView;
    }
    public void send_mail(){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"shubhamdrolia87@gmail.com"});
//        i.putExtra(Intent.EXTRA_SUBJECT, Common.prefs.getString("MovinCart_customer_name","")+"["+Common.prefs.getString("MovinCart_customer_contact","")+"] Query");
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
}

