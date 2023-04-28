CREATE TABLE async_confirm_code
(
    id          varchar(64) NOT NULL PRIMARY KEY,
    code        varchar(32) NOT NULL,
    type        text        NOT NULL,
    biz_key     text        NOT NULL,
    biz_detail  text,
    description text,
    expired_at  timestamp,
    state       text,
    tags        text,
    notes       text,

    created_at  timestamp,
    updated_at  timestamp,
    created_by  varchar(64),
    updated_by  varchar(64)
);

CREATE INDEX async_confirm_code_udx_code ON async_confirm_code (code);
