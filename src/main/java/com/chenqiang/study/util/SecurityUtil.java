package com.chenqiang.study.util;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;

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
	 * @param rsa
	 *            rsa对象
	 * @param str
	 *            字符串
	 * @return
	 */
	public static String encryptStrByRSA(RSA rsa, String str) {
		String strEncrypted = rsa.encryptStr(str, KeyType.PublicKey);
		return strEncrypted;
	}

	/**
	 * 使用RSA方式解密字符串
	 * 
	 * @param rsa
	 *            rsa对象
	 * @param str
	 *            字符串
	 * @return
	 */
	public static String decryptStrByRSA(RSA rsa, String str) {
		String strDecrypted = rsa.decryptStr(str, KeyType.PrivateKey);
		return strDecrypted;
	}

	/**
	 * 获取base64编码后的私钥
	 * 
	 * @param rsa
	 *            rsa对象
	 * @return
	 */
	public static String getRSAPrivateKeyBase64(RSA rsa) {
		return rsa.getPrivateKeyBase64();
	}

	/**
	 * 获取base64编码后的公钥
	 * 
	 * @param rsa
	 *            rsa对象
	 * @return
	 */
	public static String getRSAPublicKeyBase64(RSA rsa) {
		return rsa.getPublicKeyBase64();
	}

	/**
	 * 生成随机公钥和私钥
	 * 
	 * @return
	 */
	public static String[] generateRSAKeyPair() {
		RSA rsa = new RSA();
		String[] keyPair = new String[] { getRSAPublicKeyBase64(rsa), getRSAPrivateKeyBase64(rsa) };
		return keyPair;
	}

	public static void main(String[] args) {
		String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCWiozudS9MaDN464HNUGOsKlk4fm5k32k+H41APKVH+IKut5cmxq/Fg4z8oLz8uOd9r61tb1vVuJG3x2XhRX/of7p3VRcHbsZRCQ15RwGDx0ngKd3cm25s9VvTNWHIe46y/+fYXtAPCguZqCHONx8dBBWmLXXUOYentjJSugAFzwIDAQAB";
		String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJaKjO51L0xoM3jrgc1QY6wqWTh+bmTfaT4fjUA8pUf4gq63lybGr8WDjPygvPy4532vrW1vW9W4kbfHZeFFf+h/undVFwduxlEJDXlHAYPHSeAp3dybbmz1W9M1Ych7jrL/59he0A8KC5moIc43Hx0EFaYtddQ5h6e2MlK6AAXPAgMBAAECgYAIStV8hSP3132OWZqqzpTO9P45KG24TL4lV+OV+EE6d6vrJ93BmwwvmxF3EPuYCC9oMvcgohOy8BnGv8sS35suA8BSd+E78NEDqfPbPCapicNxGlhxEA9JxuvUUvbvfNVLePrt3cnpofYHRd4vTwpSP+qdrZqhEQljZb53TUpF0QJBANCaLNBrQaNFM98kyj6BZ6fX+uOQMD15fK9jv7rDaTuH9M1P46AMaNcs8NwP6QWakfb8ynJFDCmpEkVWtuJ/iQMCQQC4vx+Hq6S+0FyiWum/5EM8u95XEkoLY0rjADa856pLLN+B/SlQAxZk0T1wxV3Nacm47nr9HnAm9IlOHp6NyAhFAkAVEj3Mn8j1kRv4QmMbhT7POYXiTsJQs2Dg5paLawnKNaWIx7UaiaZddfCuifzj0AVGiap5eHgODUIHNwcqQe+5AkBT/+JwJ2TitY5I65k4fKrtpGX1pQUxpFXaXLCGt7bAM4j0a40rKhw2fsS2z6ZUzXLAuP4GtJ49uBXuBRIGWRsZAkBH9p6Fw3alAriF/tpHxH6VFJ3zf5ExxdzKjlg0oz1J9Z8O54yVKXTCVp/cjPUoCZBqmmGolEkXES+NeMjoVbsj";
//		 String[] keyPair = generateRSAKeyPair();
//		 System.out.println("publicKey:" + keyPair[0]);
//		 System.out.println("privateKey:" + keyPair[1]);
		RSA rsa = new RSA(privateKey, publicKey);
		String pwd = "root";
		String pwdEnc = SecurityUtil.encryptStrByRSA(rsa, pwd);
//		String pwdEnc = "c6LB2joeq3uWd8oOm5VEF65n2nrxw3U95GvlD7RbdPZApOdb6TmsydrZ/axm7VYwJ+ZwQG+qH1mWPFzORaXLugTqUv4H//gpJagDmyU0kTLsAIylOO27fhjaRUy19JSzRhSrqSnkO/P6jmG0yAxEVKTbff8fAmPkIvIjkiMZMdw=";
		System.out.println("pwdEnc:" + pwdEnc);
		String pwdDec = SecurityUtil.decryptStrByRSA(rsa, pwdEnc);
		System.out.println("pwdDec:" + pwdDec);
	}
}
