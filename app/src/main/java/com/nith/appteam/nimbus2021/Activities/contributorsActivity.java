
package com.nith.appteam.nimbus2021.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nith.appteam.nimbus2021.Adapters.OurTeamAdapter;
import com.nith.appteam.nimbus2021.Models.TeamMember;
import com.nith.appteam.nimbus2021.R;

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

        // super final year
        TeamMembers.add(new TeamMember("Kartik Saxena", "https://res.cloudinary.com/ddl3sdojc/image/upload/c_scale,w_120/v1611338002/kartiksir_bqs6yf.jpg",
                "Mentor"));
        TeamMembers.add(new TeamMember("Muskan Jhunjhunwalla", "https://res.cloudinary.com/ddl3sdojc/image/upload/c_scale,w_120/v1611338004/muskan_q6pwkl.jpg",
                "Mentor"));
//        TeamMembers.add(new TeamMember("Bharat Shah", "https://res.cloudinary.com/duutozrvz/image/upload/c_scale,w_120/v1583177302/personal/bharat_jnycze.jpg",
//                "Mentor"));
//        TeamMembers.add(new TeamMember("Anubhaw Bhalotia", "https://res.cloudinary.com/duutozrvz/image/upload/c_scale,w_120/v1583176373/personal/anubhav_pdbqdy.jpg",
//                "Mentor"));

        // final year
        TeamMembers.add(new TeamMember("Abhinav Lamba", "https://res.cloudinary.com/ddl3sdojc/image/upload/c_scale,w_120/v1611337999/abhinavsir_qwzzbp.jpg",
                "Mentor"));
        TeamMembers.add(new TeamMember("Naman Lalit", "https://res.cloudinary.com/ddl3sdojc/image/upload/c_scale,w_120/v1611338004/naman_wufozd.jpg",
                "Mentor"));
        TeamMembers.add(new TeamMember("Nishant Singh Hada", "https://res.cloudinary.com/ddl3sdojc/image/upload/c_scale,w_120/v1611338005/nishantsir_ezifsi.jpg",
                "Mentor"));
        TeamMembers.add(new TeamMember("Utkarsh Singh", "https://res.cloudinary.com/ddl3sdojc/image/upload/c_scale,w_120/v1611338007/utkarsh_dpdsu6.jpg",
                "Mentor"));
        TeamMembers.add(new TeamMember("Vishal Parmar", "https://res.cloudinary.com/ddl3sdojc/image/upload/c_scale,w_120/v1611338007/vishalsir_qc8nqh.jpg",
                "Mentor"));
        TeamMembers.add(new TeamMember("Divyanshu", "https://res.cloudinary.com/ddl3sdojc/image/upload/c_scale,w_120/v1611338001/divyanshusir_cto7yg.jpg",
                "Mentor"));
        TeamMembers.add(new TeamMember("Daniyaal Khan", "https://res.cloudinary.com/ddl3sdojc/image/upload/c_scale,w_120/v1611338001/daniyaal_aofghr.jpg",
                "Mentor"));
        TeamMembers.add(new TeamMember("Aakanksha Tewari", "https://res.cloudinary.com/ddl3sdojc/image/upload/v1615096713/pdtzucgr9acxpqw7mudk.jpg",
                "Mentor"));
        TeamMembers.add(new TeamMember("Arjun Anand", "https://res.cloudinary.com/ddl3sdojc/image/upload/v1615096713/ceentwwdhrnkcxhpbmvv.jpg",
                "Mentor"));

        // 3rd year
        TeamMembers.add(new TeamMember("Pawan Harariya", "https://res.cloudinary.com/ddl3sdojc/image/upload/c_scale,w_120/v1611338005/pawan_ajkqtu.png",
                "Mentor"));
        TeamMembers.add(new TeamMember("Sarthak Gupta", "https://res.cloudinary.com/ddl3sdojc/image/upload/c_scale,w_120/v1611338005/sarthak_wy2gql.jpg",
                "Mentor"));
        TeamMembers.add(new TeamMember("Shubham Kumar", "https://res.cloudinary.com/ddl3sdojc/image/upload/c_scale,w_120/v1611338006/shubham_iko3fb.png",
                "Mentor"));
        TeamMembers.add(new TeamMember("Shweta Gurnani", "https://res.cloudinary.com/ddl3sdojc/image/upload/c_scale,w_120/v1611338006/shweta_ul2yeh.png",
                "Mentor"));
        TeamMembers.add(new TeamMember("Ayush Kaushal", "https://res.cloudinary.com/ddl3sdojc/image/upload/c_scale,w_120/v1611338000/ayush_dew6ea_lzgs8u.jpg",
                "Mentor"));
        TeamMembers.add(new TeamMember("Chandan Sitlani", "https://avatars.githubusercontent.com/u/40667874?s=460&v=4",
                "ChandanSitlani"));
        TeamMembers.add(new TeamMember("Shreya Agarwal", "https://res.cloudinary.com/ddl3sdojc/image/upload/c_scale,w_120/v1611338005/shreya_hebdh9.png",
                "Mentor"));
        TeamMembers.add(new TeamMember("Amishi Bansal","https://res.cloudinary.com/ddl3sdojc/image/upload/c_scale,w_120/v1611337999/amishi_fuxwcu.jpg",
                "Mentor"));
        TeamMembers.add(new TeamMember("Ananya Sharma", "https://res.cloudinary.com/ddl3sdojc/image/upload/c_scale,w_120/v1611337999/ananya_prgfcu_r6tavb.jpg",
                "Mentor"));
        TeamMembers.add(new TeamMember("Rupali Mehra", "https://res.cloudinary.com/ddl3sdojc/image/upload/c_scale,w_120/v1611338005/rupali_pyvuuh.jpg",
                "Mentor"));
        TeamMembers.add(new TeamMember("Moulik Bhardwaj", "https://res.cloudinary.com/ddl3sdojc/image/upload/c_scale,w_120/v1611338003/mouliksir_sp4q5g.jpg",
                "Mentor"));
//        TeamMembers.add(new TeamMember("Pranjal Dureja", "https://res.cloudinary.com/ddl3sdojc/image/upload/c_scale,w_120/v1611338005/pranjal_spqauz.jpg",
//                "Mentor"));


        // 2nd year
        TeamMembers.add(new TeamMember("Kashika", "https://res.cloudinary.com/ddl3sdojc/image/upload/c_scale,w_120/v1611338006/kashika_sp7bs3.jpg",
                "Android"));
        TeamMembers.add(new TeamMember("Khyati Saini", "https://res.cloudinary.com/ddl3sdojc/image/upload/c_scale,w_120/v1611338003/khyati_k1u4le.jpg",
                "Android"));
        TeamMembers.add(new TeamMember("Nishant Chandla", "https://res.cloudinary.com/ddl3sdojc/image/upload/c_scale,w_120/v1611338004/nishant_oprykt.png",
                "Android"));
        TeamMembers.add(new TeamMember("Anshit", "https://res.cloudinary.com/ddl3sdojc/image/upload/c_scale,w_120/v1611338003/anshit_k1e9f9.jpg",
                "Backend"));
        TeamMembers.add(new TeamMember("Mrigank Anand", "https://res.cloudinary.com/ddl3sdojc/image/upload/c_scale,w_120/v1611338003/mrigank_r9lsjd.png",
                "Backend"));
        TeamMembers.add(new TeamMember("Mohd Salmaan", "https://res.cloudinary.com/ddl3sdojc/image/upload/c_scale,w_120/v1611338016/salmaan_gulz1s.png",
                "Backend"));
        TeamMembers.add(new TeamMember("Chetan Sharma", "https://res.cloudinary.com/ddl3sdojc/image/upload/c_scale,w_120/v1611338002/chetan_nspstn.png",
                "Backend"));
        TeamMembers.add(new TeamMember("Yash Upadhyay", "https://res.cloudinary.com/ddl3sdojc/image/upload/c_scale,w_120/v1611338007/yash_wgd3gn.jpg",
                "Android"));
        TeamMembers.add(new TeamMember("Aman Chauhan", "https://res.cloudinary.com/ddl3sdojc/image/upload/c_scale,w_120/v1611338002/aman_pp8otc.jpg",
                "Backend"));
        TeamMembers.add(new TeamMember("Tushar Dupga", "https://res.cloudinary.com/ddl3sdojc/image/upload/c_scale,w_120/v1611338008/tushar_b63k3l.jpg",
                "Android"));
        TeamMembers.add(new TeamMember("Dechan Dolkar", "https://res.cloudinary.com/ddl3sdojc/image/upload/c_scale,w_120/v1611338003/dechan_y6awv8.jpg",
                "Android"));
        TeamMembers.add(new TeamMember("Satyam Kumar", "https://res.cloudinary.com/ddl3sdojc/image/upload/c_scale,w_120/v1611338005/satyam_bnswwy.jpg",
                "Backend"));
        TeamMembers.add(new TeamMember("Swarna Mallika", "https://res.cloudinary.com/ddl3sdojc/image/upload/c_scale,w_120/v1611338006/swarna_xyaulk.jpg",
                "Designer"));
        TeamMembers.add(new TeamMember("Archana Verma", "https://res.cloudinary.com/ddl3sdojc/image/upload/c_scale,w_120/v1611338001/archana_huc6zr.jpg",
                "Android"));
        TeamMembers.add(new TeamMember("Shwetal Soni", "https://res.cloudinary.com/ddl3sdojc/image/upload/c_scale,w_120/v1614160396/shwetal_atqkxo.jpg",
                "Designer"));
        TeamMembers.add(new TeamMember("Bhavesh Vaishnav", "https://res.cloudinary.com/ddl3sdojc/image/upload/c_scale,w_120/v1611338004/bhavesh_fnban5.jpg",
                "Backend"));


        developers = new OurTeamAdapter(TeamMembers, contributorsActivity.this);
        rvContributors.setAdapter(developers);
        rvContributors.setLayoutManager(new GridLayoutManager(this, 2));
    }
}
