package cl.crevent.crevent;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;


public class SearchDialog extends DialogFragment {
    private Spinner sp_cat;
    private Spinner sp_com;

    Button btsearch;

    public SearchDialog() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_dialog, container);
        btsearch =(Button)  view.findViewById(R.id.bt_search);
        sp_cat = (Spinner) view.findViewById(R.id.sp_cat);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.cats_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_cat.setAdapter(adapter);

        sp_com = (Spinner) view.findViewById(R.id.sp_com);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.com_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_com.setAdapter(adapter2);
        getDialog().setTitle("Buscar");


        btsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ct, cm;
                String cat, com;
                ct = sp_cat.getSelectedItemPosition();
                cm = sp_com.getSelectedItemPosition();
                cat = "";
                com = "";
                if (ct != 0) {
                    cat = sp_cat.getSelectedItem().toString();
                }
                if (cm != 0) {
                    com = sp_com.getSelectedItem().toString();
                }
                Intent intent = new Intent(getActivity(), Busqueda.class);
                intent.putExtra("Comuna", com);
                intent.putExtra("Categoria", cat);
                startActivity(intent);
                getDialog().dismiss();


            }
        });
        return view;
    }



}

