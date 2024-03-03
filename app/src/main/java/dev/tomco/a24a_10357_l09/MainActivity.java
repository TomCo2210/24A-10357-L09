package dev.tomco.a24a_10357_l09;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

import dev.tomco.a24a_10357_l09.Models.Car;
import dev.tomco.a24a_10357_l09.Models.Garage;

public class MainActivity extends AppCompatActivity {

    UUID id = UUID.randomUUID();

    private MaterialTextView main_LBL_title;
    private TextInputEditText main_ET_text;
    private MaterialButton main_BTN_update;
    private MaterialButton main_BTN_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViews();
        initViews();
        updateTitleFromDB();
        Log.d("UUID:", "UUID is:" + id);
    }

    private void updateTitleFromDB() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference titleRef = db.getReference("title");
//        titleRef.addListenerForSingleValueEvent( // For one time data fetching from DB.
        titleRef.addValueEventListener( // For one time data fetching from DB.
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String value = snapshot.getValue(String.class);
                        main_LBL_title.setText(value);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );
    }

    private void initViews() {
        main_BTN_update.setOnClickListener(v -> {
            setLabel(main_ET_text.getText().toString());
        });
        main_BTN_save.setOnClickListener(v -> {
            // createGarageAndSave();
            updateCar("269-69-402");
        });
    }

    private void createGarageAndSave() {
        Garage garage = new Garage().setName("Garage 1");
        garage.getAllCars().put(
                "269-69-402",
                new Car()
                        .setFourWheelDrive(false)
                        .setModel("Mazda 2")
                        .setLicensePlate("269-69-402")
                        .setType(Car.CarType.GASOLINE)
                        .setPrice(75_000)
                        .setKmPerLiter(17.0)
                        .setOdometer(57_500)
        );
        garage.getAllCars().put(
                "15-336-66",
                new Car()
                        .setFourWheelDrive(false)
                        .setModel("Mazda 2")
                        .setLicensePlate("15-336-66")
                        .setType(Car.CarType.GASOLINE)
                        .setPrice(12_000)
                        .setKmPerLiter(12.5)
                        .setOdometer(300_000)
        );
        garage.getAllCars().put(
                "62-963-65",
                new Car()
                        .setFourWheelDrive(false)
                        .setModel("Mazda 2")
                        .setLicensePlate("62-963-65")
                        .setType(Car.CarType.GASOLINE)
                        .setPrice(12_500)
                        .setKmPerLiter(15.0)
                        .setOdometer(200_000)
        );

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("garage");
        ref.setValue(garage);
    }

    private void updateCar(String licensePlate) {
        DatabaseReference garageRef = FirebaseDatabase.getInstance().getReference("garage");
        garageRef.child("allCars")
                .child(licensePlate)
                .child("price")
                .setValue(76_000);

    }

    private void setLabel(String string) {
        DatabaseReference titleRef = FirebaseDatabase.getInstance().getReference("title");
        titleRef.setValue(string);
    }

    private void findViews() {
        main_LBL_title = findViewById(R.id.main_LBL_title);
        main_ET_text = findViewById(R.id.main_ET_text);
        main_BTN_update = findViewById(R.id.main_BTN_update);
        main_BTN_save = findViewById(R.id.main_BTN_save);
    }
}