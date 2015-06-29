package com.traviswu.gravitydroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Iterator;

public class main extends Activity {

    final Context context = this;
    private PointF touchDown;
    private Button facebookButton;
    private Button twitterButton;
    private Button mailButton;
    private Button callButton;

    private boolean facebookOn = false;
    private boolean twitterOn = false;
    private boolean mailOn = false;
    private boolean callOn = false;

    private String beamString;

    //initalize Parse User
    ParseUser currentUser = ParseUser.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        facebookButton = (Button) findViewById(R.id.buttonFb);
        twitterButton = (Button) findViewById(R.id.buttonTwitter);
        mailButton = (Button) findViewById(R.id.buttonMail);
        callButton = (Button) findViewById(R.id.buttonCall);

        //animation variables
        final Animation animTranslate;
        final Animation animTranslateDown;
        animTranslate = AnimationUtils.loadAnimation(this, R.anim.anim_translate);
        animTranslateDown = AnimationUtils.loadAnimation(this, R.anim.anim_translate_down);

        //set initial position of the buttons
        //setButtonPosition();
        final ArrayList<Button> buttonList = new ArrayList<Button>(){{
            add(facebookButton);
            add(twitterButton);
            add(mailButton);
            add(callButton);
        }};

        //animation listener
        Iterator<Button> iter = buttonList.iterator();

        Button buttonPlanet = (Button) findViewById(R.id.buttonPlanet);
        buttonPlanet.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        startActivityForResult(new Intent(main.this, CameraTestActivity.class), 1);
                        overridePendingTransition(R.layout.cameraanimation, R.layout.cameraanimation2);
                    }
                }
        );

        //Facebook button listener
        //animation listener
        while (iter.hasNext()){
            final Button tempButton = iter.next();

            tempButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick (View v){
                    if (tempButton == facebookButton){
                        if (facebookOn){
                            v.startAnimation(animTranslateDown);
                        }
                        else if (!facebookOn){
                            v.startAnimation(animTranslate);
                        }
                        facebookOn = !facebookOn;
                    }
                    else if (tempButton == twitterButton){
                        if (twitterOn){
                            v.startAnimation(animTranslateDown);
                        }
                        else if (!twitterOn){
                            v.startAnimation(animTranslate);
                        }
                        twitterOn = !twitterOn;
                    }
                    else if(tempButton == mailButton){
                        if (mailOn){
                            v.startAnimation(animTranslateDown);
                        }
                        else if (!mailOn){
                            v.startAnimation(animTranslate);
                        }
                        mailOn = !mailOn;
                    }
                    else if (tempButton == callButton){
                        if (callOn){
                            v.startAnimation(animTranslateDown);
                        }
                        else if (!callOn){
                            v.startAnimation(animTranslate);
                        }
                        callOn = !callOn;
                    }
                    setButtonPosition();
                }
            });
        }

        Button qrCode = (Button) findViewById(R.id.qrCode);

        /**
         * Note that this is where you have the prompt number onclick.
         * Now it has been overwritten by the onTouchListener below
         * Remember to move it to another button
         */
        qrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.twilioprompt, null);
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // set prompt.xml to alergdialog builder
                alertDialogBuilder.setView(promptsView);
                //set dialog message
                // final EditText userInput = (EditText) promptsView.findViewById(R.id.promptnumber);

                alertDialogBuilder.setPositiveButton("send", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //get user input and set it to results
                        //edit text
                        Toast toast = Toast.makeText(getApplicationContext(), "6137472234", Toast.LENGTH_SHORT);
                        toast.show();
                        //textSender(userInput.getText().toString());
                        startActivity(new Intent(main.this, HelloMonkeyActivity.class));
                    }
                }).create().show();
            }
        });

        /**
         * This part is where the program tries to handle qrCode.
         */
        qrCode.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        String qrData = "Data I want to encode in QR code";
                        int qrCodeDimention = 500;

                        QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(qrData, null, Contents.Type.TEXT, BarcodeFormat.QR_CODE.toString(), qrCodeDimention);
                        ImageView imageView = (ImageView) findViewById(R.id.imageQRCode);
                        try {
                            Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
                            imageView.setImageBitmap(bitmap);
                        } catch (WriterException e) {
                            e.printStackTrace();
                        }
                        return true;
                    case MotionEvent.ACTION_UP:
                        imageView = (ImageView) findViewById(R.id.imageQRCode);
                        imageView.setImageDrawable(null);
                        return true;
                }
                return false;
            }
        });

        ImageButton buttonMessage = (ImageButton) findViewById(R.id.twilio);
        buttonMessage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                startActivity(new Intent(main.this, HelloMonkeyActivity.class));
            }
        });
    }

    public void setButtonPosition(){
        //facebook button positioning
        if (facebookOn){
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)facebookButton.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,0);
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            facebookButton.setLayoutParams(params);
        }
        else{
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)facebookButton.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP,0);
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            facebookButton.setLayoutParams(params);
        }
        //twitter button positioning
        if (twitterOn){
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) twitterButton.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,0);
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            twitterButton.setLayoutParams(params);
        }
        else{
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)twitterButton.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP,0);
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            twitterButton.setLayoutParams(params);
        }
        //mail button positioning
        if (mailOn){
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mailButton.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,0);
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            mailButton.setLayoutParams(params);
        }
        else{
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mailButton.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP,0);
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            mailButton.setLayoutParams(params);
        }
        //call button positioning
        if (callOn){
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) callButton.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,0);
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            callButton.setLayoutParams(params);
        }
        else{
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)callButton.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_TOP,0);
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            callButton.setLayoutParams(params);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    /*
    private void setBeamString(){
        String temp = "";
        if (facebookOn){
            temp = temp + "facebook.";
        }
        if (twitterOn){
            temp = temp + "twitter.";
        }
        if (mailOn){
            temp = temp + "mail.";
        }
        if (callOn){
            temp = temp + "call.";
        }
        beamString = temp;
    }
    */

    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        if (requestCode == 1){
            if (resultCode == Activity.RESULT_OK){
                String result = intent.getStringExtra("result");
                Beam beam = new Beam() ;
                Log.d("TESTING", "THIS WORKS");
                //beam.setBeam(facebookOn, twitterOn, mailOn, callOn, currentUser.getObjectId());
                beam.put("facebook", facebookOn);
                beam.put("twitter", twitterOn);
                beam.put("mail", mailOn);
                beam.put("call", callOn);
                beam.put("senderId", currentUser.getObjectId());
                beam.saveInBackground(new SaveCallback(){
                    public void done(ParseException e){
                        if (e == null){
                            Toast toast = Toast.makeText(getApplicationContext(), "Beamed!", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        else{
                            Toast toast = Toast.makeText(getApplicationContext(), "failed  !", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                });
            }
        }
        /*
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            Toast toast = Toast.makeText(getApplicationContext(),
                    scanContent, Toast.LENGTH_SHORT);
            toast.show();
            //setBeamString();
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }*/
    }

    private class DragShadow extends View.DragShadowBuilder {
        ColorDrawable greybox;

        public DragShadow(View view) {

            super(view);
            greybox = new ColorDrawable(Color.LTGRAY);
        }

        @Override
        public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint) {
            View v = getView();
            int height = v.getHeight();
            int width = v.getWidth();

            greybox.setBounds(0, 0, width, height);
            shadowSize.set(width, height);
            shadowTouchPoint.set(width / 2, height / 2);
        }

        @Override
        public void onDrawShadow(Canvas canvas) {
            greybox.draw(canvas);
        }
    }
}

