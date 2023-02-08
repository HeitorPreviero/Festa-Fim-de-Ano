package com.heitor.festafimdeano.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.heitor.festafimdeano.R;
import com.heitor.festafimdeano.constant.EndOfTheYearConstants;
import com.heitor.festafimdeano.data.SecurityPreferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewHolder mviewHolder = new ViewHolder();
    private SecurityPreferences mSecurityPreferences;
    private static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mSecurityPreferences = new SecurityPreferences(this);

        this.mviewHolder.textToday = findViewById(R.id.text_today);
        this.mviewHolder.textDaysLeft = findViewById(R.id.text_days_left);
        this.mviewHolder.buttonConfirm = findViewById(R.id.button_confirm);

        this.mviewHolder.buttonConfirm.setOnClickListener(this);

        //Datas
        this.mviewHolder.textToday.setText(SIMPLE_DATE_FORMAT.format(Calendar.getInstance().getTime()));
        String daysLeft = String.format("%s %s", String.valueOf(this.getDaysLeft()), getString(R.string.dias));
        this.mviewHolder.textDaysLeft.setText(daysLeft);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.verifyPresence();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_confirm) {

            String presence = this.mSecurityPreferences.getStoredString(EndOfTheYearConstants.PRESENCE_KEY);

            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra(EndOfTheYearConstants.PRESENCE_KEY, presence);
            startActivity(intent);
        }
    }

    private void verifyPresence() {
        //não confimado, sim, não
        String presence = this.mSecurityPreferences.getStoredString(EndOfTheYearConstants.PRESENCE_KEY);
        if (presence.equals("")) {
            this.mviewHolder.buttonConfirm.setText(getString(R.string.nao_confirmado));
        } else if (presence.equals(EndOfTheYearConstants.CONFIRMATION_YES)) {
            this.mviewHolder.buttonConfirm.setText(getString(R.string.sim));
        } else {
            this.mviewHolder.buttonConfirm.setText(getString(R.string.nao));
        }
    }

    private int getDaysLeft() {
        // Incializa instância do calendário e obtém o dia do ano
        Calendar calendarToday = Calendar.getInstance();
        int today = calendarToday.get(Calendar.DAY_OF_YEAR);

        // Pega o dia máximo do ano - De 1 até 365. Podem existir anos bissextos.
        Calendar calendarLastDay = Calendar.getInstance();
        int dayMax = calendarLastDay.getActualMaximum(Calendar.DAY_OF_YEAR);

        return dayMax - today;
    }

    private static class ViewHolder {
        TextView textToday;
        TextView textDaysLeft;
        Button buttonConfirm;
    }
}