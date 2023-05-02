CREATE TABLE "users"
(
    id         varchar(64) NOT NULL PRIMARY KEY,
    email      text        NOT NULL,
    created_at timestamp,
    updated_at timestamp,
    created_by varchar(64),
    updated_by varchar(64)
);


CREATE TABLE user_oidc_mapping
(
    id            varchar(64) NOT NULL PRIMARY KEY,
    user_id       varchar(64) NOT NULL,
    user_sub      text        NOT NULL,
    oidc_provider varchar(64) NOT NULL,
    created_at    timestamp,
    updated_at    timestamp,
    created_by    varchar(64),
    updated_by    varchar(64)
);
CREATE INDEX user_oidc_mapping_idx_user_sub ON user_oidc_mapping (user_sub);

CREATE TABLE workspace
(
    id         varchar(64) NOT NULL PRIMARY KEY,
    name       text        NOT NULL,
    created_at timestamp,
    updated_at timestamp,
    created_by varchar(64),
    updated_by varchar(64)
);

CREATE TABLE account
(
    id           varchar(64) NOT NULL PRIMARY KEY,
    user_id      varchar(64) NOT NULL,
    workspace_id varchar(64) NOT NULL,
    "role"       varchar(32) NULL,
    name         text        NOT NULL, /* user name in the workspace */
    created_at   timestamp,
    updated_at   timestamp,
    created_by   varchar(64),
    updated_by   varchar(64)
);

create unique index account_udx_user_id_workspace_id on account (user_id, workspace_id);

CREATE TABLE department
(
    id           varchar(64) NOT NULL PRIMARY KEY,
    workspace_id varchar(64) NOT NULL,
    name         text        NOT NULL,
    description  text        NULL,

    created_at   timestamp,
    updated_at   timestamp,
    created_by   varchar(64),
    updated_by   varchar(64)
);

CREATE TABLE account_department_mapping
(
    id           varchar(64) NOT NULL PRIMARY KEY,
    account_id   varchar(64) NOT NULL,
    workspace_id varchar(64) NOT NULL,
    "role"       varchar(64) NOT NULL,
    created_at   timestamp,
    updated_at   timestamp,
    created_by   varchar(64),
    updated_by   varchar(64)
);

create unique index account_department_mapping_udx_user_id_workspace_id_team_id on
    account_department_mapping (account_id, workspace_id);

CREATE TABLE team
(
    id           varchar(64) NOT NULL PRIMARY KEY,
    workspace_id varchar(64) NOT NULL,
    name         text        NOT NULL,
    description  text        NULL,

    created_at   timestamp,
    updated_at   timestamp,
    created_by   varchar(64),
    updated_by   varchar(64)
);

CREATE TABLE account_team_mapping
(
    id         varchar(64) NOT NULL PRIMARY KEY,
    account_id varchar(64) NOT NULL,
    team_id    varchar(64) NOT NULL,
    "role"     varchar(64) NOT NULL,
    created_at timestamp,
    updated_at timestamp,
    created_by varchar(64),
    updated_by varchar(64)
);

create unique index account_team_mapping_udx_user_id_workspace_id_team_id on
    account_team_mapping (account_id, team_id);