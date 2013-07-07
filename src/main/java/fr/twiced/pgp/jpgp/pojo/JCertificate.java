package fr.twiced.pgp.jpgp.pojo;

import java.io.IOException;
import java.security.Security;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPSignature;

import fr.twiced.pgp.jpgp.pojo.key.JKey;
import fr.twiced.pgp.jpgp.pojo.key.JSubkey;

public class JCertificate extends JKey {
	
	private Collection<JSubkey> subKeys = new ArrayList<JSubkey>();
	
	static {
		Security.addProvider(new BouncyCastleProvider());
	}

	public JCertificate(PGPPublicKey pubkey) {
		super(pubkey);
	}
	
	public void addSubKey(JSubkey subKey){
		subKeys.add(subKey);
	}

	@SuppressWarnings("unchecked")
	public Collection<JUserID> getUids() throws IOException {
		Collection<JUserID> uids = new ArrayList<JUserID>();
		Iterator<String> it = pubKey.getUserIDs();
		while (it.hasNext()) {
			String uid = (String) it.next();
			JUserID jUserID = new JUserID(uid);
			Iterator<PGPSignature> itSig = pubKey.getSignaturesForID(uid);
			while (itSig.hasNext()) {
				PGPSignature pgpSignature = (PGPSignature) itSig.next();
				jUserID.getSignatures().add(new JSignature(pgpSignature));
			}
			uids.add(jUserID);
		}
		return uids;
	}

	public Collection<JSubkey> getSubkeys() {
		return subKeys;
	}
	
	public boolean isSigned(String uid, JCertificate cert){
		boolean verified = false;
		Iterator<PGPSignature> it = pubKey.getSignaturesForID(uid);
		PGPSignature s = null;
		while (s == null && it != null && it.hasNext()) {
			PGPSignature pgpSignature = (PGPSignature) it.next();
			if(cert.getFingerprint().endsWith(Long.toHexString(pgpSignature.getKeyID()))){
				s = pgpSignature;
				try {
					s.initVerify(cert.pubKey, "BC");
					verified = s.verifyCertification(uid, pubKey);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
		return verified;
	}
	
	public boolean hasSigned(PGPSignature s, String data){
		boolean verified = false;
		try {
			s.initVerify(pubKey, "BC");
			s.update(data.getBytes());
			verified = s.verify();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return verified;
	}
}
