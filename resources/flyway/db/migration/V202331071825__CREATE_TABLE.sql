create table leisure_item (
        leisure_item_id bigint not null auto_increment,
        item varchar(255) not null,
        uuid BINARY(16) not null,
        primary key (leisure_item_id)
    );

create table property (
        property_id bigint not null auto_increment,
        building_area integer not null,
        city varchar(100) not null,
        complement varchar(100),
        condominium_price decimal(38,2),
        country varchar(100) not null,
        description varchar(255) not null,
        garage integer not null,
        house_number integer not null,
        is_condominium bit,
        is_furnished bit,
        is_rent bit,
        is_sale bit,
        land_size integer not null,
        neighborhood varchar(150),
        property_kind varchar(255) not null,
        registration varchar(255) not null,
        rent_price decimal(38,2),
        rooms integer not null,
        sale_price decimal(38,2),
        state varchar(2) not null,
        street_name varchar(255) not null,
        taxes decimal(38,2) not null,
        uuid BINARY(16) not null,
        zipcode varchar(50) not null,
        primary key (property_id)
    );

create table property_leisure_item (
        property_id bigint not null,
        leisure_item_id bigint not null,
        primary key (property_id, leisure_item_id)
    );

create index idx_leisure_item_entity on leisure_item (leisure_item_id, uuid, item);

alter table leisure_item add constraint UK_constraint_leisure_item_item unique (item);

alter table leisure_item add constraint UK_constraint_leisure_item_uuid unique (uuid);

create index idx_property_entity on property (property_id, uuid, registration);

alter table property add constraint UK_constraint_property_registration unique (registration);

alter table property add constraint UK_constraint_property_uuid unique (uuid);

alter table property_leisure_item add constraint FK_leisure_item_id foreign key (leisure_item_id) references leisure_item (leisure_item_id);

alter table property_leisure_item add constraint FK_property_id foreign key (property_id) ;