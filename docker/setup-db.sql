create database if not exists emu;

create table if not exists emu.measurement
(
    measurementId       int not null,
    measurementValue double,
    timeMillis          bigint,
    measurementSeriesId int not null,
    primary key (measurementId, measurementSeriesId),
    foreign key (measurementSeriesId) references measurementSeries (measurementSeriesId)
);

create table if not exists emu.measurementSeries
(
    measurementSeriesId int not null,
    timeInterval        int,
    consumer            varchar(255),
    measurementSize     varchar(255),
    primary key (measurementSeriesId)
);

insert into emu.measurementSeries(measurementSeriesId, timeInterval, consumer, measurementSize) values (1, 5000, "Smartphone", "Leistung");