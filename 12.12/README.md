# Домашнее задание №4 к 12.12.

## Задание. 
---

Взять приложение из п.3 (или любое другое), автоматизировать его сборку в Jenkins (pipeline и freestyle Job) на событие pull-request/push.
В pipeline должны входить:
- сборка приложения (maven, другой сборщик)
- запуск автотестов (unit в зависимости от проекта, postman)
- сборка результатов работы тестов в allure и отброска в Jenkins
- анализ исходного кода Sonar (в том числе необходимо исправить все ошибки и (добиться не менее 90% покрытия кода тестами)* зависит от проекта)
- деплой приложения через Ansible (из лаб №2) или сборка контейнера (т.е. отказ от ансибл)

Для сдачи лабы прислать код pipeline (скриншоты) или продемонстрировать экран с работой джобов
---

## Решение.

С помощью `docker-compose up -d` поднял локально инстансы Jenkins и Sonarqube, настроил каждый.

Поставил необходимые плагины - Docker, Allure, Sonarqube.
![jenkins_installed_plugins](./images/jenkins_installed_plugins.jpg)

В директории `app/` лежит приложение, которое собиралось.

Настройки Docker в Jenkins.
![settings_docker](./images/settings_docker.jpg)

Настройки Maven в Jenkins.
![settings_maven](./images/settings_maven.jpg)

Настройки Sonarqube Scanner в Jenkins.
![settings_sonarqube_scanner](./images/settings_sonarqube_scanner.jpg)

Настройки Sonarqube в Jenkins.
IP-адрес получил с помощью команды `ifconfig`
![settings_sonarqube](./images/settings_sonarqube.jpg)

Авторизационные токены в Sonarqube.
![sonarqube_auth_tokens](./images/sonarqube_auth_tokens.jpg)

Авторизационные токены в Jenkins.
![jenkins_auth_tokens](./images/jenkins_auth_tokens.jpg)

Про сам пайплайн.  
Настройка git.
![pipeline_git_settings](./images/pipeline_git_settings.jpg)

Настройка webhook'а и Sonarqube (для этого создал авторизационный токен в Sonarqube, добавил в Credentials в Jenkins).
![pipeline_webhook_and_sonarqube_settings](./images/pipeline_webhook_and_sonarqube_settings.jpg)  

Шаги Maven.
![pipeline_maven_steps](./images/pipeline_maven_steps.jpg)

Шаг Docker.
![docker_step](./images/docker_step.jpg)

Шаг с репортом Allure.
![pipeline_allure_step](./images/pipeline_allure_step.jpg)

Главная страница Pipeline выглядит так.
![pipeline_main_page](./images/pipeline_main_page.jpg)

Вот добавленный hook в github.
![github_webhook_page](./images/github_webhook_page.jpg)

Создал hook с помощью утилиты ngrok (чтобы открыть локальный порт, сделать его публичным `ngrok http 8081`).
![ngrok](./images/ngrok.jpg)

Страница Sonarqube с покрытием 90% по проекту.
![sonarqube_main_page](./images/sonarqube_main_page.jpg)

Страницы Allure, на которых видны Unit тесты и Suits.
![allure_main_page](./images/allure_main_page.jpg)
![allure_suits_page](./images/allure_suits_page.jpg)