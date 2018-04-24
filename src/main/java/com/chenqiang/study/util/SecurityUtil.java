package com.chenqiang.study.util;

import java.security.Key;
import java.util.Map;

/**
 * 安全工具类
 * 
 * @author qchen
 * @date 2018-4-24
 *
 */
public class SecurityUtil {
	/**
	 * 使用RSA方式加密字符串
	 * 
	 * @param str
	 *            字符串
	 * @param publicKey
	 *            公钥
	 * @return
	 * @throws Exception
	 */
	public static String encByRSA(String str, String publicKey) throws Exception {
		byte[] encodedData = RSACoder.encryptByPublicKey(str, publicKey);
		String outputStr = RSACoder.encryptBASE64(encodedData);
		return outputStr;
	}

	/**
	 * 使用RSA方式解密字符串
	 * 
	 * @param str
	 *            字符串
	 * @param privateKey
	 *            私钥
	 * @return
	 * @throws Exception
	 */
	public static String decByRSA(String str, String privateKey) throws Exception {
		byte[] byteB64 = RSACoder.decryptBASE64(str);
		byte[] decodedData = RSACoder.decryptByPrivateKey(byteB64, privateKey);
		String outputStr = new String(decodedData);
		return outputStr;
	}

	/**
	 * 生成随机密钥对
	 * 
	 * @return 数组{私钥, 公钥}
	 * @throws Exception
	 */
	public static String[] generateKeyPair() throws Exception {
		Map<String, Key> keyMap = RSACoder.initKey();
		String publicKey = RSACoder.getPublicKey(keyMap);
		String privateKey = RSACoder.getPrivateKey(keyMap);
		return new String[] { privateKey, publicKey };
	}

}
