package com.android.miniroulette;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Random;

public class MainFragment extends Fragment {

    private static final int ROULETTE_MAX_VALUE = 3;
    private static final int RESULT_REQUEST_CODE = 0;
    private static final String WIN_RESULT_KEY = "random_numbers";
    private static final String IS_NOT_FIRST_KEY = "IS_NOT_FIRST_KEY";

    private TextView oneWindowTv;
    private TextView twoWindowTv;
    private TextView threeWindowTv;
    private TextView resultTextView;
    private Button startButton;

    private int firstWindowValue = 0;
    private int secondWindowValue = 0;
    private int thirstWindowValue = 0;

    // Cоздаёт вью
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main, container, false);
    }

    // Сразу после создания вью
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        fillWindowsByNumbers(
                getRandomRouletteValue(),
                getRandomRouletteValue(),
                getRandomRouletteValue()
        );
        setListeners();
    }

    private void initViews(View view) {
        oneWindowTv = view.findViewById(R.id.one_text_view);
        twoWindowTv = view.findViewById(R.id.two_text_view);
        threeWindowTv = view.findViewById(R.id.three_text_view);
        resultTextView = view.findViewById(R.id.result_text_view);
        startButton = view.findViewById(R.id.start_button);
    }

    private void setListeners() {
        startButton.setOnClickListener(v -> openNextScreen());
    }

    private void openNextScreen() {
        if (isWin(firstWindowValue, secondWindowValue, thirstWindowValue)) {
            Intent resultIntent = new Intent();
            @SuppressLint("DefaultLocale") String resultStr = String.format(
                    "%d %d %d !!!",
                    firstWindowValue,
                    secondWindowValue,
                    thirstWindowValue
            );
            resultIntent.putExtra(WIN_RESULT_KEY, resultStr);
        } else {
            Intent intent = new Intent(getContext(), RootActivity.class);
            intent.putExtra(IS_NOT_FIRST_KEY, true);
            startActivityForResult(intent, RESULT_REQUEST_CODE);
        }
    }

    private int getRandomRouletteValue() {
        return new Random().nextInt(ROULETTE_MAX_VALUE);
    }

    private void fillWindowsByNumbers(int first, int second, int third) {
        firstWindowValue = first;
        secondWindowValue = second;
        thirstWindowValue = third;

        oneWindowTv.setText(String.valueOf(first));
        twoWindowTv.setText(String.valueOf(second));
        threeWindowTv.setText(String.valueOf(third));
        setResultText();
    }

    private boolean isWin(int first, int second, int third) {
        return first == second && second == third;
    }

    private void setResultText() {
        if (isWin(firstWindowValue, secondWindowValue, thirstWindowValue)) {
            resultTextView.setText("Ура, Вы победили!");
        } else {
            resultTextView.setText("Попробуйте еще!");
        }
    }

}