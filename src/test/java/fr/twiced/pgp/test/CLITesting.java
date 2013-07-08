package fr.twiced.pgp.test;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import static fr.twiced.pgp.test.TestData.*;

import fr.twiced.pgp.jpgp.Main;

public class CLITesting {

	public static final String[] CMD_CGEEK_CERT = new String[]{
		"-P",
		"-c", PUBK_CGEEK
	};
	
	public static final String[] CMD_CGEEK_SIGNED = new String[]{
		"-V",
		"-s", SIG_CGEEK_MSG1,
		"-d", MSG1,
		"-p", PUBK_CGEEK
	};
	
	public static final String[] CMD_CGEEK_SIGNED_DELTA = new String[]{
		"-V",
		"-s", SIG_CGEEK_MSG1,
		"-d", MSG1 + "AA",
		"-p", PUBK_CGEEK
	};
	
	public static final String[] CMD_CGEEK_SIGNED_UBOT1 = new String[]{
		"-V",
		"-s", SIG_CGEEK_MSG1,
		"-d", MSG1,
		"-p", PUBK_UBOT1
	};
	
	public static final String[] CMD_CGEEK_UID_SIGNEDBY_CGEEK = new String[]{
		"-I",
		"-c", PUBK_CGEEK,
		"-u", UID_CGEEK_OPENUDC,
		"-p", PUBK_CGEEK
	};
	
	public static final String[] CMD_CGEEK_UID_SIGNEDBY_JBAR= new String[]{
		"-I",
		"-c", PUBK_CGEEK,
		"-u", UID_CGEEK_OPENUDC,
		"-p", PUBK_JBAR
	};
	
	public static final String[] CMD_CGEEK_UID_SIGNEDBY_UBOT1= new String[]{
		"-I",
		"-c", PUBK_CGEEK,
		"-u", UID_CGEEK_OPENUDC,
		"-p", PUBK_UBOT1
	};
	
	public static final String[] CMD_CGEEK_FAKEUID_SIGNEDBY_CGEEK= new String[]{
		"-I",
		"-c", PUBK_CGEEK,
		"-u", "uiojkl,jplkp$m;ùkjôjĵ",
		"-p", PUBK_CGEEK
	};
	
	public static final String FALSE_RESPONSE = "{\"data\":false,\"result\":\"success\"}";
	public static final String TRUE_RESPONSE = "{\"data\":true,\"result\":\"success\"}";
	public static final String CMD_CGEEK_CERT_RESPONSE = "{\"data\":{\"uids\":[{\"uid\":\"cgeek twicedd (udid2;c;MOREAU;CEDRIC;1988-04-29;e+47.47-000.56;0;) <cem.moreau@gmail.com>\",\"signatures\":[{\"asciiArmored\":\"-----BEGIN PGP SIGNATURE-----\\nVersion: BCPG v1.46\\n\\niQE+BBMBAgAoBQJQnT4FAhsDBQkUrTaABgsJCAcDAgYVCAIJCgsEFgIDAQIeAQIX\\ngAAKCRA5nrNBXCN/k3VkB/0emU8LCaLlwS2e/KeDyF+ML1wP4e5+0jW6f5PJihGG\\n0MgznKmZARzsKczk8UZxhqe6RbFY4dj6z+0XGQEHoqn/mrGk80/U2UOM0EIKE1Fv\\nMfoGNBjec8mIkX9ipU19BaEVgENb0/APe8Ly6U65An7/Ml1iyeXHwV1U9LtFvlnI\\nAG6xcV0mFY3kTK8rsrAOyqvotpR3g4bBGBKYNLKx0zDIPbPox2rm/Vxy3z9cL/2t\\nfAEujjpYdVOQIuQV3NzIMYQiaXlzEZo41i5IQfnldtI92mLbh/kjy/McsJyEtiAr\\nCSQmVGQdZmXjSd2M4j7eV7P1ZRwEJB8KKfdAnre3KGNz\\n=3dac\\n-----END PGP SIGNATURE-----\\n\",\"author\":\"399eb3415c237f93\"},{\"asciiArmored\":\"-----BEGIN PGP SIGNATURE-----\\nVersion: BCPG v1.46\\n\\niQIcBBABCAAGBQJQw2w9AAoJEEQsfkXu9ermdPgQAM0s6/FcE4V3T/HTWg8SrosR\\nBNlr30hhd1Vmx4vliRSCf3p2qv4wklve5gs7NA+rsbvTIQLiGOiF3VNWkHrbrQYb\\ntSyNs0/rTjvV9/G15Q6i7TwqPqhkUGHH62Hj79oIMGofHtzhtxWQD/N65ytCQYjw\\n3p0wfPmunaTjBI/ruq2bdALsOSP4lyyiqs72Pri0LB3gpMkUyZNOpHm3W0gHLVFd\\nr/OEos03+3JQHvCenszZmeMiKvmJKQ1NRqo9IDgGoSJQ7cIEtTRVA7i3ZlBalC27\\nEppwTx/htcoZZ+OcRaW15Q5SC2L7T11/YBadKElS4uuCS7DS6pg0L9kPUJd3lh9P\\nCfb1BasfaVjRgDw7Vfi0fu1Q7ATEEzixBGTwcVKB6jlZtZHdMiKB3XQuac2sLpup\\nezwIyMt6k2DDx3EAbuAZRmP5rj/4uh6C2yzCsAeANDklVlMxfXhny8f2EphDJwdV\\n3SC7ipsq1n4UuypNg37M8E1K2Gns1uwlEuFt7pmHU2jWBeCsyeCV5tmKjzS5Akyp\\ni3VSJ9Y2kPcJDkUYoCgfF/d5NG7JuVRjGyAmrx5Vaxv6mYWdVa3/lKjs2eUedInO\\ngBYi/0RvAsjvkCmFpT5dxEVEdjyZEVYOiulqejCw8qp0hc2Srwn+4E1yrR34eD8q\\nYZvou96O9ODymFEflNKN\\n=Thhq\\n-----END PGP SIGNATURE-----\\n\",\"author\":\"442c7e45eef5eae6\"}]}],\"subkeys\":[{\"subkeyBinding\":{\"asciiArmored\":\"-----BEGIN PGP SIGNATURE-----\\nVersion: BCPG v1.46\\n\\niQElBBgBAgAPBQJQnT4FAhsMBQkUrTaAAAoJEDmes0FcI3+TkKgIAIIdaJoLgOKa\\nYs8UlDuHKvndDgpJVayjfMZn5pR5PgRqgLn3OTW4urlyLXPnaZJOPux04ktUejOC\\nFrucuEdnsXeyXxMHT9+6t79ktMbZbNUtuCXCR97SSFQBm/FlhuNYdT9NM41ltyeO\\nRXvsuERw26mg+IM7PkEqzAIq2JvozQBGYbHQRXul0NmpVG63FY+B31b4lZPpukys\\nnueoC51LTtB2Tfg9eZOwD1ro1LHA42ETe1YVAWStRMMRvl/TmQUJA5SGIGFFUSvF\\nMuL/VcL1DYRg8op4bt6CF5g+rY2IsIXE6AGmsU83WKTlOGqPHEH6autCVbRjLHhE\\nTAmz2RIvhoI=\\n=K2Yv\\n-----END PGP SIGNATURE-----\\n\",\"author\":\"399eb3415c237f93\"},\"subkeyRevoke\":null,\"fingerprint\":\"ff6548b16e96b851bd546613ac598f8a43aaa54e\"}],\"fingerprint\":\"31a6302161ac8f5938969e85399eb3415c237f93\"},\"result\":\"success\"}";

	@Test
	public void checkCert(){
		String exp = CMD_CGEEK_CERT_RESPONSE;
		String actual = new Main().exec(CMD_CGEEK_CERT).replace("\\r", "");
		Assert.assertEquals(exp, actual);
	}
	
	@Test
	public void checkSignature(){
		Assert.assertEquals(TRUE_RESPONSE, new Main().exec(CMD_CGEEK_SIGNED));
		Assert.assertEquals(FALSE_RESPONSE, new Main().exec(CMD_CGEEK_SIGNED_DELTA));
		Assert.assertEquals(FALSE_RESPONSE, new Main().exec(CMD_CGEEK_SIGNED_UBOT1));
	}
	
	@Test
	public void checkUID(){
		Assert.assertEquals(TRUE_RESPONSE, new Main().exec(CMD_CGEEK_UID_SIGNEDBY_CGEEK));
		Assert.assertEquals(TRUE_RESPONSE, new Main().exec(CMD_CGEEK_UID_SIGNEDBY_JBAR));
		Assert.assertEquals(FALSE_RESPONSE, new Main().exec(CMD_CGEEK_UID_SIGNEDBY_UBOT1));
		Assert.assertEquals(FALSE_RESPONSE, new Main().exec(CMD_CGEEK_FAKEUID_SIGNEDBY_CGEEK));
	}
	
	@Test
	@Ignore
	public void noArgs(){
		String actual = new Main().exec(new String[]{});
		System.out.println(actual);
	}
}
