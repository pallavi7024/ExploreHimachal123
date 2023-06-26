package com.tourism.myapplication;

import static com.tourism.myapplication.utils.Constants.isValidCardNumber;
import static com.tourism.myapplication.utils.Constants.isValidUpiNumber;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.tourism.myapplication.api.model.HotelModel;
import com.tourism.myapplication.databinding.ActivityPaymentBinding;

public class PaymentActivity extends AppCompatActivity {

    private ActivityPaymentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getDataFromIntent();
        buttonLogic();
    }

    private void buttonLogic() {
        binding.buttonPayCard.setOnClickListener(v -> {
            var number = binding.editTextCardNumber.getText().toString();
            var cvv = binding.editTextCVV.getText().toString();
            var expiry = binding.editTextYear.getText().toString();
            var month = binding.editTextMonth.getText().toString();
            if (number.isEmpty() || cvv.isEmpty() || expiry.isEmpty() || month.isEmpty()) {
                Toast.makeText(this, "Enter valid details !!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!isValidCardNumber(number)
                    && cvv.length() != 3
                    && expiry.length() != 2
                    && month.length() != 2
                    && Integer.parseInt(month) > 12
                    && Integer.parseInt(expiry) < 35
            ) {
                Toast.makeText(this, "Enter valid details !!", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(this, "Payment Successful !!", Toast.LENGTH_SHORT).show();
            navigateToHome();
        });
        binding.buttonPayUPI.setOnClickListener(v -> {
            var id = binding.editTextUPI.getText().toString();
            if (id.isEmpty()) {
                Toast.makeText(this, "Enter valid details !!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!isValidUpiNumber(id)) {
                Toast.makeText(this, "Enter valid details !!", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(this, "Payment Successful !!", Toast.LENGTH_SHORT).show();
            navigateToHome();
        });
        binding.buttonBook.setOnClickListener(v -> {
            Toast.makeText(this, "Booking Successful !!", Toast.LENGTH_SHORT).show();
            navigateToHome();
        });
    }

    private void getDataFromIntent() {
        HotelModel hotelName = (HotelModel) getIntent().getSerializableExtra("hotelModel");
        binding.textViewDetails.append(hotelName.toString());
        handleButtons();
    }

    private void handleButtons() {
        binding.radioButtonCard.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                setViews(PaymentType.CARD);
        });
        binding.radioButtonUPI.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                setViews(PaymentType.UPI);
        });
        binding.radioButtonPayOnCheckIn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                setViews(PaymentType.CHECK_IN);
        });
    }

    private void setViews(PaymentType type) {
        if (type == PaymentType.CARD) {
            binding.radioButtonUPI.setChecked(false);
            binding.radioButtonPayOnCheckIn.setChecked(false);
            binding.constraintLayoutCard.setVisibility(View.VISIBLE);
            binding.llUPI.setVisibility(View.GONE);
            binding.buttonBook.setVisibility(View.GONE);
        }
        if (type == PaymentType.UPI) {
            binding.radioButtonCard.setChecked(false);
            binding.radioButtonPayOnCheckIn.setChecked(false);
            binding.constraintLayoutCard.setVisibility(View.GONE);
            binding.llUPI.setVisibility(View.VISIBLE);
            binding.buttonBook.setVisibility(View.GONE);
        }
        if (type == PaymentType.CHECK_IN) {
            binding.radioButtonUPI.setChecked(false);
            binding.radioButtonCard.setChecked(false);
            binding.constraintLayoutCard.setVisibility(View.GONE);
            binding.llUPI.setVisibility(View.GONE);
            binding.buttonBook.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void navigateToHome() {
        var intend = new Intent(this, MainActivity.class);
        intend.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intend);
    }

    enum PaymentType {
        CARD, UPI, CHECK_IN
    }

}