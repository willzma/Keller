package willzma.com.keller;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    private SurfaceView preview;
    private SurfaceHolder previewHolder;

    @SuppressWarnings("required")
    private Camera camera;
    private boolean inPreview;
    private boolean cameraConfigured;

    private int TAKE_PHOTO_CODE;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        preview = (SurfaceView) findViewById(R.id.surfaceView);
        previewHolder = preview.getHolder();
        previewHolder.addCallback(surfaceCallback);
        previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        try {
            File newdir = new File(new URI(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Iris").getPath());
            newdir.mkdirs();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        Button takePicture = (Button) findViewById(R.id.takePicture);
        takePicture.setOnClickListener(new View.OnClickListener() {


            private Camera.PictureCallback getJpegCallback() {
                Camera.PictureCallback jpeg = new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] data, Camera camera) {
                        FileOutputStream fos;

                        byte[] data2 = data.clone();

                        Bitmap bmp = BitmapFactory.decodeByteArray(data2, 0, data2.length);

                        Matrix matrix = new Matrix();
                        matrix.postRotate(90);

                        Bitmap bmp2 = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);

                        ByteArrayOutputStream stm = new ByteArrayOutputStream();
                        bmp2.compress(Bitmap.CompressFormat.JPEG, 50, stm);

                        byte[] byteMe = stm.toByteArray();

                        try {
                            File imageFile = new File(new URI(Environment.getExternalStorageDirectory().toString() + "/Pictures/Iris/" + "Iris" + count + ".jpg").getPath());
                            fos = new FileOutputStream(imageFile);

                            fos.write(byteMe);
                            fos.close();
                            mediaScan(imageFile);
                            System.out.println("FILE SAVED!!! GOGOGOGO");
                            File f = new File(new URI(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                                    .getAbsolutePath() + "/Iris/Iris" + (count) + ".jpg").getPath());

                        } catch (IOException e) {
                            //do something about it
                        } catch (URISyntaxException e1) {

                        }
                    }
                };
                return jpeg;
            }

            public void onClick(View v) {

                try {
                    camera.takePicture(null, null, getJpegCallback());

                } catch (Exception e) {
                    e.printStackTrace();
                }

                count++;


            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        camera=Camera.open();

        Camera.Parameters params = camera.getParameters();
        params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        camera.setParameters(params);

        camera.setDisplayOrientation(90);
        //startPreview();
    }

    @Override
    public void onPause() {
        if (inPreview) {
            camera.stopPreview();
        }

        camera.release();
        camera=null;
        inPreview=false;

        super.onPause();
    }

    SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {
        public void surfaceCreated(SurfaceHolder holder) {
            // no-op -- wait until surfaceChanged()
        }

        public void surfaceChanged(SurfaceHolder holder,
                                   int format, int width,
                                   int height) {
            if (!inPreview) {
                initPreview(width, height);
                startPreview();
            }
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
            // no-op
        }
    };

    private void initPreview(int width, int height) {
        if (camera!=null && previewHolder.getSurface()!=null) {
            try {
                camera.setPreviewDisplay(previewHolder);
            }
            catch (Throwable t) {
                Log.e("PreviewDemo-surfaceCall",
                        "Exception in setPreviewDisplay()", t);
                Toast
                        .makeText(this, t.getMessage(), Toast.LENGTH_LONG)
                        .show();
            }

            if (!cameraConfigured) {
                Camera.Parameters parameters = camera.getParameters();
                Camera.Size size = getBestPreviewSize(width, height,
                        parameters);

                if (size!=null) {
                    parameters.setPreviewSize(size.width, size.height);
                    camera.setParameters(parameters);
                    cameraConfigured=true;
                }
            }
        }
    }

    private void startPreview() {
        if (cameraConfigured && camera != null) {
            camera.startPreview();
            inPreview=true;
        }
    }

    private Camera.Size getBestPreviewSize(int width, int height,
                                           Camera.Parameters parameters) {
        Camera.Size result=null;

        for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
            if (size.width<=width && size.height<=height) {
                if (result==null) {
                    result=size;
                } else {
                    int resultArea=result.width*result.height;
                    int newArea=size.width*size.height;

                    if (newArea>resultArea) {
                        result=size;
                    }
                }
            }
        }

        return(result);
    }

    private static File getOutputMediaFile() {
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "Iris");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("Iris", "failed to create directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "IMG_" + timeStamp + ".jpg");

        return mediaFile;
    }

    public void mediaScan(File file) {
        MediaScannerConnection.scanFile(this,
                new String[]{file.toString()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
