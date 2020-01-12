
    create table Calificacion (
        id bigint not null auto_increment,
        parteCuerpo integer,
        sensacion integer,
        id_Usuario bigint,
        primary key (id)
    )

    create table Evento (
        id bigint not null auto_increment,
        descripcion varchar(255),
        frecuencia_id bigint,
        primary key (id)
    )

    create table Foto (
        id bigint not null auto_increment,
        ruta varchar(255),
        primary key (id)
    )

    create table FrecuenciaDeEvento (
        DTYPE varchar(31) not null,
        id bigint not null auto_increment,
        fechaEvento timestamp,
        hora integer,
        limiteDeProximidad integer,
        diaDelMes integer,
        diaDeLaSemana integer,
        primary key (id)
    )

    create table Guardarropa (
        id bigint not null auto_increment,
        primary key (id)
    )

    create table MedioDeNotificacion (
        id bigint not null auto_increment,
        primary key (id)
    )

    create table Prenda (
        id bigint not null auto_increment,
        colorPrimario varchar(255),
        colorSecundario varchar(255),
        nivelAbrigo integer not null,
        tela varchar(255),
        tipo varchar(255),
        usada bit not null,
        foto_id bigint,
        id_Guardarropa bigint,
        primary key (id)
    )

    create table Sugerencia (
        id bigint not null auto_increment,
        estado varchar(255),
        evento_id bigint,
        id_usuario bigint,
        primary key (id)
    )

    create table Sugerencia_Prenda (
        Sugerencia_id bigint not null,
        atuendo_id bigint not null,
        primary key (Sugerencia_id, atuendo_id)
    )

    create table Usuario (
        id bigint not null auto_increment,
        maximoDePrendas integer not null,
        nombreUsuario varchar(255),
        password varchar(255),
        tipo integer,
        ultimaSugerencia_id bigint,
        primary key (id)
    )

    create table Usuario_Evento (
        Usuario_id bigint not null,
        eventos_id bigint not null,
        primary key (Usuario_id, eventos_id)
    )

    create table Usuario_Guardarropa (
        Usuario_id bigint not null,
        guardarropas_id bigint not null,
        primary key (Usuario_id, guardarropas_id)
    )

    create table Usuario_MedioDeNotificacion (
        Usuario_id bigint not null,
        medios_id bigint not null,
        primary key (Usuario_id, medios_id)
    )

    alter table Calificacion 
        add constraint FK_8ybhq1h1jt9cpvnrgbs4sqef0 
        foreign key (id_Usuario) 
        references Usuario (id)

    alter table Evento 
        add constraint FK_pasmjcbogrp9c0w64o6tyx7ea 
        foreign key (frecuencia_id) 
        references FrecuenciaDeEvento (id)

    alter table Prenda 
        add constraint FK_6qi54hm62lqlenehxrjxcm9ct 
        foreign key (foto_id) 
        references Foto (id)

    alter table Prenda 
        add constraint FK_jbilf2j0eptfcsyr2moage01x 
        foreign key (id_Guardarropa) 
        references Guardarropa (id)

    alter table Sugerencia 
        add constraint FK_jpqwgsu9a1iuyvekwncs5lqvv 
        foreign key (evento_id) 
        references Evento (id)

    alter table Sugerencia 
        add constraint FK_8hd8nr0441k1yoiqo751eivn1 
        foreign key (id_usuario) 
        references Usuario (id)

    alter table Sugerencia_Prenda 
        add constraint FK_rrm6nd08lmt06c0s7eag06x7x 
        foreign key (atuendo_id) 
        references Prenda (id)

    alter table Sugerencia_Prenda 
        add constraint FK_1xm70rphp9yt6y0l3opjpaqw3 
        foreign key (Sugerencia_id) 
        references Sugerencia (id)

    alter table Usuario 
        add constraint FK_fi48dh5vahm7ft72li618vdb7 
        foreign key (ultimaSugerencia_id) 
        references Sugerencia (id)

    alter table Usuario_Evento 
        add constraint FK_m22euilx0wlwhtcbyy9acid6t 
        foreign key (eventos_id) 
        references Evento (id)

    alter table Usuario_Evento 
        add constraint FK_75a35oe72ffg57t42fr3578h8 
        foreign key (Usuario_id) 
        references Usuario (id)

    alter table Usuario_Guardarropa 
        add constraint FK_exvaq4sy1rbjcil7xs13w5xs2 
        foreign key (guardarropas_id) 
        references Guardarropa (id)

    alter table Usuario_Guardarropa 
        add constraint FK_d02fvs98p85p43dex3uu6q7bs 
        foreign key (Usuario_id) 
        references Usuario (id)

    alter table Usuario_MedioDeNotificacion 
        add constraint FK_l8wumqbtb0vylwuaexdsvvpbe 
        foreign key (medios_id) 
        references MedioDeNotificacion (id)

    alter table Usuario_MedioDeNotificacion 
        add constraint FK_8woivxcjqidp5b5twg87s8nu0 
        foreign key (Usuario_id) 
        references Usuario (id)
