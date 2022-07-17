package com.example.modernxox;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;

import com.example.modernxox.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    ArrayList<ImageButton> oyuncu1Butonlar;
    ArrayList<ImageButton> oyuncu2Butonlar;
    ArrayList<ImageButton> oyunAlaniButonlar;
    Drawable seciliDrawable;
    ImageButton seciliButton;
    int siraNo = 1;
    int kazananOyuncu;
    Handler handler;
    Runnable runnable;
    boolean drawableGuncelle = true; //drawableGuncelle = true -> drawable güncelle. drawableGuncelle = false hamle mümkün mü kontrol et

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        atamalar();

        for (ImageButton oyuncu1Butonlari : oyuncu1Butonlar) {
            oyuncu1Butonlari.setOnClickListener(view -> butonOdaklan(oyuncu1Butonlari, 1));
        }

        for (ImageButton oyuncu2Butonlari : oyuncu2Butonlar) {
            oyuncu2Butonlari.setOnClickListener(view -> butonOdaklan(oyuncu2Butonlari, 2));
        }

        for (ImageButton oyunAlaniButonlari : oyunAlaniButonlar) {
            oyunAlaniButonlari.setOnClickListener(view -> drawableKontrol(oyunAlaniButonlari));
        }
    }

    public void atamalar() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        oyuncu1Butonlar = new ArrayList<>();
        oyuncu2Butonlar = new ArrayList<>();
        oyunAlaniButonlar = new ArrayList<>();

        oyuncu1Butonlar.add(binding.oyuncu1Buton1);
        oyuncu1Butonlar.add(binding.oyuncu1Buton2);
        oyuncu1Butonlar.add(binding.oyuncu1Buton3);
        oyuncu1Butonlar.add(binding.oyuncu1Buton4);
        oyuncu1Butonlar.add(binding.oyuncu1Buton5);
        oyuncu1Butonlar.add(binding.oyuncu1Buton6);

        oyuncu2Butonlar.add(binding.oyuncu2Buton1);
        oyuncu2Butonlar.add(binding.oyuncu2Buton2);
        oyuncu2Butonlar.add(binding.oyuncu2Buton3);
        oyuncu2Butonlar.add(binding.oyuncu2Buton4);
        oyuncu2Butonlar.add(binding.oyuncu2Buton5);
        oyuncu2Butonlar.add(binding.oyuncu2Buton6);

        oyunAlaniButonlar.add(binding.buton11);
        oyunAlaniButonlar.add(binding.buton12);
        oyunAlaniButonlar.add(binding.buton13);
        oyunAlaniButonlar.add(binding.buton21);
        oyunAlaniButonlar.add(binding.buton22);
        oyunAlaniButonlar.add(binding.buton23);
        oyunAlaniButonlar.add(binding.buton31);
        oyunAlaniButonlar.add(binding.buton32);
        oyunAlaniButonlar.add(binding.buton33);
        if (siraNo == 1) {
            binding.oyuncu1LinearLayout.setBackgroundColor(Color.parseColor("#FFFAEF94"));
        } else if (siraNo == 2) {
            binding.oyuncu2LinearLayout.setBackgroundColor(Color.parseColor("#FFFAEF94"));
        }
    }

    public void butonOdaklan(ImageButton buton, int oyuncuNo) {
        if (siraNo == 1 && oyuncuNo == siraNo) {
            if (buton.getBackground().getConstantState().equals(getDrawable(R.drawable.focus_button).getConstantState())) {
                buton.setBackgroundResource(R.drawable.oyuncu1_button);
                for (ImageButton button : oyuncu1Butonlar) {
                    if (buton.getId() != button.getId()) {
                        button.setClickable(true);
                    }
                }
                seciliDrawable = null;
            } else {
                buton.setBackgroundResource(R.drawable.focus_button);

                for (ImageButton button : oyuncu1Butonlar) {
                    if (buton.getId() != button.getId()) {
                        button.setClickable(false);
                    }
                }

                seciliDrawable = buton.getDrawable();
                seciliButton = buton;
            }
        } else if (siraNo == 2 && oyuncuNo == siraNo) {
            if (buton.getBackground().getConstantState().equals(getDrawable(R.drawable.focus_button).getConstantState())) {

                buton.setBackgroundResource(R.drawable.oyuncu2_button);
                for (ImageButton button : oyuncu2Butonlar) {
                    if (buton.getId() != button.getId()) {
                        button.setClickable(true);
                    }
                }

                seciliDrawable = null;
            } else {
                buton.setBackgroundResource(R.drawable.focus_button);

                for (ImageButton button : oyuncu2Butonlar) {
                    if (buton.getId() != button.getId()) {
                        button.setClickable(false);
                    }
                }

                seciliDrawable = buton.getDrawable();
                seciliButton = buton;
            }
        }
    }

    public void drawableGuncelle(ImageButton buton) {
        if (seciliDrawable != null) {
            buton.setImageDrawable(seciliDrawable);
            buton.setTag(siraNo);
            seciliDrawable = null;
            if (siraNo == 1) {
                oyuncu1Butonlar.remove(seciliButton);
                for (ImageButton button : oyuncu1Butonlar) {
                    if (buton.getId() != button.getId()) {
                        button.setClickable(true);
                    }
                }
                siraNo = 2;
            } else if (siraNo == 2) {
                oyuncu2Butonlar.remove(seciliButton);
                for (ImageButton button : oyuncu2Butonlar) {
                    if (buton.getId() != button.getId()) {
                        button.setClickable(true);
                    }
                }
                siraNo = 1;
            }
            seciliButton.setClickable(false);
            seciliButton.setVisibility(View.INVISIBLE);
            kazananKontrol();
            if ((oyuncu1Butonlar.size() == 0 && oyuncu2Butonlar.size() == 0)) {
                kalan_butonlarin_clickable_kapat();
                uyari_ekrani_beraberlik();
            } else if (!(kazananOyuncu == 1 || kazananOyuncu == 2)) {
                if (!hamle_mumkun_mu()) {
                    kalan_butonlarin_clickable_kapat();
                    uyari_ekrani_beraberlik();
                }
            }
            siraDegis();
        }
    }

    public boolean drawableKontrol(ImageButton button) {
        if (seciliDrawable != null) {
            if (button.getDrawable().getConstantState().equals(getDrawable(R.drawable.empty_drawable).getConstantState())) {
                if (drawableGuncelle) {
                    drawableGuncelle(button);
                }
                return true;
            } else {
                Drawable.ConstantState state = button.getDrawable().getConstantState();
                if (getDrawable(R.drawable.ic_oyuncu1_1).getConstantState().equals(state)) {
                    if (seciliDrawable.getConstantState() == getDrawable(R.drawable.ic_oyuncu2_2).getConstantState()
                            || seciliDrawable.getConstantState() == getDrawable(R.drawable.ic_oyuncu2_3).getConstantState()
                            || seciliDrawable.getConstantState() == getDrawable(R.drawable.ic_oyuncu2_4).getConstantState()
                            || seciliDrawable.getConstantState() == getDrawable(R.drawable.ic_oyuncu2_5).getConstantState()
                            || seciliDrawable.getConstantState() == getDrawable(R.drawable.ic_oyuncu2_6).getConstantState()
                    ) {
                        if (drawableGuncelle) {
                            drawableGuncelle(button);
                        }
                        return true;
                    } else {
                        return false;
                    }
                } else if (getDrawable(R.drawable.ic_oyuncu1_2).getConstantState().equals(state)) {
                    if (seciliDrawable.getConstantState() == getDrawable(R.drawable.ic_oyuncu2_3).getConstantState()
                            || seciliDrawable.getConstantState() == getDrawable(R.drawable.ic_oyuncu2_4).getConstantState()
                            || seciliDrawable.getConstantState() == getDrawable(R.drawable.ic_oyuncu2_5).getConstantState()
                            || seciliDrawable.getConstantState() == getDrawable(R.drawable.ic_oyuncu2_6).getConstantState()
                    ) {
                        if (drawableGuncelle) {
                            drawableGuncelle(button);
                        }
                        return true;
                    } else {
                        return false;
                    }
                } else if (getDrawable(R.drawable.ic_oyuncu1_3).getConstantState().equals(state)) {
                    if (seciliDrawable.getConstantState() == getDrawable(R.drawable.ic_oyuncu2_4).getConstantState()
                            || seciliDrawable.getConstantState() == getDrawable(R.drawable.ic_oyuncu2_5).getConstantState()
                            || seciliDrawable.getConstantState() == getDrawable(R.drawable.ic_oyuncu2_6).getConstantState()
                    ) {
                        if (drawableGuncelle) {
                            drawableGuncelle(button);
                        }
                        return true;
                    } else {
                        return false;
                    }

                } else if (getDrawable(R.drawable.ic_oyuncu1_4).getConstantState().equals(state)) {
                    if (seciliDrawable.getConstantState() == getDrawable(R.drawable.ic_oyuncu2_5).getConstantState()
                            || seciliDrawable.getConstantState() == getDrawable(R.drawable.ic_oyuncu2_6).getConstantState()
                    ) {
                        if (drawableGuncelle) {
                            drawableGuncelle(button);
                        }
                        return true;
                    } else {
                        return false;
                    }
                } else if (getDrawable(R.drawable.ic_oyuncu1_5).getConstantState().equals(state)) {
                    if (seciliDrawable.getConstantState() == getDrawable(R.drawable.ic_oyuncu2_6).getConstantState()
                    ) {
                        if (drawableGuncelle) {
                            drawableGuncelle(button);
                        }
                        return true;
                    } else {
                        return false;
                    }
                } else if (getDrawable(R.drawable.ic_oyuncu1_6).getConstantState().equals(state)) {
                    //en büyük kartın üzerina başka bir kart konamaz
                    return false;
                } else if (getDrawable(R.drawable.ic_oyuncu2_1).getConstantState().equals(state)) {
                    if (seciliDrawable.getConstantState() == getDrawable(R.drawable.ic_oyuncu1_2).getConstantState()
                            || seciliDrawable.getConstantState() == getDrawable(R.drawable.ic_oyuncu1_3).getConstantState()
                            || seciliDrawable.getConstantState() == getDrawable(R.drawable.ic_oyuncu1_4).getConstantState()
                            || seciliDrawable.getConstantState() == getDrawable(R.drawable.ic_oyuncu1_5).getConstantState()
                            || seciliDrawable.getConstantState() == getDrawable(R.drawable.ic_oyuncu1_6).getConstantState()
                    ) {
                        if (drawableGuncelle) {
                            drawableGuncelle(button);
                        }
                        return true;
                    } else {
                        return false;
                    }
                } else if (getDrawable(R.drawable.ic_oyuncu2_2).getConstantState().equals(state)) {
                    if (seciliDrawable.getConstantState() == getDrawable(R.drawable.ic_oyuncu1_3).getConstantState()
                            || seciliDrawable.getConstantState() == getDrawable(R.drawable.ic_oyuncu1_4).getConstantState()
                            || seciliDrawable.getConstantState() == getDrawable(R.drawable.ic_oyuncu1_5).getConstantState()
                            || seciliDrawable.getConstantState() == getDrawable(R.drawable.ic_oyuncu1_6).getConstantState()
                    ) {
                        if (drawableGuncelle) {
                            drawableGuncelle(button);
                        }
                        return true;
                    } else {
                        return false;
                    }
                } else if (getDrawable(R.drawable.ic_oyuncu2_3).getConstantState().equals(state)) {
                    if (seciliDrawable.getConstantState() == getDrawable(R.drawable.ic_oyuncu1_4).getConstantState()
                            || seciliDrawable.getConstantState() == getDrawable(R.drawable.ic_oyuncu1_5).getConstantState()
                            || seciliDrawable.getConstantState() == getDrawable(R.drawable.ic_oyuncu1_6).getConstantState()
                    ) {
                        if (drawableGuncelle) {
                            drawableGuncelle(button);
                        }
                        return true;
                    } else {
                        return false;
                    }

                } else if (getDrawable(R.drawable.ic_oyuncu2_4).getConstantState().equals(state)) {
                    if (seciliDrawable.getConstantState() == getDrawable(R.drawable.ic_oyuncu1_5).getConstantState()
                            || seciliDrawable.getConstantState() == getDrawable(R.drawable.ic_oyuncu1_6).getConstantState()
                    ) {
                        if (drawableGuncelle) {
                            drawableGuncelle(button);
                        }
                        return true;
                    } else {
                        return false;
                    }
                } else if (getDrawable(R.drawable.ic_oyuncu2_5).getConstantState().equals(state)) {
                    if (seciliDrawable.getConstantState() == getDrawable(R.drawable.ic_oyuncu1_6).getConstantState()
                    ) {
                        if (drawableGuncelle) {
                            drawableGuncelle(button);
                        }
                        return true;
                    } else {
                        return false;
                    }
                } else if (getDrawable(R.drawable.ic_oyuncu2_6).getConstantState().equals(state)) {
                    //en büyük kartın üzerina başka bir kart konamaz
                    return false;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    public void siraDegis() {
        if (siraNo == 1) {
            binding.oyuncu1LinearLayout.setBackgroundColor(Color.parseColor("#FFFAEF94"));
            binding.oyuncu2LinearLayout.setBackgroundColor(Color.TRANSPARENT);
        } else if (siraNo == 2) {
            binding.oyuncu1LinearLayout.setBackgroundColor(Color.TRANSPARENT);
            binding.oyuncu2LinearLayout.setBackgroundColor(Color.parseColor("#FFFAEF94"));
        }
    }

    public void kazananKontrol() {
        if (binding.buton11.getTag() != null && binding.buton12.getTag() != null && binding.buton13.getTag() != null) {
            if (binding.buton11.getTag().toString().equals(binding.buton12.getTag().toString())
                    && binding.buton11.getTag().toString().equals(binding.buton13.getTag().toString())) {
                kazananOyuncu = Integer.parseInt(binding.buton11.getTag().toString());
                kalan_butonlarin_clickable_kapat();
                uyari_ekrani_kazanan();
            }
        }
        if (binding.buton21.getTag() != null && binding.buton22.getTag() != null && binding.buton23.getTag() != null) {
            if (binding.buton21.getTag().toString().equals(binding.buton22.getTag().toString())
                    && binding.buton21.getTag().toString().equals(binding.buton23.getTag().toString())) {
                kazananOyuncu = Integer.parseInt(binding.buton21.getTag().toString());
                kalan_butonlarin_clickable_kapat();
                uyari_ekrani_kazanan();
            }
        }
        if (binding.buton31.getTag() != null && binding.buton32.getTag() != null && binding.buton33.getTag() != null) {
            if (binding.buton31.getTag().toString().equals(binding.buton32.getTag().toString())
                    && binding.buton31.getTag().toString().equals(binding.buton33.getTag().toString())) {
                kazananOyuncu = Integer.parseInt(binding.buton21.getTag().toString());
                kalan_butonlarin_clickable_kapat();
                uyari_ekrani_kazanan();
            }
        }
        if (binding.buton11.getTag() != null && binding.buton21.getTag() != null && binding.buton31.getTag() != null) {
            if (binding.buton11.getTag().toString().equals(binding.buton21.getTag().toString())
                    && binding.buton11.getTag().toString().equals(binding.buton31.getTag().toString())) {
                kazananOyuncu = Integer.parseInt(binding.buton11.getTag().toString());
                kalan_butonlarin_clickable_kapat();
                uyari_ekrani_kazanan();
            }
        }
        if (binding.buton12.getTag() != null && binding.buton22.getTag() != null && binding.buton32.getTag() != null) {
            if (binding.buton12.getTag().toString().equals(binding.buton22.getTag().toString())
                    && binding.buton12.getTag().toString().equals(binding.buton32.getTag().toString())) {
                kazananOyuncu = Integer.parseInt(binding.buton12.getTag().toString());
                kalan_butonlarin_clickable_kapat();
                uyari_ekrani_kazanan();
            }
        }
        if (binding.buton13.getTag() != null && binding.buton23.getTag() != null && binding.buton33.getTag() != null) {
            if (binding.buton13.getTag().toString().equals(binding.buton23.getTag().toString())
                    && binding.buton13.getTag().toString().equals(binding.buton33.getTag().toString())) {
                kazananOyuncu = Integer.parseInt(binding.buton13.getTag().toString());
                kalan_butonlarin_clickable_kapat();
                uyari_ekrani_kazanan();
            }
        }
        if (binding.buton11.getTag() != null && binding.buton22.getTag() != null && binding.buton33.getTag() != null) {
            if (binding.buton11.getTag().toString().equals(binding.buton22.getTag().toString())
                    && binding.buton11.getTag().toString().equals(binding.buton33.getTag().toString())) {
                kazananOyuncu = Integer.parseInt(binding.buton11.getTag().toString());
                kalan_butonlarin_clickable_kapat();
                uyari_ekrani_kazanan();
            }
        }
        if (binding.buton13.getTag() != null && binding.buton22.getTag() != null && binding.buton31.getTag() != null) {
            if (binding.buton13.getTag().toString().equals(binding.buton22.getTag().toString())
                    && binding.buton13.getTag().toString().equals(binding.buton31.getTag().toString())) {
                kazananOyuncu = Integer.parseInt(binding.buton13.getTag().toString());
                kalan_butonlarin_clickable_kapat();
                uyari_ekrani_kazanan();
            }
        }
    }

    public void uyari_ekrani_kazanan() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Tebrikler!");
        if (kazananOyuncu == 1) {
            builder.setMessage("Yeşil renk kazandı.");
        } else if (kazananOyuncu == 2) {
            builder.setMessage("Kırmızı renk kazandı.");
        }
        builder.setCancelable(false);

        builder.setNeutralButton(("Oyunu Göster"), (dialog, which) -> alert_dialog_tekrar_ac());

        builder.setNegativeButton("Tekrar Oyna", (dialog, which) -> {
            finish();
            startActivity(getIntent());
        });

        builder.show();
    }

    public void uyari_ekrani_beraberlik() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        if (oyuncu1Butonlar.size() == 0 && oyuncu2Butonlar.size() == 0) {
            builder.setTitle("Beraberlik!");
        } else {
            builder.setTitle("Daha fazla hamle mümkün değil!");
        }

        builder.setCancelable(false);

        builder.setNeutralButton(("Oyunu Göster"), (dialog, which) -> alert_dialog_tekrar_ac());

        builder.setNegativeButton("Tekrar Oyna", (dialog, which) -> {
            finish();
            startActivity(getIntent());
        });
        builder.show();
    }

    public boolean hamle_mumkun_mu() {
        //true -> hamle mümkün. false -> hamle mümkün değil
        drawableGuncelle = false;
        if (siraNo == 1) {
            int basariliSayisi = 0;
            for (ImageButton button : oyuncu1Butonlar) {
                seciliDrawable = button.getDrawable();
                for (ImageButton oyunAlaniButon : oyunAlaniButonlar) {
                    boolean sonuc = drawableKontrol(oyunAlaniButon);
                    if (sonuc) {
                        basariliSayisi++;
                    }
                }
            }
            seciliDrawable = null;
            drawableGuncelle = true;
            return basariliSayisi != 0;
        } else if (siraNo == 2) {
            int basariliSayisi = 0;
            for (ImageButton button : oyuncu2Butonlar) {
                seciliDrawable = button.getDrawable();
                for (ImageButton oyunAlaniButon : oyunAlaniButonlar) {
                    boolean sonuc = drawableKontrol(oyunAlaniButon);
                    if (sonuc) {
                        basariliSayisi++;
                    }
                }
            }
            seciliDrawable = null;
            drawableGuncelle = true;
            return basariliSayisi != 0;
        } else {
            drawableGuncelle = true;
            return false;
        }
    }

    public void alert_dialog_tekrar_ac() {
        handler = new Handler();
        runnable = () -> {
            if (kazananOyuncu == 1 || kazananOyuncu == 2) {
                uyari_ekrani_kazanan();
            } else {
                uyari_ekrani_beraberlik();
            }
        };
        handler.postDelayed(runnable, 3000);
    }

    public void kalan_butonlarin_clickable_kapat() {
        for (ImageButton button : oyuncu1Butonlar) {
            button.setClickable(false);
        }
        for (ImageButton button : oyuncu2Butonlar) {
            button.setClickable(false);
        }
        binding.buton11.setClickable(false);
        binding.buton12.setClickable(false);
        binding.buton13.setClickable(false);
        binding.buton21.setClickable(false);
        binding.buton22.setClickable(false);
        binding.buton23.setClickable(false);
        binding.buton31.setClickable(false);
        binding.buton32.setClickable(false);
        binding.buton33.setClickable(false);
    }
}