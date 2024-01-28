# Моделирование системы массового обслуживания
## Описание
Бизнес-домен представляет собой систему изготовления/обработки и хранения скоропортящихся продуктов на фабрике и продажу их магазину.
| Название    | Описание |
| ----------- | -------- |
| Предметная область  | Изготовление и хранение скоропортящихся продуктов премиум класса на фабрике и ихпродажа |
| Источник | Источником является продукт, который включает тип название продукта, фирма производства, страна производства, вес продуктции, дата изготовления, цена готовой продукции. Перемещение происходит с помощью конвейера на фабрике     |
| Приборы    | Прибором является ЭВМ с сотрудником фабрики, который следит за сроками годности продуктов и отдаёт самую свежую продукцию заказчикам   |
| Буфер | Буфером является морозильная камера где хранится продукция до их продажи |
| Дисциплина постановки вбуфер | В порядке поступления, ни одна из заявок не имеет приоритета |
| Дисциплина отказа | Отказ от самой старой заявки в буфере |
| Дисциплина выбора из буфера | Заявки выбираются по принципу: LIFO(последний пришел первый вышел). Заявки не имеют приоритета выбора |
| Дисциплина постановки на обслуживание | По кольцу; Выбор осуществляется с помощью указателя, который принимает значение номера места, с которого начинается поиск заявок в БП. Как только будет найдено место, занятое заявкой, эта заявка ставится на освободившийся прибор, а указатель передвигается на место, следующее за тем, откуда была выбрана на обслуживание заявка. Поиск следующего занятого места буфера будет производиться также, начиная с указателя. |

## Вариант  2
### Расшифровка
```ИБ  ИЗ2  ПЗ1  Д10З1  Д10О3  Д2П2  Д2Б2  ОР2  ОД2```

#### Источники:
+ ```ИБ``` — бесконечный источник;
+ ```И32``` — равномерный закон распределения заявок;
#### Приборы:
+ ```П31``` — экспоненциальный закон распределения времени обслуживания;
#### Описание дисциплин постановки и выбора
+ Буферизации ```Д10З1```  заполнение буферной памяти «по кольцу»
#### Дисциплина отказа:
+ ```Д10О3``` — самая старая в буфере;
#### Дисциплина постановки на обслуживание:
+ ```Д2П2``` — выбор прибора по кольцу,
+ ```Д2Б2``` — выбор заявки из буфера  с помощью LIFO;
#### Виды отображения результатов работы программной модели:
+ Динамическое отражение результатов (пошаговый режим): ```ОД2``` – формализованная схема модели
#### Tекущее состояние;
+ Отражение результатов после сбора статистики (автоматический режим): ```ОР2``` -  графики по значениям параметров

## Диаграмма последовательности (Sequence diagram)
![sequence_diagram.png](img%2Fsequence_diagram.png)

## Диаграмма классов
![class_diagram.png](img%2Fclass_diagram.png)

## Схема базы данных 
![bd_diagram.png](img%2Fbd_diagram.png)

## Технологический стек
* Java 17
* Spring (Boot, Data)
* JPA (Hibernate ORM)
* Gradle
* Docker-compose
* SQL
* PostgreSQL
* Lombok.
