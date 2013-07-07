package fr.twiced.pgp.jpgp.pojo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class JUserID {

	private String uid = "";
	private Collection<JSignature> signatures;

	public JUserID() {
	}

	public JUserID(String string) {
		this(string, new ArrayList<JSignature>());
	}

	public JUserID(String string, JSignature[] signatures) {
		this(string, Arrays.asList(signatures));
	}

	public JUserID(String uid, Collection<JSignature> signatures) {
		super();
		this.uid = uid;
		this.signatures = signatures;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Collection<JSignature> getSignatures() {
		return signatures;
	}

	public void setSignatures(Collection<JSignature> signatures) {
		this.signatures = signatures;
	}
}
