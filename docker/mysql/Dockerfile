FROM mysql:8.0.22

ADD ./docker/mysql/conf/my.cnf /etc/mysql/conf.d/my.cnf
RUN chmod 644 /etc/mysql/conf.d/my.cnf

CMD ["mysqld"]
