package willzma.com.keller;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;

public class LearnActivity extends AppCompatActivity {

    private Vibrator vib;
    private LinearLayout lexLuthor;

    private Button[][] dots;
    private boolean[][] buttonsTouched;

    private String braille;
    private String text;
    private int current;
    //private int next;

    private Rect[][] rekt;
    private boolean[][] enabledButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Intent in = getIntent();

        current = in.getIntExtra("current", 0);
        //next = in.getIntExtra("next", 0);
        braille = in.getStringExtra("braille");
        text = in.getStringExtra("english");
        buttonsTouched = new boolean[3][2];
        rekt = new Rect[3][2];

        enabledButtons = determineEnabledButtons(braille.charAt(current));
        negateMatrix(enabledButtons, buttonsTouched);

        System.out.println(braille);

        vib = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        dots = new Button[3][2];

        setContentView(R.layout.activity_learn);

        lexLuthor = (LinearLayout) findViewById(R.id.lexLuthor);
    }

    @Override
    protected void onStart() {
        super.onStart();

        generateButtons(enabledButtons);

        lexLuthor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println(event.getAction());
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP: {
                        if (readyToGo()) {

                            if (current == (braille.length() - 1)) {
                                Intent myIntent = new Intent(LearnActivity.this, MyBrailleActivity.class);

                                System.out.println("should be doing this");

                                startActivity(myIntent);

                                finish();
                            } else {
                                Intent myIntent = new Intent(LearnActivity.this, LearnActivity.class);

                                System.out.println("should not be doing this");

                                myIntent.putExtra("braille", braille);
                                myIntent.putExtra("english", text);
                                myIntent.putExtra("current", current + 1);
                                //myIntent.putExtra("next", braille.charAt(next + 1));
                                startActivity(myIntent);

                                finish();
                            }
                        }
                    }break;
                    case MotionEvent.ACTION_DOWN: {
                        System.out.println("yes.");
                    }break;
                    case MotionEvent.ACTION_MOVE: {
                        //String s = getResources().getResourceName(v.getId());
                        //int i = Integer.parseInt(s.substring(s.length() - 2, s.length() - 1));
                        //int j = Integer.parseInt(s.substring(s.length() - 1, s.length()));

                        System.out.println("meme supreme");

                        System.out.println(event.getX() + " and " + event.getY());

                        for (int i = 0; i < rekt.length; i++) {
                            for (int j = 0; j < rekt[0].length; j++) {
                                System.out.println(rekt[i][j].toString());
                            }
                        }

                        if (rektContains(v.getLeft() + (int) event.getX(), v.getTop() + (int) event.getY())){

                            System.out.println("Coldstone memery");

                            vibrate(v);

                            int x = v.getLeft() + (int) event.getX();
                            int y = v.getTop() + (int) event.getY();

                            int[] temp = indices(x, y);

                            buttonsTouched[temp[0]][temp[1]] = true;
                        }
                    }
                }
                if(event.getAction() != MotionEvent.ACTION_DOWN) {
                    return false;
                } else {
                    return true;
                }

            }
        });
    }

    private boolean rektContains(int x, int y) {
        for (int i = 0; i < rekt.length; i++) {
            for (int j = 0; j < rekt[0].length; j++) {
                if (rekt[i][j].contains(x,  y)) {
                    System.out.println(x + " and " + y);
                    return true;
                }
            }
        }

        return false;
    }

    private int[] indices(int x, int y) {
        for (int i = 0; i < rekt.length; i++) {
            for (int j = 0; j < rekt[0].length; j++) {
                if (rekt[i][j].contains(x,  y)) {
                    int temp[] = {i, j};
                    return temp;
                }
            }
        }

        return null;
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
        vib.vibrate(100);
    }

    private void generateButtons(boolean[][] bigButts) {

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int width = size.x;
        int height = size.y;

        for (int i = 0; i < bigButts.length; i++) {
            for (int j = 0; j < bigButts[0].length; j++) {
                String buttonID = "button" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", "willzma.com.keller");

                if (bigButts[i][j]) {
                    dots[i][j] = (Button) findViewById(resID);

                    int centerX = (int)(((2 * j) + 1) * width * 0.25);
                    int centerY = (int)(((i + 1) * height * 0.25));
                    int radius = height / 15;

                    if (i == 0) {
                        rekt[i][j] = new Rect(centerX - radius, centerY - radius - (int)(0.09 * width),
                                centerX + radius, centerY + radius - (int)(0.07 * width));
                    } else if (i == 2) {
                        rekt[i][j] = new Rect(centerX - radius, centerY - radius + (int)(0.11 * width),
                                centerX + radius, centerY + radius + (int)(0.11 * width));
                    } else {
                        rekt[i][j] = new Rect(centerX - radius, centerY - radius + (int)(0.037 * width),
                                centerX + radius, centerY + radius);
                    }
                } else {
                    dots[i][j] = (Button) findViewById(resID);

                    rekt[i][j] = new Rect(0, 0, 0, 0);

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

    @Override
    public void onBackPressed() {
        Intent in = new Intent(LearnActivity.this, MyBrailleActivity.class);

        startActivity(in);

        finish();
    }
}
