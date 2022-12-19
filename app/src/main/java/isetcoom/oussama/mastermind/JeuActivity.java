package isetcoom.oussama.mastermind;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class JeuActivity extends AppCompatActivity {

    EditText userTry;
    Button btnConfirm, btnSurrender, btnRestart, btnInfo;
    TextView tvResult, tvResults;
    String strcodeAlea;
    int vache, codeAlea, taureau, tries = 0;
    LinearLayout mainlayout, fragmenttLayout, restartlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeu);
        userTry = this.findViewById(R.id.userTry);
        btnConfirm = this.findViewById(R.id.confirm);
        btnSurrender = this.findViewById(R.id.btnSurrender);
        btnRestart = this.findViewById(R.id.btnRestart);
        btnInfo = this.findViewById(R.id.btnInfo);
        tvResult = this.findViewById(R.id.tvResult);
        tvResults = this.findViewById(R.id.tvResults);
        mainlayout = (LinearLayout) this.findViewById(R.id.llgame);
        restartlayout = (LinearLayout) this.findViewById(R.id.llh);
        fragmenttLayout = (LinearLayout) this.findViewById(R.id.fragentLayout);


        codeAlea = genererCode();
        strcodeAlea = Integer.toString(codeAlea);

        btnConfirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                tries++;
                vache = 0;
                taureau = 0;
                String struserInput = userTry.getText().toString();
                int userInput = Integer.valueOf(struserInput);
                if (struserInput.equals(strcodeAlea)) {
                    tvResult.setText("The Correct answer : "+strcodeAlea + "\n u won with : " + tries + " tries");
                    win(view);
                    restartlayout.setVisibility(View.VISIBLE);
                }
                for (int i = 0; i < 4; i++) {
                    if (strcodeAlea.charAt(i) == struserInput.charAt(i)) {
                        taureau++;
                    } else if (struserInput.indexOf(strcodeAlea.charAt(i)) != -1) {
                        vache++;
                    }
                }
                tvResults.setText("Num :" + userInput + " : Vache=" + vache + ": Teaureau=" + taureau + "\n" + tvResults.getText());
                userTry.setText("");
            }
        });

        btnSurrender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvResult.setText("the Correct Answer is" + codeAlea);
                lose(view);
                mainlayout.setVisibility(View.GONE);
                restartlayout.setVisibility(View.VISIBLE);
                fragmenttLayout.setVisibility(View.VISIBLE);
            }
        });

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmenttLayout.setVisibility(View.GONE);
                mainlayout.setVisibility(View.VISIBLE);
                codeAlea = genererCode();
                strcodeAlea = Integer.toString(codeAlea);
                tvResults.setText("");
                restartlayout.setVisibility(View.GONE);
            }
        });
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.fragplace, InfoFragment.class, null).setReorderingAllowed(true).addToBackStack("name").commit();
            }
        });

    }

    protected int genererCode() {
        int c = 0;
        int k = 0;
        Random r = new Random();
        for (int i = 0; i < 4; i++) {
            do {
                k = r.nextInt(9) + 1;
            } while (k == c / 1000 || k == c / 100 % 10 || k == (c % 100) / 10 || k == c % 10);
            c += k * Math.pow(10, i);
        }
        return c;
    }

    public void win(View view) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fragplace, WinFragment.class, null).setReorderingAllowed(true).addToBackStack("name").commit();
    }

    public void lose(View view) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fragplace, LossFragment.class, null).setReorderingAllowed(true).addToBackStack("name").commit();
//        this.onCreate(Bundle savedInstanceState);
    }
}