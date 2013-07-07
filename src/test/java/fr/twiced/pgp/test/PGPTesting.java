package fr.twiced.pgp.test;

import static org.junit.Assert.assertFalse;
import static fr.twiced.pgp.test.TestData.*;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import fr.twiced.pgp.jpgp.service.IPGPService;
import fr.twiced.pgp.jpgp.service.PGPService;

@RunWith(JUnit4.class)
public class PGPTesting {

	private IPGPService pgp = new PGPService();

	@Test
	public void publicKeyDearmoring() {
		assertTrue("31A6302161AC8F5938969E85399EB3415C237F93".toLowerCase() .equals(pgp.dearmor(PUBK_CGEEK).getFingerprint()));
	}

	@Test
	public void publicKeyVerifications() {
		assertTrue(pgp.checkUidSignature(PUBK_CGEEK, PUBK_CGEEK, UID_CGEEK_OPENUDC));
		assertFalse(pgp.checkUidSignature(PUBK_CGEEK, PUBK_CGEEK, UID_CGEEK_FAKE));
	}

	@Test
	public void signatureVerification() {
		assertTrue(pgp.checkStringSignature(SIG_CGEEK_MSG1, PUBK_CGEEK, MSG1));
		assertFalse(pgp.checkStringSignature(SIG_CGEEK_MSG1, PUBK_CGEEK, MSG1 + "delta"));
		System.out.println(SIG_CGEEK_MSG1);
	}
}
