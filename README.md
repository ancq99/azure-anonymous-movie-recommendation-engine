# Cel
Prosta aplikacja webowa do rekomendacji filmów.

# Skład zespołu
* [Bartłomiej Anczok](https://github.com/ancq99)
* [Kamil Wójcik](https://github.com/Harry29-exe)
* [Andrzej Czechowski](https://github.com/czechoa)
* [Lidia Łachman](https://github.com/LidiaLachman)
* Mateusz Smoliński

# Opis działania projektu
Użytkownik podaje filmy, które mu się podobają, a następnie strona zwraca przynajmniej 10 propozycji filmów do obejrzenia.

# Link do przykładu
[Filmik prezentujący działanie](https://www.youtube.com/watch?v=BDy3EB7fzNY)

[Poradnik jak odtworzyć projekt](https://www.youtube.com/watch?v=dvQsflhWTao)

# Opis funkcjonalności
- Prosta, szybka aplikacja webowa bez logowania.
- Do rekomendacji wystarczy podać chociaż jeden film.
- Program działający w chmurze Azura.
- Model jest aktualizowany na podstawie danych otrzymywanych od użytkowników
- Ładowanie danych poprzez piepeline w ADF


# Diagram
![diagram](https://user-images.githubusercontent.com/66008982/211417620-7f17c634-a60a-4956-8dcc-1f62b3f0e444.png)


# Opis projektu
Projektu składał się z trzech części:
- załadowanie filmów do bazy [data_loading/README](data_loading/README.md)
- aplikacji webowej [movie-re-app/README](movie-re-app/README.md)
- modelu rekomendacji  [machine-learning/README](machine-learning/README.md)


# Wybrane stos technologiczny
- Azure Data Factory - Ładowanie danych do bazu oraz schedule job do update'u modelu
- Azure Load Balancer - równe rozdzielenie ruchu przychodzącego na klastry w ramach scale set
- Azure PostgreSQL - Baza filmów oraz ocen uzytkowników z IMDB oraz MovieLens.
- Microsoft Azure Machine Learning Studio - Tworzenie modelu oraz endpointu do rekomendacji.
- Azure Kubernetes Service - Api modelu rekomendacji.
- Azure Web App  - Aplikacja webowa.
