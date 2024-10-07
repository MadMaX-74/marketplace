--liquibase formatted sql

--changeset mmaltsev:1 labels:v0.0.1
CREATE TYPE "todo_status_type" AS ENUM ('none', 'in_progress', 'completed');

CREATE TABLE "todos" (
                         "id" text primary key constraint todos_id_length_ctr check (length("id") < 64),
                         "title" text not null constraint todos_title_length_ctr check (length("title") < 128),
                         "description" text constraint todos_description_length_ctr check (length("description") < 4096),
                         "status" todo_status_type not null,
                         "created_date" text,
                         "completed_date" text
);

CREATE INDEX todos_title_idx ON "todos" USING hash ("title");
CREATE INDEX todos_status_idx ON "todos" USING hash ("status");
