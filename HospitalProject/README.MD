# Проект записи пациентов на приём
Проект реализованный с помощью Rest и Soap сервисов с помощью Soap реализован
сервис создания записей на указанную дату.
# Soap сервис
Взаимодействие идет путём отправки Xml типа:
![Схема отправки запроса](https://github.com/victorstruve/library-spring-course/blob/master/HospitalProject/src/main/resources/Screenshots/XMLRequest.png)

На адрес `/ws/schedule.wsdl`
После отправки формируются записи в бд по переданным правилам
# Rest сервис
Взаимодействие идёт путём отправки Html запросов:

`/doctors`
Get запрос возвращает список врачей с их id и ФИО.

`/patients`
Get запрос возвращает список пациентов с их id, ФИО и датой рождения.

`/patients/{id}`
Get запрос возвращает данные о переданном по Id в запросе пациенте и записи, 
которые есть у данного пациента.

`/patients/add`
Post запрос, тело запроса состоит из обязательных полей: `fullName` и `dateOfBirth`,
после отправки добавляет объект в БД.

`/doctors/add`
Post запрос, тело запроса содержит обязательное поле `fullName`, после отправки добавляет
объект в БД

`/patients/checkById`
Post запрос, тело запроса содержит обязательные поля: `id`, `year`, `month`, `day`,
после отправки возвращает список талонов доступных для записи
![Схема отправки запроса](https://github.com/victorstruve/library-spring-course/tree/master/HospitalProject/src/main/resources/Screenshots/checkById.png)

`patients/getSlots`
Post запрос, тело запроса содержит обязательное поле `patientId`, после отправки
возвращает все талоны занятые переданным пациентом
![Схема отправки запроса](https://github.com/victorstruve/library-spring-course/tree/master/HospitalProject/src/main/resources/Screenshots/getSlots.png)

`patient/{id}/selectDoctor`
Put запрос, тело запроса состоит из обязательных полей `doctorId` - id врача
и `recordId` - id талона на который требуется записать пациента, после отпраки объеденяет
объекты доктор и пациент по переданному талону.
