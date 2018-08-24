package net.callofdroidy.wealendar;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import net.callofdroidy.wealendar.database.entity.Weather;
import net.callofdroidy.wealendar.repository.WeatherViewModel;
import net.callofdroidy.wealendar.ui.CityListItem;
import net.callofdroidy.wealendar.ui.CityListRvAdapter;
import net.callofdroidy.wealendar.ui.WeatherDetailsFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int NUM_PAGES = 5;

    private RecyclerView rvCityList;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvCityList = findViewById(R.id.rv_city_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(rvCityList.getContext(), layoutManager.getOrientation());
        rvCityList.addItemDecoration(dividerItemDecoration);
        rvCityList.setHasFixedSize(true);

        viewPager = findViewById(R.id.vp_weather_details);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        WeatherViewModel model = ViewModelProviders.of(this).get(WeatherViewModel.class);
        model.getCurrentWeathers(5).observe(MainActivity.this, weathers -> {
            if (weathers != null) {
                List<CityListItem> cityListItems = new ArrayList<>();

                for (Weather weather : weathers) {
                    cityListItems.add(new CityListItem(
                            weather.cityName,
                            String.format(Locale.CANADA, "%.0f", weather.temperature),
                            weather.weatherDescription,
                            getWeatherIconByConditionId(weather.weatherConditionId)));
                }

                CityListRvAdapter cityListRvAdapter = new CityListRvAdapter(cityListItems);
                rvCityList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                rvCityList.setAdapter(cityListRvAdapter);
            } else {
                Log.e(TAG, "onChanged: weathers null");
            }
        });
    }

    private Drawable getWeatherIconByConditionId(int conditionId) {
        if (conditionId / 100 == 2) { // 2xx Thunderstorm
            return getResources().getDrawable(R.drawable.ic_thunder);
        }
        if (conditionId / 100 == 3) { // 3xx Drizzle
            return getResources().getDrawable(R.drawable.ic_drizzle);
        }
        if (conditionId / 100 == 5) { // 5xx Rain
            return getResources().getDrawable(R.drawable.ic_rainy);
        }
        if (conditionId / 100 == 6) { // 6xx Snow
            return getResources().getDrawable(R.drawable.ic_snowy);
        }
        if (conditionId / 100 == 7) { // 7xx Mist
            return getResources().getDrawable(R.drawable.ic_cloudy);
        }
        if (conditionId == 800) { // 800 Clear
            return getResources().getDrawable(R.drawable.ic_sunny);
        }
        if (conditionId / 100 == 8) { // 80x Clouds
            return getResources().getDrawable(R.drawable.ic_cloudy);
        }
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return WeatherDetailsFragment.newInstance("Day " + (position + 1), null);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
