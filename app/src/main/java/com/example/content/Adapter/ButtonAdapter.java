package com.example.content.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.content.Entity.ButtonSet;
import com.example.content.PlayActivity;
import com.example.content.R;

import java.util.List;

/**
 * Created by 佳南 on 2017/9/21.
 */

public class ButtonAdapter extends RecyclerView.Adapter<ButtonAdapter.ViewHolder> {

    private PlayActivity context;
    private List<ButtonSet> mList;
    private Context mcontext;
    private Context mainContext;
    private Button currentPressedBtn;
    private static final String TAG = "ButtonAdapter";

    public class ViewHolder extends RecyclerView.ViewHolder {

        Button button;
        CardView cardView;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            button = (Button) view.findViewById(R.id.rb_esposid);
        }
    }


    public ButtonAdapter(Context context, List<ButtonSet> list) {
        mainContext = context;
        mList = list;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mcontext == null) {
            mcontext = parent.getContext();
        }
        View view = LayoutInflater.from(mcontext).inflate(R.layout.item_button, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ButtonSet buttonset = mList.get(position);
        holder.button.setText(buttonset.getButtonId());
        holder.button.setTag(position);

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPressedBtn == null) {
                    currentPressedBtn = (Button) v;
                    currentPressedBtn.setSelected(true);
                    Log.i(TAG, "onClick: ");
//                    mainContext.chk_collection
                } else {
                    Log.i(TAG, "onClick: dsf");
                    currentPressedBtn.setSelected(false);
                    currentPressedBtn = (Button) v;
                }
                v.setSelected(true);
                Log.i("ButtonAdapter", "onClick: " + "clicked");
                Intent intent = new Intent(mcontext, PlayActivity.class);
                intent.putExtra("episode", (int) holder.button.getTag());
//                intent.putExtra("episode",(int)holder.button.getTag());
                mcontext.startActivity(intent);
                Log.i("ButtonAdapter", "onClick: " + "click" + holder.button.getTag().toString());
            }
        });
        for (int i = 0; i < mList.size(); i++) {
            Log.i("", "onBindViewHolder: " + mList.get(i));
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}
