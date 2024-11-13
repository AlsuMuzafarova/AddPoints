package Poem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PoemList {

    public static void main(String[] args) {
        // Список стихов
        List<String> poems = new ArrayList<>(List.of(
                "Зимний вечер. Вьюга злая",
                "Листья желтые летят",
                "Снег кружится, ветер свищет",
                "В небесах плывут облака",
                "Солнце светит, небо синее",
                "Дождь стучит по крыше дома",
                "Ветер шепчет в поле травам",
                "Речка течет, журчит, играет",
                "Ночь наступила, звезды светят",
                "Месяц в небе, как фонарь",
                "Тихо в лесу, птички спят",
                "Утро настало, солнце встало",
                "Дни летят, как птицы в небе",
                "Весна пришла, цветы расцвели",
                "Лето жаркое, солнце палит",
                "Осень золотая, листья летят",
                "Зима пришла, все замело",
                "Мир вокруг, такой прекрасный",
                "Счастье есть, его найди",
                "Жизнь прекрасна, живи, люби"
        ));

        // Поиск по фразе
        String searchPhrase = "снег";
        List<String> foundPoems = poems.stream()
                .filter(poem -> poem.toLowerCase().contains(searchPhrase.toLowerCase()))
                .collect(Collectors.toList());
        System.out.println("Стихи, содержащие фразу '" + searchPhrase + "':");
        foundPoems.forEach(System.out::println);

        // Вывод на печать в порядке возрастания количества символов
        System.out.println("\nСтихи в порядке возрастания количества символов:");
        poems.stream()
                .sorted(Comparator.comparingInt(String::length))
                .forEach(System.out::println);
    }
}


