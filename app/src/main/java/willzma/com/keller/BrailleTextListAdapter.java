package willzma.com.keller;

/**
 * Created by ChaityaShah on 2/20/16.
 */

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Query;

public class BrailleTextListAdapter extends FirebaseListAdapter<BrailleText> {

    // The mUsername for this client. We use this to indicate which messages originated from this user

    public BrailleTextListAdapter(Query ref, Activity activity, int layout) {
        super(ref, BrailleText.class, layout, activity);
    }

    /**
     * Bind an instance of the <code>BrailleText</code> class to our view. This method is called by <code>FirebaseListAdapter</code>
     * when there is a data change, and we are given an instance of a View that corresponds to the layout that we passed
     * to the constructor, as well as a single <code>BrailleText</code> instance that represents the current data to bind.
     *
     * @param view A view instance corresponding to the layout we passed to the constructor.
     * @param brailleText An instance representing the current state of a BrailleText message
     */
    @Override
    protected void populateView(View view, BrailleText brailleText) {
        // Map a BrailleText object to an entry in our listview
        Typeface typeface=Typeface.createFromAsset(view.getContext().getAssets(),"braille.ttf");
        ((TextView) view.findViewById(R.id.english)).setText(brailleText.getEnglish());
        ((TextView) view.findViewById(R.id.ascii)).setTypeface(typeface);
        ((TextView) view.findViewById(R.id.ascii)).setText(fullBraille(brailleText.getEnglish()));
    }
    public String fullBraille(String english) {
        boolean isNum = false;
        String result = "";
        for(int i = 0; i < english.length(); i++) {
            if(Character.isDigit(english.charAt(i))) {
                if(!isNum) {
                    isNum = true;
                    result += "# "+english.charAt(i) + " ";
                } else {
                    result+=english.charAt(i)+ " ";
                }
            } else {
                if(isNum) {
                    isNum = false;
                    if(english.charAt(i) == ' ') {
                        result+= "; "+english.charAt(i) + " ";
                    } else {
                        result+= "; "+english.charAt(i) + " ";
                    }
                } else {
                    result+=english.charAt(i);
                }
            }
        }
        return result;
    }
}
