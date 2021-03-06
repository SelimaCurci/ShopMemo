package com.example.selima.shopmemo;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.selima.shopmemo.model.Combo;
import com.example.selima.shopmemo.model.ComboFactory;
import com.example.selima.shopmemo.model.Product;
import com.example.selima.shopmemo.model.ProductFactory;

import java.util.ArrayList;
import java.util.List;

public class EditCombo_Activity extends AppCompatActivity implements PageFragmentAll.OnListItemClickListener {

    List<Product> productList;
    int id;

    @Override
    protected void onResume(){
        productList = ComboFactory.getInstance(this).getComboById(id).getListaProdotti();
        Combo combo = ComboFactory.getInstance(this).getComboById(id);
        new EditComboFragment().setList(combo.getListaProdotti());
        super.onResume();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_combo_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);
        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor("#EF6C00"));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent i = getIntent();
        id = Integer.parseInt(i.getStringExtra("combo"));

        Combo combo = ComboFactory.getInstance(this).getComboById(id);
        ab.setTitle("Modifica " + combo.getNome());

        new EditComboFragment().setList(combo.getListaProdotti());


    }

    @Override
    public void onListItemClick(int idI) {
        Intent i = new Intent(getApplicationContext(), SummaryObject_Activity.class);
        i.putExtra("oggetto", String.valueOf(idI));
        i.putExtra("parent", "combo");
        i.putExtra("idCombo", id);
        i.putExtra("cat", "");
        startActivity(i);
    }

    public void updateList(){
        new EditComboFragment().setList(productList);
    }

    public void cardMoreFunctionProd(View view){
        final List<Product> lista = productList;
        final FragmentManager supportoFragment = getFragmentManager();
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        TextView child = (TextView) viewGroup.getChildAt(6);
        String idP = child.getText().toString();
        DialogFragment fragment = new DeleteProductFromCombo();
        Bundle bundle = new Bundle();
        bundle.putInt("IDCOMBO",id);
        bundle.putInt("IDPROD",(Integer.parseInt(idP)));

        fragment.setArguments(bundle);
        fragment.show(supportoFragment,"conferma");
        updateList();
    }

    public void addObject(View view) {
        if(ComboFactory.getInstance(this).getProductNotIn(id).size()==0)
            Toast.makeText(this, "Non ci sono altri oggetti da aggiungere", Toast.LENGTH_SHORT).show();
        else {
            Intent i = new Intent(this, NewCombo_Activity.class);
            i.putExtra("NOME", ComboFactory.getInstance(this).getComboById(id).getNome());
            i.putExtra("CREAZIONE", false);
            i.putExtra("ID", id);
            startActivity(i);
        }
    }
}


class EditComboFragment extends Fragment {
    static private PageFragmentAll.OnListItemClickListener mListItemClickListener;

    static RecyclerView recyclerView;
    static List<Product> list = new ArrayList<>();
    public void setList(List<Product> l){
        list = l;

        if(recyclerView == null) return;
        RecyclerAdapterAll adapter = new RecyclerAdapterAll(l);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemTapListener(mListItemClickListener);
        recyclerView.invalidate();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.editcombo_fragment, container, false);
        Log.d("Z","Sono qui");

        recyclerView = (RecyclerView) view.findViewById(R.id.deletelist);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(linearLayoutManager);


        RecyclerAdapterAll adapter = new RecyclerAdapterAll(list);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemTapListener(mListItemClickListener);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListItemClickListener = (PageFragmentAll.OnListItemClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnListItemClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListItemClickListener = null;
    }


}