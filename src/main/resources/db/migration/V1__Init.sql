CREATE TABLE csv_data (
    id bigserial primary key,
   file_name  text NOT NULL,
  line_number bigint NOT NULL,
  data jsonb NOT NULL
);

