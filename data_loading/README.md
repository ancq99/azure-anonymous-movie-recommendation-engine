# Instrukcja odtworzenia bazy w Azure

W celu odtworzenia rozwiązania w Azure, mamy dwie możliwości:

 * skonfigurowanie i uruchomienia pakietu SSIS (niezalecne)
 * wykorzystanie pliku dump, zawierającego gotową baze
 * wykorzystnie ADF, opis w filmie [potrzbne dane](https://drive.google.com/file/d/1APADG8QwTOmfsF2Y8wOWMYmWPV9j8ZCZ/view?usp=sharing)
 
### Użycie pliku dump

Plik z dump'em bazy znajduje się tutaj: [dump](https://drive.google.com/file/d/1vgSqVK6JPomM9fUf5Pggo_XdlSYpowXU/view?usp=share_link).

1. Tworzymy zasób PostgreSQL w Azure  (Azure Database for PostgreSQL flexible server)
2. Instalujemy postgres'a na lokalnym komputerze [link](https://www.postgresql.org/download/)
3. PObieramy plik dump
4. Uruchamiamy polecenie w terminalu:
```
pg_restore -v --no-owner --host=mydemoserver.postgres.database.azure.com --port=5432 --username=mylogin --dbname=mypgsqldb azure.dump
```
5. Gotowe. Po wykonaniu polecenia dane zostały załadowe do utworzonej w kroku pierwszym bazy

# Schemat i opis bazy


![diagram_db](https://user-images.githubusercontent.com/66008982/211031793-a02e533e-8037-4760-a8df-8d98a2826826.png)


W bazie znajduje się:
* ponad 27 minlionów ocen
* ponad 26 tysięcy filmów
* ponad 280 tysięcy użykowników 
* ~40 tys. aktorów
* ~52500 reżyserów/scenarzystów

Kilka ciekawostek o zbiorze danych:
* Maksymalna ilość ocen jednego użytkownika w zbiorze to 22 198
* Średnia ilość ocen użytkownika to: 96.69
* Średnia ocena użytkowników to 3.52 (skala do 5)
* Średnia ilość ocen dla każdego filmu to 602.61
* Najwięcej filmów jest z 2014 oraz 2015 roku


# Plik SSIS

### Control Flow
Do załadowania danych użyliśmy pakietów SSIS oraz VS2019. 
![control_flow](https://user-images.githubusercontent.com/66008982/202820701-76fbd437-8531-4a3e-ae48-d753a1361881.png)

W celu poprawnego ponownego załadowania danych, najprostszym rozwiazaniem jest wyczyszczenie całkowite bazy, a następnie jej odtworzenie.
Dzięki temu, nie musimy męczyć się z kluczami w trakcie ładowania.

### Data Flow
![data_flow](https://user-images.githubusercontent.com/66008982/202820754-30fa06ba-cbbe-4254-8c95-caae08fd9239.png)
Głównym komponentem data flow jest customowy skrypt. W nim odbywa się całe dodawnie oraz tworzenie danych. Został on wykorzystany w celu obsługi pliku JSON (brak wsparcia ze storny SSIS). Kod tego komponentu został dodany osobno w repozytorium.
