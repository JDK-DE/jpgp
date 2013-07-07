package fr.twiced.pgp.jpgp.pojo.key;

import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.util.encoders.Hex;

public class JKey {

	protected PGPPublicKey pubKey = null;

	public JKey(PGPPublicKey pubKey) {
		this.pubKey = pubKey;
	}

	public String getFingerprint() {
		return new String(Hex.encode(pubKey.getFingerprint()));
	}

	public PGPPublicKey pubKey() {
		return pubKey;
	}

	public void setPubKey(PGPPublicKey pubKey) {
		this.pubKey = pubKey;
	}
}
