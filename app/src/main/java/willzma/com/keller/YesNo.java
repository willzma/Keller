package willzma.com.keller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class YesNo extends AppCompatActivity {
    final Context CONTEXT = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final boolean success = getIntent().getBooleanExtra("success", false);
        final String msg = getIntent().getStringExtra("msg");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yes_no);
        (findViewById(R.id.buttonYes)).setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
                new TTS(CONTEXT, false).execute("YES");
                return true;
            }
        });


        (findViewById(R.id.buttonYes)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (success) {
                    new TTS(CONTEXT, false).execute("Chaitya get your ass over here");
                } else {
                    new TTS(CONTEXT, false).execute("Camera.");
                    Intent myIntent = new Intent(YesNo.this,
                            CameraActivity.class);
                    startActivity(myIntent);
                    finish();
                }
            }
        });


        (findViewById(R.id.buttonNo)).setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
                new TTS(CONTEXT, false).execute("NO");
                return true;
            }
        });

        (findViewById(R.id.buttonNo)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                    new TTS(CONTEXT, false).execute("Main menu.");
                Intent myIntent = new Intent(YesNo.this,
                        MainActivity.class);
                startActivity(myIntent);
                finish();
            }
        });
    }
}
