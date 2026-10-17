# Reguły modelu i repozytoriów

- Wszystkie ID mają typ `long`. ID `0` oznacza nowy, niezapisany obiekt.
  `add` zwraca zapisany obiekt z dodatnim ID. Należy korzystać z tego wyniku.
  Repozytoria pamięciowe nadają kolejne ID; encje JPA używają sekwencji bazy.
  Jawne dodatnie ID w konstruktorach służą odtwarzaniu danych.
- Klienta identyfikuje `clientId`, a telefon jest zmiennym, unikalnym tekstem
  zawierającym dziewięć cyfr (także zero na początku). Telefon rezerwują również
  klienci archiwalni. Zmiana telefonu odbywa się przez `ClientManager` /
  `ClientRepository.changePhoneNumber`, gdzie sprawdzana jest unikalność.
- `findById` i `findByPhoneNumber` zgłaszają `IllegalArgumentException`, gdy nie
  ma wyniku. Archiwalne obiekty nadal są dostępne do odczytu.
- Cena bazowa i końcowa zlecenia są niezmiennymi wartościami z chwili utworzenia.
  Zmiana cennika nie zmienia wcześniejszych zleceń.
- Zakończone zlecenie można odczytać. Nie można zmienić jego opisu, cen ani
  powiązań, ponownie zakończyć, otworzyć lub usunąć.
- Klienta można zarchiwizować po zakończeniu wszystkich jego zleceń.
  Nie można go przywrócić, usunąć, zmieniać jego danych ani przyjąć dla niego
  nowego zlecenia. Historia pozostaje dostępna po ID klienta.
- Menu nadaje ID automatycznie i pokazuje je po utworzeniu obiektu.

## Uruchamianie i weryfikacja

Projekt wymaga JDK 25 lub nowszego. Testy: `mvn test`.
W VS Code użyj Java Run/Debug dla `pobi.Main`.

`BusinessRulesTest` sprawdza repozytoria pamięciowe, reguły modelu i mappery.
Nie jest to test transakcji, współbieżności ani zapisu do PostgreSQL.

## Istniejąca baza danych

Zmienił się schemat: `clients.client_id` zastępuje telefon jako klucz główny,
`clients.phone_number` ma typ tekstowy z ograniczeniem unikalności,
`services.client_id` wskazuje nowy klucz, a `services.actual_price` zmienia
nazwę na `final_price`. Dodano generowanie ID sekwencjami.
Samo `hibernate.hbm2ddl.auto=update` nie migruje poprawnie starych kluczy i danych.
Przed uruchomieniem na istniejącej bazie potrzebna jest migracja zachowująca
powiązania i synchronizująca sekwencje z obecnymi ID albo osobna, pusta baza
testowa. Ta zmiana kodu nie modyfikuje i nie usuwa istniejącej bazy.
