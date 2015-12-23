CREATE TABLE IF NOT EXISTS post(id_post INT primary key auto_increment ,
                  topic VARCHAR(40) not null,
                  message VARCHAR(500)not null);