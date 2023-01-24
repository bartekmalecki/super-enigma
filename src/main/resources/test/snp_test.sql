drop table if exists socialnetworkpost CASCADE;
create table socialnetworkpost (id bigint auto_increment not null, author varchar(255), content varchar(255), post_date timestamp, view_count bigint, primary key (id));


insert into socialnetworkpost(post_date, author, content, view_count) values ('2023-01-22T22:54:25.459852', 'author1', 'content1', 22);
insert into socialnetworkpost(post_date, author, content, view_count) values ('2023-01-20T22:54:25.459854', 'author2', 'content2', 22);
insert into socialnetworkpost(post_date, author, content, view_count) values ('2023-01-20T22:54:25.459852', 'author3', 'content3', 22);
insert into socialnetworkpost(post_date, author, content, view_count) values ('2023-01-18T22:54:25.459852', 'author4', 'content4', 22);
insert into socialnetworkpost(post_date, author, content, view_count) values ('2023-01-18T22:54:25.459852', 'author5', 'content5', 23);
insert into socialnetworkpost(post_date, author, content, view_count) values ('2023-01-17T22:54:25.459852', 'author6', 'content6', 22);
insert into socialnetworkpost(post_date, author, content, view_count) values ('2023-01-16T22:54:25.459852', 'author7', 'content7', 22);
insert into socialnetworkpost(post_date, author, content, view_count) values ('2023-01-16T22:54:25.459853', 'author8', 'content8', 22);
insert into socialnetworkpost(post_date, author, content, view_count) values ('2023-01-15T22:54:25.459854', 'author9', 'content9', 22);
insert into socialnetworkpost(post_date, author, content, view_count) values ( '2023-01-15T22:54:25.459852', 'author10', 'content10', 22);
insert into socialnetworkpost(post_date, author, content, view_count) values ('2023-01-12T22:54:25.459852', 'author11', 'content11', 22);
insert into socialnetworkpost(post_date, author, content, view_count) values ('2023-01-21T22:54:25.459852', 'author12', 'content12', 22);