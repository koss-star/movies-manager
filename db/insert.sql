insert into countries
values (default, 'Russia');

insert into persons
values (default, 'Frank Darabont',
        1.83,
        (select id from countries where name = 'France'),
        60);

insert into movies
values (default,
        'admin',
        'The Green Mile',
        1999,
        (select id from countries where name = 'USA'),
        (select id from moviegenres where name = 'Drama'),
        (select id from persons where name = 'Frank Darabont'),
        60000000,
        286801374,
        (select id from mpaaratings where name = 'R'),
        189,
        0,
        (select current_timestamp));