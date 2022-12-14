package com.enesuzumcu.modernxox;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageButton;

import com.enesuzumcu.modernxox.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    ArrayList<ImageButton> player1Buttons;
    ArrayList<ImageButton> player2Buttons;
    ArrayList<ImageButton> gameAreaButtons;
    Drawable selectedDrawable;
    ImageButton selectedButton;
    String selectedTag = "";
    int siraNo = 1;
    String winner = "";
    boolean movesPossible = true; //updateDrawable = true -> drawable güncelle. updateDrawable = false hamle mümkün mü kontrol et

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        atamalar();

        for (ImageButton player1Button : player1Buttons) {
            player1Button.setOnClickListener(view -> focusButton(player1Button, 1));
        }

        for (ImageButton player2Button : player2Buttons) {
            player2Button.setOnClickListener(view -> focusButton(player2Button, 2));
        }

        for (ImageButton gameAreaButton : gameAreaButtons) {
            gameAreaButton.setOnClickListener(view -> checkDrawable(gameAreaButton));
        }
    }

    public void atamalar() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        player1Buttons = new ArrayList<>();
        player2Buttons = new ArrayList<>();
        gameAreaButtons = new ArrayList<>();

        player1Buttons.add(binding.player1Btn1);
        player1Buttons.add(binding.player1Btn2);
        player1Buttons.add(binding.player1Btn3);
        player1Buttons.add(binding.player1Btn4);
        player1Buttons.add(binding.player1Btn5);
        player1Buttons.add(binding.player1Btn6);

        player2Buttons.add(binding.player2Btn1);
        player2Buttons.add(binding.player2Btn2);
        player2Buttons.add(binding.player2Btn3);
        player2Buttons.add(binding.player2Btn4);
        player2Buttons.add(binding.player2Btn5);
        player2Buttons.add(binding.player2Btn6);

        gameAreaButtons.add(binding.btn11);
        gameAreaButtons.add(binding.btn12);
        gameAreaButtons.add(binding.btn13);
        gameAreaButtons.add(binding.btn21);
        gameAreaButtons.add(binding.btn22);
        gameAreaButtons.add(binding.btn23);
        gameAreaButtons.add(binding.btn31);
        gameAreaButtons.add(binding.btn32);
        gameAreaButtons.add(binding.btn33);
        setTagBtn();
        if (siraNo == 1) {
            binding.llPlayer1.setBackgroundColor(Color.parseColor("#FFFAEF94"));
        } else if (siraNo == 2) {
            binding.llPlayer2.setBackgroundColor(Color.parseColor("#FFFAEF94"));
        }
    }

    private void setTagBtn() {
        binding.player1Btn1.setTag(Constants.player1Btn1);
        binding.player1Btn2.setTag(Constants.player1Btn2);
        binding.player1Btn3.setTag(Constants.player1Btn3);
        binding.player1Btn4.setTag(Constants.player1Btn4);
        binding.player1Btn5.setTag(Constants.player1Btn5);
        binding.player1Btn6.setTag(Constants.player1Btn6);
        binding.player2Btn1.setTag(Constants.player2Btn1);
        binding.player2Btn2.setTag(Constants.player2Btn2);
        binding.player2Btn3.setTag(Constants.player2Btn3);
        binding.player2Btn4.setTag(Constants.player2Btn4);
        binding.player2Btn5.setTag(Constants.player2Btn5);
        binding.player2Btn6.setTag(Constants.player2Btn6);
    }

    public void focusButton(ImageButton buton, int oyuncuNo) {
        if (siraNo == 1 && oyuncuNo == siraNo) {
            if (buton.getTag().toString().equals(selectedTag)) {
                buton.setBackgroundResource(R.drawable.oyuncu1_button);
                selectedDrawable = null;
                selectedTag = "";
            } else {
                buton.setBackgroundResource(R.drawable.focus_button);

                for (ImageButton button : player1Buttons) {
                    if (buton.getId() != button.getId()) {
                        button.setBackgroundResource(R.drawable.oyuncu1_button);
                    }
                }

                selectedDrawable = buton.getDrawable();
                selectedButton = buton;
                selectedTag = buton.getTag().toString();
            }
        } else if (siraNo == 2 && oyuncuNo == siraNo) {
            if (buton.getTag().toString().equals(selectedTag)) {
                buton.setBackgroundResource(R.drawable.oyuncu2_button);
                selectedDrawable = null;
                selectedTag = "";
            } else {
                buton.setBackgroundResource(R.drawable.focus_button);

                for (ImageButton button : player2Buttons) {
                    if (buton.getId() != button.getId()) {
                        button.setBackgroundResource(R.drawable.oyuncu2_button);
                    }
                }

                selectedDrawable = buton.getDrawable();
                selectedButton = buton;
                selectedTag = buton.getTag().toString();
            }
        }
    }

    public void updateDrawable(ImageButton buton) {
        if (selectedDrawable != null) {
            buton.setImageDrawable(selectedDrawable);
            buton.setTag(selectedTag);
            selectedTag = "";
            selectedDrawable = null;
            if (siraNo == 1) {
                player1Buttons.remove(selectedButton);
                for (ImageButton button : player1Buttons) {
                    if (buton.getId() != button.getId()) {
                        button.setClickable(true);
                    }
                }
                siraNo = 2;
            } else if (siraNo == 2) {
                player2Buttons.remove(selectedButton);
                for (ImageButton button : player2Buttons) {
                    if (buton.getId() != button.getId()) {
                        button.setClickable(true);
                    }
                }
                siraNo = 1;
            }
            selectedButton.setClickable(false);
            selectedButton.setVisibility(View.INVISIBLE);
            checkWinner();
            if ((player1Buttons.size() == 0 && player2Buttons.size() == 0)) {
                disableOtherClickableButtons();
                alertDialogResult();
            } else if (!((winner.equals(Constants.player1)) || winner.equals(Constants.player2))) {
                if (!isMovesPossible()) {
                    disableOtherClickableButtons();
                    alertDialogResult();
                }
            }
            changePlayer();
        }
    }

    public boolean checkDrawable(ImageButton button) {
        if (selectedDrawable != null) {
            if (button.getTag() == null) {
                if (movesPossible) {
                    updateDrawable(button);
                }
                return true;
            } else {
                switch (button.getTag().toString()) {
                    case Constants.player1Btn1: {
                        if (selectedTag.equals(Constants.player2Btn2)
                                || selectedTag.equals(Constants.player2Btn3)
                                || selectedTag.equals(Constants.player2Btn4)
                                || selectedTag.equals(Constants.player2Btn5)
                                || selectedTag.equals(Constants.player2Btn6)
                        ) {
                            if (movesPossible) {
                                updateDrawable(button);
                            }
                            return true;
                        } else {
                            return false;
                        }
                    }
                    case Constants.player1Btn2: {
                        if (selectedTag.equals(Constants.player2Btn3)
                                || selectedTag.equals(Constants.player2Btn4)
                                || selectedTag.equals(Constants.player2Btn5)
                                || selectedTag.equals(Constants.player2Btn6)
                        ) {
                            if (movesPossible) {
                                updateDrawable(button);
                            }
                            return true;
                        } else {
                            return false;
                        }
                    }
                    case Constants.player1Btn3: {
                        if (selectedTag.equals(Constants.player2Btn4)
                                || selectedTag.equals(Constants.player2Btn5)
                                || selectedTag.equals(Constants.player2Btn6)
                        ) {
                            if (movesPossible) {
                                updateDrawable(button);
                            }
                            return true;
                        } else {
                            return false;
                        }
                    }
                    case Constants.player1Btn4: {
                        if (selectedTag.equals(Constants.player2Btn5)
                                || selectedTag.equals(Constants.player2Btn6)
                        ) {
                            if (movesPossible) {
                                updateDrawable(button);
                            }
                            return true;
                        } else {
                            return false;
                        }
                    }
                    case Constants.player1Btn5: {
                        if (selectedTag.equals(Constants.player2Btn6)
                        ) {
                            if (movesPossible) {
                                updateDrawable(button);
                            }
                            return true;
                        } else {
                            return false;
                        }
                    }
                    case Constants.player1Btn6:
                    case Constants.player2Btn6: {
                        //en büyük kartın üzerina başka bir kart konamaz
                        return false;
                    }
                    case Constants.player2Btn1: {
                        if (selectedTag.equals(Constants.player1Btn2)
                                || selectedTag.equals(Constants.player1Btn3)
                                || selectedTag.equals(Constants.player1Btn4)
                                || selectedTag.equals(Constants.player1Btn5)
                                || selectedTag.equals(Constants.player1Btn6)
                        ) {
                            if (movesPossible) {
                                updateDrawable(button);
                            }
                            return true;
                        } else {
                            return false;
                        }
                    }
                    case Constants.player2Btn2: {
                        if (selectedTag.equals(Constants.player1Btn3)
                                || selectedTag.equals(Constants.player1Btn4)
                                || selectedTag.equals(Constants.player1Btn5)
                                || selectedTag.equals(Constants.player1Btn6)
                        ) {
                            if (movesPossible) {
                                updateDrawable(button);
                            }
                            return true;
                        } else {
                            return false;
                        }
                    }
                    case Constants.player2Btn3: {
                        if (selectedTag.equals(Constants.player1Btn4)
                                || selectedTag.equals(Constants.player1Btn5)
                                || selectedTag.equals(Constants.player1Btn6)
                        ) {
                            if (movesPossible) {
                                updateDrawable(button);
                            }
                            return true;
                        } else {
                            return false;
                        }
                    }
                    case Constants.player2Btn4: {
                        if (selectedTag.equals(Constants.player1Btn5)
                                || selectedTag.equals(Constants.player1Btn6)
                        ) {
                            if (movesPossible) {
                                updateDrawable(button);
                            }
                            return true;
                        } else {
                            return false;
                        }
                    }
                    case Constants.player2Btn5: {
                        if (selectedTag.equals(Constants.player1Btn6)
                        ) {
                            if (movesPossible) {
                                updateDrawable(button);
                            }
                            return true;
                        } else {
                            return false;
                        }
                    }
                    default:
                        return false;
                }
            }
        } else {
            return false;
        }
    }

    public void changePlayer() {
        if (siraNo == 1) {
            binding.llPlayer1.setBackgroundColor(Color.parseColor("#FFFAEF94"));
            binding.llPlayer2.setBackgroundColor(Color.TRANSPARENT);
        } else if (siraNo == 2) {
            binding.llPlayer1.setBackgroundColor(Color.TRANSPARENT);
            binding.llPlayer2.setBackgroundColor(Color.parseColor("#FFFAEF94"));
        }
    }

    private void compareTags(Object tag1, Object tag2, Object tag3) {
        if (tag1 != null && tag2 != null && tag3 != null) {
            if (splitTag(tag1.toString()).equals(splitTag(tag2.toString()))
                    && splitTag(tag1.toString()).equals(splitTag(tag3.toString()))
            ) {
                winner = splitTag(tag1.toString());
                disableOtherClickableButtons();
                alertDialogResult();
            }
        }
    }

    private String splitTag(String tag) {
        String[] x = tag.split("-");
        return x[0];
    }

    public void checkWinner() {
        compareTags(binding.btn11.getTag(), binding.btn12.getTag(), binding.btn13.getTag());
        compareTags(binding.btn21.getTag(), binding.btn22.getTag(), binding.btn23.getTag());
        compareTags(binding.btn31.getTag(), binding.btn32.getTag(), binding.btn33.getTag());
        compareTags(binding.btn11.getTag(), binding.btn21.getTag(), binding.btn31.getTag());
        compareTags(binding.btn12.getTag(), binding.btn22.getTag(), binding.btn32.getTag());
        compareTags(binding.btn13.getTag(), binding.btn23.getTag(), binding.btn33.getTag());
        compareTags(binding.btn11.getTag(), binding.btn22.getTag(), binding.btn33.getTag());
        compareTags(binding.btn13.getTag(), binding.btn22.getTag(), binding.btn31.getTag());
    }

    public void alertDialogResult() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Tebrikler!");
        if (winner.equals(Constants.player1)) {
            builder.setMessage("Yeşil renk kazandı.");
        } else if (winner.equals(Constants.player2)) {
            builder.setMessage("Kırmızı renk kazandı.");
        } else if (player1Buttons.size() == 0 && player2Buttons.size() == 0) {
            builder.setTitle("Beraberlik!");
        } else {
            builder.setTitle("Daha fazla hamle mümkün değil!");
        }
        builder.setCancelable(false);

        builder.setNeutralButton(("Oyunu Göster"), (dialog, which) ->
                new Handler(Looper.getMainLooper()).postDelayed(
                        this::alertDialogResult,
                        3000));

        builder.setNegativeButton("Tekrar Oyna", (dialog, which) -> {
            finish();
            startActivity(getIntent());
        });

        builder.show();
    }

    public boolean isMovesPossible() {
        //true -> hamle mümkün. false -> hamle mümkün değil
        movesPossible = false;
        switch (siraNo) {
            case 1: {
                return checkPossibleMoves(player1Buttons);
            }
            case 2: {
                return checkPossibleMoves(player2Buttons);
            }
            default: {
                movesPossible = true;
                return false;
            }
        }
    }

    private Boolean checkPossibleMoves(ArrayList<ImageButton> list) {
        int movesCount = 0;
        for (ImageButton button : list) {
            selectedDrawable = button.getDrawable();
            selectedTag = button.getTag().toString();
            for (ImageButton btn : gameAreaButtons) {
                boolean result = checkDrawable(btn);
                if (result) {
                    movesCount++;
                }
            }
        }
        selectedDrawable = null;
        movesPossible = true;
        selectedTag = "";
        return movesCount != 0;
    }

    public void disableOtherClickableButtons() {
        for (ImageButton button : player1Buttons) {
            button.setClickable(false);
        }
        for (ImageButton button : player2Buttons) {
            button.setClickable(false);
        }
        for (ImageButton button : gameAreaButtons) {
            button.setClickable(false);
        }
    }
}