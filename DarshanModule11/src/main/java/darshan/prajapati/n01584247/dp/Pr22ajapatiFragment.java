package darshan.prajapati.n01584247.dp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Pr22ajapatiFragment extends Fragment {

    private final LatLng CAMBAY = new LatLng(22.3181, 72.6194);
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            googleMap.addMarker(new MarkerOptions().position(CAMBAY).title(getString(R.string.map_location))
                    .snippet(getString(R.string.map_snippet)));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CAMBAY, 15));

            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    getAddressFromLatLng(latLng, googleMap);
                }
            });

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pr22ajapati, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.DarGmap);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    private void getAddressFromLatLng(LatLng latLng, GoogleMap googleMap) {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);

            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                String addressText = String.format("%s, %s, %s",
                        address.getAddressLine(0),
                        address.getLocality(),
                        address.getCountryName());

                // Update TextView
                TextView fullNameTextView = requireActivity().findViewById(R.id.DarAboveMapTV);
                fullNameTextView.setText(addressText);

                googleMap.clear(); // Clear previous markers
                googleMap.addMarker(new MarkerOptions().position(latLng).title("Darshan Prajapati")
                        .snippet(addressText));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

                // Show Snack bar
                showSnackbar(addressText);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showSnackbar(String addressText) {
        Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content),
                addressText, Snackbar.LENGTH_INDEFINITE);

        snackbar.setAction(R.string.dismiss, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });

        snackbar.show();
    }
}