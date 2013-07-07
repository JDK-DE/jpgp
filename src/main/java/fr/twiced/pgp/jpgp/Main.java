package fr.twiced.pgp.jpgp;

import java.io.IOException;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import fr.twiced.pgp.jpgp.pojo.JCertificate;
import fr.twiced.pgp.jpgp.service.IPGPService;
import fr.twiced.pgp.jpgp.service.PGPService;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Options options = new Options();
		
		options.addOption("c", "cert", true, "Certificate to be checked");
		options.addOption("p", "pubkey", true, "Public key used for signature");
		options.addOption("s", "signature", true, "Signature to verify");
		options.addOption("d", "data", true, "Data the signature is based upon");
		
		CommandLineParser parser = new BasicParser();
		try {
			CommandLine cmd = parser.parse(options, args);
			if(cmd.hasOption("p")){
				IPGPService pgp = new PGPService();
				JCertificate cert = pgp.dearmor(cmd.getOptionValue("p"));
				ObjectMapper om = new ObjectMapper();
				System.out.println(om.writerWithDefaultPrettyPrinter().writeValueAsString(cert));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
