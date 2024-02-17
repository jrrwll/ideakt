package org.dreamcat.ideatk.util;

import com.intellij.credentialStore.CredentialAttributes;
import com.intellij.credentialStore.CredentialAttributesKt;
import com.intellij.credentialStore.Credentials;
import com.intellij.ide.passwordSafe.PasswordSafe;
import org.jetbrains.annotations.Nullable;

/**
 * 储存秘钥到本地，储存格式依赖于OS
 * Mac：钥匙串访问 - 默认钥匙串 - 登录 - 密码，IntelliJ Platform...
 *
 * @author Jerry Will
 * @version 2024-02-11
 * @see Credentials#getPasswordAsString()
 */
public class PasswordSafeUtil {

    public static @Nullable String getPassword(String key) {
        CredentialAttributes attributes = createCredentialAttributes(key);
        // get password only
        return PasswordSafe.getInstance().getPassword(attributes);
    }

    public static @Nullable Credentials getCredentials(String key) {
        CredentialAttributes attributes = createCredentialAttributes(key);
        return PasswordSafe.getInstance().get(attributes);
    }

    public static void storePassword(String key, String username, @Nullable String password) {
        CredentialAttributes attributes = createCredentialAttributes(key);
        Credentials credentials = new Credentials(username, password);
        PasswordSafe.getInstance().set(attributes, credentials);
    }

    public static void clearPassword(String key) {
        CredentialAttributes attributes = createCredentialAttributes(key);
        PasswordSafe.getInstance().set(attributes, null);
    }

    private static CredentialAttributes createCredentialAttributes(String key) {
        return new CredentialAttributes(
                CredentialAttributesKt.generateServiceName("Default", key)
        );
    }
}
