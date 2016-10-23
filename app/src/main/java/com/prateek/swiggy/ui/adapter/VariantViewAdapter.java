package com.prateek.swiggy.ui.adapter;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.prateek.swiggy.R;
import com.prateek.swiggy.event.VariantItemClickEvent;
import com.prateek.swiggy.rest.Request.GroupChoice;
import com.prateek.swiggy.rest.Request.Variation;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by prateek.kesarwani on 22/10/16.
 */

public class VariantViewAdapter extends RecyclerView.Adapter<VariantViewAdapter.MyViewHolder> {

    private String groupId;
    private ArrayList<Variation> mVariant;

    public VariantViewAdapter() {

    }

    public VariantViewAdapter(ArrayList<Variation> variants, String groupId) {
        this.mVariant = variants;
        this.groupId = groupId;
    }

    public void refreshWithData(ArrayList<Variation> variants) {
        this.mVariant = variants;
        this.notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_variant_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Variation varient = mVariant.get(position);
        holder.mainTitle.setText("" + varient.getName());
        holder.mainPrice.setText(holder.mainPrice.getResources().getString(R.string.rs) + " " + varient.getPrice());
        if (varient.isSelected()) {
            holder.mainRadioButton.setChecked(true);
        } else {
            holder.mainRadioButton.setChecked(false);
        }

        holder.mainRadioButton.setClickable(false);

        if (varient.isEnabled()) {
            holder.mainRadioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new VariantItemClickEvent(position, new GroupChoice(groupId, varient.getId())));
                    for (Variation variation : mVariant) {
                        if (variation.equals(varient)) {
                            variation.setSelected(true);
                        } else {
                            variation.setSelected(false);
                        }

                    }
                    notifyDataSetChanged();
                }
            });
        } else {
            holder.cardView.setCardBackgroundColor(Color.LTGRAY);
        }
    }

    @Override
    public int getItemCount() {
        if (mVariant != null) {
            return mVariant.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView mainTitle;
        TextView mainPrice;
        RadioButton mainRadioButton;

        public MyViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.variant_item_card_view);
            mainTitle = (TextView) view.findViewById(R.id.item_main_title);
            mainPrice = (TextView) view.findViewById(R.id.item_main_price);
            mainRadioButton = (RadioButton) view.findViewById(R.id.item_main_selected);
        }
    }
}