INSERT INTO public.entity_type(id, code, name, description)  values (10001, '001', 'entity_type_1', null);
INSERT INTO public.entity_type(id, code, name, description)  values (10002, '002', 'entity_type_2', 'description_2');
INSERT INTO public.entity_type(id, code, name, description)  values (10003, '003', 'entity_type_3', null);
INSERT INTO public.entity_type(id, code, name, description)  values (10004, '004', 'entity_type_4', null);
INSERT INTO public.entity_type(id, code, name, description)  values (10005, '005', 'entity_type_5', null);
INSERT INTO public.entity_type(id, code, name, description)  values (10006, '006', 'entity_type_6', null);
INSERT INTO public.entity_type(id, code, name, description)  values (10007, '007', 'entity_type_7', null);
INSERT INTO public.entity_type(id, code, name, description)  values (10008, '008', 'entity_type_8', null);
INSERT INTO public.entity_type(id, code, name, description)  values (10009, '009', 'entity_type_9', null);
INSERT INTO public.entity_type(id, code, name, description)  values (100010, '010', 'entity_type_10', null);
INSERT INTO public.entity_type(id, code, name, description)  values (100011, '011', 'entity_type_11', null);
INSERT INTO public.entity_type(id, code, name, description)  values (100012, '012', 'entity_type_12', null);
INSERT INTO public.entity_type(id, code, name, description)  values (100013, '013', 'entity_type_13', null);

INSERT INTO public.entity(id, entity_type_id, code) values (10001, 10001, '01');
INSERT INTO public.entity(id, entity_type_id, code) values (10002, 10001, '02');
INSERT INTO public.entity(id, entity_type_id, code) values (10003, 10001, '03');
INSERT INTO public.entity(id, entity_type_id, code) values (10004, 10001, '04');
INSERT INTO public.entity(id, entity_type_id, code) values (10005, 10001, '05');
INSERT INTO public.entity(id, entity_type_id, code) values (10006, 10001, '06');
INSERT INTO public.entity(id, entity_type_id, code) values (10007, 10001, '07');
INSERT INTO public.entity(id, entity_type_id, code) values (10008, 10001, '08');
INSERT INTO public.entity(id, entity_type_id, code) values (10009, 10001, '09');
INSERT INTO public.entity(id, entity_type_id, code) values (100010, 10001, '10');
INSERT INTO public.entity(id, entity_type_id, code) values (100011, 10001, '11');
INSERT INTO public.entity(id, entity_type_id, code) values (100012, 10001, '12');
INSERT INTO public.entity(id, entity_type_id, code) values (100013, 10001, '13');

INSERT INTO public.attribute(id, code, name, description, entity_type_id) values(10001, '01', 'attribute_1', 'description_1', 100013);
INSERT INTO public.metadata(id, data_type, required, repeatable) values(10001, 'STRING', false, false);

INSERT INTO public.attribute(id, code, name, description, entity_type_id) values(10002, '02', 'attribute_2', 'description_2', 100013);
INSERT INTO public.metadata(id, data_type, required, repeatable) values(10002, 'STRING', false, false);

INSERT INTO public.attribute(id, code, name, description, entity_type_id) values(10003, '03', 'attribute_3', 'description_3', 100013);
INSERT INTO public.metadata(id, data_type, required, repeatable) values(10003, 'STRING', false, false);

INSERT INTO public.attribute(id, code, name, description, entity_type_id) values(10004, '04', 'attribute_4', 'description_4', 100013);
INSERT INTO public.metadata(id, data_type, required, repeatable) values(10004, 'STRING', false, false);

INSERT INTO public.attribute(id, code, name, description, entity_type_id) values(10005, '05', 'attribute_51', 'description_5', 100013);
INSERT INTO public.metadata(id, data_type, required, repeatable) values(10005, 'STRING', false, false);

INSERT INTO public.attribute(id, code, name, description, entity_type_id) values(10006, '06', 'attribute_6', 'description_6', 100013);
INSERT INTO public.metadata(id, data_type, required, repeatable) values(10006, 'STRING', false, false);

INSERT INTO public.attribute(id, code, name, description, entity_type_id) values(10007, '07', 'attribute_7', 'description_7', 100013);
INSERT INTO public.metadata(id, data_type, required, repeatable) values(10007, 'STRING', false, false);

INSERT INTO public.attribute(id, code, name, description, entity_type_id) values(10008, '08', 'attribute_8', 'description_1', 100013);
INSERT INTO public.metadata(id, data_type, required, repeatable) values(10008, 'STRING', false, false);

INSERT INTO public.attribute(id, code, name, description, entity_type_id) values(10009, '09', 'attribute_9', 'description_9', 100013);
INSERT INTO public.metadata(id, data_type, required, repeatable) values(10009, 'STRING', false, false);

INSERT INTO public.attribute(id, code, name, description, entity_type_id) values(100010, '10', 'attribute_10', 'description_10', 100013);
INSERT INTO public.metadata(id, data_type, required, repeatable) values(100010, 'STRING', false, false);

INSERT INTO public.attribute(id, code, name, description, entity_type_id) values(100011, '11', 'attribute_11', 'description_11', 100013);
INSERT INTO public.metadata(id, data_type, required, repeatable) values(100011, 'STRING', false, false);