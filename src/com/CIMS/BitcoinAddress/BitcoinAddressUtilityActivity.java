package com.CIMS.BitcoinAddress;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.CIMS.BitcoinAddress_Donate.R;
import com.google.bitcoin.core.ECKey;
import com.google.bitcoin.core.NetworkParameters;


public class BitcoinAddressUtilityActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button btnGenerate = (Button) findViewById(R.id.btnGenerate);
        
        btnGenerate.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				EditText txtPassphrase = (EditText) findViewById(R.id.txtPassphrase);
				String passphrase = txtPassphrase.getText().toString();
				if (passphrase.length() > 0) {
					try {
						TextView lblError = (TextView) findViewById(R.id.lblError);
						lblError.setText("");
						
						MessageDigest md = MessageDigest.getInstance("SHA-256");
						md.update(passphrase.getBytes("UTF-8"));
						byte[] digest = md.digest();
						StringBuilder sb = new StringBuilder();
						for (byte b : digest) {
							sb.append(String.format("%02X ", b));
						}
						
						NetworkParameters params = NetworkParameters.prodNet();
						
						TextView PrivKeyHex = (TextView) findViewById(R.id.PrivKeyHex);
						PrivKeyHex.setText(sb.toString());
						
						TextView PrivKeyWIF = (TextView) findViewById(R.id.PrivKeyWIF);
						ECKey key;
						BigInteger privkey = new BigInteger(1,digest);
						key = new ECKey(privkey);
						PrivKeyWIF.setText(key.getPrivateKeyEncoded(params).toString());
						
						/*TextView PubKeyHex = (TextView) findViewById(R.id.PubKeyHex);
						byte[] pubKey = key.getPubKey();
						StringBuilder sb2 = new StringBuilder();
						for (byte b : pubKey) {
							sb2.append(String.format("%02X ", b));
						}
						PubKeyHex.setText(sb2.toString());*/
						
						TextView addr = (TextView) findViewById(R.id.Address);
						addr.setText(key.toAddress(params).toString());
						
						
					} catch (NoSuchAlgorithmException e) {
						TextView lblError = (TextView) findViewById(R.id.lblError);
						TextView PrivKeyHex = (TextView) findViewById(R.id.PrivKeyHex);
						TextView PrivKeyWIF = (TextView) findViewById(R.id.PrivKeyWIF);
						TextView addr = (TextView) findViewById(R.id.Address);
						lblError.setText(e.getMessage());
						PrivKeyHex.setText("");
						PrivKeyWIF.setText("");
						addr.setText("");
					} catch (UnsupportedEncodingException e) {
						TextView lblError = (TextView) findViewById(R.id.lblError);
						TextView PrivKeyHex = (TextView) findViewById(R.id.PrivKeyHex);
						TextView PrivKeyWIF = (TextView) findViewById(R.id.PrivKeyWIF);
						TextView addr = (TextView) findViewById(R.id.Address);
						lblError.setText(e.getMessage());
						PrivKeyHex.setText("");
						PrivKeyWIF.setText("");
						addr.setText("");
					}
				} else {
					TextView lblError = (TextView) findViewById(R.id.lblError);
					TextView PrivKeyHex = (TextView) findViewById(R.id.PrivKeyHex);
					TextView PrivKeyWIF = (TextView) findViewById(R.id.PrivKeyWIF);
					TextView addr = (TextView) findViewById(R.id.Address);
					lblError.setText("Please enter a passphrase");
					PrivKeyHex.setText("");
					PrivKeyWIF.setText("");
					addr.setText("");
				}
			}
		});
        
    }
}