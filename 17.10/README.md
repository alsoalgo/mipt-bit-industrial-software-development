# Домашнее задание №2 к 17.10.

## Задание. 
---

Задание состоит из двух частей:
1. Через dockerfile собрать свое рабочее приложение и отправить его в docker-registry
2. Собрать через docker-compose трех или более компонентное приложение (состоящее из более чем одного docker image), где один компонент БД и научить их "общаться" между собой. 

В чате пояснили, что достаточно двух контейнеров:
> Не принципиально.  
> Просто, чтобы у тебя было минимум два контейнера и они там общаются

---

## Часть 1. Push to Docker Registry.

### Описание.

1. Написал простенький `python-app/Dockerfile` для сборки образа с приложением. Рядом положил файл `python-app/src/index.html` для проверки работоспособности сервера.  
    Команда:
    ```docker
    CMD ["python3", "-m", "http.server", "8000"]
    ```  
    запускает сервер на порту 8000, который отдает файлы из текущей директории.
2. Собрал образ командой:
   ```bash
   docker build -t python-app .
   ```  

   > Команды выполняются внутри директории `python-app/`.  

   Таким образом собрался образ `python-app` с тегом `latest`.
   Проверил, что контейнер запускается и отдает файлы из текущей директории:
   ```bash
   docker run -d -p 80:8000 python-app
   ```  
   По адресу `http://localhost:80` открывается список директорий из одного элемента `src/`, при переходе в который открывается `index.html`.
   
3. Запушил образ в docker-registry, предварительно указав, в какой репозиторий:
   ```bash
   docker tag python-app:latest alsoalgo/mipt-bit-isd:part1
   docker push alsoalgo/mipt-bit-isd:part1
   ```
4. Проверил, что репозиторий [создался](https://hub.docker.com/r/alsoalgo/mipt-bit-isd). В списке тегов видно созданный `part1`.


## Часть 2. Docker-compose.

### Описание.

Внутри директории `python-redis-app` я собрал двухкомпонентное приложение, состоящее из двух частей: питоновского приложения `app` и сервера `redis`.

Задача приложения `app`: подняться и сохранить в базу данных `redis` один ключ `k` со значением `Hello, World!`. Затем запросить значение ключа `k` и вывести его в консоль и в файл.

В свою очередь `redis` поднимается и ждет, пока приложение `app` не запишет в нее ключ `k`.

Внутри каждой директории находится `Dockerfile` для сборки образа.  

Внутри директории `app` также находится `requirements.txt` для установки зависимостей для библиотеки `redis`.

`docker-compose.yml` файл описывает два сервиса: `redis` и `app`. В нем описано, что `app` зависит от `redis`, и они оба находятся в общей сети `redis-net`.

### Про скрипты.

#### `run.sh`

Скрипт для запуска приложения. Сначала он создает сеть `redis-net`, затем выполяет `docker compose up -d`. После этого он выводит в консоль логи приложения `app`.

Пример использования: `./run.sh` 
В скрипте дополнительно выводятся логи, поясняющие происходящее.

#### `clean.sh`

Скрипт для очистки. Останавливает контейнеры, удаляет их, удаляет сеть `redis-net`.





