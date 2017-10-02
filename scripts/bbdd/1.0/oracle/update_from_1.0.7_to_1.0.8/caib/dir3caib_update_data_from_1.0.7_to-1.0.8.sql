drop SEQUENCE DIR_SEQ_ALL;
create sequence DIR_SEQ_ALL;
grant select on DIR_SEQ_ALL to www_dir3caib;

BEGIN
  FOR c IN (SELECT table_name, constraint_name FROM user_constraints WHERE constraint_type = 'R')
  LOOP
    EXECUTE IMMEDIATE ('alter table ' || c.table_name || ' disable constraint ' || c.constraint_name);
  END LOOP;
  FOR c IN (SELECT table_name FROM user_tables)
  LOOP
    EXECUTE IMMEDIATE ('truncate table ' || c.table_name);
  END LOOP;
  FOR c IN (SELECT table_name, constraint_name FROM user_constraints WHERE constraint_type = 'R')
  LOOP
    EXECUTE IMMEDIATE ('alter table ' || c.table_name || ' enable constraint ' || c.constraint_name);
  END LOOP;
END;