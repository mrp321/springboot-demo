// 公钥
var publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCcDeoixXS61ezgnnFq7uGj6YhtrxPtSyAgb78HyMjDLc4Yup794LMIBpCqcgnqr5Icby82d5ELkabfEuytdYZxwqzQbZx0OgJYvcNBgWjdeQbPotp+3AnRQK+hf9rVh+4JZ4Cp46XhxthphDI6iDLBNVCeq3S0jNQXsGq2qy2HrwIDAQAB";
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
