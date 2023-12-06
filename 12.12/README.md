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

---

## Про скрипты.

### `run.sh`

### `clean.sh`