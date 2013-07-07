package fr.twiced.pgp.jpgp.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Collection;

import org.bouncycastle.bcpg.ArmoredInputStream;
import org.bouncycastle.openpgp.PGPCompressedData;
import org.bouncycastle.openpgp.PGPLiteralData;
import org.bouncycastle.openpgp.PGPObjectFactory;
import org.bouncycastle.openpgp.PGPOnePassSignature;
import org.bouncycastle.openpgp.PGPOnePassSignatureList;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPSignature;
import org.bouncycastle.openpgp.PGPSignatureList;

import fr.twiced.pgp.jpgp.PGPHelper;
import fr.twiced.pgp.jpgp.pojo.JCertificate;

public class PGPService implements IPGPService {

	@Override
	public JCertificate dearmor(String base64Stream) {
		Collection<JCertificate> keys = PGPHelper.extract(base64Stream);
		if(keys.size() == 0)
			throw new RuntimeException("Stream must contain at least 1 key");
		return keys.iterator().next();
	}

	@Override
	public boolean checkUidSignature(String base64Cert, String base64Signatory, String uid) {
		JCertificate target = dearmor(base64Cert);
		JCertificate signatory = dearmor(base64Signatory);
		return target.isSigned(uid, signatory);
	}

	@Override
	public boolean checkStringSignature(String signatureString, String base64Signatory, String data) {
		try {
			JCertificate cert = dearmor(base64Signatory);
			Signature s = new Signature(signatureString, cert.pubKey());
			return s.verify(data);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private class Signature {
		
		private PGPOnePassSignature onePassSignature;
		private PGPSignature signature;
		private String data;
		
		public Signature(String signatureStream, PGPPublicKey publicKey) throws Exception {

			PGPObjectFactory pgpObjFactory = new PGPObjectFactory(new ArmoredInputStream(new ByteArrayInputStream(signatureStream.getBytes())));
			
			// 1. Extrait la signature compressée
			PGPCompressedData compressedData = (PGPCompressedData)pgpObjFactory.nextObject();
			
			//Get the signature from the file
	         
			// 2. Extrait la signature décompressée
			pgpObjFactory = new PGPObjectFactory(compressedData.getDataStream());
			PGPOnePassSignatureList onePassSignatureList = (PGPOnePassSignatureList)pgpObjFactory.nextObject();
			onePassSignature = onePassSignatureList.get(0);
	        
			//Get the literal data from the file

			// 3. Extrait les données signées
			PGPLiteralData pgpLiteralData = (PGPLiteralData) pgpObjFactory.nextObject();
			InputStream literalDataStream = pgpLiteralData.getInputStream();
	        
			ByteArrayOutputStream literalDataOutputStream = new ByteArrayOutputStream();
			onePassSignature.initVerify(publicKey, "BC");
			
			int ch;
			while ((ch = literalDataStream.read()) >= 0) {
				// 4. Feed onePassStructure + literalData
			    onePassSignature.update((byte)ch);
			    literalDataOutputStream.write(ch);
			}
			literalDataOutputStream.close();
			data = new String(literalDataOutputStream.toByteArray());
			 
			//Get the signature from the written out file
			PGPSignatureList p3 = (PGPSignatureList)pgpObjFactory.nextObject();
			signature = p3.get(0);
		}
		
		public boolean verify(String originalData) throws Exception {
			//Verify the two signatures
			if (onePassSignature.verify(signature)) {
			    return true && data.equals(originalData);
			}
			else {
			    return false;
			}
		}
	}
}
