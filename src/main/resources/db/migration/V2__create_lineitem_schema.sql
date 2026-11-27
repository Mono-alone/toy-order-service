create table line_items
(
    id       uuid default gen_random_uuid(),
    order_id uuid         ,
    name     varchar(255) not null,
    type     varchar(255) not null,

    constraint pk_line_items primary key (id),
    constraint fk_line_items_orders
        foreign key (order_id) references orders (id)
        on delete cascade,
    constraint chk_line_items_name check ( length(trim(name)) > 0)
);