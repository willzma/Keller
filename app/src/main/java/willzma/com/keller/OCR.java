package willzma.com.keller;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

public class OCR {

    public String parseImage(File f) {

        ITesseract instance;
        instance = new Tesseract();

        try {
            String result = instance.doOCR(f);
            return result;
        } catch (TesseractException e) {
            return "It broke";
        }
    }

}
