# Tworzenie modelu

W tym celu należy uruchomić notebook model. W wyniku jego działania zostanie utworzony plik pickle, oraz w Azure powstanie nowy eksperyment, środowisko oraz model. 

W notatniku należy zmienić dwie wartości:
* paramerty połaczenia do bazy danych 
* oraz run_id do odczytania w Azure (ML Studio -> Jobs -> Output and logs -> model -> MLModel)

W przypadku, gdy nie chcemy uczyc całego modelu od zera, możemy załadować plik pickle, dostępny do pobrania [tutaj](https://drive.google.com/file/d/1g8IKrn-Cu8QdE9GbggBJZQ-iHeQLQKu8/view?usp=sharing). 
Załadopwać go do notatnika i przejść od razu do wdrożenia. 

# Tworzenie Endpointu 
W MLStudio tworzymy nowy AKS o pamięci conajmnioej 24Gb. 

![obraz](https://user-images.githubusercontent.com/66008982/202914257-090f4f18-1483-4c38-9236-fae8fb5d1342.png)


Następnie przechodzimy do zakładki Models, wybiermay stworzony model (model_rec) i klikamy Deploy.

![obraz](https://user-images.githubusercontent.com/66008982/202914226-5a7ba8bc-f38f-4c97-a336-c8d77feed129.png)


Wybieramy Deploy Web Service, uzupełniamy pola (Entry Script -> azure/score.py, Conda dependencies -> azure/conda.yaml) i klikamy Deploy. 

![obraz](https://user-images.githubusercontent.com/66008982/202914394-9d21d227-525b-4685-b958-e969064b12b2.png)



Po zakończeniu wdrożenia, szczególy endpointu możemy znaleźć w zakłądce Endpoints. 



# Przykład danych
* Wejście - filmy które nam się podobają w formacie -> ["title1", "title2", "title3", ...]
* Wyjście - rekomendowane filmy dla tytułów w formacie -> ["title1", "title2", "title3", ...]

