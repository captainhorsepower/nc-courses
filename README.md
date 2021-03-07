# 2. OOP Task

### Deadline: Monday, March 20, 2019 23:59

## Теория

[Презентация](https://drive.google.com/file/d/1t77KNhQxrGuzfG614nvrxGR5CFqri7F1/view?usp=sharing)

[Лекция](https://drive.google.com/file/d/1DKL4AzEFw7zUUzM4iaEzDByAUcHXWruF/view?usp=sharing)

## Задание

[Ссылка](https://docs.google.com/document/d/1vnPV3pof5MnVAL2PiHSfCMnZOWq8SKVE5x4Texdd_r0/edit?usp=sharing)

### Описание задачи

Создать консольное приложение, удовлетворяющее следующим требованиям:

- Использовать возможности ООП: классы, наследование, полиморфизм, инкапсуляция.
- Каждый класс должен иметь исчерпывающее смысл название и информативный состав.
- Наследование должно применяться только тогда, когда это имеет смысл.
- При кодировании должны быть использованы соглашения об оформлении кода java code convention.
- Классы должны быть грамотно разложены по пакетам.
- Работа с консолью или консольное меню должно быть минимальным.(вложенность не более 3)
- Для хранения параметров инициализации можно использовать файлы.
- Определить для набора объектов несколько видов сортировок по отдельным характеристикам экземпляра.
- Определить поиск в заданном диапазоне по различным характеристикам.
- Создать методы вычисления значений (сумм значений) различных параметров.

### Ограничения
    - Должно работать сразу при выкачивания проекта
    - Нельзя использовать Guava: Google Core Libraries for Java
    - Нельзя использовать Java 8 Stream API
    - Нельзя использовать Spring/Strats/Micronaut и другие фреймворки
    - Можно гуглить возникшие проблемы и пользоваться StackOverflow
    - Нельзя изменять проект после дедлайна
    
### Как сдавать
    - Написанный проект должен успешно пройти фазу package в Maven.
    - В вашем проекте должен быть бранч 'jar' в нем должен находиться собранный проект ( только jar файл )
    - Если выше пункты выполенны, вы скидываете ментору ссылку на github c заданием.
    - Рефакторить и коммитать исправления до дедлайна.
    
### Требования к репозиторию: 
1. Вам необходимо сделать `fork` проекта.
2. Весь код должен находиться в бранче `master`.

### Требования к коммитам
- Минимум 5 коммитов
- Названия коммитов должны быть согласно гайдлайна - https://www.conventionalcommits.org/en/v1.0.0-beta.2/ 

Основные требования:
```
  * Allowed Types:
    * feat: - *a new feature*
    * refactor: - *a code change that neither fixes a bug nor adds a feature*
    * fix: - *a bug fix*
    * perf: - *a code change that improves performance*
    * style: - *сhanges that do not affect the meaning of the code (white-space, formatting, missing semi-colons, etc)*
    * docs: - *documentation only changes*
    * ...
  * Use the present tense ("add feature" not "added feature")
  * Use the imperative mood ("move cursor to..." not "moves cursor to...")
  * Limit the first line to 72 characters or less
```

### Критерий оценки:
- Сборка проекта прошла успешна
- Ваш ментор вышлет вам отзыв после проверки

## Кто выполянет проверку задания?
1) maven-plugin
2) Ваш метор
