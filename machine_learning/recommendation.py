import numpy as np
import pandas as pd


# import scipy.sparse

def recommendation(title: pd.DataFrame):
    data = pd.read_parquet("distance_between_movies.parquet")
    indices = pd.DataFrame({'title': data.columns, 'index_distance': data.index}).drop_duplicates(keep='last')

    similarity = data.values

    index = list(indices[indices['title'].isin(title)]['index_distance'])

    sum_index_distance = np.sum(similarity[index], axis=0).reshape(-1,
                                                                   1)  # add distance_between_movies if more than one title

    similarity_scores = list(enumerate(list(sum_index_distance)))  # add distance_index

    similarity_scores = sorted(similarity_scores, key=lambda x: x[1])  # sort by distance_between_movies

    similarity_scores = similarity_scores[0 + len(title):10 + len(title)]
    movie_indices = [i[0] for i in similarity_scores]

    return [indices[indices['index_distance'] == index_distance]['title'].values[0] for index_distance in movie_indices]


if __name__ == "__main__":
    title = pd.DataFrame({'title': ['Harry Potter and the Order of the Phoenix']})

    data = pd.read_parquet("https://azuremachinele8230842786.blob.core.windows.net/azureml-blobstore-06d4a1a7-8a72-4950-8f78-b26adba1d0f4/UI/2022-11-19_145658_UTC/distance_between_movies.parquet?sp=r&st=2022-11-19T16:10:10Z&se=2022-11-20T00:10:10Z&spr=https&sv=2021-06-08&sr=b&sig=oSY8zu%2Fm2299sCC9FU%2F9oPrpO56CXMtuZWu%2FOb7kDlc%3D")

    print(data)
