#!/bin/bash
cd "$(dirname "$0")"

PATH_TO_CODE_BASE=`pwd`

for i in "$@"; do
    case $i in
        -D*=*)
        JAVA_OPTS="$JAVA_OPTS $i"
        ;;
        *)
        break
        ;;
    esac
done

MAIN_CLASS="ar.edu.itba.pod.client.queries.Query3"


java  $JAVA_OPTS -cp 'lib/jars/*' $MAIN_CLASS $*
