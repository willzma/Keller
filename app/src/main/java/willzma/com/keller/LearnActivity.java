package willzma.com.keller;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LearnActivity extends AppCompatActivity {

    private Vibrator vib;

    private Button[][] dots;
    private boolean[][] buttonsTouched;

    private String braille;
    private String text;
    private int current;
    private int next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent in = getIntent();

        current = in.getIntExtra("current", 0);
        next = in.getIntExtra("next", 0);
        braille = in.getStringExtra("braille");
        text = in.getStringExtra("english");

        vib = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        dots = new Button[3][2];

        boolean[][] enabledButtons = determineEnabledButtons(text.charAt(current));
        negateMatrix(enabledButtons, buttonsTouched);

        generateButtons(enabledButtons);

        setContentView(R.layout.activity_learn);
    }

    private boolean readyToGo() {
        for (int i = 0; i < buttonsTouched.length; i++) {
            for (int j = 0; j < buttonsTouched[0].length; j++) {
                if (!buttonsTouched[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    private void vibrate(View v) {
        vib.vibrate(500);
    }

    private void generateButtons(boolean[][] bigButts) {
        for (int i = 0; i < bigButts.length; i++) {
            for (int j = 0; j < bigButts[0].length; j++) {
                String buttonID = "button" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", "willzma.com.keller");

                if (bigButts[i][j]) {
                    dots[i][j] = (Button) findViewById(resID);
                    dots[i][j].setOnTouchListener(enabledListener);
                } else {
                    dots[i][j] = (Button) findViewById(resID);
                    dots[i][j].setVisibility(View.GONE);
                }
            }
        }
    }

    private boolean[][] determineEnabledButtons(char brailleC) {
        boolean[][] temp = new boolean[3][2];

        switch(brailleC) {
            case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '\"': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
                toggleBoolean(temp, 6);
            }break;case '#': {
                toggleBoolean(temp, 2);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
                toggleBoolean(temp, 6);
            }break;case '&': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case '!': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;
        }

        return temp;
    }

    private void toggleBoolean(boolean[][] b, int index) {
        int iterator = 0;
        boolean done = false;

        for (int i = 0; i < b.length && !done; i++) {
            for (int j = 0; j < b[0].length && !done; j++) {
                if (index == iterator) {
                    b[i][j] = true;
                    done = true;
                }

                iterator++;
            }
        }
    }

    private void negateMatrix(boolean[][] b1, boolean[][] b2) {
        for (int i = 0; i < b1.length; i++) {
            for (int j = 0; j < b1[0].length; j++) {
                b2[i][j] = !b1[i][j];
            }
        }
    }

    private View.OnTouchListener enabledListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            vibrate(v);

            String s = getResources().getResourceName(v.getId());
            int i = Integer.parseInt(s.substring(s.length() - 2, s.length() - 1));
            int j = Integer.parseInt(s.substring(s.length() - 1, s.length()));

            buttonsTouched[i][j] = true;

            if (readyToGo()) {
                Intent myIntent = new Intent(LearnActivity.this, LearnActivity.class);

                myIntent.putExtra("braille", braille);
                myIntent.putExtra("english", text);
                myIntent.putExtra("current", braille.charAt(current + 1));
                myIntent.putExtra("next", braille.charAt(next + 1));
                startActivity(myIntent);
            }

            return true;
        }
    };

    /*private View.OnTouchListener disabledListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return false;
        }
    };*/
}
