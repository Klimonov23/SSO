--liquibase formatted sql

--changeset kklimonov:user-roles-1
CREATE SEQUENCE IF NOT EXISTS sso.user_roles_sq START 1;

--changeset kklimonov:user-roles-2
CREATE TABLE IF NOT EXISTS sso.user_roles
(
    user_role_id UUID                        NOT NULL DEFAULT uuid_generate_v4(),
    user_id      UUID                        NOT NULL,
    role_id      UUID                        NOT NULL,

    created_by   VARCHAR(50)                 NOT NULL DEFAULT 'system',
    created_date TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT current_timestamp,
    constraint user_roles_pk PRIMARY KEY (user_role_id)
    );

COMMENT ON TABLE sso.user_roles IS 'Маппинг пользователей и ролей';
COMMENT ON COLUMN sso.user_roles.user_role_id IS 'УИ записи';
COMMENT ON COLUMN sso.user_roles.user_id IS 'Уникальный идентификатор пользователя';
COMMENT ON COLUMN sso.user_roles.role_id IS 'Уникальный идентификатор роли';

COMMENT ON column sso.user_roles.created_by IS 'Логин пользователя, создавшего запись';
COMMENT ON column sso.user_roles.created_date IS 'Дата создания записи';

--changeset kklimonov:user-roles-3
CREATE UNIQUE INDEX idx_user_roles_u1 ON sso.user_roles (user_id, role_id);