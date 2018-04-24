// 公钥
var publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCWiozudS9MaDN464HNUGOsKlk4fm5k32k+H41APKVH+IKut5cmxq/Fg4z8oLz8uOd9r61tb1vVuJG3x2XhRX/of7p3VRcHbsZRCQ15RwGDx0ngKd3cm25s9VvTNWHIe46y/+fYXtAPCguZqCHONx8dBBWmLXXUOYentjJSugAFzwIDAQAB";
var crypt = new JSEncrypt();
// 设置公钥
crypt.setPublicKey(publicKey);
/**
 * 加密
 * 
 * @param text
 *            明文
 * @returns
 */
function enc(text) {
	return crypt.encrypt(text);
}
/**
 * 解密
 * 
 * @param text
 *            密文
 * @returns
 */
function dec(text) {
	return crypt.decrypt(text);
}
