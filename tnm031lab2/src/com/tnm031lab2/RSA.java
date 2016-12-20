package com.tnm031lab2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Random;

public class RSA {

    private BigInteger p;
    private BigInteger q;
    private BigInteger n;
    private BigInteger phi;
    private BigInteger e;
    private BigInteger d;
    private int bitLength = 10; // Bit-length is the number of binary digits,
    private Random r;

    // Constructor. Calculates according to the RSA pdf.
    public RSA() {

        // Generate a random number.
        r = new Random();

        // Returns a probable prime number based on the random nr "r".
        p = BigInteger.probablePrime(bitLength, r);
        q = BigInteger.probablePrime(bitLength, r);

        // Multiply p and q.
        n = p.multiply(q);

        // Calculate phi and e, de = 1 ( mod (p-1)q-1) )
        e = BigInteger.probablePrime(bitLength/2, r);
        phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        d = e.modInverse(phi);
    }


    public static void main(String[] args) throws IOException {
        RSA rsa = new RSA();

        // Read the user input.
        System.out.println("Enter your message: ");
        String input = (new BufferedReader(new InputStreamReader(System.in))).readLine();

        System.out.println("Encrypting the user message: " + input);

        // Encrypt the input.
        byte[] encryptedMessage = rsa.encryptMessage(input.getBytes());

        // The encrypted message is now in bytes. Converting it to String in order to print it.
        String byteToString = "";
        for(byte idx : encryptedMessage){ byteToString += idx;};
        System.out.print("The encrypted message: " + byteToString + "\n");

        // Decrypt the input.
        byte[] decryptedMessage = rsa.decryptMessage(encryptedMessage);
        System.out.print("The decrypted message: " + new String(decryptedMessage));


    }

    // Encrypt message, mod n c = m^e
    public byte[] encryptMessage(byte[] message) {
        BigInteger c = new BigInteger(message);

        // c = m^e, "e" is the exponent and "n" is the modulus.
        c.modPow(e, n);

        return c.toByteArray();
    }

    // Decrypt message, m = c^d
    public byte[] decryptMessage(byte[] message) {
        BigInteger m2 = new BigInteger(message);

        // m > n breaks the message
//        System.out.print("m = " + m + " n = " + n + "\n");

//        if(m.compareTo(n) == 1){ // Returns "1" if m is larger than n.
//            System.out.print("Breaking!! because m is larger than n!");
//            m.setBit(0);
//            return m.toByteArray();
//        }

        // m = c^d
        m2.modPow(d, n);

        return m2.toByteArray();
    }
}