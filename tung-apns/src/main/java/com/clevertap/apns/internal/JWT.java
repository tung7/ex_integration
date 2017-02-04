/*
 * Copyright (c) 2016, CleverTap
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * - Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * - Neither the name of CleverTap nor the names of its
 *   contributors may be used to endorse or promote products derived from
 *   this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.clevertap.apns.internal;

//import com.sun.org.apache.xml.internal.security.utils.Base64;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;

public final class JWT {
    private static BASE64Encoder encoder =  new BASE64Encoder();
    private static BASE64Decoder decoder =  new BASE64Decoder();
    private static final char[] toBase64URL = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_'
    };


    /**
     * Generates a JWT token as per Apple's specifications.
     *
     * @param teamID The team ID (found in the member center)
     * @param keyID  The key ID (found when generating your private key)
     * @param secret The private key (excluding the header and the footer)
     * @return The resulting token, which will be valid for one hour
     * @throws InvalidKeySpecException  if the key is incorrect
     * @throws NoSuchAlgorithmException if the key algo failed to load
     * @throws InvalidKeyException      if the key is invalid
     * @throws SignatureException       if this signature object is not initialized properly.
     */
    public static String getToken(final String teamID, final String keyID, final String secret)
            throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        final int now = (int) (System.currentTimeMillis() / 1000);
        final String header = "{\"alg\":\"ES256\",\"kid\":\"" + keyID + "\"}";
        final String payload = "{\"iss\":\"" + teamID + "\",\"iat\":" + now + "}";
//        final String part1 = Base64.getUrlEncoder().encodeToString(header.getBytes(StandardCharsets.UTF_8))
//                + "."
//                + Base64.getUrlEncoder().encodeToString(payload.getBytes(StandardCharsets.UTF_8));
        final String part1 = encoder.encode(header.getBytes(StandardCharsets.UTF_8))
                + "."
                + encoder.encode(payload.getBytes(StandardCharsets.UTF_8));
        String es256 = ES256(secret, part1);
        return part1 + "." + es256;
    }

    /**
     * Adopted from http://stackoverflow.com/a/20322894/2274894
     *
     * @param secret The secret
     * @param data   The data to be encoded
     * @return The encoded token
     * @throws InvalidKeySpecException  if the key is incorrect
     * @throws NoSuchAlgorithmException if the key algo failed to load
     * @throws InvalidKeyException      if the key is invalid
     * @throws SignatureException       if this signature object is not initialized properly.
     */
    private static String ES256(final String secret, final String data)
            throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {

        KeyFactory kf = KeyFactory.getInstance("EC");
        KeySpec keySpec = null;
        try {
            keySpec = new PKCS8EncodedKeySpec(new BASE64Decoder().decodeBuffer(secret));
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrivateKey key = kf.generatePrivate(keySpec);

        final Signature sha256withECDSA = Signature.getInstance("SHA256withECDSA");
        sha256withECDSA.initSign(key);

        sha256withECDSA.update(data.getBytes(StandardCharsets.UTF_8));

        final byte[] signed = sha256withECDSA.sign();
        return encodeToString(signed);

    }

    public static byte[] encode(byte[] src) {
        int len = 4 * ((src.length + 2) / 3);          // dst array size
        byte[] dst = new byte[len];
        int ret = encode0(src, 0, src.length, dst);
        if (ret != dst.length)
            return Arrays.copyOf(dst, ret);
        return dst;
    }

    public static String encodeToString(byte[] src) {
        byte[] encoded = encode(src);
        return new String(encoded, 0, 0, encoded.length);
    }

    private static int encode0(byte[] src, int off, int end, byte[] dst) {
        char[] base64 = toBase64URL;
        int sp = off;
        int slen = (end - off) / 3 * 3;
        int sl = off + slen;

        int dp = 0;
        while (sp < sl) {
            int sl0 = Math.min(sp + slen, sl);
            for (int sp0 = sp, dp0 = dp ; sp0 < sl0; ) {
                int bits = (src[sp0++] & 0xff) << 16 |
                        (src[sp0++] & 0xff) <<  8 |
                        (src[sp0++] & 0xff);
                dst[dp0++] = (byte)base64[(bits >>> 18) & 0x3f];
                dst[dp0++] = (byte)base64[(bits >>> 12) & 0x3f];
                dst[dp0++] = (byte)base64[(bits >>> 6)  & 0x3f];
                dst[dp0++] = (byte)base64[bits & 0x3f];
            }
            int dlen = (sl0 - sp) / 3 * 4;
            dp += dlen;
            sp = sl0;
        }
        if (sp < end) {               // 1 or 2 leftover bytes
            int b0 = src[sp++] & 0xff;
            dst[dp++] = (byte)base64[b0 >> 2];
            if (sp == end) {
                dst[dp++] = (byte)base64[(b0 << 4) & 0x3f];
                    dst[dp++] = '=';
                    dst[dp++] = '=';
            } else {
                int b1 = src[sp++] & 0xff;
                dst[dp++] = (byte)base64[(b0 << 4) & 0x3f | (b1 >> 4)];
                dst[dp++] = (byte)base64[(b1 << 2) & 0x3f];
                    dst[dp++] = '=';
            }
        }
        return dp;
    }
}
