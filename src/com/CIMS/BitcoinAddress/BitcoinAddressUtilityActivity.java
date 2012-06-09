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
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.CIMS.BitcoinAddress.R;
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
        
        // Set listener for Address QR button
        ImageView imgAddress = (ImageView) findViewById(R.id.imgAddressQR);
        imgAddress.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				
				final FrameLayout mainFrame = (FrameLayout)findViewById(R.id.MainFrame);
				final ImageView overlay = new ImageView(BitcoinAddressUtilityActivity.this);
				
				Display display = getWindowManager().getDefaultDisplay();
				final Point point = new Point();
			    try {
			        display.getSize(point);
			    } catch (java.lang.NoSuchMethodError ignore) { // Older device
			        point.x = display.getWidth();
			        point.y = display.getHeight();
			    }
				int d = 0;
				if (point.x > point.y) {
					d = point.x;
				} else {
					d = point.y;
				}
				d = d / 2;
				
				// Address QR code
				Charset charset = Charset.forName("ISO-8859-1");
				CharsetEncoder encoder = charset.newEncoder();
				byte[] b = null;
				try {
					EditText Addr = (EditText) findViewById(R.id.Address);
					ByteBuffer bbuf = encoder.encode(CharBuffer.wrap(Addr.getText().toString()));
					b = bbuf.array();
				} catch (CharacterCodingException e) {
					TextView lblError1 = (TextView) findViewById(R.id.lblError);
					lblError1.setText(e.toString());
				}
				
				String data = null;
				try {
					data = new String(b, "ISO-8859-1");
				} catch (UnsupportedEncodingException e) {
					TextView lblError1 = (TextView) findViewById(R.id.lblError);
					lblError1.setText(e.toString());
				}
				
				BitMatrix matrix = null;
				int h = d;
				int w = d;
				com.google.zxing.Writer writer = new QRCodeWriter();
				try {
					matrix = writer.encode(data, com.google.zxing.BarcodeFormat.QR_CODE, w, h);
				} catch (com.google.zxing.WriterException e) {
					TextView lblError1 = (TextView) findViewById(R.id.lblError);
					lblError1.setText(e.toString());
				}
				
				Bitmap mBitmap = Bitmap.createBitmap(h, w, Config.ARGB_8888);
				for (int i=0;i<w;i++) {
					for (int j=0;j<h;j++) {
						mBitmap.setPixel(i, j, matrix.get(i, j)?Color.BLACK:Color.WHITE);
					}
				}
				
				// imgAddressQR.setImageBitmap(mBitmap);
				
				overlay.setImageBitmap(mBitmap);
				overlay.setBackgroundResource(R.drawable.layout);
				mainFrame.addView(overlay);
				
				overlay.setOnClickListener(new View.OnClickListener() {
					
					public void onClick(View v) {
						mainFrame.removeView(overlay);
					}
				});
				
			}
		});
        
        // Set listener for PKWIF QR button
        ImageView imgPKWIFQR = (ImageView) findViewById(R.id.imgPKWIFQR);
        imgPKWIFQR.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				
				final FrameLayout mainFrame = (FrameLayout)findViewById(R.id.MainFrame);
				final ImageView overlay = new ImageView(BitcoinAddressUtilityActivity.this);
				
				Display display = getWindowManager().getDefaultDisplay();
				final Point point = new Point();
			    try {
			        display.getSize(point);
			    } catch (java.lang.NoSuchMethodError ignore) { // Older device
			        point.x = display.getWidth();
			        point.y = display.getHeight();
			    }
				int d = 0;
				if (point.x > point.y) {
					d = point.x;
				} else {
					d = point.y;
				}
				d = d / 2;
				
				// Address QR code
				Charset charset = Charset.forName("ISO-8859-1");
				CharsetEncoder encoder = charset.newEncoder();
				byte[] b = null;
				try {
					EditText pkWIF = (EditText) findViewById(R.id.PrivKeyWIF);
					ByteBuffer bbuf = encoder.encode(CharBuffer.wrap(pkWIF.getText().toString()));
					b = bbuf.array();
				} catch (CharacterCodingException e) {
					TextView lblError1 = (TextView) findViewById(R.id.lblError);
					lblError1.setText(e.toString());
				}
				
				String data = null;
				try {
					data = new String(b, "ISO-8859-1");
				} catch (UnsupportedEncodingException e) {
					TextView lblError1 = (TextView) findViewById(R.id.lblError);
					lblError1.setText(e.toString());
				}
				
				BitMatrix matrix = null;
				int h = d;
				int w = d;
				com.google.zxing.Writer writer = new QRCodeWriter();
				try {
					matrix = writer.encode(data, com.google.zxing.BarcodeFormat.QR_CODE, w, h);
				} catch (com.google.zxing.WriterException e) {
					TextView lblError1 = (TextView) findViewById(R.id.lblError);
					lblError1.setText(e.toString());
				}
				
				Bitmap mBitmap = Bitmap.createBitmap(h, w, Config.ARGB_8888);
				for (int i=0;i<w;i++) {
					for (int j=0;j<h;j++) {
						mBitmap.setPixel(i, j, matrix.get(i, j)?Color.BLACK:Color.WHITE);
					}
				}
				
				// imgAddressQR.setImageBitmap(mBitmap);
				
				overlay.setImageBitmap(mBitmap);
				overlay.setBackgroundResource(R.drawable.layout);
				mainFrame.addView(overlay);
				
				overlay.setOnClickListener(new View.OnClickListener() {
					
					public void onClick(View v) {
						mainFrame.removeView(overlay);
					}
				});
				
			}
		});
        
        
        // Set listener for the generate button
        Button btnGenerate = (Button) findViewById(R.id.btnGenerate);
        btnGenerate.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				
				EditText txtPassphrase = (EditText) findViewById(R.id.txtPassphrase);
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(txtPassphrase.getWindowToken(), 0);
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
							lblError1.setText(e.toString());
						}
						
						String data = null;
						try {
							data = new String(b, "ISO-8859-1");
						} catch (UnsupportedEncodingException e) {
							TextView lblError1 = (TextView) findViewById(R.id.lblError);
							lblError1.setText(e.toString());
						}
						
						BitMatrix matrix = null;
						int h = 80;
						int w = 80;
						com.google.zxing.Writer writer = new QRCodeWriter();
						try {
							matrix = writer.encode(data, com.google.zxing.BarcodeFormat.QR_CODE, w, h);
						} catch (com.google.zxing.WriterException e) {
							TextView lblError1 = (TextView) findViewById(R.id.lblError);
							lblError1.setText(e.toString());
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
							lblError1.setText(e.toString());
						}
						
						data = null;
						try {
							data = new String(b, "ISO-8859-1");
						} catch (UnsupportedEncodingException e) {
							TextView lblError1 = (TextView) findViewById(R.id.lblError);
							lblError1.setText(e.toString());
						}
						
						matrix = null;
						h = 80;
						w = 80;
						// com.google.zxing.Writer writer = new QRCodeWriter();
						try {
							matrix = writer.encode(data, com.google.zxing.BarcodeFormat.QR_CODE, w, h);
						} catch (com.google.zxing.WriterException e) {
							TextView lblError1 = (TextView) findViewById(R.id.lblError);
							lblError1.setText(e.toString());
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