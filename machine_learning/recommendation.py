import numpy as np
import pandas as pd


def recommendation(title: list, similarity, indices):
    index = list(indices[indices['title'].isin(title)]['index_distance'])

    sum_index_distance = np.sum(similarity[index], axis=0).reshape(-1,
                                                                   1)  # add distance_between_movies if more than one title

    similarity_scores = list(enumerate(list(sum_index_distance)))  # add distance_index

    similarity_scores = sorted(similarity_scores, key=lambda x: x[1])  # sort by distance_between_movies

    similarity_scores = similarity_scores[0 + len(title):10 + len(title)]
    movie_indices = [i[0] for i in similarity_scores]

    return [indices[indices['index_distance'] == index_distance]['title'].values[0] for index_distance in movie_indices]


def load_distance_between_movies():
    similarity = np.load("distance_between_movies.npy", )

    indices = pd.read_csv("movies_indices.csv", )
    indices.columns = ['title', 'index_distance']
    return similarity, indices


if __name__ == "__main__":
    title = ['Harry Potter and the Order of the Phoenix']
    similarity, indices = load_distance_between_movies()
    print(recommendation(title, similarity, indices))

    title = ['Harry Potter and the Order of the Phoenix', 'Harry Potter and the Half-Blood Prince']
    print(recommendation(title, similarity, indices))
