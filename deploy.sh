#!/bin/sh
HOST=${FTP_HOST}
USER=${FTP_USER}
PASSWD=${FTP_PASSWORD}
PORT=${FTP_PORT}
OLD_FILE="gestor-financas-api-1.0.0.war"
NEW_FILE="api-gestor-financas.war"

cd target
mv $OLD_FILE $NEW_FILE

curl --verbose --progress-bar --ipv4 --connect-timeout 8 --max-time 120 --retry 128 --ftp-ssl --disable-epsv --ftp-pasv -T $NEW_FILE -u $USER:$PASSWD ftp://$HOST:$PORT

exit 0