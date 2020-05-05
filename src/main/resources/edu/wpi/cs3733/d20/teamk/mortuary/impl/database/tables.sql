create table employees (
    empID varchar(36),
    name varchar(32),
    username varchar(16),
    constraint EMPLOYEE_PK primary key (empID)
);
create table deceased (
    personID varchar(36),
    name varchar(32),
    sex varchar(20),
    age int,
    constraint DECEASED_PK primary key (personID)
);
create table tickets (
    ticketID varchar(36),
    opened varchar(36),
    closed varchar(36),
    requestor varchar(36),
    deceased varchar(36),
    circumstance varchar(32),
    description varchar(256),
    time varchar(36),
    location varchar(256),
    constraint TICKET_PK primary key (ticketID),
    constraint TICKET_EMP_FK foreign key (requestor) references employees (empID) on delete set null,
    constraint TICKET_DEC_FK foreign key (deceased) references deceased (personID) on delete set null
);