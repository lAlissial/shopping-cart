-- password in plaintext: "password"
INSERT INTO USER (user_id, password, email, username, name, last_name, active)
VALUES
  (1, '$2a$06$OAPObzhRdRXBCbk7Hj/ot.jY3zPwR8n7/mfLtKIgTzdJa4.6TwsIm', 'user@mail.com', 'user', 'Name', 'Surname',
   1);
-- password in plaintext: "password"
INSERT INTO USER (user_id, password, email, username, name, last_name, active)
VALUES
  (2, '$2a$06$OAPObzhRdRXBCbk7Hj/ot.jY3zPwR8n7/mfLtKIgTzdJa4.6TwsIm', 'johndoe@gmail.com', 'johndoe', 'John', 'Doe', 1);
-- password in plaintext: "password"
INSERT INTO USER (user_id, password, email, username, name, last_name, active)
VALUES (3, '$2a$06$OAPObzhRdRXBCbk7Hj/ot.jY3zPwR8n7/mfLtKIgTzdJa4.6TwsIm', 'name@gmail.com', 'namesurname', 'Name',
        'Surname', 1);

INSERT INTO ROLE (role_id, role)
VALUES (1, 'ROLE_ADMIN');
INSERT INTO ROLE (role_id, role)
VALUES (2, 'ROLE_USER');

INSERT INTO USER_ROLE (user_id, role_id)
VALUES (1, 1);
INSERT INTO USER_ROLE (user_id, role_id)
VALUES (1, 2);
INSERT INTO USER_ROLE (user_id, role_id)
VALUES (2, 2);
INSERT INTO USER_ROLE (user_id, role_id)
VALUES (3, 2);

INSERT INTO PRODUCT (name, description, quantity, price, type, isbn)
VALUES ('A garota do lago', 'Summit Lake, uma pequena cidade entre montanhas, é esse tipo de lugar, bucólico e com encantadoras casas dispostas à beira de um longo trecho de água intocada.', 5, 8.50, 'book', '9781501299292');
INSERT INTO PRODUCT (name, description, quantity, price, type, isbn)
VALUES ('O guia do mochileiro das galáxias', 'Considerado um dos maiores clássicos da literatura de ficção científica, O Guia do Mochileiro das Galáxias vem encantando gerações de leitores ao redor do mundo com seu humor afiado.', 5, 25.06, 'book', '9781405053976');
INSERT INTO PRODUCT (name, description, quantity, price, type, isbn)
VALUES ('A Biblioteca da Meia-Noite', 'A Biblioteca da Meia-Noite é um romance incrível que fala dos infinitos rumos que a vida pode tomar e da busca incessante pelo rumo certo.', 3, 36.80, 'book', '9786558380634');
INSERT INTO PRODUCT (name, description, quantity, price, type, isbn)
VALUES ('O Retrato de Dorian Gray', 'Único romance escrito por Oscar Wilde, O retrato de Dorian Gray foi publicado em 1890 na revista Lippincott’s Monthly Magazine.', 40, 21.90, 'book', '9780140431872');
INSERT INTO PRODUCT (name, description, quantity, price, type, isbn)
VALUES ('O Pequeno Príncipe', 'As sábias, encantadoras e inesquecíveis histórias contadas pelo pequeno príncipe falam de seu próprio planeta, com seus três vulcões e uma flor presunçosa.', 80, 18.81, 'book', '856709710X');
INSERT INTO PRODUCT (name, description, quantity, price, type)
VALUES ('Apple Magic Mouse', 'Mouse muito bom', 10, 800.00, 'electronic');
INSERT INTO PRODUCT (name, description, quantity, price, type)
VALUES ('Apple Magic Keyboard', 'Teclado muito bom', 10, 850.00, 'electronic');
INSERT INTO PRODUCT (name, description, quantity, price, type)
VALUES ('Apple Watch', 'Relógio muito bom', 10, 1850.00, 'electronic');
INSERT INTO PRODUCT (name, description, quantity, price, type)
VALUES ('IPad Pro', 'Tablet muito bom', 10, 3050.00, 'electronic');
INSERT INTO PRODUCT (name, description, quantity, price, type)
VALUES ('IPhone 13', 'Celular muito bom', 10, 5050.00, 'electronic');