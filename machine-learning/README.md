# Opis 

Naszym pierwotnym pomysłem było zostosowanie modelu Wide and Deep, jednak mieliśmy z nim sporo problemów. Dwa główne problmey to: bardzo słaba dokumentacja oraz zwracanie w wyniku predykcji ciągle tych samych filmów, niezależnie od danych na wejsciu. 

W związku z tym, postawiliśmy na prostsze roziązanie, a mianiownicie policzenie odległości miedzy filmami uzywajać odległości manhattan. 
Jako vector cech zastowaliśmy jedynie opinie użytkowników, którzy ocenili film na 5 gwiazdek oraz ekipę oraz obsade wystepująca w filmie. 

# Tworzenie modelu

W tym celu należy uruchomić notebook model. W wyniku jego działania zostanie utworzony plik pickle, który zostanie zarestrowany Azure Machine Learnig Studio.

W przypadku, gdy nie chcemy uczyc całego modelu od zera, możemy załadować plik pickle (similarity_df.pkl)
Załadopwać go do notatnika i przejść od razu do rejestracji. 

# Tworzenie Endpointu 
W MLStudio tworzymy nowy ASK. 

![askcompute](https://user-images.githubusercontent.com/57688356/210434701-50b86d27-6cfc-4edc-b96b-c2b4e6869f42.png)

Wybieramy maszyne.

![endpoint_azure](https://user-images.githubusercontent.com/57688356/210434264-c03b3b8f-4c83-4cc9-8d9f-10c0b28d7fd5.png)


Następnie przechodzimy do zakładki Models, wybiermay stworzony model (model_rec) i klikamy Deploy.

![obraz](https://user-images.githubusercontent.com/66008982/202914226-5a7ba8bc-f38f-4c97-a336-c8d77feed129.png)


Wybieramy Deploy Web Service, uzupełniamy pola (Entry Script -> azure/score.py, Conda dependencies -> azure/conda.yaml) i klikamy Deploy. 

![obraz](https://user-images.githubusercontent.com/66008982/202914394-9d21d227-525b-4685-b958-e969064b12b2.png)


Po zakończeniu wdrożenia, szczególy endpointu możemy znaleźć w zakłądce Endpoints. 


# Przykład danych
* Wejście - filmy które nam się podobają w formacie -> ["title1", "title2", "title3", ...]
* Wyjście - rekomendowane filmy dla tytułów w formacie -> ["title1", "title2", "title3", ...]

