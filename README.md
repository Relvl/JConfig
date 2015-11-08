JConfig
=======
The configuration system of the application without the need to describe the reading and writing each particular option.  
Система конфигурации приложения, не требующая описывать чтение и запись каждой опции.


Использование:
--------------
- Наследоваться от [`AJconfigBase.class`](https://github.com/Relvl/JConfig/blob/master/src/johnson/jconfig/AJconfigBase.java)
- Определить методы `load()` и `store()`, которые будут вести к, соответственно, `loadInternal(YourConfigClass.class)` и `storeInternal(YourConfigClass.class)`.
- Пометить класс аннотацией [`@JConfigClass`](https://github.com/Relvl/JConfig/blob/master/src/johnson/jconfig/annotations/JConfigClass.java)`(fileName = "your-destionation-file")`.
- Описать нужные вам опции в виде публичных статических не-финальных полей.

>Система принимает так же внутренние публичные статические классы и их содержимое (неограничеснная вложенность).

[Пример готового файла настроек](https://github.com/Relvl/JConfig/blob/master/src-test/johnson/test/TestConfig.java "Ссылка на файл в репозитории"). Больше ни чего писать не придется.

***
Настройка основного класса:
---------------------------
    @JConfigClass(
        fileName = "config/test-config", // Имя файла конфига. К нему будет приписываться соответствующее расширение (.xml, .json) при записи в файл.
        serializeTypes = {EJConfigDomType.XML, EJConfigDomType.JSON}, // Массив типов сериализации.
        isPrettyPrinting = true, // "Человекочитаемый" вывод результата сохранения конфигов.
        target = EJconfigLoadTarget.FILESYSTEM, // Цель загрузки (и сохранения) конфигов. RESOURCES/FILESYSTEM/URL. Сохранение возможно только для цели FILESYSTEM.
        systemOut = true // Попутный вывод в Sytem.out (для отладки).
    )

***
Настройки членов класса:
------------------------
Комментарии будут добавлены над результатом сериализации при записи:

    @JConigComment("Your comment for config") // комментарии будут добавлены при записи над соответствующими полями.

    @JConfigElement(
        value = "SimpleInt", // имя будет использоваться в качестве имени переменной
        valueIsAttribute = true // значение по возможности будет записано как аттрибут (для XML, не работает на внутренних классах)
    )

    @JConfigIgnore // (или делать их transient) они будут игнорироваться системой.
    
    @JConfigCustomSerializer(YourSerializationClass.class) // для сериализации поля/класса будет использоваться описанный вами сериализатор.

***
Сериализация произвольных объектов:
-----------------------------------
{TODO}

***
Использование целей загрузки:
-----------------------------
{TODO}


