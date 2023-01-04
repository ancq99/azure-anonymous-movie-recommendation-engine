# Cel
Prosta aplikacja webowa do rekomendacji filmów.

# Skład zespołu
* [Bartłomiej Anczok](https://github.com/ancq99)
* [Kamil Wójcik](https://github.com/Harry29-exe)
* [Andrzej Czechowski](https://github.com/czechoa)
* [Lidia Łachman](https://github.com/LidiaLachman)


# Opis działania projektu
Użytkownik podaje filmy, które mu się podobają, a następnie strona zwraca przynajmniej 10 propozycji filmów do obejrzenia.

# Link do przykładu
[Filmik prezentujący](https://www.youtube.com/watch?v=BDy3EB7fzNY)


# Opis funkcjonalności
- Prosta, szybka aplikacja webowa bez logowania.
- Do rekomendacji wystarczy podać chociaż jeden film.
- Program działający w chmurze Azura.


# Diagram
![diagram](https://user-images.githubusercontent.com/66008982/210626950-01f4c642-5600-477b-8acc-e4d4c726be78.svg)

# Opis projektu
Projektu składał się z trzech części:
- załadowanie filmów do bazy [data_loading/README](data_loading/README.md)
- aplikacji webowej [movie-re-app/README](movie-re-app/README.md)
- modelu rekomendacji  [machine-learning/README](machine-learning/README.md)


# Wybrane stos technologiczny
- Azure PostgreSQL - Baza filmów oraz ocen uzytkowników z IMDB oraz MovieLens.
- Microsoft Azure Machine Learning Studio - Tworzenie modelu oraz endpointu do rekomendacji.
- Azure Kubernetes Service - Api modelu rekomendacji.
- Azure Web App  - Aplikacja webowa.
