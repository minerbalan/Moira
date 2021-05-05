CREATE TABLE users
(
    id         bigint unsigned not null auto_increment primary key,
    user_name  varchar(255)    not null,
    email      varchar(511)    not null unique,
    password   varchar(255)    not null,
    created_at datetime        not null,
    updated_at datetime default null
);

CREATE TABLE subscriptions
(
    id              bigint unsigned not null auto_increment primary key,
    url             varchar(255)    not null unique,
    created_at      datetime        not null,
    last_fetched_at datetime,
    updated_at      datetime default null,
    deleted_at      datetime default null
);

CREATE TABLE users_subscriptions
(
    users_id         bigint unsigned not null,
    subscriptions_id bigint unsigned not null,
    name             varchar(255)    not null,
    created_at       datetime        not null,
    primary key (users_id, subscriptions_id),
    foreign key fk_users_subscriptions_users (users_id)
        references users (id) ON UPDATE CASCADE ON DELETE CASCADE,
    foreign key fk_users_subscriptions_subscriptions (subscriptions_id)
        references subscriptions (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE articles
(
    id              bigint unsigned not null auto_increment primary key,
    subscription_id bigint unsigned not null,
    url             varchar(255)    not null default '',
    title           varchar(1024)   not null default '',
    description     mediumtext,
    thumbnail       varchar(1024)            default null,
    created_at      datetime        not null default now(),
    published_at    datetime        not null,
    foreign key fk_articles_subscription_id (subscription_id) references subscriptions (id),
    unique uq_articles (subscription_id, url)
);
