package willzma.com.keller;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Nick on 2/20/2016.
 */
public class OCR {

    public String parseImage(File f) throws IOException {

       final CountDownLatch cdl = new CountDownLatch(1);

    class ocrThread implements Runnable {

        private File f = null;
        private String res = "No text found.";

        public ocrThread(File fi) {
            f = fi;
        }

        public String getResults() {
            return res;
        }

        public void run() {
            try {
                String url = "https://api.ocr.space/Parse/Image";
                HttpClient client = HttpClientBuilder.create().build();
                HttpPost post = new HttpPost(url);

                FileBody bin = new FileBody(f);

                MultipartEntity reqEntity = new MultipartEntity();
                reqEntity.addPart("file", bin);
                reqEntity.addPart("apikey", new StringBody("helloworld"));
                reqEntity.addPart("language", new StringBody("eng"));
                post.setEntity(reqEntity);

                HttpResponse response = client.execute(post);
                System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                StringBuffer result = new StringBuffer();
                String line = "";
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
                res =  result.toString();
                cdl.countDown();
            } catch (IOException e) {

            }
        }
    }

        ocrThread o = new ocrThread(f);
        Thread t = new Thread(o);
        t.start();
        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return o.getResults();


    }

}
