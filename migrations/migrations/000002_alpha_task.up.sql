CREATE TABLE alpha_task_project
(
    id           varchar(64) NOT NULL PRIMARY KEY,
    workspace_id varchar(64) NOT NULL,
    "name"       varchar(64) NOT NULL,
    owner_id     varchar(64) NOT NULL,
    owner_type   varchar(32) NOT NULL,

    liked        bool        NOT NULL,

    created_at   timestamp,
    updated_at   timestamp,
    created_by   varchar(64),
    updated_by   varchar(64)
);

CREATE TABLE alpha_task
(
    id           varchar(64)  NOT NULL PRIMARY KEY,
    workspace_id varchar(64)  NOT NULL,
    owner_id     varchar(64)  NULL,
    owner_type   varchar(32)  NULL,

    project_id   varchar(64)  NOT NULL,

    "name"       varchar(64)  NOT NULL,
    status       varchar(16)  NOT NULL,
    task_path    varchar(256) NOT NULL,

    group_id     varchar(64)  NOT NULL,
    "version"    int          NOT NULL,


    created_at   timestamp,
    updated_at   timestamp,
    created_by   varchar(64),
    updated_by   varchar(64)
);

CREATE INDEX alpha_task_idx_group_id ON alpha_task (group_id);
