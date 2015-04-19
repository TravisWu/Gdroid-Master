package com.traviswu.gravitydroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * Created by traviswu on 2015-03-12.
 */
public class HelloMonkeyActivity extends Activity implements View.OnClickListener
{
    private MonkeyPhone phone;
    private EditText numberField;

    @Override
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.twilioprompt);

        phone = new MonkeyPhone(getApplicationContext());
        ImageButton dialButton =(ImageButton)findViewById(R.id.Sbutton);
        dialButton.setOnClickListener(this);

        numberField = (EditText)findViewById(R.id.numberField);
    }

    @Override

    public void onClick(View view)
    {

        if (view.getId() == R.id.Sbutton) {
            phone.connect(numberField.getText().toString());
            //phone.disconnect();

        }
    }

}
//ImageButton buttonMessage = (ImageButton)findViewById(R.id.twilio);
//buttonMessage.setOnClickListener(new View.OnClickListener(){
//public void onClick(View arg0) {
//        startActivity(new Intent(main.this, HelloMonkeyActivity.class));
//        }
//});
