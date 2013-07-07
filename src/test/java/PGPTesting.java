import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import fr.twiced.pgp.jpgp.service.IPGPService;
import fr.twiced.pgp.jpgp.service.PGPService;

@RunWith(JUnit4.class)
public class PGPTesting {

	private static final String PUBLIC_KEY_CGEEK = "-----BEGIN PGP PUBLIC KEY BLOCK-----\n"
			+ "Version: SKS 1.1.0\n"
			+ "\n"
			+ "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\n"
			+ "tGMseERMCbPZEi+Ggg==\n"
			+ "=dLy8\n"
			+ "-----END PGP PUBLIC KEY BLOCK-----";

	private static final String SIGNATURE_CGEEK_MSG1 = "-----BEGIN PGP MESSAGE-----\n"
			+ "Version: GnuPG v1.4.12 (GNU/Linux)\n"
			+ "\n"
			+ "owEBRwG4/pANAwAIATmes0FcI3+TAawXYgVhLnR4dFHZUKZNb24gbWVzc2FnZQqJ\n"
			+ "ARwEAAEIAAYFAlHZUKYACgkQOZ6zQVwjf5MkKwgAqINR9ffqpWZUQ8ry9r4U9qrj\n"
			+ "Bt7PSgb/IGWYc+MBrHcd0+rkh8q+a7mOL/vDiFdYRtc96mtTerTpnfVqoKPgzF+i\n"
			+ "LiqK1nfEaPhYRdcqwVsdfauGOv4287JVJeNlzuyZJRfD9GNwXdrHIcI5nZOQ8ca5\n"
			+ "/s1AFyIodfMKnOKFu/co5gVbZzPTd0N5WDZKDx0MLfI0DRkxxHDNpdNUIrQrZDCr\n"
			+ "IM4AIW/iMoXgi4yl/Oc5C8BvIxGwpxdinFakzq3f7h7u31p2/Nxy8FFHN5RnOIyG\n"
			+ "TgNsto8Euc10ljk8CCBPX2rPU2A4Ex0ZbROq2jotR8Nh/FvwnPY3UIbxZaxrSw==\n"
			+ "=m2LV\n" + "-----END PGP MESSAGE-----\n";
	
	private static final String MSG1 = "Mon message\n";

	private static final String CGEEK_UID_OPENUDC = "cgeek twicedd (udid2;c;MOREAU;CEDRIC;1988-04-29;e+47.47-000.56;0;) <cem.moreau@gmail.com>";
	private static final String CGEEK_UID_FAKE = "fake fake (FAKE) fake@fake.fr";

	private IPGPService pgp = new PGPService();

	@Test
	public void publicKeyDearmoring() {
		assertTrue("31A6302161AC8F5938969E85399EB3415C237F93".toLowerCase()
				.equals(pgp.dearmor(PUBLIC_KEY_CGEEK).getFingerprint()));
	}

	@Test
	public void publicKeyVerifications() {
		assertTrue(pgp.checkUidSignature(PUBLIC_KEY_CGEEK, PUBLIC_KEY_CGEEK,
				CGEEK_UID_OPENUDC));
		assertFalse(pgp.checkUidSignature(PUBLIC_KEY_CGEEK, PUBLIC_KEY_CGEEK,
				CGEEK_UID_FAKE));
	}

	@Test
	public void signatureVerification() {
		assertTrue(pgp.checkStringSignature(SIGNATURE_CGEEK_MSG1, PUBLIC_KEY_CGEEK, MSG1));
		assertFalse(pgp.checkStringSignature(SIGNATURE_CGEEK_MSG1, PUBLIC_KEY_CGEEK, MSG1 + "delta"));
	}
}
