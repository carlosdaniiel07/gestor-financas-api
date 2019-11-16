#!/bin/sh
touch teste.txt

HOST=${FTP_HOST}
USER=${FTP_USER}
PASSWD=${FTP_PASSWORD}
FILE='teste.txt'

ftp -n $HOST <<END_SCRIPT
quote USER $USER
quote PASS $PASSWD
binary
put $FILE
quit
END_SCRIPT
exit 0