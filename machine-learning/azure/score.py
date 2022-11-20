import pandas as pd
import numpy as np
import json
import psycopg2
import joblib
import os
from scipy.sparse import csr_matrix
from sklearn.neighbors import NearestNeighbors

def init():
    global knn
    model_path = os.path.join(os.getenv('AZUREML_MODEL_DIR'), 'model.pkl')
    knn = joblib.load(model_path)
    dbConn()

def dbConn():
    global movies
    global final_dataset
    global csr_data

    conn = psycopg2.connect(
        host="movie-warehouse.postgres.database.azure.com", dbname="warehouse", user="ancq", password="997xXFsu", sslmode="require"
    )
    print("connected to db")
    sql = """
    WITH tmp as 
    (
        SELECT castlistfk as movie_id, STRING_AGG(name, ', ') as cast FROM castlist GROUP BY movie_id
    ),

    tmp2 as (
        SELECT crewlistfk as movie_id, STRING_AGG(name, ', ') as crew FROM crewlist GROUP BY movie_id
    ), 


    tmp3 as (
        SELECT tmp.*, tmp2.crew FROM tmp JOIN tmp2 ON tmp.movie_id = tmp2.movie_id 
    )

    SELECT movies.*, tmp3.cast, tmp3.crew FROM movies JOIN tmp3 USING(movie_id) WHERE numofvotes > 200
    """
    movies = pd.read_sql_query(sql, con=conn)

    print("movies done")
    print(movies.head(5))
    sql = """
    WITH tmp as 
    (
        SELECT userId FROM ratinglist GROUP BY userId HAVING COUNT(*) > 25
    )

    SELECT list.userid, list.rating, list.ratinglistfk as movie_id FROM tmp LEFT JOIN ratinglist as list USING(userId) WHERE ratinglistfk IN (SELECT movie_id FROM movies WHERE numofvotes > 150)
    """

    ratings = pd.read_sql_query(sql, con=conn)
    print("ratings done")
    print(ratings.head(5))
    final_dataset = ratings.pivot(index='movie_id',columns='userid',values='rating')
    final_dataset.fillna(0,inplace=True)
    no_movies_voted = ratings.groupby('userid')['rating'].agg('count')
    no_user_voted = ratings.groupby('movie_id')['rating'].agg('count')
    final_dataset = final_dataset.loc[no_user_voted[no_user_voted > 150].index,:]
    final_dataset=final_dataset.loc[:,no_movies_voted[no_movies_voted > 50].index]
    csr_data = csr_matrix(final_dataset.values)
    final_dataset.reset_index(inplace=True)
    print("final_dataset done")
    print(final_dataset.head(5))

def get_movie_recommendation(movies1, n_movies_to_reccomend=10):
    df_final = pd.DataFrame([], columns=['Title','Distance'])

    for movie_name in movies1:
        movie_list = movies[movies['title'] == movie_name]

        if len(movie_list):        
            movie_idx= movie_list.iloc[0]['movie_id']
            movie_idx = final_dataset[final_dataset['movie_id'] == movie_idx].index[0]
            distances , indices = knn.kneighbors(csr_data[movie_idx],n_neighbors=n_movies_to_reccomend+1)    
            rec_movie_indices = sorted(list(zip(indices.squeeze().tolist(),distances.squeeze().tolist())),key=lambda x: x[1])[:0:-1]
            recommend_frame = []
            for val in rec_movie_indices:
                movie_idx = final_dataset.iloc[val[0]]['movie_id']
                idx = movies[movies['movie_id'] == movie_idx].index
            
                recommend_frame.append({'Title':movies.iloc[idx]['title'].values[0],'Distance':val[1]})
            df = pd.DataFrame(recommend_frame,index=range(1,n_movies_to_reccomend+1))
            df_final= df_final.append(df)
    
    
    df_final = df_final.groupby('Title').mean().reset_index()
    df_final = df_final[~df_final['Title'].isin(movies1)].sort_values(by=['Distance'], ascending=True)
    
    return df_final

def run(data):
    data = json.loads(data)
    recc = get_movie_recommendation(data, 10)
    return json.dumps(recc['Title'].tolist())
