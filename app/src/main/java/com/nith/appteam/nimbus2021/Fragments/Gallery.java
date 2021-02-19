package com.nith.appteam.nimbus2021.Fragments;

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

import com.nith.appteam.nimbus2021.Adapters.GalleryAdapter;
import com.nith.appteam.nimbus2021.Adapters.OurTeamAdapter;
import com.nith.appteam.nimbus2021.Models.ClubDetail;
import com.nith.appteam.nimbus2021.Models.TeamMember;
import com.nith.appteam.nimbus2021.R;

import java.util.ArrayList;
import java.util.List;

public class Gallery extends Fragment {

    public Gallery() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gallery, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.gallery_recycler_view);

        TextView quote1 = rootView.findViewById(R.id.quote1);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            quote1.setText(Html.fromHtml("<p>\"THESE GUYS COME FROM LEGEND \uD83D\uDEF8, <font color=\"#2fc0d1\">CAPTAIN</font><br>THEY'RE BASICALLY, <font color=\"#2fc0d1\">GODS</font> ✨.\" <small><i><font color=\"#888888\"> ~ BLACK WIDOW</font></i></small></p>", Html.FROM_HTML_MODE_COMPACT));
//        }
//        else {
//            quote1.setText(Html.fromHtml("<p>\"THESE GUYS COME FROM LEGEND \uD83D\uDEF8, <font color=\"#2fc0d1\">CAPTAIN</font><br>THEY'RE BASICALLY, <font color=\"#2fc0d1\">GODS</font> ✨.\" <small><i><font color=\"#888888\"> ~ BLACK WIDOW</font></i></small></p>"));
//        }

        List<ClubDetail> clubList = new ArrayList<>();
        ClubDetail clubDetail = new ClubDetail("App Team","https://avatars.githubusercontent.com/u/17087131?s=200&v=4",12);
        clubList.add(clubDetail);
        clubList.add(new ClubDetail("Team Exe","https://teamexe.in/images/logo.png",9));
        clubList.add(new ClubDetail("Hermetica","https://scontent.fslv1-2.fna.fbcdn.net/v/t1.0-9/84570307_2600774863478544_2688631209260482560_n.jpg?_nc_cat=101&ccb=3&_nc_sid=85a577&_nc_ohc=yKo1sioroQYAX8HJ2vg&_nc_ht=scontent.fslv1-2.fna&oh=b16c5ee401f1fa74bacac1dbb145eb26&oe=605389DD",3));
        clubList.add(new ClubDetail("App Team","https://avatars.githubusercontent.com/u/17087131?s=200&v=4",12));

        GalleryAdapter galleryAdapter = new GalleryAdapter(clubList, getActivity());
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(galleryAdapter);
        return rootView;
    }
}
