insert into course(id, name, created_at, updated_at, is_deleted) values (10001, 'JPA curso udemy', sysdate(), sysdate(), false);
insert into course(id, name, created_at, updated_at, is_deleted) values (10002, 'Livro de Ouro da Liderança', sysdate(), sysdate(), false);
insert into course(id, name, created_at, updated_at, is_deleted) values (10003, 'Bootcamp Rocketseat 12.0', sysdate(), sysdate(), false);
insert into course(id, name, created_at, updated_at, is_deleted) values (10004, 'PHP with laravel', sysdate(), sysdate(), false);
insert into course(id, name, created_at, updated_at, is_deleted) values (10005, 'NodeJS, ReactJS e Rect Native Courses', sysdate(), sysdate(), false);
insert into course(id, name, created_at, updated_at, is_deleted) values (10006, 'Course 1', sysdate(), sysdate(), false);
insert into course(id, name, created_at, updated_at, is_deleted) values (10007, 'Course 2', sysdate(), sysdate(), false);
insert into course(id, name, created_at, updated_at, is_deleted) values (10008, 'Course 3', sysdate(), sysdate(), false);

insert into passport(id, number, created_at, updated_at) values (40001, 'E123456', sysdate(), sysdate());
insert into passport(id, number, created_at, updated_at) values (40002, 'F987654', sysdate(), sysdate());
insert into passport(id, number, created_at, updated_at) values (40003, 'G159753', sysdate(), sysdate());

insert into student(id, name, passport_id, created_at, updated_at) values (20001, 'Jonh Doe', 40001, sysdate(), sysdate());
insert into student(id, name, passport_id, created_at, updated_at) values (20002, 'Jonh Tre', 40002, sysdate(), sysdate());
insert into student(id, name, passport_id, created_at, updated_at) values (20003, 'Jonh Qua', 40003, sysdate(), sysdate());

insert into review(id, rating, description, course_id, created_at, updated_at) values (50001, 'FOUR', 'Great course', 10001, sysdate(), sysdate());
insert into review(id, rating, description, course_id, created_at, updated_at) values (50002, 'FIVE', 'Wonderful course', 10001, sysdate(), sysdate());
insert into review(id, rating, description, course_id, created_at, updated_at) values (50003, 'THREE', 'Normal course', 10003, sysdate(), sysdate());

insert into student_course(student_id, course_id) values (20001, 10001);
insert into student_course(student_id, course_id) values (20001, 10002);
insert into student_course(student_id, course_id) values (20002, 10001);
insert into student_course(student_id, course_id) values (20002, 10002);
insert into student_course(student_id, course_id) values (20002, 10003);
insert into student_course(student_id, course_id) values (20003, 10003);