# Cel 
Prosta applikacja webowa do rekomendacji filmów.

# Opis działa  projektu 
Użytkownik podaje filmy, które mu się podobałają a nastepnie strona zwraca mu 10 propozycji filmów do obejrzania.


![obraz](https://user-images.githubusercontent.com/66008982/202915524-e50b310a-4e30-461d-9445-f66ef70cb3c4.png)

# Link do filmu


# Opis funkcjanalności 
- Prosta, szybka aplikacja webowa bez logowania. 
- Do rekomendacji wystarczy podać chociaż jeden film. 
- Program działający w chmurze Azura. 

# azure-movie-recommendation-engine

![obraz](https://user-images.githubusercontent.com/66008982/202915524-e50b310a-4e30-461d-9445-f66ef70cb3c4.png)



# Opis projektu 
- Projektu składał się z dwóch cześci z aplikacji webowej oraz modelu. Aplikacja webowa została napisana w kotlinie, nastepnie hostowania w Azure Web app natomiast model został napisyny w Pythonie i udostepniony w Azure Kunernetes Service. Wiecej o modelu rekomendacji filmów można przeczytać w ![link][machine_learning/README.md]


# Wybrane stos technologiczny
- Azure postgres - Baza filmów oraz oceń uzytkowników z IMD.
- Azure machine learning studio - Tworzenie endpointu z modelem rekomendacji. 
- Azure Kubernetes Service - Api modelu rekomendacji.
- Azure Web app - Aplikacja webowa. 






