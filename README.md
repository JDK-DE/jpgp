jpgp
====

Some Java PGP high level tools in order to interact with Node.js

Usage
------

    usage: java -jar jpgp.jar
     -c,--cert <arg>        Certificate to be checked, ASCII armored.
     -d,--data <arg>        Data the signature is based upon, ASCII armored.
     -P,--parse <arg>       Parses Base64 certificate and outputs JSON
                            structure of it.
                            Requires --cert option.
     -p,--pubkey <arg>      Public key used for signature, ASCII armored.
     -s,--signature <arg>   Signature to verify, ASCII armored.
     -V,--verify <arg>      Verifies if a signature matches some data.
                            Requires --pubkey, --signature and --data options.

License
=======

MIT
