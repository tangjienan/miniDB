# a Mini DataBase
a mini relational database written in Java, data is stored in file and support ```CREATE SELECT INSERT UPDATE DROP DELETE``` operations.
Only support ```String``` datatype. For each table, a hashmap is created as index for look up primary key.

## Operations

### CREATE

```create table test (a b c primary key a)```

### INSERT

```insert into test (a b c values 10 10 10)```

### SELECT

    select * from test
    ========================TEST=========================
    A               ||B               ||C               ||
    20              ||20              ||20              ||
    10              ||10              ||10              ||
    30              ||30              ||30              ||
    =========================================================

    select * from test where c > 10
    ========================TEST=========================
    A               ||B               ||C               ||
    20              ||20              ||20              ||
    30              ||30              ||30              ||
    =========================================================

### DELETE

WIP

### DROP

WIP

### UPDATE

WIP