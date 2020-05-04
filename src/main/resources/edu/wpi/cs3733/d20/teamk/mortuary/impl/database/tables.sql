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
    sex varchar(20),
    age int,
    constraint DECEASED_PK primary key (personID)
);
create table tickets (
    ticketID char(36),
    opened TIMESTAMP,
    closed TIMESTAMP,
    requestor char(36),
    deceased char(36),
    circumstance varchar(32),
    description varchar(256),
    time TIMESTAMP,
    location varchar(256),
    constraint TICKET_PK primary key (ticketID),
    constraint TICKET_EMP_FK foreign key (requestor) references employees (empID) on delete set null,
    constraint TICKET_DEC_FK foreign key (deceased) references deceased (personID) on delete set null
);