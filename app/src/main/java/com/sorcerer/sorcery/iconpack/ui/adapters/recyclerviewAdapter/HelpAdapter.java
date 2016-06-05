package com.sorcerer.sorcery.iconpack.ui.adapters.recyclerviewAdapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.sorcerer.sorcery.iconpack.R;
import com.sorcerer.sorcery.iconpack.ui.adapters.base.BaseRecyclerAdapter;
import com.sorcerer.sorcery.iconpack.ui.adapters.base.GridRecyclerAdapter;

import java.util.Arrays;

import butterknife.BindView;

/**
 * Created by Sorcerer on 2016/3/8 0008.
 */
public class HelpAdapter extends GridRecyclerAdapter<HelpAdapter.ViewHolder, String> {

    public HelpAdapter(Context context, int span) {
        super(context,
                Arrays.asList(context.getResources().getStringArray(R.array.help_list)),
                span);
    }

    public final class ViewHolder extends BaseRecyclerAdapter.BaseViewHolder {

        @BindView(R.id.cardView_help_card_item)
        CardView card;

        @BindView(R.id.textView_help_item_title)
        TextView title;

        @BindView(R.id.textView_help_item_content)
        TextView content;

        public View main;

        public ViewHolder(View itemView) {
            super(itemView);
            main = itemView;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_help, mHoldingRecyclerView, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.title.setText(getTitle(position));
        holder.content.setText(getContent(position));

//        setViewMargin(holder.card, position, 2);
        setViewMargin(holder.card, position, 2);


        holder.card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                MaterialDialog.Builder builder = new MaterialDialog.Builder(mContext);
                builder.title(mContext.getString(R.string.action_copy_to_clipboard) + " ?");
                builder.positiveText(mContext.getString(R.string.action_copy));
                builder.negativeText(mContext.getString(R.string.cancel));
                builder.onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog,
                            @NonNull DialogAction which) {
                        ClipboardManager clipboard = (ClipboardManager) mContext
                                .getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip =
                                ClipData.newPlainText(getTitle(holder.getAdapterPosition()),
                                        getContent(holder.getAdapterPosition()));
                        clipboard.setPrimaryClip(clip);
                    }
                });
                builder.show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size() / 2;
    }

    private String getTitle(int position) {
        return mDataList.get(position * 2);
    }

    private String getContent(int position) {
        return mDataList.get(position * 2 + 1)
                .replace("|", "\n")
                .replace("#", "   " + " ");
    }
}
