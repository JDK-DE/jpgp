package fr.twiced.pgp.jpgp.pojo;

import java.io.IOException;

import org.bouncycastle.openpgp.PGPSignature;

import fr.twiced.pgp.jpgp.PGPHelper;

public class JSignature {

	private String asciiArmored = null;
	private String author = null;

	public JSignature(PGPSignature sig) {
		try {
			asciiArmored = PGPHelper.enarmor(sig.getEncoded());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		author = Long.toHexString(sig.getKeyID());
	}

	public String getAsciiArmored() {
		return asciiArmored;
	}

	public void setAsciiArmored(String asciiArmored) {
		this.asciiArmored = asciiArmored;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
}
