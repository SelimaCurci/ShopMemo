package com.example.selima.shopmemo;

/**
 * Created by selim on 10/01/2017.
 */

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.selima.shopmemo.model.Product;

import java.util.ArrayList;
import java.util.List;


public class RecyclerAdapterAll extends RecyclerView.Adapter<RecyclerAdapterAll.ViewHolder> {

    private List<Product> mItemsList;
    private PageFragmentAll.OnListItemClickListener mListItemClickListener;

    public RecyclerAdapterAll(List<Product> itemsList) {
        mItemsList = (itemsList == null) ? new ArrayList<Product>() : itemsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view by inflating the row item xml.
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);

        // Set the view to the ViewHolder
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(mItemsList.get(position).getNome());
        holder.shopANDprice.setText(mItemsList.get(position).getNegozio() + " - " +
                                    mItemsList.get(position).getPrezzo() + " €");

        if(mItemsList.get(position).getVoto()==0)
            holder.preference.setText("0");
        else
            holder.preference.setText(mItemsList.get(position).getVoto().toString());
        holder.star.setImageResource(R.drawable.star);

        int id = Home_Activity.context().getResources().getIdentifier(mItemsList.get(position).getPathFoto(),
                "drawable", Home_Activity.context().getPackageName());

        holder.photo.setImageResource(id);
        holder.hiddenId.setText(mItemsList.get(position).getId().toString());
    }

    @Override
    public int getItemCount() {
        return mItemsList.size();
    }

    // Create the ViewHolder class to keep references to your views
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardView;
        TextView name;
        TextView shopANDprice;
        TextView preference;
        ImageView star;
        ImageView photo;
        TextView hiddenId;

        /**
         * Constructor
         * @param v The container view which holds the elements from the row item xml
         */
        public ViewHolder(View v) {
            super(v);

            cardView = (CardView)v.findViewById(R.id.card_view);
            name = (TextView)v.findViewById(R.id.name);
            shopANDprice = (TextView)v.findViewById(R.id.shopANDprice);
            preference = (TextView)v.findViewById(R.id.preference);
            star = (ImageView) v.findViewById(R.id.star);
            photo = (ImageView) v.findViewById(R.id.photo);
            hiddenId  = (TextView) v.findViewById(R.id.hidden_id);
            v.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if (null != mListItemClickListener) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                mListItemClickListener.onListItemClick(mItemsList.get(getAdapterPosition()).getNome());
            }
        }
    }

    public void setOnItemTapListener(PageFragmentAll.OnListItemClickListener itemClickListener) {
        mListItemClickListener = itemClickListener;
    }
}