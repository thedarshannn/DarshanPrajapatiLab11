package darshan.prajapati.n01584247.dp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class PrajapatiActivity11 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new Da11rshanFragment());
        fragmentList.add(new Pr22ajapatiFragment());
        fragmentList.add(new n0153384247Fragment());

        ViewPager2 viewPager = findViewById(R.id.DarViewPager);
        viewPager.setAdapter(new ViewPagerAdapter(this, fragmentList));

        TabLayout tabLayout = findViewById(R.id.DarTabLayout);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    // Customize the tab name based on position/index
                    if (position == 0) {
                        tab.setText("Darshan");
                        tab.setIcon(R.drawable.database__1_);
                    }
                    if (position == 1) {
                        tab.setText("Prajapati");
                        tab.setIcon(R.drawable.land_layer_location);
                    }
                    if (position == 2) {
                        tab.setText("N01584247");
                        tab.setIcon(R.drawable.site_browser);
                    }
                }
        ).attach();
    }
}