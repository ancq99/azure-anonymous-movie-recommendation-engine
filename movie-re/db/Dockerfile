FROM postgres:13.2

EXPOSE 5432
ENV POSTGRES_PASSWORD=123

ADD azure.dump /docker-entrypoint-initdb.d

CMD ["postgres", "-c", "log_statement=all"]


sudo docker exec postgres psql -U postgres my_db_name < dump.sql