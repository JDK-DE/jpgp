package fr.twiced.pgp.jpgp.pojo.key;

import java.io.IOException;
import java.util.Iterator;

import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPSignature;

import fr.twiced.pgp.jpgp.pojo.JSignature;

public class JSubkey extends JKey {

	private JSignature subkeyBinding;
	private JSignature subkeyRevoke;

	public JSubkey(PGPPublicKey pubkey) throws IOException {
		this(pubkey, null, null);
	}

	public JSubkey(PGPPublicKey pubkey, JSignature subkeyBinding) {
		this(pubkey, subkeyBinding, null);
	}

	public JSubkey(PGPPublicKey pubkey, JSignature subkeyBinding, JSignature subkeyRevoke) {
		super(pubkey);
		Iterator<PGPSignature> iter = pubkey.getSignaturesOfType(PGPSignature.SUBKEY_BINDING);
		if(iter.hasNext()){
			PGPSignature sig = iter.next();
			subkeyBinding = new JSignature(sig);
		}
		if(iter.hasNext()){
			throw new RuntimeException("Only one subkey binding authorized by subkey");
		}
		iter = pubkey.getSignaturesOfType(PGPSignature.SUBKEY_REVOCATION);
		if(iter.hasNext()){
			PGPSignature sig = iter.next();
			subkeyRevoke = new JSignature(sig);
		}
		if(iter.hasNext()){
			throw new RuntimeException("Only one subkey revocation authorized by subkey");
		}
		this.subkeyBinding = subkeyBinding;
		this.subkeyRevoke = subkeyRevoke;
	}

	public JSignature getSubkeyBinding() {
		return subkeyBinding;
	}

	public void setSubkeyBinding(JSignature subkeyBinding) {
		this.subkeyBinding = subkeyBinding;
	}

	public JSignature getSubkeyRevoke() {
		return subkeyRevoke;
	}

	public void setSubkeyRevoke(JSignature subkeyRevoke) {
		this.subkeyRevoke = subkeyRevoke;
	}
}
