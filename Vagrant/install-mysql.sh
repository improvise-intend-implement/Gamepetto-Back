#!/usr/bin/env bash

DBHOST=localhost
DBNAME=gamepetto
DBUSER=gamepetto
DBPASSWORD=development_random_password
GAMEPETTO_BACK_PROFILE=dev

apt-get update
apt-get install --yes mariadb-server
/etc/init.d/mysql restart

mysql -u root -e "CREATE DATABASE $DBNAME;"
mysql -u root -e "CREATE USER '$DBUSER'@'$DBHOST' IDENTIFIED BY '$DBPASSWORD';"
mysql -u root -e "GRANT ALL PRIVILEGES ON $DBNAME.* TO '$DBUSER'@'$DBHOST'; FLUSH PRIVILEGES;"
