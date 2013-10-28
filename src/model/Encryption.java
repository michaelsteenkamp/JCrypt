package model;


import java.security.*;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.*;

import view.GuiMain;


public class Encryption {

	private byte[] ciphertext;

	private String passphrase;
	private SecretKeySpec key;
	
	private String plainText;
	
	//Constructor
	public Encryption(String passphrase){
		this.passphrase = passphrase;
	}
	
	//Getters and setters --------------------------------------------------------
	public String getPlainText(){
		return plainText;
	}
	
	
	public void setPlainText(String plainText){
		this.plainText = plainText;
	}

	public byte[] getCipherText(){
		return ciphertext;
	}
	
	public void setCipherText(byte[] c){
		this.ciphertext = c;
	}
	
	//End getters and setters ---------------------------------------------------
	
	
	public void encrypt(){
		
		MessageDigest digest = null;
		//Get required number of bytes
		try {
			digest = MessageDigest.getInstance("SHA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		digest.update(passphrase.getBytes());
		key = new SecretKeySpec(digest.digest(), 0, 16, "AES");
		
		//Encrypt plain text with key
		Cipher aes = null;
		try {
			aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			aes.init(Cipher.ENCRYPT_MODE, key);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			ciphertext = aes.doFinal(plainText.getBytes());
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void decrypt(){
		
		MessageDigest digest = null;
		//Get required number of bytes
		try {
			digest = MessageDigest.getInstance("SHA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		digest.update(passphrase.getBytes());
		key = new SecretKeySpec(digest.digest(), 0, 16, "AES");
		
		Cipher aes = null;
		try {
			aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			aes.init(Cipher.DECRYPT_MODE, key);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			plainText = new String(aes.doFinal(ciphertext));
		} catch (IllegalBlockSizeException e) {
		} catch (BadPaddingException e) {
			GuiMain.caught = true;
		}
		
		
		
	}
	
}
