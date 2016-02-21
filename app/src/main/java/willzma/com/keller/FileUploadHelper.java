package willzma.com.keller;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.concurrent.CountDownLatch;

public class FileUploadHelper extends AsyncTask<Void, Void, Void> {

    private CountDownLatch cdl = null;
    private MultipartEntityBuilder multipartEntity;
    private String URL;
    private File f;
    private String URL2 = "";

    public FileUploadHelper(String URL, File f, CountDownLatch cdl) {
        multipartEntity = MultipartEntityBuilder.create();
        this.URL = URL;
        this.f = f;
        this.cdl = cdl;
    }

    public String getRes() {
        return URL2;
    }

    @SuppressLint("TrulyRandom")
    @Override
    protected Void doInBackground(Void... arg0) {
        try {

            HttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(URL);

            // add header

            HttpEntity entity = MultipartEntityBuilder
                    .create()
                    //.addBinaryBody("file", f)
                    //.addTextBody("apikey","helloworld")
                    //.addTextBody("language","eng")
                    .addBinaryBody("upload", f)
                    .addTextBody("img_width","2600px")

            .build();

            post.setEntity(entity);


            HttpResponse response = client.execute(post);
            System.out.println("Response Code : "
                    + response.getStatusLine().getStatusCode());

            BufferedReader rd = new BufferedReader(
            new InputStreamReader(response.getEntity().getContent()));
            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            String out = result.toString();
            out = out.substring(out.indexOf("thumb_url") + 10);

                int count = 0;
                boolean fq = false;
                char[] charray = out.toCharArray();
                while (!fq) {
                    if (charray[count] == '\"') {
                        fq = true;
                    } else {
                        count++;
                    }
                }
                out = out.substring(count + 1);

            count = 0;
            fq = false;
            charray = out.toCharArray();
            while (!fq) {
                if (charray[count] == '\"') {
                    fq = true;
                } else {
                    count++;
                }
            }
            out = out.substring(0,count);
            out = out.replace("\\","");
            URL2 = out;

        } catch (Exception e) {
            e.printStackTrace();
        }
        cdl.countDown();
        return null;
    }

}