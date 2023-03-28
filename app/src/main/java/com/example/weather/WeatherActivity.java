package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.weather.databinding.ActivityWeatherBinding;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherActivity extends AppCompatActivity {
    ActivityWeatherBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_weather);
        binding = ActivityWeatherBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.editTextCityName.getText().toString();
                getWeatherData(name);
                binding.editTextCityName.setText("");
            }
        });

    }
    public void getWeatherData(String name)
    {
        WeatherAPI weatherAPI = RetrofitWeather.getClient().create(WeatherAPI.class);
        Call<OpenWeatherMap> call = weatherAPI.getWeatherWithCityName(name);

        call.enqueue(new Callback<OpenWeatherMap>() {
            @Override
            public void onResponse(Call<OpenWeatherMap> call, Response<OpenWeatherMap> response) {
                if (response.isSuccessful())
                {
                    binding.textViewCityWeather.setText(response.body().getName()+" , "+response.body().getSys().getCountry());
                    binding.textViewTempWeather.setText(response.body().getMain().getTemp()+" °C");
                    binding.textViewWeatherConditionWeather.setText(response.body().getWeather().get(0).getDescription());
                    binding.humidityWeather.setText(" : "+response.body().getMain().getHumidity()+"%");
                    binding.maxTempWeather.setText(" : "+response.body().getMain().getTempMax()+" °C");
                    binding.minTempWeather.setText(" : "+response.body().getMain().getTempMin()+" °C");
                    binding.pressureWeather.setText(" : "+response.body().getMain().getPressure());
                    binding.windWeather.setText(" : "+response.body().getWind().getSpeed());

                    String iconCode = response.body().getWeather().get(0).getIcon();
                    Picasso.get().load("https://openweathermap.org/img/wn/"+iconCode+"@2x.png")
                            .placeholder(R.drawable.ic_launcher_background)
                            .into(binding.imageViewWeather);
                }
                else{
                    Toast.makeText(WeatherActivity.this, "There is an error", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<OpenWeatherMap> call, Throwable t) {

            }
        });

    }
}