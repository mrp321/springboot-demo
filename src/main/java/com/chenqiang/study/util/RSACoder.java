package com.chenqiang.study.util;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

/**
 * RSA编解码工具类
 * 
 * @author QChen
 * @date 2018-4-24
 *
 */
public class RSACoder {
	public static final String KEY_ALGORITHM = "RSA";
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

	private static final String PUBLIC_KEY = "RSAPublicKey";
	private static final String PRIVATE_KEY = "RSAPrivateKey";

	public static byte[] decryptBASE64(String key) {
		return Base64.decodeBase64(key);
	}

	public static String encryptBASE64(byte[] bytes) {
		return Base64.encodeBase64String(bytes);
	}

	/**
	 * 用私钥对信息生成数字签名
	 *
	 * @param data
	 *            加密数据
	 * @param privateKey
	 *            私钥
	 * @return
	 * @throws Exception
	 */
	public static String sign(byte[] data, String privateKey) throws Exception {
		// 解密由base64编码的私钥
		byte[] keyBytes = decryptBASE64(privateKey);
		// 构造PKCS8EncodedKeySpec对象
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		// KEY_ALGORITHM 指定的加密算法
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		// 取私钥匙对象
		PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
		// 用私钥对信息生成数字签名
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(priKey);
		signature.update(data);
		return encryptBASE64(signature.sign());
	}

	/**
	 * 校验数字签名
	 *
	 * @param data
	 *            加密数据
	 * @param publicKey
	 *            公钥
	 * @param sign
	 *            数字签名
	 * @return 校验成功返回true 失败返回false
	 * @throws Exception
	 */
	public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
		// 解密由base64编码的公钥
		byte[] keyBytes = decryptBASE64(publicKey);
		// 构造X509EncodedKeySpec对象
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		// KEY_ALGORITHM 指定的加密算法
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		// 取公钥匙对象
		PublicKey pubKey = keyFactory.generatePublic(keySpec);
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(pubKey);
		signature.update(data);
		// 验证签名是否正常
		return signature.verify(decryptBASE64(sign));
	}

	public static byte[] decryptByPrivateKey(byte[] data, String key) throws Exception {
		// 对密钥解密
		byte[] keyBytes = decryptBASE64(key);
		// 取得私钥
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		// 对数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return cipher.doFinal(data);
	}

	/**
	 * 解密<br>
	 * 用私钥解密
	 *
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(String data, String key) throws Exception {
		return decryptByPrivateKey(decryptBASE64(data), key);
	}

	/**
	 * 解密<br>
	 * 用公钥解密
	 *
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] data, String key) throws Exception {
		// 对密钥解密
		byte[] keyBytes = decryptBASE64(key);
		// 取得公钥
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicKey = keyFactory.generatePublic(x509KeySpec);
		// 对数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		return cipher.doFinal(data);
	}

	/**
	 * 加密<br>
	 * 用公钥加密
	 *
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(String data, String key) throws Exception {
		// 对公钥解密
		byte[] keyBytes = decryptBASE64(key);
		// 取得公钥
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicKey = keyFactory.generatePublic(x509KeySpec);
		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return cipher.doFinal(data.getBytes());
	}

	/**
	 * 加密<br>
	 * 用私钥加密
	 *
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] data, String key) throws Exception {
		// 对密钥解密
		byte[] keyBytes = decryptBASE64(key);
		// 取得私钥
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		return cipher.doFinal(data);
	}

	/**
	 * 取得私钥
	 *
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static String getPrivateKey(Map<String, Key> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		return encryptBASE64(key.getEncoded());
	}

	/**
	 * 取得公钥
	 *
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static String getPublicKey(Map<String, Key> keyMap) throws Exception {
		Key key = keyMap.get(PUBLIC_KEY);
		return encryptBASE64(key.getEncoded());
	}

	/**
	 * 初始化密钥
	 *
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Key> initKey() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		keyPairGen.initialize(1024);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		Map<String, Key> keyMap = new HashMap<String, Key>(2);
		keyMap.put(PUBLIC_KEY, keyPair.getPublic());// 公钥
		keyMap.put(PRIVATE_KEY, keyPair.getPrivate());// 私钥
		return keyMap;
	}

	public static void main(String[] args) throws Exception {
		Map<String, Key> keyMap = RSACoder.initKey();
		String publicKey = RSACoder.getPublicKey(keyMap);
		String privateKey = RSACoder.getPrivateKey(keyMap);
		publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCcDeoixXS61ezgnnFq7uGj6YhtrxPtSyAgb78HyMjDLc4Yup794LMIBpCqcgnqr5Icby82d5ELkabfEuytdYZxwqzQbZx0OgJYvcNBgWjdeQbPotp+3AnRQK+hf9rVh+4JZ4Cp46XhxthphDI6iDLBNVCeq3S0jNQXsGq2qy2HrwIDAQAB";
		privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJwN6iLFdLrV7OCecWru4aPpiG2vE+1LICBvvwfIyMMtzhi6nv3gswgGkKpyCeqvkhxvLzZ3kQuRpt8S7K11hnHCrNBtnHQ6Ali9w0GBaN15Bs+i2n7cCdFAr6F/2tWH7glngKnjpeHG2GmEMjqIMsE1UJ6rdLSM1BewararLYevAgMBAAECgYAMGnwtZDkwgIUVytQrIgn4J5XARBL8lZYRTfl4BhekI7pXaqoIMNRR7AhuX9frDuD7OZhk1hM23X4e0R3wOixKKXe8Z/sX63IP8s7iG4m6YtvCvmVEhITR/KmT0SLM5X9zK4WpweCAN8LDKEETt7awFTbk62IK4NTBl2+Q6NPfYQJBAMt1NwQHHvSt5Sku/lBm0iNUthJe/oT4U4KNDoLHY0SKjOEz3+gOWB/+pR4nsI7rEQKg2maM9RnbXnRvzrqDpPECQQDEWs9d0sg5fsmors77Bq58A+k1BVoTAnUOSTrD7qDvg+MeDhJhAw9a2jl+dLUlbcYPlYUzFA4S2F5eODtfk3afAkA3qcf1eCVF99V8YPj+8kKHnAN8HzUvF+lxDDpuGTLVa7qDWnfluxbtGfP+vgkV8MXknDFkYSglfNjorjY23RFhAkBp/uOGMGzprKnLp2IQXJEAXVQczSDMtjzeU2Wnsz+pTDbkWJBPUkmNr9Ptka7f35xVeU+IPUFDpw16KzzR4VbdAkA1G1QReI83sCaNYa7DJmf6iY+41mZqIaLnXwRrdI0WUi/fUR52bshXAkyh0VaqMEcvlDDcbZfW9rEzCTqOQfji";
		System.out.println("公钥: \n\r" + publicKey);
		System.out.println("私钥： \n\r" + privateKey);
		System.out.println("公钥加密——私钥解密");
		String inputStr = "abc";
		byte[] encodedData = RSACoder.encryptByPublicKey(inputStr, publicKey);
		System.out.println("编码后: " + encryptBASE64(encodedData));
		inputStr = "Mra+k/ZyJ36CaHtWlSPSvVdPvl3Oo7+9xRNOTlZdOFMXlPpZUDVl85a2H8KM9XHxd95ChHXc7RJUtZi38W7fg6uAhSawpSePsly0S5BxapMUfd7apJna/4tyLYsU7g6PJWd5xIKl3xjwc41/Q1DvHbMNoO6fntZu16Icg8xKxW4=";
		encodedData = decryptBASE64(inputStr);
		byte[] decodedData = RSACoder.decryptByPrivateKey(encodedData, privateKey);
		String outputStr = new String(decodedData);
		System.out.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);

	}
}