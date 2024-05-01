INSERT INTO "security"."role" (id, "name") VALUES (nextval('security.role_id_seq'), 'READER');
INSERT INTO "security"."role" (id, "name") VALUES (nextval('security.role_id_seq'), 'ADMINISTRATOR');
INSERT INTO "security"."role" (id, "name") VALUES (nextval('security.role_id_seq'), 'TEST_ROLE_1');
INSERT INTO "security"."role" (id, "name") VALUES (nextval('security.role_id_seq'), 'TEST_ROLE_2');

INSERT INTO "security"."user" (id, email, email_confirmed, enabled, expire_at, "password", username)
VALUES(nextval('security.user_id_seq'), 'admin@eav.eav', true, true, '2030-12-31 00:00:00.000', '$2a$10$iz4lSRkDXgzjBCZupy9IS.D0RfI5HJK9eoCES.YWHwbtj/mGVA0M6', 'admin@eav.eav');
INSERT INTO "security"."user" (id, email, email_confirmed, enabled, expire_at, "password", username)
VALUES(nextval('security.user_id_seq'), 'reader@eav.eav', true, true, '2030-12-31 00:00:00.000', '$2a$10$iz4lSRkDXgzjBCZupy9IS.D0RfI5HJK9eoCES.YWHwbtj/mGVA0M6', 'reader@eav.eav');

INSERT INTO "security"."user_role" (user_id, role_id)
VALUES ((SELECT id FROM "security"."user" WHERE username = 'admin@eav.eav'), (SELECT id from "security"."role" WHERE "name" = 'ADMINISTRATOR'));
INSERT INTO "security"."user_role" (user_id, role_id)
VALUES ((SELECT id FROM "security"."user" WHERE username = 'reader@eav.eav'), (SELECT id from "security"."role" WHERE "name" = 'READER'));

INSERT INTO "public"."entity_type" (id, "code", "name", "description") VALUES (nextval('public.entity_type_id_seq'), 'C', 'car', NULL);
INSERT INTO "public"."entity_type" (id, "code", "name", "description") VALUES (nextval('public.entity_type_id_seq'), 'B', 'book', NULL);

INSERT INTO "public"."attribute" (id, code, description, "name", entity_type_id) VALUES(nextval('public.attribute_id_seq'), 'MAK', NULL, 'make', (SELECT id FROM "public"."entity_type" WHERE "name" = 'car'));
INSERT INTO "public"."attribute" (id, code, description, "name", entity_type_id) VALUES(nextval('public.attribute_id_seq'), 'MOD', NULL, 'model', (SELECT id FROM "public"."entity_type" WHERE "name" = 'car'));
INSERT INTO "public"."attribute" (id, code, description, "name", entity_type_id) VALUES(nextval('public.attribute_id_seq'), 'COL', NULL, 'color', (SELECT id FROM "public"."entity_type" WHERE "name" = 'car'));

INSERT INTO "public"."attribute" (id, code, description, "name", entity_type_id) VALUES(nextval('public.attribute_id_seq'), 'ISBN', NULL, 'isbn', (SELECT id FROM "public"."entity_type" WHERE "name" = 'book'));
INSERT INTO "public"."attribute" (id, code, description, "name", entity_type_id) VALUES(nextval('public.attribute_id_seq'), 'AUT', NULL, 'author', (SELECT id FROM "public"."entity_type" WHERE "name" = 'book'));
INSERT INTO "public"."attribute" (id, code, description, "name", entity_type_id) VALUES(nextval('public.attribute_id_seq'), 'TIT', NULL, 'title', (SELECT id FROM "public"."entity_type" WHERE "name" = 'book'));
INSERT INTO "public"."attribute" (id, code, description, "name", entity_type_id) VALUES(nextval('public.attribute_id_seq'), 'PYE', NULL, 'publish_year', (SELECT id FROM "public"."entity_type" WHERE "name" = 'book'));

INSERT INTO public.metadata (id, data_type, max_length, max_value, min_length, min_value, "repeatable", required, sub_attribute_ids)
VALUES((SELECT id FROM "public"."attribute" WHERE "name" = 'make' AND entity_type_id =  (SELECT id FROM "public"."entity_type" WHERE "name" = 'car')), 'STRING', NULL, NULL, NULL, NULL, false, true, '[]'::jsonb);
INSERT INTO public.metadata (id, data_type, max_length, max_value, min_length, min_value, "repeatable", required, sub_attribute_ids)
VALUES((SELECT id FROM "public"."attribute" WHERE "name" = 'model' AND entity_type_id =  (SELECT id FROM "public"."entity_type" WHERE "name" = 'car')), 'STRING', NULL, NULL, NULL, NULL, false, true, '[]'::jsonb);
INSERT INTO public.metadata (id, data_type, max_length, max_value, min_length, min_value, "repeatable", required, sub_attribute_ids)
VALUES((SELECT id FROM "public"."attribute" WHERE "name" = 'color' AND entity_type_id =  (SELECT id FROM "public"."entity_type" WHERE "name" = 'car')), 'STRING', NULL, NULL, NULL, NULL, false, true, '[]'::jsonb);

INSERT INTO public.metadata (id, data_type, max_length, max_value, min_length, min_value, "repeatable", required, sub_attribute_ids)
VALUES((SELECT id FROM "public"."attribute" WHERE "name" = 'isbn' AND entity_type_id =  (SELECT id FROM "public"."entity_type" WHERE "name" = 'book')), 'STRING', NULL, NULL, NULL, NULL, false, true, '[]'::jsonb);
INSERT INTO public.metadata (id, data_type, max_length, max_value, min_length, min_value, "repeatable", required, sub_attribute_ids)
VALUES((SELECT id FROM "public"."attribute" WHERE "name" = 'author' AND entity_type_id =  (SELECT id FROM "public"."entity_type" WHERE "name" = 'book')), 'STRING', NULL, NULL, NULL, NULL, false, true, '[]'::jsonb);
INSERT INTO public.metadata (id, data_type, max_length, max_value, min_length, min_value, "repeatable", required, sub_attribute_ids)
VALUES((SELECT id FROM "public"."attribute" WHERE "name" = 'title' AND entity_type_id =  (SELECT id FROM "public"."entity_type" WHERE "name" = 'book')), 'STRING', NULL, NULL, NULL, NULL, false, true, '[]'::jsonb);
INSERT INTO public.metadata (id, data_type, max_length, max_value, min_length, min_value, "repeatable", required, sub_attribute_ids)
VALUES((SELECT id FROM "public"."attribute" WHERE "name" = 'publish_year' AND entity_type_id =  (SELECT id FROM "public"."entity_type" WHERE "name" = 'book')), 'INTEGER', NULL, NULL, NULL, NULL, false, false, '[]'::jsonb);

INSERT INTO "security"."attribute_permission" (actions, attribute_id, role_id)
VALUES('["READ"]'::jsonb, (SELECT id FROM "public"."attribute" WHERE "name" = 'make' AND entity_type_id =  (SELECT id FROM "public"."entity_type" WHERE "name" = 'car')), (SELECT id FROM "security"."role" WHERE "name" = 'READER'));
INSERT INTO "security"."attribute_permission" (actions, attribute_id, role_id)
VALUES('["READ"]'::jsonb, (SELECT id FROM "public"."attribute" WHERE "name" = 'model' AND entity_type_id =  (SELECT id FROM "public"."entity_type" WHERE "name" = 'car')), (SELECT id FROM "security"."role" WHERE "name" = 'READER'));
INSERT INTO "security"."attribute_permission" (actions, attribute_id, role_id)
VALUES('["READ"]'::jsonb, (SELECT id FROM "public"."attribute" WHERE "name" = 'isbn' AND entity_type_id =  (SELECT id FROM "public"."entity_type" WHERE "name" = 'book')), (SELECT id FROM "security"."role" WHERE "name" = 'READER'));
INSERT INTO "security"."attribute_permission" (actions, attribute_id, role_id)
VALUES('["READ"]'::jsonb, (SELECT id FROM "public"."attribute" WHERE "name" = 'author' AND entity_type_id =  (SELECT id FROM "public"."entity_type" WHERE "name" = 'book')), (SELECT id FROM "security"."role" WHERE "name" = 'READER'));
INSERT INTO "security"."attribute_permission" (actions, attribute_id, role_id)
VALUES('["READ"]'::jsonb, (SELECT id FROM "public"."attribute" WHERE "name" = 'title' AND entity_type_id =  (SELECT id FROM "public"."entity_type" WHERE "name" = 'book')), (SELECT id FROM "security"."role" WHERE "name" = 'READER'));
INSERT INTO "security"."attribute_permission" (actions, attribute_id, role_id)
VALUES('["READ"]'::jsonb, (SELECT id FROM "public"."attribute" WHERE "name" = 'publish_year' AND entity_type_id =  (SELECT id FROM "public"."entity_type" WHERE "name" = 'book')), (SELECT id FROM "security"."role" WHERE "name" = 'READER'));
INSERT INTO "security"."attribute_permission" (actions, attribute_id, role_id)
VALUES('["READ", "CREATE"]'::jsonb, (SELECT id FROM "public"."attribute" WHERE "name" = 'make' AND entity_type_id =  (SELECT id FROM "public"."entity_type" WHERE "name" = 'car')), (SELECT id FROM "security"."role" WHERE "name" = 'ADMINISTRATOR'));
INSERT INTO "security"."attribute_permission" (actions, attribute_id, role_id)
VALUES('["READ", "CREATE", "UPDATE", "DELETE"]'::jsonb, (SELECT id FROM "public"."attribute" WHERE "name" = 'model' AND entity_type_id =  (SELECT id FROM "public"."entity_type" WHERE "name" = 'car')), (SELECT id FROM "security"."role" WHERE "name" = 'ADMINISTRATOR'));
INSERT INTO "security"."attribute_permission" (actions, attribute_id, role_id)
VALUES('["READ", "CREATE", "DELETE"]'::jsonb, (SELECT id FROM "public"."attribute" WHERE "name" = 'color' AND entity_type_id =  (SELECT id FROM "public"."entity_type" WHERE "name" = 'car')), (SELECT id FROM "security"."role" WHERE "name" = 'ADMINISTRATOR'));

INSERT INTO "public"."entity" (id, entity_type_id, code, description)
VALUES (nextval('public.entity_id_seq'), (SELECT id FROM "public"."entity_type" WHERE "name" = 'car'), 'C1', NULL);

INSERT INTO "public"."attribute_value" (value, "position", attribute_id, entity_id)
VALUES('Ford', NULL, (SELECT id FROM "public"."attribute" WHERE "name" = 'make' AND entity_type_id = (SELECT id FROM "public"."entity_type" WHERE "name" = 'car')), (SELECT id from "public"."entity" LIMIT 1));
INSERT INTO "public"."attribute_value" (value, "position", attribute_id, entity_id)
VALUES('Puma', NULL, (SELECT id FROM "public"."attribute" WHERE "name" = 'model' AND entity_type_id = (SELECT id FROM "public"."entity_type" WHERE "name" = 'car')), (SELECT id from "public"."entity" LIMIT 1));
INSERT INTO "public"."attribute_value" (value, "position", attribute_id, entity_id)
VALUES('red', NULL, (SELECT id FROM "public"."attribute" WHERE "name" = 'color' AND entity_type_id = (SELECT id FROM "public"."entity_type" WHERE "name" = 'car')), (SELECT id from "public"."entity" LIMIT 1));
