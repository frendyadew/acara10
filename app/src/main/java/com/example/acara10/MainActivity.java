package com.example.acara10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.acara10.model.Data;
import com.example.acara10.network.ApiClient;
import com.example.acara10.network.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the ListView by ID
        ListView listView = findViewById(R.id.tv_nama);

        // Create service
        ApiService client = ApiClient.getInstance();

        // Call the API to get users
        Call<List<Data>> response = client.getAllUsers();

        // List to hold user details
        List<String> dataUser = new ArrayList<>();

        // Enqueue the call
        response.enqueue(new Callback<List<Data>>() {

            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (Data data : response.body()) {
                        // Check gender and translate it accordingly
                        String gender = data.getJeniskelamin().equalsIgnoreCase("l") ? "Laki-laki" : "Perempuan";
                        // Format user details
                        String userDetails = "Surveyor: " + data.getNamaSurveyor() + "\n" +
                                "Usia: " + data.getUsia() + "\n" +
                                "Jenis Kelamin: " + gender + "\n" +
                                "Pekerjaan: " + data.getPekerjaan() + "\n" +
                                "ID Desa: " + data.getIdDesa() + "\n" +
                                "Nama Desa: " + data.getNamaDesa() + "\n" +
                                "Latitude: " + data.getLatitude() + "\n" +
                                "Longitude: " + data.getLongitude();
                        dataUser.add(userDetails);
                    }

                    // Set the adapter to display the data in the ListView
                    ArrayAdapter<String> listAdapter = new ArrayAdapter<>(
                            MainActivity.this, android.R.layout.simple_list_item_1, dataUser
                    );
                    listView.setAdapter(listAdapter);
                } else {
                    // Show a toast message if the response is not successful
                    Toast.makeText(MainActivity.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {
                // Display an error message if the request fails
                Toast.makeText(MainActivity.this, "Request failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
