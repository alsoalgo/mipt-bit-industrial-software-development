#!/bin/bash

function monitor() {
    while true; do
        timestamp=$(date +%s)
        cpuUtil=$(ps -A -o %cpu | awk '{s+=$1} END {print s}')
        memUtil=$(ps -A -o %mem | awk '{m+=$1} END {print m}')
        echo "$timestamp;$cpuUtil;$memUtil;" >> monitor.csv
        sleep 600
    done
}
export -f monitor

function start() {
    bash -c ./clean.sh
    nohup bash -c monitor > /dev/null 2>&1 &
    echo "[start] PID=$!"
    echo $! > pid
}

function status() {
    if pid=$(cat pid); then 
        if ps -p $(cat pid) > /dev/null 2>&1; then
            echo "[status] is running"
        else 
            echo "[status] is not running"
        fi
    fi
}

function kill_subprocesses() {
    local toKill="$1"
    if childs="$(pgrep -P "$toKill")"; then
        for child in $childs; do
            kill_subprocesses "$child"
        done
    fi
    if ps -p $toKill > /dev/null 2>&1; then 
        kill -9 "$toKill"
    fi
}

function stop() {
    echo "[stop] in progress"
    if pid=$(cat pid); then 
        kill_subprocesses $pid
    fi
    echo "[stop] done"
}

function help() {
    echo "[help] usage: $0 START | STATUS | STOP"
}

case $1 in
"START") 
    start
    ;;
"STATUS") 
    status
    ;;
"STOP") 
    stop
    ;;
*)
    help
    ;;
esac
