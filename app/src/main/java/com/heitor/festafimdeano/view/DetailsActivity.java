package com.heitor.festafimdeano.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.heitor.festafimdeano.R;
import com.heitor.festafimdeano.constant.EndOfTheYearConstants;
import com.heitor.festafimdeano.data.SecurityPreferences;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewHolder mViewHolder = new ViewHolder();
    private SecurityPreferences mSecurityPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        this.mSecurityPreferences = new SecurityPreferences(this);

        this.mViewHolder.checkParticipate = findViewById(R.id.checked_participate);
        this.mViewHolder.checkParticipate.setOnClickListener(this);

        this.loadDataFromActivity();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.checked_participate) {

            if (this.mViewHolder.checkParticipate.isChecked()) {
                //Salvar a Presença
                this.mSecurityPreferences.storeString(EndOfTheYearConstants.PRESENCE_KEY, EndOfTheYearConstants.CONFIRMATION_YES);
            } else {
                //Salvar a ausencia
                this.mSecurityPreferences.storeString(EndOfTheYearConstants.PRESENCE_KEY, EndOfTheYearConstants.CONFIRMATION_NO);
            }

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void loadDataFromActivity() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String presence = this.mSecurityPreferences.getStoredString(EndOfTheYearConstants.PRESENCE_KEY);
            if (presence != null && presence.equals(EndOfTheYearConstants.CONFIRMATION_YES)) {
                this.mViewHolder.checkParticipate.setChecked(true);
            } else {
                this.mViewHolder.checkParticipate.setChecked(false);
            }
        }
    }

    private static class ViewHolder {
        CheckBox checkParticipate;
    }
}