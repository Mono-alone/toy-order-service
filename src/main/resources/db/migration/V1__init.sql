CREATE TABLE If not exists orders (
     id         uuid primary key default gen_random_uuid(),
     status     varchar(50)    not null,
     customer   varchar(255)   not null,
     comment    varchar(500),
     amount     numeric(10, 2) not null,
     created_at timestamp      not null,
     constraint chk_amount check ( amount >= 0 ),
     constraint chk_customer check ( length(trim(customer)) > 0),
     constraint chk_status check ( status in ('NEW', 'PAID', 'SHIPPED', 'CANCELLED'))
);