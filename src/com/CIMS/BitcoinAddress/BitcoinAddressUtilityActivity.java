package com.CIMS.BitcoinAddress;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.bitcoin.core.ECKey;
import com.google.bitcoin.core.NetworkParameters;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;


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
						
						EditText PrivKeyHex = (EditText) findViewById(R.id.PrivKeyHex);
						PrivKeyHex.setText(sb.toString());
						
						// Make sure we're set to Bitcoin prodNet
						NetworkParameters params = NetworkParameters.prodNet();
						
						// Generate keypair from privkey and display WIF formatted privkey
						EditText PrivKeyWIF = (EditText) findViewById(R.id.PrivKeyWIF);
						ECKey key;
						BigInteger privkey = new BigInteger(1,digest);
						key = new ECKey(privkey);
						PrivKeyWIF.setText(key.getPrivateKeyEncoded(params).toString());
						
						// Generate and display address
						EditText addr = (EditText) findViewById(R.id.Address);
						addr.setText(key.toAddress(params).toString());
						
						// Address QR code
						ImageView imgAddressQR = (ImageView) findViewById(R.id.imgAddressQR);
						Charset charset = Charset.forName("ISO-8859-1");
						CharsetEncoder encoder = charset.newEncoder();
						byte[] b = null;
						try {
							ByteBuffer bbuf = encoder.encode(CharBuffer.wrap(key.toAddress(params).toString()));
							b = bbuf.array();
						} catch (CharacterCodingException e) {
							TextView lblError1 = (TextView) findViewById(R.id.lblError);
						}
						
						String data = null;
						try {
							data = new String(b, "ISO-8859-1");
						} catch (UnsupportedEncodingException e) {
							TextView lblError1 = (TextView) findViewById(R.id.lblError);
						}
						
						BitMatrix matrix = null;
						int h = 80;
						int w = 80;
						com.google.zxing.Writer writer = new QRCodeWriter();
						try {
							matrix = writer.encode(data, com.google.zxing.BarcodeFormat.QR_CODE, w, h);
						} catch (com.google.zxing.WriterException e) {
							TextView lblError1 = (TextView) findViewById(R.id.lblError);
						}
						
						Bitmap mBitmap = Bitmap.createBitmap(h, w, Config.ARGB_8888);
						for (int i=0;i<w;i++) {
							for (int j=0;j<h;j++) {
								mBitmap.setPixel(i, j, matrix.get(i, j)?Color.BLACK:Color.WHITE);
							}
						}
						
						imgAddressQR.setImageBitmap(mBitmap);
						
						
						// Private Key (WIF) QR code
						ImageView imgPKWIFQR = (ImageView) findViewById(R.id.imgPKWIFQR);
						b = null;
						try {
							ByteBuffer bbuf = encoder.encode(CharBuffer.wrap(PrivKeyWIF.getText().toString()));
							b = bbuf.array();
						} catch (CharacterCodingException e) {
							TextView lblError1 = (TextView) findViewById(R.id.lblError);
						}
						
						data = null;
						try {
							data = new String(b, "ISO-8859-1");
						} catch (UnsupportedEncodingException e) {
							TextView lblError1 = (TextView) findViewById(R.id.lblError);
						}
						
						matrix = null;
						h = 80;
						w = 80;
						// com.google.zxing.Writer writer = new QRCodeWriter();
						try {
							matrix = writer.encode(data, com.google.zxing.BarcodeFormat.QR_CODE, w, h);
						} catch (com.google.zxing.WriterException e) {
							TextView lblError1 = (TextView) findViewById(R.id.lblError);
						}
						
						mBitmap = Bitmap.createBitmap(h, w, Config.ARGB_8888);
						for (int i=0;i<w;i++) {
							for (int j=0;j<h;j++) {
								mBitmap.setPixel(i, j, matrix.get(i, j)?Color.BLACK:Color.WHITE);
							}
						}
						
						imgPKWIFQR.setImageBitmap(mBitmap);
						
						
						
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