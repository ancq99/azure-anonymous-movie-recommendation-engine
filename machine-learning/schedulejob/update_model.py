import pickle
import pandas as pd
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.metrics import pairwise_distances
import psycopg2

import numpy as np


from azure.storage.blob import BlobClient

query_sql = """
select *
from movies as mv

         inner join

     (select castlistfk, string_agg(name, ',') as castlist
      from castlist
      GROUP BY castlistfk) as castlist
     on mv.movie_id = castlist.castlistfk

         inner join

     (select crewlistfk, string_agg(name, ',') as crewlist
      from crewlist
      GROUP BY crewlistfk
     ) as crewlistfk
     on mv.movie_id = crewlistfk.crewlistfk

         inner join
     (select ratinglistfk, string_agg(userid::character varying, ',') as useridlist ,count(userid) as number_of_votes
      from ratinglist
      where rating = 5


      group by ratinglistfk
        having count(userid) > 300
     ) as useridlist
     on mv.movie_id = useridlist.ratinglistfk
"""


def select_data_from_db():

    conn = psycopg2.connect("host=movie-warehouse.postgres.database.azure.com port=5432 dbname=warehouse user=read_data_user password=zaq1@WSX sslmode=require")

    data_org = pd.read_sql(query_sql, conn)

    return data_org


def prepare_data_from_db(data_org):
    data = data_org.copy()
    data = data.drop(columns=['castlistfk', 'crewlistfk', 'crewnumber', 'castnumber', 'ratinglistfk'])
    data = data.replace('\\N', '')

    data['genre'] = data[['genre1', 'genre2', 'genre3']].apply(lambda row: ",".join([genre for genre in row if genre]),
                                                               axis=1)

    data['year'] = pd.to_numeric(data['year'], errors='coerce')
    data['runtime'] = pd.to_numeric(data['runtime'], errors='coerce')
    data[['year', 'runtime']] = data[['year', 'runtime']].interpolate(method='linear', limit_direction='forward',
                                                                      axis=0)

    return data


def create_features_by_one_hot_encoding_columns(data):
    count_genre = CountVectorizer(stop_words='english', tokenizer=lambda x: x.split(','), min_df=10)
    vector_genre = count_genre.fit_transform(data['genre'])  # remove first col, because empty (not genre )

    count_cast = CountVectorizer(stop_words='english', tokenizer=lambda x: x.split(','), min_df=3)
    vector_castlist = count_cast.fit_transform(data['castlist'])

    count_crew = CountVectorizer(stop_words='english', tokenizer=lambda x: x.split(','), min_df=3)
    vector_crewlist = count_crew.fit_transform(data['crewlist'])

    count_user = CountVectorizer(stop_words='english', tokenizer=lambda x: x.split(','), min_df=100)
    vector_userlist = count_user.fit_transform(data['useridlist'])

    indices = pd.Series(index=data['title'], data=data.reset_index().index).drop_duplicates(keep='last')
    #
    features = np.hstack(
        [vector_genre.toarray() * 100, vector_crewlist.toarray() * 100, vector_castlist.toarray() * 100,
         vector_userlist.toarray()])

    return features, indices


def calculate_distance_between_movies(features, indices):
    similarity = pairwise_distances(features, n_jobs=-1, metric='manhattan')
    similarity_df = pd.DataFrame(data=similarity, columns=indices.index)
    return similarity_df


def save_distance_between_movies(similarity_df):
    f = open('similarity_df.pkl', 'wb')
    pickle.dump(similarity_df, f)
    f.close()



def save_model_in_blob_file():
    connectionString = "DefaultEndpointsProtocol=https;AccountName=azuremachinele8230842786;AccountKey=EDDV7/IgWaE06todRtOxj4TEpwFlFMJjAxF47EHvKE+KcwGxO8tDs4jMIOnYI3Q+dONukza4tebe+ASt7z0pmA==;EndpointSuffix=core.windows.net"
    containerName = 'pythoncode'
    outputBlobName = "similarity_df.pkl"
    blob = BlobClient.from_connection_string(conn_str=connectionString, container_name=containerName,
                                             blob_name=outputBlobName)

    with open(outputBlobName, "rb") as data:
        # blob.download_blob(data,overwrite=True)
        blob.upload_blob(data,overwrite=True)

def main():
    """Main function of the script."""
    print('Python schedule trigger function processed a request.')

    data_org = select_data_from_db()
    print('Data loaded.')

    data = prepare_data_from_db(data_org)
    data,indices = create_features_by_one_hot_encoding_columns(data)
    similarity_df = calculate_distance_between_movies(data,indices)
    print('Model created.')

    print(similarity_df.head())
    save_distance_between_movies(similarity_df)
    print('Model saved.')

    save_model_in_blob_file()



if __name__ == "__main__":
    main()




