CREATE TABLE "users" (
    id char(64) NOT NULL PRIMARY KEY,
    default_tenant_id char(64) NOT NULL,
    email char(255) NOT NULL,
    created_at timestamp,
    updated_at timestamp,
    created_by char(64),
    updated_by char(64)
);
CREATE INDEX user_idx_tenant_id ON "users"(default_tenant_id);

CREATE TABLE user_oidc_mapping (
    id SERIAL NOT NULL PRIMARY KEY,
    user_id char(64) NOT NULL,
    user_sub char(64) NOT NULL,
    created_at timestamp,
    updated_at timestamp,
    created_by char(64),
    updated_by char(64)
);
CREATE INDEX user_oidc_mapping_idx_user_sub ON user_oidc_mapping(user_sub);

CREATE TABLE tenant (
    id char(64) NOT NULL PRIMARY KEY,
    name char(255) NOT NULL,
    created_at timestamp,
    updated_at timestamp,
    created_by char(64),
    updated_by char(64)
);

CREATE TABLE user_tenant_mapping (
    id SERIAL NOT NULL PRIMARY KEY,
    user_id char(64) NOT NULL,
    tenant_id char(64) NOT NULL,
    role char(16) NOT NULL,
    tenant_user_id char(64) NOT NULL,
    tenant_user_name char(255) NOT NULL, /* user name in the tenant */
    created_at timestamp,
    updated_at timestamp,
    created_by char(64),
    updated_by char(64)
);