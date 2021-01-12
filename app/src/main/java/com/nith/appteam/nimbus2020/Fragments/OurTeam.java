package com.nith.appteam.nimbus2020.Fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nith.appteam.nimbus2020.Adapters.OurTeamAdapter;
import com.nith.appteam.nimbus2020.Models.TeamMember;
import com.nith.appteam.nimbus2020.R;

import java.util.ArrayList;
import java.util.List;

public class OurTeam extends Fragment {
    RecyclerView recyclerView;
    TextView quote1;
    OurTeamAdapter ourTeamAdapter;
    List<TeamMember> teamList;
    Context context;

    public OurTeam(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_our_team, container, false);
        recyclerView = rootView.findViewById(R.id.our_team_recycler_view);

        quote1 = rootView.findViewById(R.id.quote1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            quote1.setText(Html.fromHtml("<p>\"THESE GUYS COME FROM LEGEND \uD83D\uDEF8, <font color=\"#2fc0d1\">CAPTAIN</font><br>THEY'RE BASICALLY, <font color=\"#2fc0d1\">GODS</font> ✨.\" <small><i><font color=\"#888888\"> ~ BLACK WIDOW</font></i></small></p>", Html.FROM_HTML_MODE_COMPACT));
        } else {
            quote1.setText(Html.fromHtml("<p>\"THESE GUYS COME FROM LEGEND \uD83D\uDEF8, <font color=\"#2fc0d1\">CAPTAIN</font><br>THEY'RE BASICALLY, <font color=\"#2fc0d1\">GODS</font> ✨.\" <small><i><font color=\"#888888\"> ~ BLACK WIDOW</font></i></small></p>"));
        }

        teamList = new ArrayList<>();
        TeamMember teamMember = new TeamMember("Anubhav", String.valueOf(R.string.defaultImageUrl), "Mr. President");
        teamList.add(teamMember);
        ourTeamAdapter = new OurTeamAdapter(teamList, getActivity());
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(ourTeamAdapter);
        return rootView;
    }
}
