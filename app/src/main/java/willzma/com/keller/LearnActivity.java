package willzma.com.keller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;

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
        buttonsTouched = new boolean[3][2];

        vib = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        dots = new Button[3][2];

        boolean[][] enabledButtons = determineEnabledButtons(text.charAt(current));
        negateMatrix(enabledButtons, buttonsTouched);

        setContentView(R.layout.activity_learn);

        generateButtons(enabledButtons);
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
        vib.vibrate(200);
    }

    private void generateButtons(boolean[][] bigButts) {
        for (int i = 0; i < bigButts.length; i++) {
            for (int j = 0; j < bigButts[0].length; j++) {
                String buttonID = "button" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", "willzma.com.keller");

                if (bigButts[i][j]) {
                    dots[i][j] = (Button) findViewById(resID);
                    dots[i][j].setOnTouchListener(new View.OnTouchListener() {
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
                    });
                } else {
                    dots[i][j] = (Button) findViewById(resID);
                    dots[i][j].setBackgroundColor(Color.TRANSPARENT);
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
                toggleBoolean(temp, 1);
                toggleBoolean(temp, 2);
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 5);
                toggleBoolean(temp, 6);
            }break;case '\'': {
                toggleBoolean(temp, 5);
            }break;case '(': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 5);
                toggleBoolean(temp, 6);
            }break;case ')': {
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
                toggleBoolean(temp, 6);
            }break;case '*': {
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case ',': {
                toggleBoolean(temp, 3);
            }break;case '-': {
                toggleBoolean(temp, 5);
                toggleBoolean(temp, 6);
            }break;case '.': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 6);
            }break;case '/': {
                toggleBoolean(temp, 2);
                toggleBoolean(temp, 5);
            }break;case '0': {
                toggleBoolean(temp, 2);
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
            }break;case '1': {
                toggleBoolean(temp, 1);
            }break;case '2': {
                toggleBoolean(temp, 1);
                toggleBoolean(temp, 3);
            }break;case '3': {
                toggleBoolean(temp, 1);
                toggleBoolean(temp, 2);
            }break;case '4': {
                toggleBoolean(temp, 1);
                toggleBoolean(temp, 2);
                toggleBoolean(temp, 4);
            }break;case '5': {
                toggleBoolean(temp, 1);
                toggleBoolean(temp, 4);
            }break;case '6': {
                toggleBoolean(temp, 1);
                toggleBoolean(temp, 2);
                toggleBoolean(temp, 3);
            }break;case '7': {
                toggleBoolean(temp, 1);
                toggleBoolean(temp, 2);
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
            }break;case '8': {
                toggleBoolean(temp, 1);
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
            }break;case '9': {
                toggleBoolean(temp, 2);
                toggleBoolean(temp, 3);
            }break;case ':': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
            }break;case ';': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 5);
            }break;case '?': {
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 6);
            }break;case 'A': {
                toggleBoolean(temp, 1);
            }break;case 'B': {
                toggleBoolean(temp, 1);
                toggleBoolean(temp, 3);
            }break;case 'C': {
                toggleBoolean(temp, 1);
                toggleBoolean(temp, 2);
            }break;case 'D': {
                toggleBoolean(temp, 1);
                toggleBoolean(temp, 2);
                toggleBoolean(temp, 4);
            }break;case 'E': {
                toggleBoolean(temp, 1);
                toggleBoolean(temp, 4);
            }break;case 'F': {
                toggleBoolean(temp, 1);
                toggleBoolean(temp, 2);
                toggleBoolean(temp, 3);
            }break;case 'G': {
                toggleBoolean(temp, 1);
                toggleBoolean(temp, 2);
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
            }break;case 'H': {
                toggleBoolean(temp, 1);
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
            }break;case 'I': {
                toggleBoolean(temp, 2);
                toggleBoolean(temp, 3);
            }break;case 'J': {
                toggleBoolean(temp, 2);
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
            }break;case 'K': {
                toggleBoolean(temp, 1);
                toggleBoolean(temp, 5);
            }break;case 'L': {
                toggleBoolean(temp, 1);
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 5);
            }break;case 'M': {
                toggleBoolean(temp, 1);
                toggleBoolean(temp, 2);
                toggleBoolean(temp, 5);
            }break;case 'N': {
                toggleBoolean(temp, 1);
                toggleBoolean(temp, 2);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case 'O': {
                toggleBoolean(temp, 1);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case 'P': {
                toggleBoolean(temp, 1);
                toggleBoolean(temp, 2);
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 5);
            }break;case 'Q': {
                toggleBoolean(temp, 1);
                toggleBoolean(temp, 2);
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case 'R': {
                toggleBoolean(temp, 1);
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case 'S': {
                toggleBoolean(temp, 2);
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 5);
            }break;case 'T': {
                toggleBoolean(temp, 2);
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case 'U': {
                toggleBoolean(temp, 1);
                toggleBoolean(temp, 5);
                toggleBoolean(temp, 6);
            }break;case 'V': {
                toggleBoolean(temp, 1);
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 5);
                toggleBoolean(temp, 6);
            }break;case 'W': {
                toggleBoolean(temp, 2);
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 6);
            }break;case 'X': {
                toggleBoolean(temp, 1);
                toggleBoolean(temp, 2);
                toggleBoolean(temp, 5);
                toggleBoolean(temp, 6);
            }break;case 'Y': {
                toggleBoolean(temp, 1);
                toggleBoolean(temp, 2);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
                toggleBoolean(temp, 6);
            }break;case 'Z': {
                toggleBoolean(temp, 1);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
                toggleBoolean(temp, 6);
            }break;case 'a': {
                toggleBoolean(temp, 1);
            }break;case 'b': {
                toggleBoolean(temp, 1);
                toggleBoolean(temp, 3);
            }break;case 'c': {
                toggleBoolean(temp, 1);
                toggleBoolean(temp, 2);
            }break;case 'd': {
                toggleBoolean(temp, 1);
                toggleBoolean(temp, 2);
                toggleBoolean(temp, 4);
            }break;case 'e': {
                toggleBoolean(temp, 1);
                toggleBoolean(temp, 4);
            }break;case 'f': {
                toggleBoolean(temp, 1);
                toggleBoolean(temp, 2);
                toggleBoolean(temp, 3);
            }break;case 'g': {
                toggleBoolean(temp, 1);
                toggleBoolean(temp, 2);
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
            }break;case 'h': {
                toggleBoolean(temp, 1);
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
            }break;case 'i': {
                toggleBoolean(temp, 2);
                toggleBoolean(temp, 3);
            }break;case 'j': {
                toggleBoolean(temp, 2);
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
            }break;case 'k': {
                toggleBoolean(temp, 1);
                toggleBoolean(temp, 5);
            }break;case 'l': {
                toggleBoolean(temp, 1);
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 5);
            }break;case 'm': {
                toggleBoolean(temp, 1);
                toggleBoolean(temp, 2);
                toggleBoolean(temp, 5);
            }break;case 'n': {
                toggleBoolean(temp, 1);
                toggleBoolean(temp, 2);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case 'o': {
                toggleBoolean(temp, 1);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case 'p': {
                toggleBoolean(temp, 1);
                toggleBoolean(temp, 2);
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 5);
            }break;case 'q': {
                toggleBoolean(temp, 1);
                toggleBoolean(temp, 2);
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case 'r': {
                toggleBoolean(temp, 1);
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case 's': {
                toggleBoolean(temp, 2);
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 5);
            }break;case 't': {
                toggleBoolean(temp, 2);
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
            }break;case 'u': {
                toggleBoolean(temp, 1);
                toggleBoolean(temp, 5);
                toggleBoolean(temp, 6);
            }break;case 'v': {
                toggleBoolean(temp, 1);
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 5);
                toggleBoolean(temp, 6);
            }break;case 'w': {
                toggleBoolean(temp, 2);
                toggleBoolean(temp, 3);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 6);
            }break;case 'x': {
                toggleBoolean(temp, 1);
                toggleBoolean(temp, 2);
                toggleBoolean(temp, 5);
                toggleBoolean(temp, 6);
            }break;case 'y': {
                toggleBoolean(temp, 1);
                toggleBoolean(temp, 2);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
                toggleBoolean(temp, 6);
            }break;case 'z': {
                toggleBoolean(temp, 1);
                toggleBoolean(temp, 4);
                toggleBoolean(temp, 5);
                toggleBoolean(temp, 6);
            }break;case '~': {
                toggleBoolean(temp, 5);
            }break;
        }

        return temp;
    }

    private void toggleBoolean(boolean[][] b, int index) {
        int iterator = 1;
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
}
