package dev.boiarshinov.backlog.parser;

import dev.boiarshinov.backlog.parser.model.Variant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BacklogParserTest {

    private BacklogParser backlogParser;

    @BeforeEach
    void setUp() {
        backlogParser = new BacklogParser();
    }

    @Test
    void parseAndGetVotingVariants() {
        String inputString = """
            ---
            title: "Бэклог"
            draft: false
            ---
                        
            Список тем для обсуждения. \s
            Выбираем тему с помощью голосования за несколько дней до встречи.
                        
            ## Актуальный бэклог
                        
            | Тип          | Тема       | Хост |
            |--------------|------------|---------|
            | :two_hearts: | Способы создания DTO для Unit-тестов | Артём |
            | :cyclone:    | Как тестировать взаимодействие двух сервисов и быть уверенным в обратной совместимости | |
            | :fire:       | Maven или Gradle | Рома |
            | :mortar_board: | Деплой веб-приложения на своей vps (docker / docker compose / https / subdomain by instance) | Катя |
            | :mortar_board: | Api Management - когда это нужно и как работает | Катя |
            | :two_hearts: | CD / CI - автоматизация проверки и сборки кода (Github actions, Gitlab) | Катя |
                        
            ### Легенда
            - :fire: `holywar` - жаркое обсуждение и ломание копий
            - :two_hearts: `sharing` - озвучивание своего опыта каждым из участников
            - :cyclone: `brainstorm` - совместное обсуждение, в ходе которого пытаемся найти лучшее решение какой-либо проблемы
            - :mortar_board: `lecture` - рассказ о чем-либо одним из участников
            - :eyes: `code review` - смотрим на код и ревьювим. Возможно написание кода в онлайне
                        
            ---
                        
            ## Скамейка запасных
            Эти темы были в бэклоге, но к ним не проявили интерес :weary:. \s
            У них есть шанс на возвращение, если бэклог опустеет.
                        
            - :fire: Способы конфигурации бинов в спринге. Хост - Артём
            - :two_hearts: Event Driven Design внутри одного приложения. Хост - Артём\s
            - :fire: Библиотеки для конвертации DTO (MapStruct и другие) или обычные механизмы Java\s
            - :fire: Способы построения динамических запросов: QueryDSL, Criteria API, Spring Specification, JOOQ. Хост - Артём
            - :two_hearts: Применение паттернов проектирования. Часть 2. Хост - Рома
            - :eyes: Выполнение тестового задания в онлайне. Хост - Женя\s
            - :two_hearts: Презентация своих pet-проектов\s
            - :mortar_board: Из Java разработки в GameDev. Хост - Рома
            - :eyes: Мини демка фронт разработки - Angular & TypeScript на реальном тикете. Хост - Катя
            """;

        final List<Variant> expectedResult = List.of(
            new Variant("Способы создания DTO для Unit-тестов", "Артём", Variant.Type.SHARING),
            new Variant("Как тестировать взаимодействие двух сервисов и быть уверенным в обратной совместимости", "", Variant.Type.BRAINSTORM),
            new Variant("Maven или Gradle", "Рома", Variant.Type.HOLYWAR),
            new Variant("Деплой веб-приложения на своей vps (docker / docker compose / https / subdomain by instance)", "Катя", Variant.Type.LECTURE),
            new Variant("Api Management - когда это нужно и как работает", "Катя", Variant.Type.LECTURE),
            new Variant("CD / CI - автоматизация проверки и сборки кода (Github actions, Gitlab)", "Катя", Variant.Type.SHARING)
        );

        final List<Variant> actualResult = backlogParser.parseAndGetVotingVariants(inputString);

        assertEquals(expectedResult, actualResult);
    }

}