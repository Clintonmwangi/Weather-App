package com.example.weatherapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText cityInput;
    private Button fetchButton;
    private TextView resultView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityInput = findViewById(R.id.editTextCity);
        fetchButton = findViewById(R.id.buttonFetch);
        resultView = findViewById(R.id.textViewResult);
        progressBar = findViewById(R.id.progressBar);

        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = cityInput.getText().toString().trim();
                if (!city.isEmpty()) {
                    fetchWeather(city);
                } else {
                    resultView.setText("Please enter a city name.");
                }
            }
        });
    }

    private void fetchWeather(String city) {
        progressBar.setVisibility(View.VISIBLE);
        resultView.setText("");
        String apiKey = getString(R.string.api_key);
        WeatherService.fetchCurrentWeather(this, city, apiKey, new WeatherService.Callback() {
            @Override
            public void onSuccess(Weather weather) {
                progressBar.setVisibility(View.GONE);
                if (weather != null) {
                    String display = String.format("%s: %.1f°C, %s",
                            weather.name, weather.tempCelsius, weather.description);
                    resultView.setText(display);
                } else {
                    resultView.setText("No weather data available.");
                }
            }

            @Override
            public void onError(Exception e) {
                progressBar.setVisibility(View.GONE);
                resultView.setText("Error: " + e.getMessage());
            }
        });
    }
}
