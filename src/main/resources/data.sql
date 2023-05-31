insert into users(user_id, user_type, user_name, role, user_password, user_addr, create_date, update_date) values(1, "ADMINISTRATOR", "test_admin", "ADMINISTRATOR" ,"1234", "korea", NOW(), NOW());
insert into event(event_id, administrator_id, event_type, create_date, update_date) values(1, 1, "TIMEDEAL", NOW(), NOW());
insert into publish_event(publish_event_id, event_code, event_desc, event_name, event_start_time, event_end_time, event_status, event_id, create_date, update_date) values(1, "TIMEDEAL", 20, "2023/06 TIMEDEAL", "2023-05-30 00:00:00.000000", "2023-06-30 23:59:59.000000", "IN_PROGRESS", 1, NOW(), NOW());

