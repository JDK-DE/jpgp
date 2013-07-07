package fr.twiced.pgp.jpgp;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.codehaus.jackson.map.ObjectMapper;

import fr.twiced.pgp.jpgp.pojo.JCertificate;
import fr.twiced.pgp.jpgp.service.IPGPService;
import fr.twiced.pgp.jpgp.service.PGPService;

public class Main {
	
	private static final String OPT_CERT = "c";
	private static final String OPT_PUBK = "p";
	private static final String OPT_DATA = "d";
	private static final String OPT_SIG = "s";
	private static final String OPT_UID = "u";
	private static final String OPT_CMD_PARSE = "P";
	private static final String OPT_CMD_VERIFY = "V";
	private static final String OPT_CMD_ISSIGNED = "I";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(new Main().exec(args));
	}
	
	public String exec(String[] args){
		Options options = new Options();
		options.addOption(OPT_CERT, "cert", true, "Certificate to be checked - ASCII armored.");
		options.addOption(OPT_PUBK, "pubkey", true, "Public key used for signature - ASCII armored.");
		options.addOption(OPT_SIG, "signature", true, "Signature to verify - ASCII armored.");
		options.addOption(OPT_DATA, "data", true, "Data the signature is based upon - ASCII armored.");
		options.addOption(OPT_UID, "uid", true, "UID the signature is based upon - clear text.");
		options.addOption(OPT_CMD_PARSE, "parse", false, "Parses Base64 certificate and outputs JSON structure of it.\nRequires --cert option.");
		options.addOption(OPT_CMD_VERIFY, "verify", false, "Verifies if a signature matches some data.\nRequires --pubkey, --signature and --data options.");
		options.addOption(OPT_CMD_ISSIGNED, "signed", false, "Verifies if a UID is signed by the given public key.\nRequires --pubkey, --cert, --uid options.");
		
		CommandLineParser parser = new BasicParser();
		IPGPService pgp = new PGPService();
		ObjectMapper om = new ObjectMapper();
		try {
			
			CommandLine cmd = parser.parse(options, args);
			JSONResponse res = null;
			
			// Command ?
			if(!cmd.hasOption(OPT_CMD_PARSE) && !cmd.hasOption(OPT_CMD_VERIFY) && !cmd.hasOption(OPT_CMD_ISSIGNED)){
				throw new OptionRequiredException(cmd);
			}
			
			// --parse
			if(cmd.hasOption(OPT_CMD_PARSE)){
				if(!cmd.hasOption(OPT_CERT))
					throw new OptionRequiredException(cmd);
				JCertificate cert = pgp.dearmor(cmd.getOptionValue(OPT_CERT));
				res = new JSONResponse(cert);
			}
			
			// --verify
			if(cmd.hasOption(OPT_CMD_VERIFY)){
				if(!cmd.hasOption(OPT_PUBK) || !cmd.hasOption(OPT_SIG) || !cmd.hasOption(OPT_DATA))
					throw new OptionRequiredException(cmd);
				boolean verified = pgp.checkStringSignature(cmd.getOptionValue(OPT_SIG), cmd.getOptionValue(OPT_PUBK), cmd.getOptionValue(OPT_DATA));
				res = new JSONResponse(verified);
			}
			
			// --signed
			if(cmd.hasOption(OPT_CMD_ISSIGNED)){
				if(!cmd.hasOption(OPT_PUBK) || !cmd.hasOption(OPT_CERT) || !cmd.hasOption(OPT_UID))
					throw new OptionRequiredException(cmd);
				boolean verified = pgp.checkUidSignature(cmd.getOptionValue(OPT_CERT), cmd.getOptionValue(OPT_PUBK), cmd.getOptionValue(OPT_UID));
				res = new JSONResponse(verified);
			}
			return om.writeValueAsString(res);
			
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			HelpFormatter hf = new HelpFormatter();
			hf.printHelp("java -jar jpgp.jar", options);
		}
		return "{\"result\": \"error\", \"data\": null}";
	}
	
	private static class OptionRequiredException extends RuntimeException {
		private static final long serialVersionUID = -5866603787705576032L;
		public OptionRequiredException(CommandLine cmd) {
			super("Bad arguments given to CLI.");
		}
	}
	
	public static class JSONResponse {
		
		private Object data;
		
		public JSONResponse(Object data) {
			super();
			this.data = data;
		}

		public String getResult(){
			return "success";
		}

		public Object getData() {
			return data;
		}
	}
	
	public class ObjectSuccess extends JSONResponse {
		public ObjectSuccess(Object data) {
			super(data);
		}
	}
	
	public class SimpleSuccess extends JSONResponse {
		public SimpleSuccess(Boolean value) {
			super(value);
		}
	}
}
