package com.example.tasteteaser;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.tasteteaser.entities.MealResponse;
import com.example.tasteteaser.interfaces.GetDataService;
import com.example.tasteteaser.retrofitclient.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends BaseActivity {

    private String youtubeLink = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String id = getIntent().getStringExtra("id");

        getSpecificItem(id);

        findViewById(R.id.imgToolbarBtnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findViewById(R.id.btnYoutube).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(youtubeLink);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    private void getSpecificItem(String id) {
        GetDataService service = RetrofitClientInstance.Companion.getRetrofitInstance().create(GetDataService.class);
        Call<MealResponse> call = service.getSpecificItem(id);
        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                Toast.makeText(DetailActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                Glide.with(DetailActivity.this).load(response.body().getMealsEntity().get(0).getStrmealthumb()).into(findViewById(R.id.imgItem));

                findViewById(R.id.tvCategory).setText(response.body().getMealsEntity().get(0).getStrmeal());

                String ingredient = response.body().getMealsEntity().get(0).getStringredient1() + "      " + response.body().getMealsEntity().get(0).getStrmeasure1() + "\n" +
                        response.body().getMealsEntity().get(0).getStringredient2() + "      " + response.body().getMealsEntity().get(0).getStrmeasure2() + "\n" +
                        response.body().getMealsEntity().get(0).getStringredient3() + "      " + response.body().getMealsEntity().get(0).getStrmeasure3() + "\n" +
                        response.body().getMealsEntity().get(0).getStringredient4() + "      " + response.body().getMealsEntity().get(0).getStrmeasure4() + "\n" +
                        response.body().getMealsEntity().get(0).getStringredient5() + "      " + response.body().getMealsEntity().get(0).getStrmeasure5() + "\n" +
                        response.body().getMealsEntity().get(0).getStringredient6() + "      " + response.body().getMealsEntity().get(0).getStrmeasure6() + "\n" +
                        response.body().getMealsEntity().get(0).getStringredient7() + "      " + response.body().getMealsEntity().get(0).getStrmeasure7() + "\n" +
                        response.body().getMealsEntity().get(0).getStringredient8() + "      " + response.body().getMealsEntity().get(0).getStrmeasure8() + "\n" +
                        response.body().getMealsEntity().get(0).getStringredient9() + "      " + response.body().getMealsEntity().get(0).getStrmeasure9() + "\n" +
                        response.body().getMealsEntity().get(0).getStringredient10() + "      " + response.body().getMealsEntity().get(0).getStrmeasure10() + "\n" +
                        response.body().getMealsEntity().get(0).getStringredient11() + "      " + response.body().getMealsEntity().get(0).getStrmeasure11() + "\n" +
                        response.body().getMealsEntity().get(0).getStringredient12() + "      " + response.body().getMealsEntity().get(0).getStrmeasure12() + "\n" +
                        response.body().getMealsEntity().get(0).getStringredient13() + "      " + response.body().getMealsEntity().get(0).getStrmeasure13() + "\n" +
                        response.body().getMealsEntity().get(0).getStringredient14() + "      " + response.body().getMealsEntity().get(0).getStrmeasure14() + "\n" +
                        response.body().getMealsEntity().get(0).getStringredient15() + "      " + response.body().getMealsEntity().get(0).getStrmeasure15() + "\n" +
                        response.body().getMealsEntity().get(0).getStringredient16() + "      " + response.body().getMealsEntity().get(0).getStrmeasure16() + "\n" +
                        response.body().getMealsEntity().get(0).getStringredient17() + "      " + response.body().getMealsEntity().get(0).getStrmeasure17() + "\n" +
                        response.body().getMealsEntity().get(0).getStringredient18() + "      " + response.body().getMealsEntity().get(0).getStrmeasure18() + "\n" +
                        response.body().getMealsEntity().get(0).getStringredient19() + "      " + response.body().getMealsEntity().get(0).getStrmeasure19() + "\n" +
                        response.body().getMealsEntity().get(0).getStringredient20() + "      " + response.body().getMealsEntity().get(0).getStrmeasure20() + "\n";

                findViewById(R.id.tvIngredients).setText(ingredient);
                findViewById(R.id.tvInstructions).setText(response.body().getMealsEntity().get(0).getStrinstructions());

                if (response.body().getMealsEntity().get(0).getStrsource() != null) {
                    youtubeLink = response.body().getMealsEntity().get(0).getStrsource();
                } else {
                    findViewById(R.id.btnYoutube).setVisibility(View.GONE);
                }
            }
        });
    }
}
