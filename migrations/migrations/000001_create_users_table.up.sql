CREATE TABLE "users"
(
    id                   varchar(64) NOT NULL PRIMARY KEY,
    email                text        NOT NULL,
    created_at           timestamp,
    updated_at           timestamp,
    created_by           varchar(64),
    updated_by           varchar(64)
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

CREATE TABLE user_workspace_mapping
(
    id                  varchar(64) NOT NULL PRIMARY KEY,
    user_id             varchar(64) NOT NULL,
    workspace_id        varchar(64) NOT NULL,
    "role"              varchar(32) NULL,
    workspace_user_name text        NOT NULL, /* user name in the workspace */
    created_at          timestamp,
    updated_at          timestamp,
    created_by          varchar(64),
    updated_by          varchar(64)
);

create unique index user_workspace_mapping_idx_user_id_workspace_id on user_workspace_mapping (user_id, workspace_id);

CREATE TABLE workspace_team
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

CREATE TABLE user_workspace_team_mapping
(
    id                          varchar(64) NOT NULL PRIMARY KEY,
    user_id                     varchar(64) NOT NULL,
    workspace_id                varchar(64) NOT NULL,
    workspace_team_id           varchar(64) NOT NULL,
    user_in_workspace_team_role varchar(32) NOT NULL,
    created_at                  timestamp,
    updated_at                  timestamp,
    created_by                  varchar(64),
    updated_by                  varchar(64)
);

create unique index user_workspace_team_mapping_idx_user_id_workspace_id_team_id on
    user_workspace_team_mapping (user_id, workspace_id, workspace_team_id);
