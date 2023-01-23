# Rest Api реализация приложения библиотеки из прошлых проектов
Взаимодействие происходит с помощью отправки следующих запросов:

# 'domen'/people
Get зпрос возвращает список сущностей Person переведенные в PersonDTO из БД из таблицы People.

# 'domen'/people/add
Post запрос добавляет  в БД сущность Person с соответствующими полями
"fullName" типа String и "yearOfBirth" типа int.

# 'domen'/people/{id}
Get запрос возвращает объект класса PersonDTO по id.

# 'domen'/people/{id}/edit
Patch запрос принимает измененные поля класса Person указанные выше.

# 'domen'/people/{personId}/dropBook/{bookId}
Get запрос принимает в строке запроса personId - id объекта Person, и bookId - id объекта класса Book.
После отправки запроса происходит удаление из БД соответствующих связанных сущностей Person и Book.
