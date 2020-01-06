#!/bin/sh
HOST=${FTP_HOST}
USER=${FTP_USER}
PASSWD=${FTP_PASSWORD}
PORT=${FTP_PORT}
OLD_FILE="gestor-financas-api-1.0.0.war"
NEW_FILE="api-gestor-financas.war"

cd target
mv $OLD_FILE $NEW_FILE

if [$TRAVIS_BRANCH == "master"]; then
	curl --verbose -T $NEW_FILE -u $USER:$PASSWD ftp://$HOST:$PORT
fi

exit 0
