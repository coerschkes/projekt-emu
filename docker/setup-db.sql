create database if not exists emu;

create table if not exists emu.measurementSeries
(
    measurementSeriesId int not null,
    timeInterval        int,
    consumer            varchar(255),
    measurementSize     varchar(255),
    primary key (measurementSeriesId)
);

create table if not exists emu.measurement
(
    measurementId       int not null,
    measurementValue double,
    timeMillis          float,
    measurementSeriesId int not null,
    primary key (measurementId),
    foreign key (measurementSeriesId) references measurementSeries (measurementSeriesId)
);
