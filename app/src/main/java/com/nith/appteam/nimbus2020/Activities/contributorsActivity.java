package com.nith.appteam.nimbus2020.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nith.appteam.nimbus2020.Adapters.OurTeamAdapter;
import com.nith.appteam.nimbus2020.Models.TeamMember;
import com.nith.appteam.nimbus2020.R;

import java.util.ArrayList;
import java.util.List;

public class contributorsActivity extends AppCompatActivity {

    RecyclerView rvContributors;
    OurTeamAdapter developers;
    List<TeamMember> TeamMembers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contributors);

        TextView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.ease_in, R.anim.ease_out);
            }
        });

//        YoYo.with(Techniques.Bounce)
//                .duration(5000)
//                .repeat(0)
//                .playOn(findViewById(R.id.backdrop));
        rvContributors = findViewById(R.id.contributors);
        TeamMembers = new ArrayList<>();

        //final Year
        TeamMembers.add(new TeamMember("Muskan Jhunjhunwalla", "https://res.cloudinary.com/duutozrvz/image/upload/c_scale,w_120/v1583176381/personal/muskan_ymhpfh.jpg", "Mentor"));
        TeamMembers.add(new TeamMember("Kartik Saxena", "https://res.cloudinary.com/duutozrvz/image/upload/c_scale,w_120/v1583176521/personal/kartiksir_x1fh6a.jpg", "Mentor"));
        TeamMembers.add(new TeamMember("Bharat Shah", "https://res.cloudinary.com/duutozrvz/image/upload/c_scale,w_120/v1583177302/personal/bharat_jnycze.jpg", "Mentor"));
        TeamMembers.add(new TeamMember("Anubhaw Bhalotia", "https://res.cloudinary.com/duutozrvz/image/upload/c_scale,w_120/v1583176373/personal/anubhav_pdbqdy.jpg", "Mentor"));
        //3rd Year

        TeamMembers.add(new TeamMember("Abhinav Lamba", "https://res.cloudinary.com/duutozrvz/image/upload/c_scale,w_120/v1583176520/personal/abhinavsir_pux7go.jpg",
                "Mentor"));
        TeamMembers.add(new TeamMember("Naman Lalit", "https://res.cloudinary.com/duutozrvz/image/upload/c_scale,w_120/v1583176382/personal/naman_docxcd.jpg",
                "Mentor"));
        TeamMembers.add(new TeamMember("Nishant Singh Hada", "https://res.cloudinary.com/duutozrvz/image/upload/c_scale,w_120/v1583176521/personal/nishantsir_lvdi1i.jpg",
                "Mentor"));
        TeamMembers.add(new TeamMember("Utkarsh Singh", "https://res.cloudinary.com/duutozrvz/image/upload/c_scale,w_120/v1583176391/personal/utkarsh_ruepds.jpg",
                "Mentor"));
        TeamMembers.add(new TeamMember("Vishal Parmar", "https://res.cloudinary.com/duutozrvz/image/upload/c_scale,w_120/v1583176522/personal/vishalsir_mqcu18.jpg",
                "Mentor"));
        TeamMembers.add(new TeamMember("Divyanshu", "https://res.cloudinary.com/duutozrvz/image/upload/c_scale,w_120/v1583176521/personal/divyanshusir_txryep.jpg",
                "Mentor"));
        TeamMembers.add(new TeamMember("Daniyaal Khan", "https://res.cloudinary.com/duutozrvz/image/upload/c_scale,w_120/v1583176373/personal/daniyaal_chstch.jpg", "Other"));


//        TeamMembers.add(
//                new TeamMember("Aakanksha Tewari", "aakankshaa23.png",
//                        "aakankshaa23"));
        //2nd year
        TeamMembers.add(new TeamMember("Pawan Harariya", "https://res.cloudinary.com/duutozrvz/image/upload/c_scale,w_120/v1583250505/personal/pawan_fig2oe.png", "Android"));
        TeamMembers.add(new TeamMember("Sarthak Gupta", "https://res.cloudinary.com/duutozrvz/image/upload/c_scale,w_120/v1583176384/personal/sarthak_tmbyfg.jpg", "Backend"));
        TeamMembers.add(new TeamMember("Shubham Kumar", "https://res.cloudinary.com/duutozrvz/image/upload/c_scale,w_120/v1583175656/personal/shubham_olbpm9.png", "Android"));
        TeamMembers.add(new TeamMember("Shweta Gurnani", "https://res.cloudinary.com/duutozrvz/image/upload/c_scale,w_120/v1583176525/personal/shweta_wio26j.png",
                "Backend"));
        TeamMembers.add(new TeamMember("Ayush Kaushal", "https://res.cloudinary.com/duutozrvz/image/upload/c_scale,w_120/v1583176384/personal/ayush_dew6ea.jpg", "Android"));

//        TeamMembers.add(
//                new TeamMember("Chandan Sitlani", "ChandanSitlani.png",
//                        "ChandanSitlani"));
        TeamMembers.add(new TeamMember("Shreya Agarwal", "https://res.cloudinary.com/duutozrvz/image/upload/c_scale,w_120/v1583250766/personal/shreya_pi13bm.png", "Backend"));
        TeamMembers.add(new TeamMember("Amishi Bansal","https://res.cloudinary.com/duutozrvz/image/upload/c_scale,w_120/v1583176372/personal/amishi_jr8ojz.jpg", "Android"));
        TeamMembers.add(new TeamMember("Ananya Sharma", "https://res.cloudinary.com/duutozrvz/image/upload/c_scale,w_120/v1583176373/personal/ananya_prgfcu.jpg", "Backend"));
        TeamMembers.add(new TeamMember("Rupali Mehra", "https://res.cloudinary.com/duutozrvz/image/upload/c_scale,w_120/v1583250713/personal/rupali_spwlxh.jpg", "Designer"));
        TeamMembers.add(new TeamMember("Moulik Bhardwaj", "https://i.imgur.com/2Uvhgnj_d.jpg?maxwidth=640&shape=thumb&fidelity=medium", "Backend"));
        TeamMembers.add(new TeamMember("Pranjal Dureja", "https://res.cloudinary.com/duutozrvz/image/upload/c_scale,w_120/v1583176418/personal/pranjal_dbxz0a.jpg", "Backend"));

        //1st year
        TeamMembers.add(new TeamMember("Kashika", "https://res.cloudinary.com/duutozrvz/image/upload/c_scale,w_120/v1583176492/personal/kashika_qsprow.jpg",
                "Volunteer"));
        TeamMembers.add(new TeamMember("Khyati Saini", "https://res.cloudinary.com/duutozrvz/image/upload/c_scale,w_120/v1583176379/personal/khyati_ludtxm.jpg",
                "Volunteer"));

        TeamMembers.add(new TeamMember("Nishant Chandla", "https://res.cloudinary.com/duutozrvz/image/upload/c_scale,w_120/v1583176386/personal/nishant_njcizn.jpg",
                "Volunteer"));
        TeamMembers.add(new TeamMember("Shushant Chandla", "https://res.cloudinary.com/duutozrvz/image/upload/c_scale,w_120/v1583176468/personal/sushant_gr9ogu.jpg",
                "Volunteer"));
        TeamMembers.add(
                new TeamMember("Anshit", "https://res.cloudinary.com/duutozrvz/image/upload/c_scale,w_120/v1583176383/personal/anshit_meb7bk.jpg", "Volunteer"));
        TeamMembers.add(new TeamMember("Mrigank Anand", "https://res.cloudinary.com/duutozrvz/image/upload/c_scale,w_120/v1583250712/personal/mrigank_pf6uu5.jpg",
                "Volunteer"));
        TeamMembers.add(new TeamMember("Gautam Verma", "https://res.cloudinary.com/duutozrvz/image/upload/c_scale,w_120/v1583176374/personal/gautm_bthppv.jpg",
                "Volunteer"));
        TeamMembers.add(new TeamMember("Dhruv Panwar", "https://res.cloudinary.com/duutozrvz/image/upload/c_scale,w_120/v1583176381/personal/dhruv_iwdspb.jpg",
                "Volunteer"));

        TeamMembers.add(new TeamMember("Shefali Kanojia", "https://res.cloudinary.com/duutozrvz/image/upload/c_scale,w_120/v1583176385/personal/shefali_f0voxo.jpg",
                "Volunteer"));
        TeamMembers.add(new TeamMember("Salmaan", "https://res.cloudinary.com/duutozrvz/image/upload/c_scale,w_120/v1583250714/personal/salmaan_hscbwc.jpg",
                "Volunteer"));


        developers = new OurTeamAdapter(TeamMembers, contributorsActivity.this);
        rvContributors.setAdapter(developers);
        rvContributors.setLayoutManager(new GridLayoutManager(this, 2));
    }
}
