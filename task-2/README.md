# 3. Stream API Task

### Deadline: Monday, March 01, 2019 23:59

## Теория

[Презентация](https://drive.google.com/open?id=1u85VX2Lb1DkiYW3sdsjlved_Sod7nFOI)

[Лекция](https://drive.google.com/open?id=1QHZQ9PvrI0EEKMBCvR91vW-hhpsdt54u)

## Задание

[Ссылка](https://drive.google.com/open?id=1wMXgf9tbaooT7GO8EvEvZmVzxIMvjDrT3irr8FtMn2g)

### Ограничения
    - Должно работать сразу при выкачивания проекта
    - Нельзя использовать Guava: Google Core Libraries for Java
    - Нельзя использовать Spring/Strats/Micronaut и другие фреймворки
    - Можно гуглить возникшие проблемы и пользоваться StackOverflow
    - Нельзя изменять проект после дедлайна
    
### Как сдавать
    - Написанный проект должен успешно пройти фазу package в Maven.
    - В вашем проекте должен быть бранч 'jar' в нем должен находиться собранный проект ( только jar файл )
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

## Кто выполянет проверку задания?
1) maven-plugin
2) JUnit tests
3) Spock tests