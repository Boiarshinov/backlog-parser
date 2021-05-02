# Backlog Parser

Небольшое приложение, которое помогает мне составлять список для голосования на тему очередной встречи holywar4j.

Приложение работает следующим образом:
- Из [репозитория holywar4j](https://github.com/Boiarshinov/holywar4j) выкачивается бэклог в формате markdown;
- С помощью библиотеки [commonmark-java](https://github.com/commonmark/commonmark-java) таблица с темами преобразуется в абстрактное синтаксическое дерево (АСТ);
- Из АСТ визитором вынимаются грядущие темы и складываются в список POJO;
- Из json-файлика со всеми эмодзи, который я скачал [отсюда](https://gist.github.com/oliveratgithub/0bf11a9aff0d6da7b46f1490f86a71eb/), вытаскиваются все эмодзи с категорией 'Animals & Nature';
- К каждой теме добавляется уникальный эмодзи, чтобы за тему было удобно голосовать в дискорде;
- Список тем выводится в консоль, откуда его можно скопипастить в дискорд.

Написал этот проект на Java 16, чтобы пощупать рекорды.
Заодно попробовал написать HTTP запросы с помощью появившегося в Java 11 HTTP клиента.