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

cd ..

# Создание сети

echo "${INFO} Creating network..."
docker network create internal-net > /dev/null

# Создание общего volume

echo "${INFO} Creating volume..."
docker volume create common-dir > /dev/null

# Создание образа менеджера 

echo "${INFO} Building manager..."
docker build -f ./docker/Dockerfile -t alsoalgo/manager .

# Подъем менеджера

echo "${INFO} Starting manager..."
docker-compose up -d manager

# Получение IP-адреса менеджера
# touch ips
# cho "manager_ip=$(docker exec -it manager sh -c "hostname -I")" >> ips

# Создание образа хоста 

echo "${INFO} Building host..."
docker build -f ./docker/Dockerfile -t alsoalgo/host .

# Подъем хоста

echo "${INFO} Starting host..."
docker-compose up -d host

# Получение IP-адреса хоста

# echo "host_ip=$(docker exec -it host sh -c "hostname -I")" >> ips

# Подготовка inventory со списком хостов

# bash -c "python3 ./scripts/prepare_inventory.py"
# rm ips

# Перекладываем в общий volume приложение из manager

docker exec -it manager sh -c "ansible-playbook /ansible/playbooks/put_service.yml" 

# Берем из общего volume приложение в host

docker exec -it host sh -c "ansible-playbook /ansible/playbooks/fetch_service.yml" 

# Запускаем приложение на host, предварительно установив зависимости

docker exec -it host sh -c "ansible-playbook /ansible/playbooks/run_service.yml" 


