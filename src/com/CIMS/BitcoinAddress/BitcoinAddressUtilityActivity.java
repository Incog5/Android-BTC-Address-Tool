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
import com.CIMS.BitcoinAddress.R;
import com.google.bitcoin.core.ECKey;
import com.google.bitcoin.core.NetworkParameters;


public class BitcoinAddressUtilityActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
    	// Initialize View
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Set listener for the one and only button
        Button btnGenerate = (Button) findViewById(R.id.btnGenerate);
        btnGenerate.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				
				EditText txtPassphrase = (EditText) findViewById(R.id.txtPassphrase);
				String passphrase = txtPassphrase.getText().toString();
				if (passphrase.length() > 0) {
					try {
						TextView lblError = (TextView) findViewById(R.id.lblError);
						lblError.setText("");
						
						// Calculate SHA256(passphrase) and generate Hex(privkey)
						MessageDigest md = MessageDigest.getInstance("SHA-256");
						md.update(passphrase.getBytes("UTF-8"));
						byte[] digest = md.digest();
						StringBuilder sb = new StringBuilder();
						for (byte b : digest) {
							sb.append(String.format("%02X ", b));
						}
						
						TextView PrivKeyHex = (TextView) findViewById(R.id.PrivKeyHex);
						PrivKeyHex.setText(sb.toString());
						
						// Make sure we're set to Bitcoin prodNet
						NetworkParameters params = NetworkParameters.prodNet();
						
						// Generate keypair from privkey and display WIF formatted privkey
						TextView PrivKeyWIF = (TextView) findViewById(R.id.PrivKeyWIF);
						ECKey key;
						BigInteger privkey = new BigInteger(1,digest);
						key = new ECKey(privkey);
						PrivKeyWIF.setText(key.getPrivateKeyEncoded(params).toString());
						
						// Generate and display address
						TextView addr = (TextView) findViewById(R.id.Address);
						addr.setText(key.toAddress(params).toString());
						
						
					} catch (NoSuchAlgorithmException e) {
						// Error case: SHA-256 not supported by device
						TextView lblError = (TextView) findViewById(R.id.lblError);
						TextView PrivKeyHex = (TextView) findViewById(R.id.PrivKeyHex);
						TextView PrivKeyWIF = (TextView) findViewById(R.id.PrivKeyWIF);
						TextView addr = (TextView) findViewById(R.id.Address);
						lblError.setText(e.getMessage());
						PrivKeyHex.setText("");
						PrivKeyWIF.setText("");
						addr.setText("");
					} catch (UnsupportedEncodingException e) {
						// Error case: UTF-8 not supported by device
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
					// Error case: No password entered
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