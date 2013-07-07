package fr.twiced.pgp.jpgp.service;

import fr.twiced.pgp.jpgp.pojo.JCertificate;

public interface IPGPService {

	public JCertificate dearmor(String base64Stream);
	public boolean checkUidSignature(String base64InCertificate, String base64Signatory, String uid);
	public boolean checkStringSignature(String base64CompressedSignature, String base64Signatory, String data);
}
