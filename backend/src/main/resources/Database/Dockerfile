# hints from https://www.finalmarco.com/build-a-docker-mysql-8-0-28-pre-configured-for-node/

FROM mysql:8

# doing so will actually preinstall guestbook.sql (and all other files in preload_sql_scripts folder
COPY ./preload_sql_scripts/* /docker-entrypoint-initdb.d/