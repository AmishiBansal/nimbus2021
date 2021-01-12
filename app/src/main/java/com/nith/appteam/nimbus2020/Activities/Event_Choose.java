package com.nith.appteam.nimbus2020.Activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentManager;

import com.nith.appteam.nimbus2020.Fragments.DepartmentEvents;
import com.nith.appteam.nimbus2020.Fragments.InstituteEvents;
import com.nith.appteam.nimbus2020.R;

public class Event_Choose extends AppCompatActivity {
    Typeface psbi, psi;
    TextView dept, inst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event__choose);

        TextView back = findViewById(R.id.back);
        psbi = ResourcesCompat.getFont(this, R.font.psbitalic);
        psi = ResourcesCompat.getFont(this, R.font.psitalic);

        dept = findViewById(R.id.departmental_tab);
        inst = findViewById(R.id.institutional_tab);
        dept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dept.setTypeface(psbi);
                inst.setTypeface(psi);
                dept.setTextColor(getResources().getColor(R.color.black));
                inst.setTextColor(getResources().getColor(R.color.lightGray));
                DepartmentEvents events = new DepartmentEvents(Event_Choose.this);
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.events_fragment_holder, events)
                        .commit();

            }
        });
        inst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dept.setTypeface(psi);
                inst.setTypeface(psbi);
                dept.setTextColor(getResources().getColor(R.color.lightGray));
                inst.setTextColor(getResources().getColor(R.color.black));

                InstituteEvents events = new InstituteEvents(Event_Choose.this);
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.events_fragment_holder, events)
                        .commit();
            }
        });
        dept.performClick();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
            }
        });
    }
}
