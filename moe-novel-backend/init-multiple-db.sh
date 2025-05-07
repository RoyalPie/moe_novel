#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE DATABASE iam_db;
    CREATE DATABASE keycloak_db;
    CREATE DATABASE moe_novel_files;
EOSQL

