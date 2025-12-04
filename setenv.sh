#!/bin/bash
# Script này sẽ được Tomcat load để set environment variables

# Database configuration
export DB_URL="${DB_URL:-jdbc:postgresql://dpg-d4nq9h15pdvs73ac3hb0-a.singapore-postgres.render.com:5432/render_db_fagx?characterEncoding=UTF-8}"
export DB_USERNAME="${DB_USERNAME:-render_db_fagx_user}"
export DB_PASSWORD="${DB_PASSWORD:-hVrfapv3nbQ2UUTecQDAXpoxDgpr8Mef}"

# Gmail configuration
export GMAIL_EMAIL="${GMAIL_EMAIL:-nguyendoantruongvi11@gmail.com}"
export GMAIL_APP_PASSWORD="${GMAIL_APP_PASSWORD:-khgnpvmmldmabnbu}"

# Java options - Quote các giá trị để tránh lỗi với dấu cách
export CATALINA_OPTS="$CATALINA_OPTS -DDB_URL=\"$DB_URL\" -DDB_USERNAME=\"$DB_USERNAME\" -DDB_PASSWORD=\"$DB_PASSWORD\""
export CATALINA_OPTS="$CATALINA_OPTS -DGMAIL_EMAIL=\"$GMAIL_EMAIL\" -DGMAIL_APP_PASSWORD=\"$GMAIL_APP_PASSWORD\""
