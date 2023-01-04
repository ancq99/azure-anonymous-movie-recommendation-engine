import json
from io import BytesIO

import joblib
import pandas as pd
import numpy as np
import datetime

from azure.storage.blob import BlobClient, BlobServiceClient

def init():
    update_model_path()


def update_model_path():
    global model_path
    global last_update
    connectionString = "DefaultEndpointsProtocol=https;AccountName=azuremachinele8230842786;AccountKey=EDDV7/IgWaE06todRtOxj4TEpwFlFMJjAxF47EHvKE+KcwGxO8tDs4jMIOnYI3Q+dONukza4tebe+ASt7z0pmA==;EndpointSuffix=core.windows.net"
    containerName = 'pythoncode'
    outputBlobName = "similarity_df.pkl"

    blob = BlobClient.from_connection_string(conn_str=connectionString, container_name=containerName,
                                             blob_name=outputBlobName)

    model_path = BytesIO()
    blob.download_blob().readinto(model_path)

    last_update = datetime.date.today()


def get_movie_recommendation(title, n_movies_to_recommend=10):
    if last_update != datetime.date.today():
        update_model_path()


    similarity_df = joblib.load(model_path)

    indices = pd.DataFrame({'title': similarity_df.columns, 'index_distance': similarity_df.index}).drop_duplicates(
        keep='last')

    similarity = similarity_df.values

    index = list(indices[indices['title'].isin(title)]['index_distance'])

    sum_index_distance = np.sum(similarity[index], axis=0).reshape(-1,
                                                                   1)  # add distance_between_movies if more than one title

    similarity_scores = list(enumerate(list(sum_index_distance)))  # add distance_index

    similarity_scores = sorted(similarity_scores, key=lambda x: x[1])  # sort by distance_between_movies

    similarity_scores = similarity_scores[0 + len(title):n_movies_to_recommend + len(title)]
    movie_indices = [i[0] for i in similarity_scores]

    return [indices[indices['index_distance'] == index_distance]['title'].values[0] for index_distance in movie_indices]


def run(data):
    data = json.loads(data)
    recc = get_movie_recommendation(data, 10)
    return json.dumps(recc)


# ["Harry Potter and the Order of the Phoenix","Harry Potter and the Half-Blood Prince"]


# [
#   "Harry Potter and the Deathly Hallows: Part 1",
#   "Harry Potter and the Deathly Hallows: Part 2",
#   "Harry Potter and the Goblet of Fire",
#   "Fantastic Beasts and Where to Find Them",
#   "Harry Potter and the Chamber of Secrets",
#   "The Hunger Games: Mockingjay - Part 2",
#   "The Hunger Games: Mockingjay - Part 1",
#   "Pirates of the Caribbean: On Stranger Tides",
#   "The Chronicles of Narnia: The Voyage of the Dawn Treader",
#   "The Chronicles of Narnia: Prince Caspian"
# ]
