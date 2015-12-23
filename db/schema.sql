CREATE TABLE IF NOT EXISTS post(id_post INT primary key auto_increment ,
                  topic VARCHAR(40) not null,
                  message VARCHAR(500)not null);

CREATE TABLE IF NOT EXISTS application_log(id_application_log INT primary key auto_increment ,
                  is_completed BOOLEAN,
                  occurrence VARCHAR(500) not null,
                  result VARCHAR(500) not null);