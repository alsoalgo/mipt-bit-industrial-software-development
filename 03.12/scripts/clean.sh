GREEN='\033[0;32m'
PURPLE='\033[0;35m'
RED='\033[0;31m'
LIGHT_RED='\033[1;31m'
LIGHT_GRAY='\033[0;37m'
LIGHT_GREEN='\033[1;32m'
LIGHT_BLUE='\033[1;34m'
LIGHT_PURPLE='\033[1;35m'
YELLOW='\033[1;33m'
NC='\033[0m'

INFO="${LIGHT_GRAY}info    | ${NC}"
ERROR="${LIGHT_RED}error   | ${NC}"
SUCCESS="${GREEN}success | ${NC}"

echo "${INFO} Stopping manager..."
docker stop manager > /dev/null
docker rm manager > /dev/null
docker rmi alsoalgo/manager > /dev/null

echo "${INFO} Stopping host..."
docker stop host > /dev/null
docker rm host > /dev/null
docker rmi alsoalgo/host > /dev/null

echo "${INFO} Removing volume..."
docker volume rm common-dir > /dev/null

echo "${INFO} Removing docker network..."
docker network rm internal-net > /dev/null

echo "${INFO} Stopping docker-compose..."
docker-compose down > /dev/null

