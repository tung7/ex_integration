package com.tung7.ex.repository.apns;
//
//import com.clevertap.apns.internal.JWT;
//import com.tung.ex.repository.base.utils.Utils;
//import org.junit.Test;
//import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;
//
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.nio.charset.StandardCharsets;
//import java.security.*;
//import java.security.spec.InvalidKeySpecException;
//import java.security.spec.KeySpec;
//import java.security.spec.PKCS8EncodedKeySpec;
//import java.util.Base64;

/**
 * @author Tung
 * @version 1.0
 * @date 2016/12/18
 * @update
 */
public class Base64Test {
//    @Test
//    public void test() throws IOException {
//        String ss = "tung777tung";
//        byte[] encoded = Base64.getEncoder().encode(ss.getBytes());
//        String  encodedStr = Base64.getEncoder().encodeToString(ss.getBytes());
//        byte[] decoded = Base64.getDecoder().decode(encoded);
//        System.out.println(Utils.toHexString(encoded));
//        System.out.println(encodedStr);
//        System.out.println(new String(decoded));
//
//
//        String encodedStr2 = new BASE64Encoder().encode(ss.getBytes());
//        byte[] decoded2 = new BASE64Decoder().decodeBuffer(encodedStr2);
//        System.out.println(encodedStr2);
//        System.out.println(new String(decoded2));
//
//    }
//
//    @Test
//    public void testToken() throws Exception {
//        String teamID = "TKZ6NJZD4M";
//        String keyID = "62654WNT7L";
//        String apnsAuthKey = "MIGTAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBHkwdwIBAQQgkd3o12oSOSDgZiX1hsQpFVfFby6/43+w/6czk7pQP1KgCgYIKoZIzj0DAQehRANCAASQqxzm0MqGGWJvT3WM1ykwUPet4cIxeb3anM5n5s8O0UbW5L9ACpiMQPJIxm+LMOLfdmhUXIqc5Hy2nY+Xkl8i";
//        String jsonToken = JWT.getToken(teamID, keyID, apnsAuthKey);
//        System.out.println(jsonToken);
//
//        String jsonToken2 = getToken2(teamID, keyID, apnsAuthKey);
//        System.out.println(jsonToken2);
//    }
//
//    public static String getToken2(final String teamID, final String keyID, final String secret)
//            throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
//        final int now = (int) (System.currentTimeMillis() / 1000);
//        final String header = "{\"alg\":\"ES256\",\"kid\":\"" + keyID + "\"}";
//        final String payload = "{\"iss\":\"" + teamID + "\",\"iat\":" + now + "}";
//        final String part1 = new BASE64Encoder().encode(header.getBytes(StandardCharsets.UTF_8))
//                + "."
//                + new BASE64Encoder().encode(payload.getBytes(StandardCharsets.UTF_8));
//        String es256 = ES256_2(secret, part1);
//
//        return part1 + "." + es256;
//    }
//    private static String ES256_2(final String secret, final String data)
//            throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
//
//        KeyFactory kf = KeyFactory.getInstance("EC");
//        KeySpec keySpec = null;
//        try {
//            keySpec = new PKCS8EncodedKeySpec(new BASE64Decoder().decodeBuffer(secret));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        PrivateKey key = kf.generatePrivate(keySpec);
//        final Signature sha256withECDSA = Signature.getInstance("SHA256withECDSA");
//        sha256withECDSA.initSign(key);
//
//        sha256withECDSA.update(data.getBytes(StandardCharsets.UTF_8));
//
//        final byte[] signed = sha256withECDSA.sign();
//        return new BASE64Encoder().encode(signed);
//    }
}
