package com.sorcerer.sorcery.iconpack.ui.activities;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.sorcerer.sorcery.iconpack.R;
import com.sorcerer.sorcery.iconpack.adapters.RequestAdapter;
import com.sorcerer.sorcery.iconpack.models.AppInfo;
import com.sorcerer.sorcery.iconpack.models.MailSenderInfo;
import com.sorcerer.sorcery.iconpack.util.SimpleMailSender;
import com.sorcerer.sorcery.iconpack.util.Utility;

import java.util.List;

public class FeedbackActivity extends SlideInAndOutAppCompatActivity
        implements View.OnClickListener {

    private Context mContext;
    private Toolbar mToolbar;
    private Button mRequestButton;
    private Button mSuggestButton;

    private MaterialDialog.Builder mBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        mContext = this;

        mToolbar = (Toolbar) findViewById(R.id.toolbar_universal);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRequestButton = (Button) findViewById(R.id.button_request);
        mSuggestButton = (Button) findViewById(R.id.button_suggest);

        mRequestButton.setOnClickListener(this);
        mSuggestButton.setOnClickListener(this);

        (findViewById(R.id.textView_feedback_explain))
                .setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        ClipboardManager clipboard = (ClipboardManager)
                                getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("feedback mailbox", getString(R.string
                                .feedback_mailbox));
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(FeedbackActivity.this,
                                "copied to your clipboard :)",
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            super.onBackPressed();
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (!Utility.isNetworkAvailable(this)) {
            Toast.makeText(mContext, "network error", Toast.LENGTH_SHORT).show();
            return;
        }
        if (id == R.id.button_request) {
            showRequestDialog();
        } else if (id == R.id.button_suggest) {
            try {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_SENDTO);
                i.setData(Uri.parse("mailto:" + getString(R.string.feedback_receive_mailbox)));
                i.putExtra(Intent.EXTRA_SUBJECT, "Sorcery icon pack: suggest");
                i.putExtra(Intent.EXTRA_TEXT, "write down your suggestion:\n");
                startActivity(i);
            }catch (Exception e){
                Toast.makeText(mContext, "please login in your email app first", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private void showRequestDialog() {
        Intent intent = new Intent(this, AppSelectActivity.class);
        startActivity(intent);
    }
}

