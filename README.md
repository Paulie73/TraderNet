

https://github.com/user-attachments/assets/ff5a04f2-592b-4c24-9a2e-3f71594754f6

Архитектура приложения построена на принципах Clean Architecture

Проект разделен на модули с возможностью масштабирования:

:app - Главный модуль приложения, где находится Application, Activity, Theme

:core - модуль с базовыми модулями такими как:
    
	:common - общий модуль, который встраивается ко всем модулям, которым нужны какие то общие зависимости
	:network - сетевой модуль. В нем содержится низкоуровневый сетевой слой

:data - слой данных для фич. Состоит из data модулей для фич с data репозиториями

:feature - модуль с фиче-модулями. В каждом модуле находятся слой domain и presentation

Зависимости модулей:

:app -> :feature:quotes -> :data:quotes -> :core:network, :core:common

Для добавления новой фичи следует сделать следующее:
1. Добавляем фиче-модуль :feature:new-feature
2. Добавляем data модуль для фичи :data:new-feature. Подключаем сюда по необходимости core модули
3. Устанавливаем зависимости аналогично зависимостям выше

Используемый стэк:

	UI -> Cpmpose
	DI -> Dagger Hilt
	Network -> Ktor + Kotlin Serializer
	Compose Images -> Coil
	Presener architecture -> MVVM + Flow
