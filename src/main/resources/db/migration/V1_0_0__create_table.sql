CREATE TABLE subscriptions
(
    id              bigint unsigned not null auto_increment primary key,
    name            varchar(255)    not null,
    url             varchar(255)    not null unique,
    created_at      datetime        not null,
    last_fetched_at datetime        not null default '1970/1/1',
    updated_at      datetime                 default null,
    deleted_at      datetime                 default null
);

CREATE TABLE articles
(
    id              bigint unsigned not null auto_increment primary key,
    subscription_id bigint unsigned not null,
    url             varchar(255)    not null default '',
    title           varchar(1024)   not null default '',
    description     mediumtext      not null default '',
    thumbnail       varchar(1024)            default null,
    created_at      datetime        not null default now(),
    published_at    datetime        not null,
    foreign key fk_articles_subscription_id (subscription_id) references subscriptions (id),
    unique uq_articles (subscription_id, url)
);