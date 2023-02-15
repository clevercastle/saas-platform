package org.clevercastle.saas.app.common.auth.authrequest

import io.quarkus.security.credential.TokenCredential
import io.quarkus.security.identity.request.TokenAuthenticationRequest

class SaasTokenAuthenticationRequest(token: TokenCredential) : TokenAuthenticationRequest(token)