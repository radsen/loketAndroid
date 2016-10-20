package com.kzlabs.loket.authentication;

import android.os.Parcel;

/**
 * Created by radsen on 10/19/16.
 */

public interface AuthConstants {
    // Account type id
    String ACCOUNT_TYPE = "co.loket";

    // Account name
    String ACCOUNT_NAME = "Loket";

    // Auth token type
    String AUTH_TOKEN_TYPE = ACCOUNT_TYPE;

    // Auth key for Digits.
    String PROVIDER = "X-Auth-Service-Provider";

    // Auth key for Digits.
    String CREDENTIALS = "X-Verify-Credentials-Authorization";
}
