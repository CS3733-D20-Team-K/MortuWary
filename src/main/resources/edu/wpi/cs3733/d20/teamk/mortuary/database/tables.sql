create table employees (
    empID char(36),
    name varchar(32),
    username varchar(16),
    constraint EMPLOYEE_PK primary key (empID),
    constraint EMPLOYEE_USERNAME unique (username)
);
create table deceased (
    personID char(36),
    name varchar(32),
    sex varchar(6),
    age int,
    constraint DECEASED_PK primary key (personID)
);
create table tickets (
    ticketID char(36),
    opened bigint,
    closed bigint,
    requestor char(36),
    deceased char(36),
    circumstance varchar(32),
    description varchar(256),
    time bigint,
    location char(10),
    constraint TICKET_PK primary key (ticketID),
    constraint TICKET_EMP_FK foreign key (requestor) references employees (empID),
    constraint TICKET_DEC_FK foreign key (requestor) references deceased (personID)
);