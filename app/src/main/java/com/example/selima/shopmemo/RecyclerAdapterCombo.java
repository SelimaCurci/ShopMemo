package com.example.selima.shopmemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.selima.shopmemo.model.Combo;
import com.example.selima.shopmemo.model.Product;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by selim on 13/01/2017.
 */

public class RecyclerAdapterCombo extends RecyclerView.Adapter<RecyclerAdapterCombo.ViewHolder> {

    private List<Combo> mItemsList;
    private PageFragmentCombo.OnListItemClickListener mListItemClickListener;

    public RecyclerAdapterCombo(List<Combo> itemsList) {
        mItemsList = (itemsList == null) ? new ArrayList<Combo>() : itemsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view by inflating the row item xml.
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.combocard_view, parent, false);

        // Set the view to the ViewHolder
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nome.setText(mItemsList.get(position).getNome());

        DecimalFormat df = new DecimalFormat("#.##");
        holder.prezzo.setText(df.format(mItemsList.get(position).getPrezzoTotale()) + " €");
        holder.numOgg.setText(mItemsList.get(position).getNumOggetti() + "");

        if(mItemsList.get(position).getNumOggetti()!=0) {
            int id = Home_Activity.context().getResources().getIdentifier(mItemsList.get(position).getListaProdotti().get(0).getPathFoto(),
                    "drawable", Home_Activity.context().getPackageName());

            if(id!=0){
                holder.photo.setImageResource(id);
            }
            else {
                Bitmap bitmap = null;
                File f = new File(mItemsList.get(position).getListaProdotti().get(0).getPathFoto());
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                try {
                    bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                holder.photo.setImageBitmap(bitmap);
            }
        }
        else holder.photo.setImageResource(R.drawable.ic_camera);

    }

    @Override
    public int getItemCount() {
        return mItemsList.size();
    }

    // Create the ViewHolder class to keep references to your views
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardView;
        TextView numOgg;
        TextView nome;
        TextView prezzo;
        ImageView photo;

        /**
         * Constructor
         * @param v The container view which holds the elements from the row item xml
         */
        public ViewHolder(View v) {
            super(v);

            cardView = (CardView)v.findViewById(R.id.card_view);
            numOgg = (TextView)v.findViewById(R.id.numOgg);
            nome = (TextView)v.findViewById(R.id.nome);
            prezzo = (TextView)v.findViewById(R.id.prezzo);
            photo = (ImageView) v.findViewById(R.id.photos);
            v.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if (null != mListItemClickListener) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                mListItemClickListener.onComboItemClick(mItemsList.get(getAdapterPosition()).getId());
            }
        }
    }

    public void setOnItemTapListener(PageFragmentCombo.OnListItemClickListener itemClickListener) {
        mListItemClickListener = itemClickListener;
    }
}